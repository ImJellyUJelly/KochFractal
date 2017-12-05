package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.concurrent.*;

public class KochManager {
    private ArrayList<Edge> edges;
    private JSF31KochFractalFX application;
    private int count = 0;
    private ExecutorService pool;

    public KochManager(JSF31KochFractalFX app) {
        application = app;
        edges = new ArrayList<>();
        pool = Executors.newFixedThreadPool(3);
    }

    public void changeLevel(int nxt) {
        edges.clear();

        // Calculating calculationtime
        TimeStamp measureCalc = new TimeStamp();
        measureCalc.setBegin("start calculating");

        JavaFXTask leftTask = new JavaFXTask(this, nxt, EdgeDirection.LEFT, application);
        updateProgressBar(EdgeDirection.LEFT, leftTask);
        JavaFXTask rightTask = new JavaFXTask(this, nxt, EdgeDirection.RIGHT, application);
        updateProgressBar(EdgeDirection.RIGHT, rightTask);
        JavaFXTask bottomTask = new JavaFXTask(this, nxt, EdgeDirection.BOTTOM, application);
        updateProgressBar(EdgeDirection.BOTTOM, bottomTask);

        pool.submit(leftTask);
        pool.submit(rightTask);
        pool.submit(bottomTask);

        measureCalc.setEnd("end calculating");

        application.requestDrawEdges();

        // Display on GUI
        application.setTextNrEdges(String.valueOf(edges.size()));
        application.setTextCalc(measureCalc.toString());
    }

    public void drawEdges() {
        application.clearKochPanel();

        // Calculating drawtime
        TimeStamp measureDrawing = new TimeStamp();
        measureDrawing.setBegin("start drawing");
        for (Edge edge : edges) {
            application.drawEdge(edge, edge.color);
        }
        measureDrawing.setEnd("end drawing");

        // Display on GUI
        application.setTextDraw(measureDrawing.toString());
    }

    public synchronized void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    // Runnable method
    public synchronized void plusCount() {
        count++;
        if (count == 3) {
            application.requestDrawEdges();
            count = 0;
        }
    }

    public void updateProgressBar(EdgeDirection direction, JavaFXTask task) {
        switch (direction) {
            case LEFT:
                application.requestUpdateLeftProgressBar(task);
                break;
            case RIGHT:
                application.requestUpdateRightProgressBar(task);
                break;
            case BOTTOM:
                application.requestUpdateBottomProgressBar(task);
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}