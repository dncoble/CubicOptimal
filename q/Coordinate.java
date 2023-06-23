package q;
import c.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ListIterator;

/* interface for Coordinates. Old functionality is provided by .value(Cube cube), where the value for that cube is
 * found. New functionality is Coordinate storing its own values, and is implemented with value().
 * CO, EO, CP, EP, REO and RCO used exist within the Cube class
 */
public abstract class Coordinate implements Cloneable {
    public abstract void set(Cube cube);
    // I wish I could make this a static method and still implemented by the inherited classes
    public abstract int value(Cube cube);
    public abstract int value();
    public abstract void move(int move);
    public abstract String name();
    public abstract int size();
    public abstract Coordinate clone();
    
    public boolean isSolved() {
        return value() == 0;
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    public static Object readTableFromFile(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object rtrnMap = objectIn.readObject();
            objectIn.close();
            return rtrnMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}