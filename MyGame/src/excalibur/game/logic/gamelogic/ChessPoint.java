package excalibur.game.logic.gamelogic;

import java.awt.*;
/**
 * Created by Cee on 4/26/14.
 */
public class ChessPoint{

    public Point point;
    public int color = 0;
    public int trigger = 0;

    public ChessPoint(int color, int trigger, Point point){
        this.color = color;
        this.trigger = trigger;
        this.point = point;
    }
    
    public double getX(){
    	return point.getX();
    }
    
    public double getY(){
    	return point.getY();
    }
}