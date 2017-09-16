package nl.aalten.mijnwinkelwagen.application;

import nl.aalten.mijnwinkelwagen.domain.Eenheid;
import nl.aalten.mijnwinkelwagen.domain.Produkt;
import nl.aalten.mijnwinkelwagen.domain.ProduktGroep;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelToSQL {
    public static final String COMMA = ", ";
    private static Map<String, Long> eenheden = new HashMap();
    private static Map<Long, ProduktGroep> produktGroepen = new HashMap();
    private static Map<Long, String> produkten = new HashMap();

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Projects\\MijnWinkelWagen\\src\\main\\resources\\Boodschappen - produkten per produktgroep.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int produktGroepId = 1;
        int produktId = 1;
        ProduktGroep pg = null;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length > 0) {
                String type = parts[0];
                if (type.equals("E")) {
                    // do nothing
                } else if (type.equals("G")) {
                    String produktGroep = parts[2];
                    String sortOrder = parts[3];
                    String imageNaam = parts[4];
                    pg = new ProduktGroep();
                    pg.setNaam(produktGroep);
                    pg.setId(Long.valueOf(produktGroepId));
                    pg.setImageNaam(imageNaam);
                    pg.setSortOrder(Integer.valueOf(sortOrder));
                    produktGroepId++;
                    produktGroepen.put(pg.getId(), pg);
                } else if (type.equals("P")) {
                    String omschrijving = parts[2];
                    String merk = parts[3];
                    String eenheid = parts[4];
                    String imageNaam = parts[5];
                    processProdukt(pg, produktId, omschrijving, merk, imageNaam, eenheid);
                    produktId++;
                }
            }
        }
        toSQL();
    }

    private static void toSQL() {

        for (ProduktGroep produktGroep : produktGroepen.values()) {
            System.out.println("INSERT INTO produktgroep VALUES (" + produktGroep.getId() + COMMA + addQuotes(produktGroep.getNaam()) + COMMA + addQuotes(produktGroep.getImageNaam()) + COMMA + produktGroep.getSortOrder() + ");");
        }
        System.out.println();
        for (ProduktGroep produktGroep : produktGroepen.values()) {
            for (Produkt produkt : produktGroep.getProdukten()) {
                String merk = produkt.getMerk().equals("") ? "null" : addQuotes(produkt.getMerk());
                String eenheid = produkt.getEenheid() == null ? "null" : produkt.getEenheid().name();
                System.out.println("INSERT INTO produkt VALUES (" + produkt.getId() + COMMA + addQuotes(produkt.getNaam()) + COMMA + merk + COMMA + addQuotes(produkt.getImageNaam()) + COMMA + addQuotes(eenheid) + COMMA + produktGroep.getId() + ");");
            }
        }

    }

    private static String addQuotes(String naam) {
        return "\"" + naam + "\"";
    }

    private static void processProdukt(ProduktGroep produktGroep, Integer id, String omschrijving, String merk, String imageNaam, String eenheid) {
        Produkt produkt = new Produkt();
        produkt.setId(new Long(id));
        produkt.setNaam(omschrijving);
        produkt.setMerk(merk);
        produkt.setImageNaam(imageNaam);
        if (StringUtils.isNotEmpty(eenheid)) {
            produkt.setEenheid(Eenheid.toEnum(eenheid));
        }
        produktGroep.addProdukt(produkt);
    }

    private static void processEenheden(String eenheid, String details) {
        eenheden.put(eenheid, 1L);
        String[] detail = details.split(",");

        System.out.println(eenheid);
    }
}
