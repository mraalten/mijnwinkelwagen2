package nl.aalten.mijnwinkelwagen;

import nl.aalten.mijnwinkelwagen.domain.Boodschappenlijst;
import nl.aalten.mijnwinkelwagen.produkten.Repository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/addList")
public class MijnWinkelwagenResource {

    private static Boodschappenlijst boodschappenlijst = new Boodschappenlijst();

    public static Boodschappenlijst getBoodschappenlijst() {
        return boodschappenlijst;
    }
}
