package nl.aalten.mijnwinkelwagen.viewmodel;

import nl.aalten.mijnwinkelwagen.domain.Eenheid;
import nl.aalten.mijnwinkelwagen.domain.Produkt;
import nl.aalten.mijnwinkelwagen.domain.ProduktGroep;

public class ModelDomainConverter {

    public Produkt toProdukt(ProduktViewModel viewModel, ProduktGroep produktGroep) {
        Produkt produkt = new Produkt();
        produkt.setNaam(viewModel.getNaam());
//        produkt.setImageNaam(viewModel.getUrlAfbeelding());
        produkt.setEenheid(Eenheid.toEnum(viewModel.getEenheidId()));
        produkt.setProduktGroep(produktGroep);
        return produkt;
    }
}
