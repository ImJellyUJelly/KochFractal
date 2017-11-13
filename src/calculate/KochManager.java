package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer {
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private int currentLevel;
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private int count;

    public KochManager(JSF31KochFractalFX app) {
        count = 0;
        application = app;
        koch = new KochFractal();
        koch.addObserver(this);
    }

    public void changeLevel(int level) {
        application.clearKochPanel();
        edges.clear();
        koch.setLevel(level);

        // Nieuwe timestamp, meet de tijd van het berekenen.
        TimeStamp threadTs = new TimeStamp();
        threadTs.setBegin("start calculating");
        // Hieronder de 3 threads met de generateedge methodes
        Thread leftEdgeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                KochFractal fractal1 = koch;
                fractal1.generateLeftEdge();
                count++;
            }
        });
        leftEdgeThread.run();
        Thread bottomEdgeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                KochFractal fractal2 = koch;
                fractal2.generateBottomEdge();
                count++;
            }
        });
        bottomEdgeThread.run();
        Thread rightEdgeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                KochFractal fractal3 = koch;
                fractal3.generateRightEdge();
                count++;
            }
        });
        rightEdgeThread.run();
        threadTs.setEnd("end calculating");
        TimeStamp ts = new TimeStamp();
        ts.setBegin("start drawing");
        application.requestDrawEdges();
        ts.setEnd("end drawing");
        updateScreen(ts);
    }

    public void drawEdges() {

    }

    private void updateScreen(TimeStamp ts) {
        application.setTextDraw(ts.toString());
        application.setTextNrEdges(String.valueOf(edges.size()));
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        application.drawEdge((Edge) arg);
        edges.add((Edge) arg);
    }
}
