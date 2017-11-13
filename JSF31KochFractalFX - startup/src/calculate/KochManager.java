package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class KochManager {
    private List<Edge> edges;
    private JSF31KochFractalFX application;
    //private KochFractal koch;
    private int count = 0;

    public KochManager(JSF31KochFractalFX app) {
        application = app;
        //koch = new KochFractal();
        //koch.addObserver(this);
        edges = new ArrayList<>();
    }

    public void changeLevel(int nxt) {
        //koch.setLevel(nxt);
        edges.clear();

//        koch.generateLeftEdge();
//        koch.generateBottomEdge();
//        koch.generateRightEdge();

        // Runnables worden aangemaakt en meegegeven aan de verschillende threads
        KochRunnable leftRunnable = new KochRunnable(this, nxt, "Left");
        Thread leftThread = new Thread(leftRunnable);
        KochRunnable rightRunnable = new KochRunnable(this, nxt, "Right");
        Thread rightThread = new Thread(rightRunnable);
        KochRunnable bottomRunnable = new KochRunnable(this, nxt, "Bottom");
        Thread bottomThread = new Thread(bottomRunnable);

        // Calculating calculationtime
        TimeStamp measureCalc = new TimeStamp();
        measureCalc.setBegin("start calculating");

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
        //application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
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

//    @Override
//    public void update(Observable o, Object arg) {
//        Edge edge = (Edge) arg;
//        synchronized (this) {
//            edges.add(edge);
//        }
//    }
}