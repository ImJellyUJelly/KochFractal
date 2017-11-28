package calculate;

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
    ExecutorService pool;

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

        // Runnables worden aangemaakt en meegegeven aan de verschillende threads
        KochCallable leftCallable = new KochCallable(nxt,"Left");
        KochCallable rightCallable = new KochCallable(nxt, "Right");
        KochCallable bottomCallable = new KochCallable(nxt, "Bottom");

        Future<ArrayList<Edge>> leftFuture = pool.submit(leftCallable);
        Future<ArrayList<Edge>> rightFuture = pool.submit(rightCallable);
        Future<ArrayList<Edge>> bottomFuture = pool.submit(bottomCallable);

        try{
            edges.addAll(leftFuture.get());
            edges.addAll(rightFuture.get());
            edges.addAll(bottomFuture.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        measureCalc.setEnd("end calculating");

        application.requestDrawEdges();

        // Display on GUI
        application.setTextCalc(measureCalc.toString());
    }

    public void drawEdges() {
        application.clearKochPanel();

        // Calculating drawtime
        TimeStamp measureDrawing = new TimeStamp();
        measureDrawing.setBegin("start drawing");
        for (Edge edge : edges) {
            application.drawEdge(edge);
        }
        measureDrawing.setEnd("end drawing");

        // Display on GUI
        application.setTextNrEdges(String.valueOf(edges.size()));
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