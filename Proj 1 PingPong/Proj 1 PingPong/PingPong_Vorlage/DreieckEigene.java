import ea.edu.DreieckE;
import ea.Raum;
public class DreieckEigene extends DreieckE {

    private boolean sichtbar;
    private String farbe;

    public DreieckEigene() {
        this(100, 100, 200, 200, 300, 100);
    }

    public DreieckEigene(int x1, int y1, int x2, int y2, int x3, int y3) {
        super();
        punkteSetzen(x1, y1, x2, y2, x3, y3);
        farbe = "Rot";
        sichtbar = true;
        farbeSetzen(farbe);
        sichtbarSetzen(sichtbar);
        masseSetzen(100000);
        
    }

    public void setzeFarbe(String farbeNeu) {
        farbe = farbeNeu;
        farbeSetzen(farbe);
    }

    public void setzeSichtbar(boolean sichtbarNeu) {
        sichtbar = sichtbarNeu;
        sichtbarSetzen(sichtbar);
        
    }
    
}
 