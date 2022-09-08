package h;
/* 
 * a complete heuristic can be used by taking the norm of a list of heuristics. The power of the norm must be greater
 * than 2. if power = inf, that is equivalent to a max heuristic
 * 
 * MUST WAIT FOR HEURISTIC REFACTORING TO COMPLETE, right now just works as MaxHeuristic
 */
import c.*;
import java.util.ArrayList;

public class PowerHeuristic implements ByteHeuristic {
    private ArrayList<ByteHeuristic> heuristics;

    //initialialization just creates an empty ArrayList. use .addHeuristic() to fill out
    public PowerHeuristic() {heuristics = new ArrayList<ByteHeuristic>();}
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