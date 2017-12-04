package calculate;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaFXTask extends Task<List<Edge>> implements Observer {
    private KochFractal fractal;
    private ArrayList<Edge> edges;
    private EdgeDirection edgeDirection;
    private JSF31KochFractalFX app;
    private KochManager manager;
    private Random rnd = new Random();

    public JavaFXTask(KochManager managerr, int nxt, EdgeDirection edge, JSF31KochFractalFX app) {
        this.app = app;
        this.manager = manager;
        fractal = new KochFractal();
        fractal.addObserver(this);
        edges = new ArrayList<>();
        fractal.setLevel(nxt);
        edgeDirection = edge;
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge edge = (Edge) arg;
        edges.add(edge);
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        app.drawEdge(edge, Color.WHITE);
                    }
                }
        );
        updateProgress(edges.size(), fractal.getNrOfEdges() / 3);
        updateMessage(Integer.toString(edges.size()));
        try {
            Thread.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(KochFractal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected List<Edge> call() throws Exception {
        switch (edgeDirection) {
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
}
