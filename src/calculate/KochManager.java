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

    public KochManager(JSF31KochFractalFX app) {
        application = app;
        koch = new KochFractal();
        koch.addObserver(this);
    }

    public void changeLevel(int level) {
        koch.setLevel(level);
        drawEdges();
    }

    public void drawEdges() {
        application.clearKochPanel();
        TimeStamp ts = new TimeStamp();
        ts.setBegin("start drawing");
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        ts.setEnd("end drawing");
        application.setTextDraw(ts.toString());
        application.setTextNrEdges(String.valueOf(edges.size()));
    }

    @Override
    public void update(Observable o, Object arg) {
        application.drawEdge((Edge)arg);
        edges.add((Edge)arg);
    }
}
