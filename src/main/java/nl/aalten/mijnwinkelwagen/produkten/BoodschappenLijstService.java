package nl.aalten.mijnwinkelwagen.produkten;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import nl.aalten.mijnwinkelwagen.domain.Item;
import nl.aalten.mijnwinkelwagen.domain.ProduktGroep;
import org.apache.commons.lang.StringUtils;

public class BoodschappenLijstService {
    public static final String SPACE = " ";
    private Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 12);
    private Font FONT_BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);


    /** The resulting PDF file. */
    public static final String fileName
            = "D:\\Temp\\pdf\\boodschappen.pdf";

    /** Definition of two columns */
    public static final float[][] COLUMNS = {
            { 36, 36, 296, 806 } , { 299, 36, 559, 806 }
    };

    public void createPdf(List<Item> items) throws IOException, DocumentException, SQLException {
        Collections.sort(items);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        ColumnText ct = new ColumnText(writer.getDirectContent());
        ProduktGroep currentPg = null;
        for (Item item : items) {
            ct.addText(createProdukt(item, currentPg));
            currentPg = item.getProduct().getProduktGroep();
            ct.addText(Chunk.NEWLINE);
        }
        formatWrittenLines(document, ct);
        document.close();
    }

    private void formatWrittenLines(Document document, ColumnText ct) throws DocumentException {
        ct.setAlignment(Element.ALIGN_JUSTIFIED);
        ct.setExtraParagraphSpace(6);
        ct.setLeading(0, 1.2f);
        ct.setFollowingIndent(27);
        int column = 0;
        int status = ColumnText.START_COLUMN;
        while (ColumnText.hasMoreText(status)) {
            ct.setSimpleColumn(COLUMNS[column][0], COLUMNS[column][1], COLUMNS[column][2], COLUMNS[column][3]);
            ct.setYLine(COLUMNS[column][3]);
            status = ct.go();
            column = Math.abs(column - 1);
            if (column == 0) {
                document.newPage();
            }
        }
        ct.go();
    }

    public Phrase createProdukt(Item item, ProduktGroep currentPg) {
        Phrase p = new Phrase();
        if (currentPg == null ? true : !item.getProduct().getProduktGroep().getId().equals(currentPg.getId())) {
            writeProduktGroep(item, p);
        }
        writeProdukt(item, p);
        p.add(new LineSeparator(0.3f, 100, null, Element.ALIGN_CENTER, -2));
        return p;
    }

    private void writeProdukt(Item item, Phrase p) {
        p.add("   ");
        p.add(new Chunk(item.getProduct().getNaam(), FONT_NORMAL));
        p.add(SPACE);
        if (StringUtils.isNotEmpty(item.getProduct().getMerk())) {
            p.add("(");
            p.add(new Chunk(item.getProduct().getMerk(), FONT_NORMAL));
            p.add(") ");
        }
        p.add(" - ");
        p.add(new Chunk(item.getHoeveelheid().toString(), FONT_NORMAL));
        p.add(SPACE);
        p.add(new Chunk(item.getProduct().getEenheid().getOmschrijving(), FONT_NORMAL));
    }

    private void writeProduktGroep(Item item, Phrase p) {
        p.add(new Chunk(item.getProduct().getProduktGroep().getNaam(), FONT_BOLD));
        p.add(new LineSeparator(0.3f, 100, null, Element.ALIGN_CENTER, -2));
        p.add(Chunk.NEWLINE);
    }

}
