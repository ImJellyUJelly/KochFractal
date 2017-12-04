package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * Created by user on 22-11-2017.
 */
public class KochCallable implements Callable<ArrayList<Edge>>, Observer {
    private KochFractal fractal;
    private ArrayList<Edge> edges;
    private EdgeDirection edgeDirection;

    public KochCallable(int nxt, EdgeDirection edge){
        fractal = new KochFractal();
        fractal.addObserver(this);
        fractal.setLevel(nxt);
        edges = new ArrayList<>();
        edgeDirection = edge;
    }

    @Override
    public ArrayList<Edge> call() throws Exception {
        switch(edgeDirection){
            case LEFT:
                fractal.generateLeftEdge();
                break;
            case RIGHT:
                fractal.generateRightEdge();
                break;
            case BOTTOM:
                fractal.generateBottomEdge();
                break;
        }
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
}
