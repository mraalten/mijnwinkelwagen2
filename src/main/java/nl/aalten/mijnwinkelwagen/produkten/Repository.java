package nl.aalten.mijnwinkelwagen.produkten;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import nl.aalten.mijnwinkelwagen.domain.Boodschappenlijst;
import nl.aalten.mijnwinkelwagen.domain.EenhedenLijst;
import nl.aalten.mijnwinkelwagen.domain.Eenheid;
import nl.aalten.mijnwinkelwagen.domain.Item;
import nl.aalten.mijnwinkelwagen.domain.Produkt;
import nl.aalten.mijnwinkelwagen.domain.ProduktGroep;

public class Repository {
    public static final String PERSISTENCE_UNIT_NAME = "mijnwinkelwagen";
    public static final String SQL_FOR_PRODUKTGROEPEN = "SELECT pg from ProduktGroep pg";
    public static final int BOODSCHAPPENLIJST_NR = 1;

    private EntityManager em;

    public Repository() {
        java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, map);
        em = factory.createEntityManager();
    }

    public void toevoegenProduktAanBoodschappenlijst(Long produktId) {
        if (produktId != null) {
            Produkt produkt = getProdukt(produktId.intValue());
            Item item = new Item();
            item.setHoeveelheid(produkt.getEenheid().getDefaultHoeveelheid());
            item.setProduct(produkt);
            Boodschappenlijst boodschappenlijst = getBoodschappenLijst(BOODSCHAPPENLIJST_NR);
            boodschappenlijst.addItem(item);
            storeBoodschappenLijst(boodschappenlijst);
            return;
        }
        throw new RuntimeException("produktId can not be null");
    }

    private void storeBoodschappenLijst(Boodschappenlijst boodschappenlijst) {
        em.getTransaction().begin();
        em.merge(boodschappenlijst);
        em.getTransaction().commit();
    }

    public List<Item> verwijderProduktVanBoodschappenLijst(Long itemId) {
        Boodschappenlijst boodschappenlijst = getBoodschappenLijst(BOODSCHAPPENLIJST_NR);
        if (itemId != null) {
            for (Item item : boodschappenlijst.getItems()) {
                if (item.getId().equals(itemId)) {
                    boodschappenlijst.getItems().remove(item);
                    storeBoodschappenLijst(boodschappenlijst);
                    break;
                }
            }
        }
        return getBoodschappenLijst(BOODSCHAPPENLIJST_NR).getItems();
    }

    public EenhedenLijst getEenheden() {
        return new EenhedenLijst(Eenheid.values());
    }

    public List<ProduktGroep> getProduktGroepen() {
        Query query = em.createQuery(SQL_FOR_PRODUKTGROEPEN);
        List<ProduktGroep> pgs = (List<ProduktGroep>) query.getResultList();
        return pgs;
    }

    public List<Produkt> getProdukten(Integer produktGroepId) {
        Query query = em.createQuery("SELECT produkt from Produkt produkt where produkt.produktGroep.id = :produktGroepId ");
        query.setParameter("produktGroepId", produktGroepId);
        return (List<Produkt>) query.getResultList();
    }

    public Produkt getProdukt(Integer produktId) {
        Query query = em.createQuery("SELECT produkt from Produkt produkt where produkt.id = :produktId");
        query.setParameter("produktId", produktId);
        return (Produkt) query.getSingleResult();
    }

    public Boodschappenlijst getBoodschappenLijst(Integer boodschappenlijst) {
        Query query = em.createQuery("SELECT boodschappenlijst from Boodschappenlijst boodschappenlijst where boodschappenlijst.id = :boodschappenlijst");
        query.setParameter("boodschappenlijst", boodschappenlijst);
        return (Boodschappenlijst) query.getSingleResult();

    }

    public List<Item> addUnit(Long itemId) {
        Boodschappenlijst boodschappenlijst = getBoodschappenLijst(BOODSCHAPPENLIJST_NR);
        boodschappenlijst.addUnit(itemId);
        storeBoodschappenLijst(boodschappenlijst);
        return getBoodschappenLijst(BOODSCHAPPENLIJST_NR).getItems();
    }

    public List<Item> subtractUnit(Long itemId) {
        Boodschappenlijst boodschappenlijst = getBoodschappenLijst(BOODSCHAPPENLIJST_NR);
        boodschappenlijst.subtractUnit(itemId);
        storeBoodschappenLijst(boodschappenlijst);
        return getBoodschappenLijst(BOODSCHAPPENLIJST_NR).getItems();
    }

    public void addNewProdukt(Produkt produkt) {
        em.getTransaction().begin();
        em.persist(produkt);
        em.getTransaction().commit();
    }

    public ProduktGroep getProduktGroep(Integer produktGroepId) {
        Query query = em.createQuery("SELECT produktGroep from ProduktGroep produktGroep where produktGroep.id = :produktGroepId");
        query.setParameter("produktGroepId", produktGroepId);
        return (ProduktGroep) query.getSingleResult();
    }


    public void removeAllProductsFromList(Integer boodschappenLijstId) {
        Boodschappenlijst boodschappenlijst = getBoodschappenLijst(boodschappenLijstId);
        boodschappenlijst.setItems(new ArrayList<Item>());
        storeBoodschappenLijst(boodschappenlijst);
    }

    public void updateProdukt(Integer produktId, Produkt changedProdukt) {
        Produkt produkt = getProdukt(produktId);
        produkt.setNaam(changedProdukt.getNaam());
        produkt.setEenheid(changedProdukt.getEenheid());
        em.getTransaction().begin();
        em.persist(produkt);
        em.getTransaction().commit();
    }
}
