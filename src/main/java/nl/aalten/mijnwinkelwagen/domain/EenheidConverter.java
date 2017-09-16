package nl.aalten.mijnwinkelwagen.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class EenheidConverter extends XmlAdapter<String, Eenheid> {

    @Override
    public Eenheid unmarshal(String eenheidString) throws Exception {
        return Eenheid.valueOf(eenheidString);
    }

    @Override
    public String marshal(Eenheid eenheid) throws Exception {
        if (eenheid != null) {
            return eenheid.getOmschrijving();
        }
        return "";
    }
}
