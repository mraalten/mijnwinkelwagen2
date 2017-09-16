package nl.aalten.mijnwinkelwagen.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Unit {

    private Long id;
    private String omschrijving;

    public Unit() {
    }

    public Unit(Long id, String omschrijving) {
        this.id = id;
        this.omschrijving = omschrijving;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
