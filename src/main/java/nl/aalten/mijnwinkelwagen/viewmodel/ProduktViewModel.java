package nl.aalten.mijnwinkelwagen.viewmodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class ProduktViewModel {

    private Integer produktId;
    private Integer produktGroepId;
    private String naam;
    private String urlAfbeelding;
    private Integer eenheidId;

    public Integer getProduktId() {
        return produktId;
    }

    public Integer getProduktGroepId() {
        return produktGroepId;
    }

    public void setProduktGroepId(Integer produktGroepId) {
        this.produktGroepId = produktGroepId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getUrlAfbeelding() {
        return urlAfbeelding;
    }

    public void setUrlAfbeelding(String urlAfbeelding) {
        this.urlAfbeelding = urlAfbeelding;
    }

    public Integer getEenheidId() {
        return eenheidId;
    }

    public void setEenheidId(Integer eenheidId) {
        this.eenheidId = eenheidId;
    }

    public void setProduktId(Integer produktId) {
        this.produktId = produktId;
    }
}
