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
    }

    @Override
    public ArrayList<Edge> call() throws Exception {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
}
