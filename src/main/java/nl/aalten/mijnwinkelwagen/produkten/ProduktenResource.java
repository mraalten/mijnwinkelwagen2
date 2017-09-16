package nl.aalten.mijnwinkelwagen.produkten;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.itextpdf.text.DocumentException;
import nl.aalten.mijnwinkelwagen.domain.Boodschappenlijst;
import nl.aalten.mijnwinkelwagen.domain.EenhedenLijst;
import nl.aalten.mijnwinkelwagen.domain.Item;
import nl.aalten.mijnwinkelwagen.domain.Produkt;
import nl.aalten.mijnwinkelwagen.domain.ProduktGroep;
import nl.aalten.mijnwinkelwagen.viewmodel.ModelDomainConverter;
import nl.aalten.mijnwinkelwagen.viewmodel.ProduktViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProduktenResource {

    private static final Logger logger = LoggerFactory.getLogger(ProduktenResource.class);

    private Repository repository = new Repository();
    private BoodschappenLijstService boodschappenLijstService = new BoodschappenLijstService();

    private ModelDomainConverter modelDomainConverter = new ModelDomainConverter();

    public ProduktenResource() {
        logger.info("Application started. ProduktenResource loaded");
    }
    @GET
    @Path("/ophalenproduktgroepen")
    @Produces("application/json")
    public List<ProduktGroep> getProduktGroepen() {
        return repository.getProduktGroepen();
    }

    @GET
    @Path("/ophaleneenheden")
    @Produces("application/json")
    public EenhedenLijst getEenheden() {
        EenhedenLijst eenheden = repository.getEenheden();
        return eenheden;
    }

    @GET
    @Path("/ophalenprodukten")
    @Produces("application/json")
    public List<Produkt> getProdukten(@QueryParam("produktGroepId") Integer produktGroepId) {
        return repository.getProdukten(produktGroepId);
    }

    @GET
    @Path("/ophalenprodukt")
    @Produces("application/json")
    public Produkt getProdukt(@QueryParam("produktId") Integer produktId) {
        return repository.getProdukt(produktId);
    }

    @GET
    @Path("/ophalenboodschappenlijst")
    @Produces("application/json")
    public List<Item> ophalenBoodschappenLijst(@QueryParam("boodschappenLijstId") Integer boodschappenLijstId) {
        logger.info("Boodschappenlijst wordt opgehaald");
        System.out.println("Ophalen boodschappenlijst met id " + boodschappenLijstId);
        try {
            return repository.getBoodschappenLijst(boodschappenLijstId).getItems();
        } catch (Exception exc) {
            exc.printStackTrace();
            logger.info(exc.getMessage());
        }
        return Collections.emptyList();
    }

    @POST
    @Path("/toevoegenproduktWinkelwagen")
    @Consumes("application/json")
    public void toevoegenProdukt(Produkt produkt) {
        repository.toevoegenProduktAanBoodschappenlijst(produkt.getId());
    }

    @POST
    @Path("/opslaanprodukt")
    @Produces("application/json")
    @Consumes("application/json")
    public void opslaanProdukt(ProduktViewModel viewModel) {
        ProduktGroep produktGroep = repository.getProduktGroep(viewModel.getProduktGroepId());
        Produkt produkt = modelDomainConverter.toProdukt(viewModel, produktGroep);
        repository.addNewProdukt(produkt);
    }

    @POST
    @Path("/updateprodukt")
    @Produces("application/json")
    @Consumes("application/json")
    public void updateProdukt(ProduktViewModel viewModel) {
        ProduktGroep produktGroep = repository.getProduktGroep(viewModel.getProduktGroepId());
        Produkt produkt = modelDomainConverter.toProdukt(viewModel, produktGroep);
        repository.updateProdukt(viewModel.getProduktId(), produkt);
    }
    @POST
    @Path("/verwijderitem")
    @Produces("application/json")
    @Consumes("application/json")
    public List<Item> verwijderItem(Item item) {
        return repository.verwijderProduktVanBoodschappenLijst(item.getId());
    }

    @POST
    @Path("/addunit")
    @Produces("application/json")
    @Consumes("application/json")
    public List<Item> addUnit(Item item) {
        return repository.addUnit(item.getId());
    }

    @POST
    @Path("/subtractunit")
    @Produces("application/json")
    @Consumes("application/json")
    public List<Item> subtractUnit(Item item) {
        return repository.subtractUnit(item.getId());
    }

    @GET
    @Path("/wisboodschappenlijst")
    public void wisBoodschappenlijst(@QueryParam("boodschappenLijstId") Integer boodschappenLijstId) {
        repository.removeAllProductsFromList(boodschappenLijstId);
    }

    @GET
    @Path("/printlijst")
    @Produces("application/pdf")
    public Response printLijst() {
        Boodschappenlijst boodschappenlijst = repository.getBoodschappenLijst(1);
        try {
            boodschappenLijstService.createPdf(boodschappenlijst.getItems());
            File file = new File("D:\\Temp\\pdf\\boodschappen.pdf");
            Response.ResponseBuilder response = Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition",
                    "attachment; filename=Boodschappenlijst.pdf");
            return response.build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
