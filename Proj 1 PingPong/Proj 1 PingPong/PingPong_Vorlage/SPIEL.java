
/*
 * Engine Alpha ist eine anfaengerorientierte 2D-Gaming Engine.
 *
 * Copyright (C) 2011  Michael Andonie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @version     2014-02-29
 */
import java.awt.geom.Point2D;

import ea.*;
import ea.edu.*;
//import ea.actor;
//import ea.edu.EduActor;
import java.awt.geom.Point2D;
/**
 * Die Klasse SPIEL ist ein Template, das so wie es ist an Schueler ausgegeben werden kann.
 * (Einzige Voraussetzung ist, dass die engine-alpha-Bibliothek im Suchpfad erreichbar ist.)
 * Es startet alles Notwendige fuer ein Spiel.
 * 
 * Beim Konstruktor ohne Parameter gibt es keine Punkte-Anzeigen und auch keine Maus-Interaktion.
 * Die Methoden tick() und tasteReagieren() werden immer automatisch aufgerufen.
 * 
 * Beim Konstruktor mit Parametern koennen die linken und rechten Punkte-Anzeigen sowie die 
 * Interaktion mit der Maus ein- bzw. ausgeschaltet werden.
 * 
 * @author Andonie  (nach Idee von Bendikt Lindemann und Mike Ganshorn)
 * 
 */
public class SPIEL  
{
    /**
     * Die Anzeige des Spiels.
     */
    private AnzeigeE anzeige;
    
    /**
     * Dieser Zaehler ermoeglicht den Tik-Tak-Wechsel.
     */
    private int zaehler;
    
    
    
    /**
     * Erstellt ein Spiel. Startet die Anzeige.
     * 
     * @param   punkteLinks     ist dieser Wert <code>true</code>, so sieht man
     *                          links eine Punkteanzeige. Ist er <code>false</code>
     *                          sieht man keine.
     *                          
     * @param   punkteRechts    ist dieser Wert <code>true</code>, so sieht man
     *                          rechts eine Punkteanzeige. Ist er <code>false</code>
     *                          sieht man keine.
     *                          
     * @param   maus            ist dieser Wert <code>true</code>, wird eine Maus im
     *                          Spiel angezeigt und verwendet. Ist er
     *                          <code>false</code>, gibt es keine Maus.
     */
    Ball ball;
    CollisionDetection  collisiondetection;
    SpielFeld spielfeld;
    public SPIEL(int breite, int hoehe, boolean punkteLinks, boolean punkteRechts, boolean maus) {
        
        //Zaehler fuer Tick, Tack, ...
        zaehler = 0;
        anzeige = new AnzeigeE(breite, hoehe);
        
        ball = new Ball();
        spielfeld = new SpielFeld();
        
        
        collisiondetection= new CollisionDetection();
        
        //Punkteanzeige
        anzeige.punkteLinksSichtbarSetzen(punkteLinks);
        anzeige.punkteRechtsSichtbarSetzen(punkteRechts);
        
        //Maus ggf. aktivieren
        if(maus) {
            anzeige.klickReagierbarAnmelden(this, true);
        }
        
        //Tastatur
        anzeige.tastenReagierbarAnmelden(this);
        
        //Ticker
        //Alle 500 Millisekunden (=Jede halbe Sekunde) ein Tick
        anzeige.tickerAnmelden(this, 10); 
    }
    
    
    
    /**
     * Erstellt ein einfaches Spiel ohne Anzeige und Maus.<br />
     * Das Spiel hat somit Ticker und Tastatureingaben.
     */
    public SPIEL() {
        
        this(800, 800, false, false, false);
        System.out.println("Spiel erstellt");
    }
    public static double calculateMovementAngle(Point2D.Double startPoint, Point2D.Double endPoint) {
    double deltaX = endPoint.getX() - startPoint.getX();
    double deltaY = endPoint.getY() - startPoint.getY();
    double angleRadians = Math.atan2(deltaY, deltaX);
    double angleDegrees = Math.toDegrees(angleRadians);
    return angleDegrees;
}
public static double calculateReflectionAngle(double objectAngle, double lineAngle) {
        // Berechnen Sie den Einfallswinkel des Objekts zur reflektierenden Linie
        double incidentAngle = Math.abs(lineAngle - objectAngle);
        
        // Berechnen Sie den Ausfallswinkel (gleich dem Einfallswinkel)
        double reflectionAngle = incidentAngle;
        
        // Berechnen Sie den Winkel der reflektierenden Linie
        double reflectedLineAngle;
        if (lineAngle < 180) {
            reflectedLineAngle = objectAngle + incidentAngle;
        } else {
            reflectedLineAngle = objectAngle - incidentAngle;
        }
        
        // Stellen Sie sicher, dass der reflektierende Linienwinkel im Bereich [0, 360) liegt
        reflectedLineAngle %= 360;
        if (reflectedLineAngle < 0) {
            reflectedLineAngle += 360;
        }
        
        return reflectionAngle;
    }

    
    
    /**
     * Wird regelmaessig aufgerufen. So kommt Bewegung ins Spiel!
     */
    
    public static Vektor createVectorFromAngle(double angleDegrees, double length) {
        // Winkel in Bogenmaß umwandeln
        double angleRadians = Math.toRadians(angleDegrees);

        // x- und y-Komponenten des Vektors berechnen
        double xComponent = length * Math.cos(angleRadians);
        double yComponent = length * Math.sin(angleRadians);

        // Erstellen des Vektors
        Vektor vector = new Vektor((float) xComponent,(float) yComponent);
        
        return vector;
    }
    public static Point2D.Double calculatePointOnCircle(double radius, double angleDegrees) {
        // Winkel in Bogenmaß umwandeln
        double angleRadians = Math.toRadians(angleDegrees);
        
        // x-Koordinate berechnen
        double x = radius * Math.cos(angleRadians)+400;
        
        // y-Koordinate berechnen
        double y = radius * Math.sin(angleRadians)+400;
        
        // Den berechneten Punkt zurückgeben
        return new Point2D.Double(x, y);
    }
    Vektor bewegungsVektor= new Vektor(5,1);
    public void tick() {
    double tangentAngle = ball.aktuellerZentrumsWinkel() - 90;
    Point2D.Double centerPoint = new Point2D.Double(400, 400);
    double distanceToCenter = ball.aktuellerPunkt().distance(centerPoint);
    double movementAngle = calculateMovementAngle(ball.letzterPunkt(), ball.aktuellerPunkt());

    // Berechnung des Reflektionswinkels, wenn der Abstand zum Mittelpunkt des Kreises größer als 350 ist
    if (distanceToCenter >= 350) {
         
        double reflectionAngle = calculateReflectionAngle(movementAngle, tangentAngle);
        System.out.println("Reflektionswinkel: " + reflectionAngle);

        ball.neuenPunktHinzufügen(ball.aktuellerPunkt());
        bewegungsVektor = createVectorFromAngle(reflectionAngle, 5);
        bewegungsVektor=new Vektor(bewegungsVektor.x,bewegungsVektor.y);
        Point2D.Double point = calculatePointOnCircle(100, ball.aktuellerZentrumsWinkel());
        ea.Punkt punkt= new ea.Punkt((float) point.x,(float) point.y);
        
        
       
       // ball.positionSetzen(punkt);
         //bewegungsVektor=new Vektor(bewegungsVektor.x*-1,bewegungsVektor.y*-1);
    }

    // Bewegung des Balls mit dem aktualisierten Bewegungsvektor
    ball.bewegen(new Vektor(bewegungsVektor.x*-1,bewegungsVektor.y*-1));
    
    // Inkrementierung des Zählers
    zaehler++;
    zaehler = zaehler % 2;
    if (zaehler == 1) {
        // System.out.println("Tick!");
    } else {
        // System.out.println("Tack!");
    }
}

    
    

    /**
     * Wird bei jedem Mausklick (Linksklick) automatisch aufgerufen.
     * 
     * @param   x   Die X-Koordinate des Klicks
     * 
     * @param   y   Die Y-Koordinate des Klicks
     */
    public void klickReagieren(int x, int y) {
        
        //Einfache Bildschirmausgabe. Kann spaeter in Subklasse beliebig ueberschrieben werden.
       // System.out.println("Klick bei (" + x  + ", " + y + ").");
    }
    
    
    
    /**
     * Wird bei jedem Tastendruck automatisch aufgerufen.
     * 
     * @param   tastenkuerzel   Der int-Wert, der fuer die gedrueckte Taste steht.
     *                          Details koennen in der <i>Tabelle aller 
     *                          Tastaturkuerzel</i> abgelesen werden.
     */
    public void tasteReagieren(int tastenkuerzel) {
        System.out.println("Taste mit Kuerzel " + tastenkuerzel + 
                " wurde gedrueckt");
    }
    
    
    
    /**
     * Setzt das Ticker-Intervall.
     * 
     * @param   ms  Die Zeit in Millisekunden zwischen zwei
     *              Aufrufen der <code>tick()</code>-Methode.
     */
    public void tickerIntervallSetzen(int ms) {
        anzeige.tickerAbmelden(this);
        anzeige.tickerAnmelden(this, ms);
    }
    
    
    
    /**
     * Stoppt die Ticker-Funktion. Die <code>tick()</code>-Methode
     * wird nicht weiter aufgerufen. Der automatische Aufruf der 
     * <code>tick()</code>-Methode kann durch die Methode 
     * <code>tickerNeuStarten(int ms)</code> wiedergestartet werden.
     * 
     * @see     #tickerNeuStarten(int)
     */
    public void tickerStoppen() {
        anzeige.tickerAbmelden(this);
    }
    
    /**
     * Startet den Ticker neu.
     * 
     * @param   ms      Die Zeit in Millisekunden zwischen zwei
     *                  Aufrufen der <code>tick()</code>-Methode. 
     */
    public void tickerNeuStarten(int ms) {
        anzeige.tickerAbmelden(this);
        anzeige.tickerAnmelden(this, ms);
        
    }
    
    
    
    /**
     * Setzt ein neues Maus-Icon.
     * 
     * @param   pfad        Der Pfad zu dem Bild (jpg, bmp, png), das 
     *                      das neue Maus-Icon werden soll. ZB: "mausicon.png"
     *                      
     * @param   hotspotX    Die X-Koordinate des Hotspots fuer das neue
     *                      Maus-Icon. (relativ im Icon)
     *                      
     * @param   hotspotY    Die Y-Koordinate des Hotspots fuer das neue
     *                      Maus-Icon. (relativ im Icon)
     */
    public void mausIconSetzen(String pfad, int hotspotX, int hotspotY) {
        ea.edu.FensterE.getFenster().mausAnmelden(new Maus(new Bild(0,0,pfad), new ea.Punkt(hotspotX, hotspotY)), true);
    }
    
    
    
    /**
     * Sorgt dafuer, dass sowohl der rechte als auch der linke Punktestand sichtbar ist.
     */
    public void allePunkteSichtbar() {
        anzeige.punkteLinksSichtbarSetzen(true);
        anzeige.punkteRechtsSichtbarSetzen(true);
    }
    
    
    
    /**
     * Sorgt dafuer, dass nur der linke Punktestand sichtbar ist.
     */
    public void nurRechtePunkteSichtbar() {
        anzeige.punkteLinksSichtbarSetzen(false);
        anzeige.punkteRechtsSichtbarSetzen(true);
    }
    
    
    
    /**
     * Sorgt dafuer, dass nur der rechte Punktestand sichtbar ist.
     */
    public void nurLinkePunkteSichtbar() {
        anzeige.punkteLinksSichtbarSetzen(true);
        anzeige.punkteRechtsSichtbarSetzen(false);
    }
    
    
    
    /**
     * Sorgt dafuer, dass weder der rechte noch der linke Punktestand sichtbar ist.
     */
    public void allePunkteUnsichtbar() {
        anzeige.punkteLinksSichtbarSetzen(false);
        anzeige.punkteRechtsSichtbarSetzen(false);
    }
    
    
    
    /**
     * Setzt den linken Punktestand. Aenderungen sind nur sichtbar,
     * wenn auch der linke Punktestand sichtbar ist.
     * 
     * @param   pl      Der neue linke Punktestand.
     */
    public void punkteLinksSetzen(int pl) {
        anzeige.punkteLinksSetzen(pl);
    }
    
    
    
    /**
     * Setzt den rechten Punktestand. Aenderungen sind nur sichtbar,
     * wenn auch der rechte Punktestand sichtbar ist.
     * 
     * @param   pr      Der neue rechte Punktestand.
     */
    public void punkteRechtsSetzen(int pr) {
        anzeige.punkteRechtsSetzen(pr);
    }
    
    
    
    /**
     * Gibt eine Zufallszahl aus.
     * 
     * @param von   Die Untergrenze der Zufallszahl (INKLUSIVE)
     * 
     * @param bis   Die Obergrenze der Zufallszahl (INKLUSIVE)
     * 
     * @return      Eine Zufallszahl z mit:   von <= z <= bis
     */
    public int zufallszahlVonBis(int von, int bis) {
        return anzeige.zufallszahlVonBis(von, bis);
        
        
    }
    
    
    
    /**
     * Setzt eine Hintergrundgrafik fuer das Spiel.
     * 
     * @param   pfad    Der Pfad der Bilddatei (jpg, bmp, png) des Bildes,
     *                  das benutzt werden soll. ZB: "hintergrund.jpg"
     */
    public void hintergrundgrafikSetzen(String pfad) {
        ea.edu.FensterE.getFenster().hintergrundSetzen(new Bild(0,0,pfad));
        
    }
}

