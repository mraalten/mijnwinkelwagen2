package nl.aalten.mijnwinkelwagen.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@XmlRootElement
@XmlJavaTypeAdapter(EenheidConverter.class)
public enum Eenheid {
    KILO(1L, "kilo", 1, 1),
    GRAM(2L, "gr.", 300, 100),
    POT_KLEIN(3L, "potje", 1, 1),
    POT_GROOT(4L, "pot", 1, 1),
    STUKS(5L, "st.", 1, 1),
    ZAK(6L, "zak", 1, 1),
    BLIK_KLEIN(7L, "blikje", 1, 1),
    BLIK_GROOT(10L, "blik", 1, 1),
    LITER(8L, "ltr", 1, 1),
    TROS(9L, "tros", 1, 1),
    FLES(11L, "fl.", 1, 1),
    PAK(12L, "pak", 1, 1),
    DOOS(13L, "ds.", 1, 1);

    private Long id;
    private String omschrijving;
    private Integer defaultHoeveelheid;
    private Integer plusMinHoeveelheid;


    private Eenheid(Long id, String omschrijving, Integer defaultHoeveelheid,  Integer plusMinHoeveelheid) {
        this.id = id;
        this.omschrijving = omschrijving;
        this.defaultHoeveelheid = defaultHoeveelheid;
        this.plusMinHoeveelheid = plusMinHoeveelheid;
    }

    public Long getId() {
        return id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Integer getDefaultHoeveelheid() {
        return defaultHoeveelheid;
    }

    public Integer getPlusMinHoeveelheid() {
        return plusMinHoeveelheid;
    }

    public static List<String> asSortedList() {
        List<String> eenheden = new ArrayList();
        for (Eenheid eenheid : Eenheid.values()) {
            eenheden.add(eenheid.getOmschrijving());
        }
        Collections.sort(eenheden, new Comparator<String>() {
            public int compare(String omschrijving1, String omschrijving2) {
                return omschrijving1.compareTo(omschrijving2);
            }
        });
        return eenheden;
    }

    public static Eenheid toEnum(Integer eenheidId) {
        if (eenheidId != null) {
            for (Eenheid eenheid : values()) {
                if (eenheid.getId().intValue() == eenheidId) {
                    return eenheid;
                }
            }
        }
        return null;
    }

    public static Eenheid toEnum(String eenheid) {
        if (eenheid != null) {
            return Eenheid.valueOf(eenheid);
        }
        return null;
    }
}
