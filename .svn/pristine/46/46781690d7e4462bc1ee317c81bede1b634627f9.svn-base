package excalibur.game.logic.gamelogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Cee on 3/20/14.
 */
public class Map {

    public static int MAP_SIZE = 9;
    public static int MATCH_TYPE = 5;

    public enum ACTION{

        UP(2), DOWN(-2), LEFT(-1), RIGHT(1), NOACTION(0);

        private final int tag;

        ACTION(int tag){
      	    this.tag = tag;
        }

        public int getTag() {
			return tag;
		}
    }


    private Random random = new Random();
    private int[][] map = new int[MAP_SIZE][MAP_SIZE];
    private int[][] fillMap = new int[MAP_SIZE][MAP_SIZE];
    private int[] container = new int[MAP_SIZE];

    
    public static void main(String[] args) {
        Map test = new Map();
        test.initialMap();
        while(true){
            Scanner scanner = new Scanner(System.in);
            int lineNum = scanner.nextInt();
            int columnNum = scanner.nextInt();
            int action = scanner.nextInt();
            if (action == 1) test.clear(lineNum, columnNum, ACTION.UP);
            if (action == 2) test.clear(lineNum, columnNum, ACTION.DOWN);
            if (action == 3) test.clear(lineNum, columnNum, ACTION.LEFT);
            if (action == 4) test.clear(lineNum, columnNum, ACTION.RIGHT);
            test.dropDown();
            test.getFillMap();
        }
    }

    /**
     * initialize the map
     * @return map[9][9]
     */
    public int[][] initialMap(){
        do{
            for (int i = 0; i < MAP_SIZE; i++){
                container[i] = 0;
                for (int j = 0; j < MAP_SIZE; j++){
                    map[i][j] = random.nextInt(MATCH_TYPE) + 1;
                }
            }
        } while (!check());
        print(map);
        return map;
    }

    /**
     *
     * @param line
     * @param column
     * @param action
     * @return an ArrayList with all points that will be cleared
     */
    public ArrayList<Point> clear(int line, int column, ACTION action){

        ArrayList<Point> retPoints = new ArrayList<>();

        switch (action){
            case UP:
                if (moveUP(line, column)){
                    map[line][column] = map[line][column] ^ map[line - 1][column];
                    map[line - 1][column] = map[line][column] ^ map[line - 1][column];
                    map[line][column] = map[line][column] ^ map[line - 1][column];
                }
                break;
            case DOWN:
                if (moveDOWN(line, column)){
                    map[line][column] = map[line][column] ^ map[line + 1][column];
                    map[line + 1][column] = map[line][column] ^ map[line + 1][column];
                    map[line][column] = map[line][column] ^ map[line + 1][column];
                }
                break;
            case LEFT:
                if (moveLEFT(line, column)){
                    map[line][column] = map[line][column] ^ map[line][column - 1];
                    map[line][column - 1] = map[line][column] ^ map[line][column - 1];
                    map[line][column] = map[line][column] ^ map[line][column - 1];
                }
                break;
            case RIGHT:
                if (moveRIGHT(line, column)){
                    map[line][column] = map[line][column] ^ map[line][column + 1];
                    map[line][column + 1] = map[line][column] ^ map[line][column + 1];
                    map[line][column] = map[line][column] ^ map[line][column + 1];
                }
                break;
            case NOACTION:
                break;
            default:
                break;
        }

        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (checkInline(i, j)){
                    Point p = new Point(i, j);
                    retPoints.add(p);
                }
            }
        }

        for (int i = 0; i < retPoints.size(); i++){
            map[(int)(retPoints.get(i).getX())][(int)(retPoints.get(i).getY())] = 0;
        }
        return retPoints;
    }

    /**
     *
     * @return a MAP_SIZE int array that contains the filling map
     */
    public int[][] getFillMap(){

        dropDown();

        for (int i = 0; i < MAP_SIZE; i++){
            container[i] = 0;
            for (int j = 0; j < MAP_SIZE; j++){
                fillMap[i][j] = 0;
            }
        }

        for (int i = MAP_SIZE - 1; i >= 0; i--){
            for (int j = MAP_SIZE - 1; j >= 0; j--){
                if (map[i][j] == 0){
                    fillMap[MAP_SIZE -  container[j] - 1][j] = random.nextInt(MATCH_TYPE) + 1;
                    map[i][j] = fillMap[MAP_SIZE -  container[j] - 1][j];
                    container[j]++;
                }
            }
        }

        print(fillMap);
        print(map);
        return fillMap;
    }

    /**
     * update the map
     */
    private void dropDown(){
        for (int k = 0; k <= MAP_SIZE; k++){
            for (int j = MAP_SIZE - 1; j >= 0; j--){
                for (int i = MAP_SIZE - 1; i > 0; i--){
                    if (map[i][j] == 0 ){  
                        map[i][j] = map[i - 1][j];
                        map[i - 1][j] = 0;
                    }
                }
            }
        }
        print(map);
    }

//    public int[][] triggerA(int x, int y, int[][] map) {
//
//                                                            map[x][y] = 0;          //x,y
//        if (x - 1 >= 0)                                     map[x - 1][y] = 0;      //x-1,y
//        if (y - 1 >= 0)                                     map[x][y - 1] = 0;      //x,y-1
//        if (x + 1 <= MAP_SIZE)                              map[x + 1][y] = 0;      //x+1,y
//        if (y + 1 <= MAP_SIZE)                              map[x][y + 1] = 0;      //x,y+1
//        if ((x - 1 >= 0) && (y - 1 >= 0))                   map[x - 1][y - 1] = 0;  //x-1,y-1
//        if ((x - 1 >= 0) && (y + 1 <= MAP_SIZE))            map[x - 1][y + 1] = 0;  //x-1,y+1
//        if ((x + 1 <= MAP_SIZE) && (y - 1 >= 0))            map[x + 1][y - 1] = 0;  //x+1,y-1
//        if ((x + 1 <= MAP_SIZE) && (y + 1 <= MAP_SIZE))     map[x + 1][y + 1] = 0;  //x+1,y+1
//
//        return map;
//    }

    private void print(int[][] map){
//        for (int i = 0; i < MAP_SIZE; i++){
//            for (int j = 0; j < MAP_SIZE; j++){
//                System.out.printf("%d ", map[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    /**
     *
     * @param line
     * @param column
     * @return whether point at (x,y) has a solution
     */
    private boolean hasSolution(int line, int column){

        //UP
        if (moveUP(line, column)) return true;
        //DOWN
        if (moveDOWN(line, column)) return true;
        //LEFT
        if (moveLEFT(line, column)) return true;
        //RIGHT
        if (moveRIGHT(line, column)) return true;

        return false;

    }

    /**
     *
     * @param line
     * @param column
     * @return whether have 3 or more in a line
     */
    private boolean checkInline(int line, int column){
        //ROW
        if (line >= 2){
            if ((map[line][column] == map[line - 1][column]) && (map[line][column] == map[line - 2][column])){
                return true;
            }
        }
        if (line <= MAP_SIZE - 3){
            if ((map[line][column] == map[line + 1][column]) && (map[line][column] == map[line + 2][column])){
                return true;
            }
        }
        if (line >= 1 && line <= MAP_SIZE - 2){
            if ((map[line][column] == map[line - 1][column]) && (map[line][column] == map[line + 1][column])){
                return true;
            }
        }
        //COLUMN
        if (column >= 2){
            if ((map[line][column] == map[line][column - 1]) && (map[line][column] == map[line][column - 2])){
                return true;
            }
        }
        if (column <= MAP_SIZE - 3){
            if ((map[line][column] == map[line][column + 1]) && (map[line][column] == map[line][column + 2])){
                return true;
            }
        }
        if (column >= 1 && column <= MAP_SIZE - 2){
            if ((map[line][column] == map[line][column - 1]) && (map[line][column] == map[line][column + 1])){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return whether can make a map
     */
    private boolean check(){
        boolean ret = false;
        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (checkInline(i, j)){
                    return false;
                }
                if (hasSolution(i, j)){
                    ret = true;
                }
            }
        }
        return ret;
    }

    private boolean moveUP(int line, int column){

        if (line > 0) {

            map[line][column] = map[line][column] ^ map[line - 1][column];
            map[line - 1][column] = map[line][column] ^ map[line - 1][column];
            map[line][column] = map[line][column] ^ map[line - 1][column];

            if (checkInline(line, column)){
                map[line][column] = map[line][column] ^ map[line - 1][column];
                map[line - 1][column] = map[line][column] ^ map[line - 1][column];
                map[line][column] = map[line][column] ^ map[line - 1][column];
                return true;
            }

            if (checkInline(line - 1, column)){
                map[line][column] = map[line][column] ^ map[line - 1][column];
                map[line - 1][column] = map[line][column] ^ map[line - 1][column];
                map[line][column] = map[line][column] ^ map[line - 1][column];
                return true;
            }

            map[line][column] = map[line][column] ^ map[line - 1][column];
            map[line - 1][column] = map[line][column] ^ map[line - 1][column];
            map[line][column] = map[line][column] ^ map[line - 1][column];

        }
        return false;

    }

    private boolean moveDOWN(int line, int column){

        if (line + 1 < MAP_SIZE) {

            map[line][column] = map[line][column] ^ map[line + 1][column];
            map[line + 1][column] = map[line][column] ^ map[line + 1][column];
            map[line][column] = map[line][column] ^ map[line + 1][column];

            if (checkInline(line, column)){
                map[line][column] = map[line][column] ^ map[line + 1][column];
                map[line + 1][column] = map[line][column] ^ map[line + 1][column];
                map[line][column] = map[line][column] ^ map[line + 1][column];
                return true;
            }

            if (checkInline(line + 1, column)){
                map[line][column] = map[line][column] ^ map[line + 1][column];
                map[line + 1][column] = map[line][column] ^ map[line + 1][column];
                map[line][column] = map[line][column] ^ map[line + 1][column];
                return true;
            }

            map[line][column] = map[line][column] ^ map[line + 1][column];
            map[line + 1][column] = map[line][column] ^ map[line + 1][column];
            map[line][column] = map[line][column] ^ map[line + 1][column];

        }

        return false;
    }

    private boolean moveLEFT(int line, int column) {

        if (column > 0) {

            map[line][column] = map[line][column] ^ map[line][column - 1];
            map[line][column - 1] = map[line][column] ^ map[line][column - 1];
            map[line][column] = map[line][column] ^ map[line][column - 1];

            if (checkInline(line, column)){
                map[line][column] = map[line][column] ^ map[line][column - 1];
                map[line][column - 1] = map[line][column] ^ map[line][column - 1];
                map[line][column] = map[line][column] ^ map[line][column - 1];
                return true;
            }

            if (checkInline(line, column - 1)){
                map[line][column] = map[line][column] ^ map[line][column - 1];
                map[line][column - 1] = map[line][column] ^ map[line][column - 1];
                map[line][column] = map[line][column] ^ map[line][column - 1];
                return true;
            }

            map[line][column] = map[line][column] ^ map[line][column - 1];
            map[line][column - 1] = map[line][column] ^ map[line][column - 1];
            map[line][column] = map[line][column] ^ map[line][column - 1];

        }

        return false;
    }

    private boolean moveRIGHT(int line, int column) {

        if (column + 1 < MAP_SIZE) {

            map[line][column] = map[line][column] ^ map[line][column + 1];
            map[line][column + 1] = map[line][column] ^ map[line][column + 1];
            map[line][column] = map[line][column] ^ map[line][column + 1];

            if (checkInline(line, column)){
                map[line][column] = map[line][column] ^ map[line][column + 1];
                map[line][column + 1] = map[line][column] ^ map[line][column + 1];
                map[line][column] = map[line][column] ^ map[line][column + 1];
                return true;
            }

            if (checkInline(line, column + 1)){
                map[line][column] = map[line][column] ^ map[line][column + 1];
                map[line][column + 1] = map[line][column] ^ map[line][column + 1];
                map[line][column] = map[line][column] ^ map[line][column + 1];
                return true;
            }

            map[line][column] = map[line][column] ^ map[line][column + 1];
            map[line][column + 1] = map[line][column] ^ map[line][column + 1];
            map[line][column] = map[line][column] ^ map[line][column + 1];

        }

        return false;

    }

}
