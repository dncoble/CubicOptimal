package s;

/* Interface for search algorithms, has the search(Cube cube) method. I have stuff written for AStar and SMAStar
 * but I would only trust IDAStar. AStar and SMAStar, if not wrong, are not fast because they don't have >0.3.1
 * Coordinate updates.
 */
import c.*;
public interface Search {
    public Scramble search();
}
