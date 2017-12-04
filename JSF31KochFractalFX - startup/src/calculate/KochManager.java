package calculate;

import javafx.scene.paint.Color;
import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
        JavaFXTask rightTask = new JavaFXTask(this, nxt, EdgeDirection.RIGHT, application);
        JavaFXTask bottomTask = new JavaFXTask(this, nxt, EdgeDirection.BOTTOM, application);

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

    public synchronized void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public synchronized void plusCount(){
        count++;
        if(count == 3) {
            application.requestDrawEdges();
            count = 0;
        }
    }
}