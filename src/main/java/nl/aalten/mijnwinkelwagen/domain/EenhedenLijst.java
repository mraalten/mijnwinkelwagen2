package nl.aalten.mijnwinkelwagen.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class EenhedenLijst {

    @XmlElement
    public List<Unit> eenheden = new ArrayList();

    public EenhedenLijst() {
    }

    public EenhedenLijst(Eenheid[] eenheden) {
        for (Eenheid eenheid : eenheden) {
            this.eenheden.add(new Unit(eenheid.getId(), eenheid.getOmschrijving()));
        }
        Collections.sort(this.eenheden, new Comparator<Unit>() {
            public int compare(Unit eenheid1, Unit eenheid2) {
                return eenheid1.getOmschrijving().compareTo(eenheid2.getOmschrijving());
            }
        });
    }
}
