package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer {
    private List<Edge> edges;
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private int count = 0;

    public KochManager(JSF31KochFractalFX app) {
        application = app;
        koch = new KochFractal();
        koch.addObserver(this);
        edges = new ArrayList<>();
    }

    public void changeLevel(int nxt) {
        koch.setLevel(nxt);
        edges.clear();

        // Calculating calculationtime
        TimeStamp measureCalc = new TimeStamp();
        measureCalc.setBegin("start calculating");
//        koch.generateLeftEdge();
//        koch.generateBottomEdge();
//        koch.generateRightEdge();
        // Nieuwe classes moeten hier worden gemaakt en met de threads worden meegegeven
        Thread leftThread = new Thread();
        Thread rightThread = new Thread();
        Thread bottomThread = new Thread();
        leftThread.start();
        rightThread.start();
        bottomThread.start();
        measureCalc.setEnd("end calculating");

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
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
        application.setTextDraw(measureDrawing.toString());
    }

    public synchronized void plusCount(){
        count++;
        if(count == 3) {
            application.requestDrawEdges();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge edge = (Edge) arg;
        synchronized (this) {
            edges.add(edge);
        }
    }
}