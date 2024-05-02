import java.util.List;
import ea.Vektor;
import ea.*;
 
import java.awt.geom.Point2D;
import java.awt.Point;
import java.util.ArrayList;

import java.awt.event.KeyEvent;
public class Ball extends KREIS {
    public ArrayList<Point2D.Double> pointList = new ArrayList<>();
    
    public Ball() {
      durchmesserSetzen(10);
      positionSetzen(400, 100);
      
      pointList.add(new Point2D.Double(400, 100));  
    }

    public void neuenPunktHinzufügen(Point2D.Double point)
    {
        pointList.add(point);
    }
    public Point2D.Double letzterPunkt()
    {
        return pointList.get(pointList.size() - 1);
    }
    public static double calculateAngle(Point2D.Double P, Point2D.Double B) {
        double deltaX = B.getX() - P.getX();
        double deltaY = B.getY() - P.getY();
        return Math.atan2(deltaY, deltaX);
    }
    public double aktuellerZentrumsWinkel()
    {
         Point2D.Double P = new Point2D.Double(400, 400); // Mittelpunkt
        Point2D.Double B = new Point2D.Double(positionX(), positionY()); // Beispiel-Punkt

        double angle = calculateAngle(P, B);
        System.out.println("Der Winkel von Ball zum Zentrum beträgt: " + Math.toDegrees(angle) + " Grad");
        return Math.toDegrees(angle);
    }
    public Point2D.Double aktuellerPunkt()
    {
        Point2D.Double aktPunkt = new Point2D.Double(positionX(), positionY());
        return aktPunkt;
    }

   
       
    
    
    public void bewegen(float x,float y) {
        
      verschieben(x,y);
    }
}
