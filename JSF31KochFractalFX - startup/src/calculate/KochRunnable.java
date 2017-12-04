package calculate;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by user on 13-11-2017.
 */
public class KochRunnable implements Observer, Runnable {
    private KochManager manager;
    private KochFractal fractal;
    private EdgeDirection edgeDirection;

    public KochRunnable(KochManager manager, int nxt, EdgeDirection edge){
        this.manager = manager;
        fractal = new KochFractal();
        fractal.addObserver(this);
        fractal.setLevel(nxt);
        edgeDirection = edge;
    }


    @Override
    public void run() {
        switch(edgeDirection){
            case LEFT:
                fractal.generateLeftEdge();
                manager.plusCount();
                break;
            case RIGHT:
                fractal.generateRightEdge();
                manager.plusCount();
                break;
            case BOTTOM:
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
