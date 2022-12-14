package q;
import c.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/* interface for Coordinates. Old functionality is provided by .value(Cube cube), where the value for that cube is
 * found. New functionality is Coordinate storing its own values, and is implemented with value().
 * CO, EO, CP, EP, REO and RCO used exist within the Cube class
 */
public interface Coordinate {
    public void set(Cube cube);
    // I wish I could make this a static method and still implemented by the inherited classes
    public int value(Cube cube);
    public int value();
    public void move(int move);
    public void move(Scramble scr);
    public String name();
    public int size();
    
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