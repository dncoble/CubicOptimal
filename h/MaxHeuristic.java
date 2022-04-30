package h;
/* 
 * a heuristic is also complete if it takes the maximum of multiple complete heuristics.
 * this class is set up for byte heuristics
 */
import c.*;
import java.util.ArrayList;

public class MaxHeuristic implements ByteHeuristic {
    private ArrayList<ByteHeuristic> heuristics;

    //initialialization just creates an empty ArrayList. use .addHeuristic() to fill out
    public MaxHeuristic() {heuristics = new ArrayList<ByteHeuristic>();}
    public void addHeuristic(ByteHeuristic h) {heuristics.add(h);}
    public byte h(Cube cube) {
        byte rtrn = 0;
        for(ByteHeuristic h : heuristics) {
            byte newH = h.h(cube);
            if(newH > rtrn) {rtrn = newH;}
        }
        return rtrn;
    }
}