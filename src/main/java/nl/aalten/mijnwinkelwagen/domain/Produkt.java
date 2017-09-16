package nl.aalten.mijnwinkelwagen.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "produkt")
public class Produkt {

    @Id
    private Long id;

    private String naam;
    private String merk;
    private String imageNaam = "geen_afbeelding.jpg";

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "produktGroepId")
    private ProduktGroep produktGroep;

    @XmlTransient
    @Enumerated(EnumType.STRING)
    private Eenheid eenheid;

    @Transient
    private Boolean opBoodschappenLijst;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getImageNaam() {
        return imageNaam;
    }

    public void setImageNaam(String imageNaam) {
        this.imageNaam = imageNaam;
    }

    public ProduktGroep getProduktGroep() {
        return produktGroep;
    }

    public void setProduktGroep(ProduktGroep produktGroep) {
        this.produktGroep = produktGroep;
    }

    public Eenheid getEenheid() {
        return eenheid;
    }

    public void setEenheid(Eenheid eenheid) {
        this.eenheid = eenheid;
    }

    public Boolean isOpBoodschappenLijst() {
        return opBoodschappenLijst;
    }

    public void setOpBoodschappenLijst(Boolean opBoodschappenLijst) {
        this.opBoodschappenLijst = opBoodschappenLijst;
    }
}
