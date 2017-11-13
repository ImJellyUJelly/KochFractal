package calculate;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by user on 13-11-2017.
 */
public class KochRunnable implements Observer, Runnable {
    private KochManager manager;
    private KochFractal fractal;
    private String edge;

    public KochRunnable(KochManager manager, int nxt, String edge){
        this.manager = manager;
        fractal = new KochFractal();
        fractal.addObserver(this);
        fractal.setLevel(nxt);
        this.edge = edge;
    }


    @Override
    public void run() {
        switch(this.edge){
            case "Left":
                fractal.generateLeftEdge();
                manager.plusCount();
                break;
            case "Right":
                fractal.generateRightEdge();
                manager.plusCount();
                break;
            case "Bottom":
                fractal.generateBottomEdge();
                manager.plusCount();
                break;
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        manager.addEdge((Edge) arg);
    }
}
