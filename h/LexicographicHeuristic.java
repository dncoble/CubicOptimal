package h;
/*
 * The lexicographic heuristic organizes a list of heuristics where the first heuristic is absolutely more important
 * than the second heuristic. i.e. [5, 1] > [4, 11]. Only in the case of a tie in the first heuristic does the second
 * provide a tiebreaker [5, 10] > [5, 3]. 
 *
 * MUST WAIT FOR HEURISTIC REFACTORING TO COMPLETE, right now just works as MaxHeuristic
 */
import c.*;
import java.util.ArrayList;

public class LexicographicHeuristic implements ByteHeuristic {
    private ArrayList<ByteHeuristic> heuristics;

    //initialialization just creates an empty ArrayList. use .addHeuristic() to fill out
    public LexicographicHeuristic() {heuristics = new ArrayList<ByteHeuristic>();}
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