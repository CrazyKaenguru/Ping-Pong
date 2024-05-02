
/**
 * Beschreiben Sie hier die Klasse SpielFeld.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class SpielFeld 
{
    
    public SpielFeld()
    {
        erstelleSpielUmfang(1000, 350);
    }
    void erstelleSpielUmfang(int abschnitte,int radius)
    {
        double angleIncrement = 2 * Math.PI / abschnitte;
        for (int i = 0; i < abschnitte; i++) {
            double angle = i * angleIncrement;
            int x = (int) (450 + radius * Math.cos(angle));
            int y = (int) (450 + radius * Math.sin(angle));
            KREIS kreis= new KREIS();
            kreis.mittelpunktSetzen(x, y);
            kreis.durchmesserSetzen(2);
            
    }
}

   
    
}
