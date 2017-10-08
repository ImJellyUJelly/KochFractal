package jsf31kochfractalfx;

import calculate.Edge;

import java.util.Observable;
import java.util.Observer;

public class KochObserver implements Observer {

    @Override
    public void update(Observable observable, Object obj) {
        Edge e = (Edge)obj;

        // Hier wordt 1 lijn geprint.
        System.out.println("Punt 1: " + e.X1 + "/" + e.Y1 + ", Punt2: " + e.X2 + "/" + e.Y2);
    }
}
