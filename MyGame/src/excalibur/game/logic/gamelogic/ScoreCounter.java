package excalibur.game.logic.gamelogic;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * This (static) class is used to count the score of every step. Since all
 * patterns that are to be cleared are computed in class Map, this class doesn't
 * judge its correctness.
 * 
 * @author 喵叔
 * @version 1.0
 */
public class ScoreCounter
{
	
	private static final int scoreA=1000;
	private static final int scoreB=5000;
	private static final int[] score=new int[]{0,0,0,100,200,500,200,300,600,
	// TODO: The following scores are not in the requirement doc.
	// And they need further discuss...
	800,1000,1200,1500,2000,2500,3000,4000,5000,6500,8100,10000,12100,14400,
	16900,19600,22500,25600,28900,32400,36100,40000,44100,48400,52900,57600,
	62500,67600,72900,78400,84100,90000,96100};
	
	/**
	 * Compute the score. If srcPoints is not generated naturally in the class
	 * Map, its result is unpredictable.
	 * 
	 * @param srcPoints
	 *            The ArrayList generated in the class Map whose containing are
	 *            to be cleared
	 * @return The score of this very move.
	 * @see excalibar.logic.Map
	 */
	public static int getScore(List<ChessPoint> chessPoints)
	{
		
		int ret=0;
		// Avoid destroying the reference parameter
		LinkedList<Point> points=new LinkedList<>();
		for(ChessPoint p:chessPoints)
			//1 is just the universal color...
			points.add(p.point);
		
		// Judge basic score
		while(!points.isEmpty())
		{
			Point p=points.getFirst();
			points.remove(p);
			int count=DFS(p,points);
			if(count>=score.length)
				ret+=100000;
			else
				ret+=score[count];
		}
		
		// Judge items A and B
		for(ChessPoint p:chessPoints)
		{
			// p is item A
			if(p.trigger<0)
				ret+=scoreA;
			// p is item B
			if(p.trigger==Map.MATCH_TYPE+1)
				ret+=scoreB;
		}
		
		return ret;
	}
	
	/**
	 * This is just a coloring algorithm...
	 * 
	 * @param p
	 *            Start point of the DFS
	 * @param points
	 *            All the remaining points
	 * @return Number of p's adjacent points
	 */
	private static int DFS(Point p,List<Point> points)
	{
		int ret=1;
		Point point1=new Point(p.x-1,p.y);
		Point point2=new Point(p.x+1,p.y);
		Point point3=new Point(p.x,p.y-1);
		Point point4=new Point(p.x,p.y+1);
		
		if(points.contains(point1))
		{
			points.remove(point1);
			ret+=DFS(point1,points);
		}
		if(points.contains(point2))
		{
			points.remove(point2);
			ret+=DFS(point2,points);
		}
		if(points.contains(point3))
		{
			points.remove(point3);
			ret+=DFS(point3,points);
		}
		if(points.contains(point4))
		{
			points.remove(point4);
			ret+=DFS(point4,points);
		}
		return ret;
	}
}
