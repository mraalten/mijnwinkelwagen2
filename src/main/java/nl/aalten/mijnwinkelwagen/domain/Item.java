package nl.aalten.mijnwinkelwagen.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "item")
public class Item implements Comparable<Item> {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "produktId")
    private Produkt produkt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boodschappenlijstId")
    @XmlTransient
    private Boodschappenlijst boodschappenlijst;

    private Integer hoeveelheid;

    public Produkt getProdukt() {
        return produkt;
    }

    public Long getProduktGroepId() {
        if (produkt != null && produkt.getProduktGroep() != null) {
            return produkt.getProduktGroep().getId();
        }
        return null;
    }

    public void addUnit() {
        hoeveelheid = hoeveelheid + produkt.getEenheid().getPlusMinHoeveelheid();
    }

    public void subtractUnit() {
        if (!hoeveelheid.equals(produkt.getEenheid().getDefaultHoeveelheid())) {
            hoeveelheid = hoeveelheid - produkt.getEenheid().getPlusMinHoeveelheid();
        }
    }

    public int compareTo(Item anItem) {
        return new Integer(this.produkt.getProduktGroep().getSortOrder()).compareTo(anItem.getProdukt().getProduktGroep().getSortOrder());
    }

    public Long getId() {
        return id;
    }

    public void setHoeveelheid(Integer hoeveelheid) {
        this.hoeveelheid = hoeveelheid;
    }

    public Integer getHoeveelheid() {
        return hoeveelheid;
    }

    public void setProdukt(Produkt product) {
        this.produkt = product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBoodschappenlijst(Boodschappenlijst boodschappenlijst) {
        this.boodschappenlijst = boodschappenlijst;
    }


}
