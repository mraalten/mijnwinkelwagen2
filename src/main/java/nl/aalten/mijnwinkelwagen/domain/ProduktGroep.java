package nl.aalten.mijnwinkelwagen.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@Entity
@Table(name = "produktgroep")
public class ProduktGroep {

    @Id
    private Long id;
    private String naam;
    private String imageNaam;
    private int sortOrder;

    @Transient
    private List<Produkt> produkten = new ArrayList();

    public void addProdukt(Produkt produkt) {
        if (produkt != null) {
            produkten.add(produkt);
        }

    }

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

    public String getImageNaam() {
        return imageNaam;
    }

    public void setImageNaam(String imageNaam) {
        this.imageNaam = imageNaam;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<Produkt> getProdukten() {
        return produkten;
    }

    public void setProdukten(List<Produkt> produkten) {
        this.produkten = produkten;
    }
}
