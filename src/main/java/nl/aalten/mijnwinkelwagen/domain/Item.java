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
    private Produkt product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boodschappenlijstId")
    @XmlTransient
    private Boodschappenlijst boodschappenlijst;

    private Integer hoeveelheid;

    public Produkt getProduct() {
        return product;
    }

    public Long getProduktGroepId() {
        if (product != null && product.getProduktGroep() != null) {
            return product.getProduktGroep().getId();
        }
        return null;
    }

    public void addUnit() {
        hoeveelheid = hoeveelheid + product.getEenheid().getPlusMinHoeveelheid();
    }

    public void subtractUnit() {
        if (!hoeveelheid.equals(product.getEenheid().getDefaultHoeveelheid())) {
            hoeveelheid = hoeveelheid - product.getEenheid().getPlusMinHoeveelheid();
        }
    }

    public int compareTo(Item anItem) {
        return new Integer(this.product.getProduktGroep().getSortOrder()).compareTo(anItem.getProduct().getProduktGroep().getSortOrder());
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

    public void setProduct(Produkt product) {
        this.product = product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBoodschappenlijst(Boodschappenlijst boodschappenlijst) {
        this.boodschappenlijst = boodschappenlijst;
    }


}
