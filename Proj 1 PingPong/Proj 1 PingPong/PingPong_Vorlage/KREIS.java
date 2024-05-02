/** @author     mike ganshorn + michael andonie
 * 
 *  @Version    2013-109-11
 *  
 *  Bei Aenderung der Breite/Hoehe bleibt der Mittelpunkt erhalten
 *  keine Abhaengigkeit mehr zwischen den alpha-Formen
*/

import ea.edu.KreisE;
import ea.Raum;

/**
 * Diese Klasse stellt einen einfachen Kreis dar.
 */
public class KREIS 
extends KreisE {
     
    /**
     * Die Farbe dieses Kreises
     */
    private String farbe;
    
    /**
     * Gibt an, ob dieser Kreis sichtbar ist.
     */
    private boolean sichtbar;
    
    /**
     * Radius dieses Kreises
     */
    private int radius;
    
    /**
     * x-Koordinate des Mittelpunkts
     */
    private int M_x;
    
    /**
     * y-Koordinate des Mittelpunkts
     */
    private int M_y;
    
    
    /**
     * Konstruktor der Klasse <code>KREIS</code>. Erstellt einen neuen Kreis.
     */
    public KREIS() {
        this(50);
    }
    
    /**
     * KREIS Konstruktor
     *
     * @param   rNeu    Der radius des Kreises
     */
    public KREIS(int rNeu) {
        //this.geschwindigkeitHinzunehmen(_Vektor_)
        this.sichtbar = true;
        super.sichtbarSetzen(true);
        this.farbe = "Blau";
        super.farbeSetzen(this.farbe);
        this.radius = rNeu;
        super.radiusSetzen(this.radius);
        this.M_x = 350;
        this.M_y = 350;
        super.mittelpunktSetzen(this.M_x, this.M_y);
        
    }
    
    
     /**
     * Setzt die Farbe dieses Kreises neu.
     * @param   farbeNeu    Diese Farbe erhaelt der Kreis (z.B. "Rot")
     */
    public void setzeFarbe(String farbeNeu) {
        this.farbe = farbeNeu;
        super.farbeSetzen(farbe);
    }
    
    /**
     * Setzt den Mittelpunkt dieses Kreises neu.
     * @param m_x   Die X-Koordinate des Mittelpunktes.
     * @param m_y   Die Y-Koordinate des Mittelpunktes.
     */
    public void setzeMittelpunkt(int m_x, int m_y) {
        this.M_x = m_x;
        this.M_y = m_y;
        super.mittelpunktSetzen(m_x, m_y);
    }
    
    /**
     * Setzt den Radius dieses Kreises neu.
     * @param   radius  Der neue Radius (in Pixel)
     */
    public void setzeRadius(int radius) {
        int x = this.nenneM_x();
        int y = this.nenneM_y();
        this.radius = radius;
        super.radiusSetzen(radius);
        super.mittelpunktSetzen(x, y);
    }
    
    /**
     * Setzt, ob dieser Kreis sichtbar sein soll.
     * @param   sichtbarNeu Ist dieser Wert <code>true</code>, ist nach dem Aufruf dieser Methode dieser Kreis 
     *          sichtbar. Ist dieser Wert <code>false</code>, so ist nach dem Aufruf dieser Methode dieser Kreis unsichtbar.
     */
    public void setzeSichtbar(boolean sichtbarNeu) {
        this.sichtbar = sichtbarNeu;
        super.sichtbarSetzen(sichtbarNeu);
    }
    
    /**
     * Verschiebt diesen Kreis um eine Verschiebung - angegeben durch ein "Delta X" und "Delta Y".
     * @param   deltaX  Der X Anteil dieser Verschiebung. Positive Werte verschieben nach rechts, negative nach links.
     * @param   deltaY  Der Y Anteil dieser Verschiebung. Positive Werte verschieben nach unten, negative nach oben.
     */
    public void verschiebenUm(int deltaX, int deltaY) {
        this.M_x = this.M_x + deltaX;
        this.M_y = this.M_y + deltaY;
        
        this.verschieben(deltaX, deltaY);
        
    }
      
    /**
     * Diese Methode gibt die x-Koordinate des Mittelpunkts dieses Kreises zurueck
     * @return  Die aktuelle x-Koordinate des Mittelpunktes dieses Kreises
     */
    public int nenneM_x()
    {
        return this.M_x;
    }
    
    /**
     * Diese Methode gibt die y-Koordinate des Mittelpunkts dieses Kreises zurueck
     * @return  Die aktuelle y-Koordinate des Mittelpunktes dieses Kreises
     */
    public int nenneM_y()
    {
        return this.M_y;
    }
    
    /**
     * Diese Methode gibt den Radius dieses Kreises zurueck
     * @return  Der aktuelle Radius dieses Kreises
     */
    public int nenneRadius()
    {
        return this.radius;
    }
    
    /**
     * Diese Methode gibt die Farbe dieses Kreises zurueck
     * @return  Die aktuelle Farbe dieses Kreises
     */
    public String nenneFarbe()
    {
        return this.farbe;
    }
    
    /**
     * Diese Methode gibt die Sichtbarkeit dieses Kreises zurueck
     * @return  Die aktuelle Sichtbarkeit dieses Kreises
     */
    public boolean nenneSichtbar()
    {
        return this.sichtbar;
    }
    
    
    
    
    
    /**
     * Diese Methode prueft, wie weit der Mittelpunkt dieses Rechtecks vom Mittelpunkt 
     * eines anderen Grfik-Objekts in x-Richtung entfernt ist.
     * @param   grafikObjekt    Das andere Grafik-Objekt
     * @return  Abstand (in Pixeln) dieses Rechtecks vom anderen Grafik-Objekt in x-Richtung (>0, wenn dieses Rechteck rechts des anderen Grafik-Objekts liegt)
     */
    public int berechneAbstandX(Raum grafikObjekt)
    {
        return this.M_x - grafikObjekt.mittelPunkt().x();
    }
    
    /**
     * Diese Methode prueft, wie weit der Mittelpunkt dieses Kreises vom Mittelpunkt 
     * eines anderen Grfik-Objekts in y-Richtung entfernt ist.
     * @param   grafikObjekt    Das andere Grafik-Objekt
     * @return  Abstand (in Pixeln) dieses Kreises vom anderen Grafik-Objekt in y-Richtung (>0, wenn dieser Kreis unterhalb des anderen Grafik-Objekts liegt)
     */
    public int berechneAbstandY(Raum grafikObjekt)
    {
        return this.M_y - grafikObjekt.mittelPunkt().y();
    }
        
}