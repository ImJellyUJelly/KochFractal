package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer {
    private ArrayList<Edge> edges;
    private int currentLevel;
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private TimeStamp ts;

    public KochManager(JSF31KochFractalFX app) {
        application = app;
        edges = new ArrayList<Edge>();
        this.koch = new KochFractal();
        koch.addObserver(this);
    }

    public void changeLevel(int level) {
        koch.setLevel(level);

        // Schrijf nr of edges en dat soort stuff op het scherm
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
        // Clearen van edges voor het opnieuw tekenen voor alleen de laatste tekening
        edges.clear();
        ts = new TimeStamp();
        ts.setBegin("Start calculating");
        koch.generateRightEdge();
        koch.generateBottomEdge();
        koch.generateLeftEdge();
        ts.setEnd("End calculating");
        application.setTextCalc(ts.toString());
        drawEdges();
    }

    public void drawEdges() {
        application.clearKochPanel();

        ts = new TimeStamp();
        ts.setBegin("Start drawing");
        for(Edge e : edges) {
            application.drawEdge(e);
        }
        ts.setEnd("End drawing");

        application.setTextDraw(ts.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}
