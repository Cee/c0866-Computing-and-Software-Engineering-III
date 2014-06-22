package excalibur.game.logic.gamelogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Cee on 5/26/14.
 */
public class Map {

    public static int MAP_SIZE = 9;
    public static int MATCH_TYPE = 5;

    public enum ACTION{

        UP(2), DOWN(-2), LEFT(-1), RIGHT(1),
        TRIGGER_A(3), TRIGGER_B(4), TRIGGER_COLOR(5), NOACTION(0);

        private final int tag;

        ACTION(int tag){
            this.tag = tag;
        }

        public int getTag() {
            return tag;
        }
    }


    private Random random = new Random();

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    private int[][] map = new int[MAP_SIZE][MAP_SIZE];
    private int[][] fillMap = new int[MAP_SIZE][MAP_SIZE];
    private int[] container = new int[MAP_SIZE];

    private int[] createColumnTriggerA = new int[MAP_SIZE];
    private int[] createColumnTriggerB = new int[MAP_SIZE];
    private int[] createLineTriggerA = new int[MAP_SIZE];
    private int[] createLineTriggerB = new int[MAP_SIZE];

    private ArrayList<Point> retPoints = new ArrayList<Point>();
    private ArrayList<Point> specialPoints = new ArrayList<Point>();
    private ArrayList<Point> triggerPoints = new ArrayList<Point>();
    private ArrayList<Point> checkPoints = new ArrayList<Point>();
    private ArrayList<Point> linePoints = new ArrayList<Point>();
    private ArrayList<Point> columnPoints = new ArrayList<Point>();


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
            // test.dropRight();
            test.getFillMap(true);
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

        // init
        setZero();

        specialPoints = getSpecialPoints(line, column, action);

        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                fillMap[i][j] = map[i][j];
            }
        }

        if (action == ACTION.TRIGGER_A){
            retPoints = triggerA(line, column);
        } else if (action == ACTION.TRIGGER_B){
            retPoints = triggerB(line, column);
        } else if (action == ACTION.TRIGGER_COLOR){
            retPoints = triggerColor();
            return retPoints;
        } else {
            retPoints = normalDealing();
        }

        while (triggerPoints.size() != 0){
            Point p = triggerPoints.get(0);
            int x = (int)(p.getX());
            int y = (int)(p.getY());
            if (map[x][y] < 0){
                retPoints.addAll(triggerA(x, y));
            } else if (map[x][y] == MATCH_TYPE + 1){
                retPoints.addAll(triggerB(x, y));
            } else {
                System.out.println("Error!!!" + map[x][y] + " " + x + " " + y);
            }
            triggerPoints.remove(p);
        }

        for (Point p: retPoints){
            System.out.println(p.getX() + " " + p.getY());
        }

        HashSet<Point> hashSet = new HashSet<Point>();
        for (Point p: retPoints){
            hashSet.add(p);
        }

        retPoints.clear();
        for (Point p: hashSet){
            int x = (int)(p.getX());
            int y = (int)(p.getY());
            boolean flag = true;
            for (Point q: retPoints){
                int tempx = (int)(q.getX());
                int tempy = (int)(q.getY());
                if ((tempx == x) && (tempy == y)){
                    flag = false;
                    break;
                }
            }
            if (flag) retPoints.add(p);
        }

        return retPoints;
    }

    private void setZero(){

        retPoints = new ArrayList<Point>();
        specialPoints = new ArrayList<Point>();
        triggerPoints = new ArrayList<Point>();
        checkPoints = new ArrayList<Point>();
        linePoints = new ArrayList<Point>();
        columnPoints = new ArrayList<Point>();

        for (int i = 0; i < MAP_SIZE; i++){
            createColumnTriggerA[i] = 0;
            createColumnTriggerB[i] = 0;
            createLineTriggerA[i] = 0;
            createLineTriggerB[i] = 0;
        }
    }

    private ArrayList<Point> getSpecialPoints(int line, int column, ACTION action){

        ArrayList<Point> ret = new ArrayList<Point>();

        switch (action){
            case UP:
                if (moveUP(line, column)){
                    map[line][column] = map[line][column] ^ map[line - 1][column];
                    map[line - 1][column] = map[line][column] ^ map[line - 1][column];
                    map[line][column] = map[line][column] ^ map[line - 1][column];
                    ret.add(new Point(line, column));
                    ret.add(new Point(line - 1, column));
                }
                break;
            case DOWN:
                if (moveDOWN(line, column)){
                    map[line][column] = map[line][column] ^ map[line + 1][column];
                    map[line + 1][column] = map[line][column] ^ map[line + 1][column];
                    map[line][column] = map[line][column] ^ map[line + 1][column];
                    ret.add(new Point(line, column));
                    ret.add(new Point(line + 1, column));
                }
                break;
            case LEFT:
                if (moveLEFT(line, column)){
                    map[line][column] = map[line][column] ^ map[line][column - 1];
                    map[line][column - 1] = map[line][column] ^ map[line][column - 1];
                    map[line][column] = map[line][column] ^ map[line][column - 1];
                    ret.add(new Point(line, column));
                    ret.add(new Point(line, column - 1));
                }
                break;
            case RIGHT:
                if (moveRIGHT(line, column)){
                    map[line][column] = map[line][column] ^ map[line][column + 1];
                    map[line][column + 1] = map[line][column] ^ map[line][column + 1];
                    map[line][column] = map[line][column] ^ map[line][column + 1];
                    ret.add(new Point(line, column));
                    ret.add(new Point(line, column + 1));
                }
                break;
            default:
                break;
        }

        return ret;
    }


    /**
     *
     * @return a MAP_SIZE int array that contains the filling map
     */
    public int[][] getFillMap(boolean dropRight){

        print(map);
        if (dropRight){
            dropRight();
        }
        else {
            dropDown();
        }
        print(map);

        ArrayList<Integer> mapX = new ArrayList<Integer>();
        ArrayList<Integer> mapY = new ArrayList<Integer>();
        ArrayList<Integer> fillmapX = new ArrayList<Integer>();
        ArrayList<Integer> fillmapY = new ArrayList<Integer>();

        for (int i = 0; i < MAP_SIZE; i++){
            container[i] = 0;
            for (int j = 0; j < MAP_SIZE; j++){
                fillMap[i][j] = 0;
            }
        }

        if (dropRight){
            //DROPRIGHT
            for (int i = MAP_SIZE - 1; i >= 0; i--){
                for (int j = MAP_SIZE - 1; j >= 0; j--){
                    if (map[j][i] == 0){
                        int color = 0;
                        if (createLineTriggerB[j] > 0){
                            createLineTriggerB[j]--;
                            color = MATCH_TYPE + 1;
                        } else {
                            color = random.nextInt(MATCH_TYPE) + 1;
                            if (createLineTriggerA[j] > 0){
                                color = -color;
                                createLineTriggerA[j]--;
                            }
                        }
                        fillMap[j][MAP_SIZE -  container[j] - 1] = color;
                        map[j][i] = fillMap[j][MAP_SIZE -  container[j] - 1];
                        mapX.add(j);
                        mapY.add(i);
                        fillmapX.add(j);
                        fillmapY.add(MAP_SIZE -  container[j] - 1);
                        container[j]++;
                    }
                }
            }
        } else {
            //DROPDOWN
            for (int i = MAP_SIZE - 1; i >= 0; i--){
                for (int j = MAP_SIZE - 1; j >= 0; j--){
                    if (map[i][j] == 0){
                        int color = 0;
                        if (createColumnTriggerB[j] > 0){
                            createColumnTriggerB[j]--;
                            color = MATCH_TYPE + 1;
                        } else {
                            color = random.nextInt(MATCH_TYPE) + 1;
                            if (createColumnTriggerA[j] > 0){
                                color = -color;
                                createColumnTriggerA[j]--;
                            }
                        }
                        fillMap[MAP_SIZE -  container[j] - 1][j] = color;
                        map[i][j] = fillMap[MAP_SIZE -  container[j] - 1][j];
                        mapX.add(i);
                        mapY.add(j);
                        fillmapX.add(MAP_SIZE -  container[j] - 1);
                        fillmapY.add(j);
                        container[j]++;
                    }
                }
            }
        }

        boolean solution;
        do {
            solution = false;
            for (int i = 0; i < MAP_SIZE; i++){
                for (int j = 0; j < MAP_SIZE; j++){
                    if (hasSolution(i, j)){
                        solution = true;
                        break;
                    }
                }
            }
            if (!solution) {
                for (int i = 0; i < mapX.size(); i++){
                    int color = random.nextInt(MATCH_TYPE) + 1;
                    map[mapX.get(i)][mapY.get(i)] = color;
                    fillMap[fillmapX.get(i)][fillmapY.get(i)] = color;
                }
            }
        } while (!solution);

        print(fillMap);
        print(map);
        return fillMap;
    }

    /**
     *
     * @return
     */
    public int[] getHint(){
        int[] ret = new int[3];
        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (hasHint(i, j) != ACTION.NOACTION){
                    ret[0] = i;
                    ret[1] = j;
                    ret[2] = hasHint(i, j).getTag();
                    break;
                }
            }
        }
        return ret;
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
    }

    private void dropRight(){
        for (int k = 0; k <= MAP_SIZE; k++){
            for (int i = MAP_SIZE - 1; i >= 0; i--){
                for (int j = MAP_SIZE - 1; j > 0; j--){
                    if (map[i][j] == 0 ){
                        map[i][j] = map[i][j - 1];
                        map[i][j - 1] = 0;
                    }
                }
            }
        }
    }

    private void print(int[][] map){
        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     *
     * @param line
     * @param column
     * @return whether point at (x,y) has a solution
     */
    private boolean hasSolution(int line, int column){

        if (map[line][column] < 0) return true;
        if (map[line][column] == (MATCH_TYPE + 1)) return true;
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

    private ACTION hasHint(int line, int column){
        if (map[line][column] < 0) return ACTION.TRIGGER_A;
        if (map[line][column] == (MATCH_TYPE + 1)) return ACTION.TRIGGER_B;
        //UP
        if (moveUP(line, column)) return ACTION.UP;
        //DOWN
        if (moveDOWN(line, column)) return ACTION.DOWN;
        //LEFT
        if (moveLEFT(line, column)) return ACTION.LEFT;
        //RIGHT
        if (moveRIGHT(line, column)) return ACTION.RIGHT;
        return ACTION.NOACTION;
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
            if ((Math.abs(map[line][column]) == Math.abs(map[line - 1][column]))
                    && (Math.abs(map[line][column]) == Math.abs(map[line - 2][column]))){
                return true;
            }
        }
        if (line <= MAP_SIZE - 3){
            if ((Math.abs(map[line][column]) == Math.abs(map[line + 1][column]))
                    && (Math.abs(map[line][column]) == Math.abs(map[line + 2][column]))){
                return true;
            }
        }
        if (line >= 1 && line <= MAP_SIZE - 2){
            if ((Math.abs(map[line][column]) == Math.abs(map[line - 1][column]))
                    && (Math.abs(map[line][column]) == Math.abs(map[line + 1][column]))){
                return true;
            }
        }
        //COLUMN
        if (column >= 2){
            if ((Math.abs(map[line][column]) == Math.abs(map[line][column - 1]))
                    && (Math.abs(map[line][column]) == Math.abs(map[line][column - 2]))){
                return true;
            }
        }
        if (column <= MAP_SIZE - 3){
            if ((Math.abs(map[line][column]) == Math.abs(map[line][column + 1]))
                    && (Math.abs(map[line][column]) == Math.abs(map[line][column + 2]))){
                return true;
            }
        }
        if (column >= 1 && column <= MAP_SIZE - 2){
            if ((Math.abs(map[line][column]) == Math.abs(map[line][column - 1]))
                    && (Math.abs(map[line][column]) == Math.abs(map[line][column + 1]))){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Point> numbersInline(int line, int column){
        ArrayList<Point> ret = new ArrayList<Point>();
        ret.add(new Point(line, column));
        int j = column + 1;
        boolean flag = true;
        while (j < MAP_SIZE - 1){
            if (Math.abs(fillMap[line][j]) == Math.abs(fillMap[line][column])){
                ret.add(new Point(line, j));
                j++;
            }else{
                break;
            }
        }
        j = column - 1;
        flag = true;
        while ((j >= 0) && flag){
            if (Math.abs(fillMap[line][j]) == Math.abs(fillMap[line][column])){
                ret.add(new Point(line, j));
                j--;
            }else{
                flag = false;
            }
        }
        return ret;
    }

    private ArrayList<Point> numbersIncolumn(int line, int column){
        ArrayList<Point> ret = new ArrayList<Point>();
        ret.add(new Point(line, column));
        int i = line + 1;
        boolean flag = true;
        while ((i < MAP_SIZE - 1) && flag){
            if (Math.abs(fillMap[i][column]) == Math.abs(fillMap[line][column])){
                ret.add(new Point(i, column));
                i++;
            }else{
                flag = false;
            }
        }
        i = line - 1;
        flag = true;
        while ((i >= 0) && flag){
            if (Math.abs(fillMap[i][column]) == Math.abs(fillMap[line][column])){
                ret.add(new Point(i, column));
                i--;
            }else{
                flag = false;
            }
        }
        return ret;
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

    private ArrayList<Point> triggerA(int x, int y){

        ArrayList<Point> ret = new ArrayList<Point>();
        Point point;

        //(x, y)
        if (map[x][y] != 0){
            map[x][y] = 0;
            ret.add(new Point(x, y));
        }
        //(x - 1, y)
        if (x - 1 >= 0){
            if (map[x - 1][y] != 0){
                if ((map[x - 1][y] < 0) || (map[x - 1][y] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x - 1, y));
                } else {
                    map[x - 1][y] = 0;
                    ret.add(new Point(x - 1, y));
                }
            }
        }
        //(x, y - 1)
        if (y - 1 >= 0){
            if (map[x][y - 1] != 0){
                if ((map[x][y - 1] < 0) || (map[x][y - 1] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x, y - 1));
                } else {
                    map[x][y - 1] = 0;
                    ret.add(new Point(x, y - 1));
                }
            }
        }
        //(x + 1, y)
        if (x + 1 < MAP_SIZE){
            if (map[x + 1][y] != 0){
                if ((map[x + 1][y] < 0) || (map[x + 1][y] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x + 1, y));
                } else {
                    map[x + 1][y] = 0;
                    ret.add(new Point(x + 1, y));
                }
            }
        }
        //(x, y + 1)
        if (y + 1 < MAP_SIZE){
            if (map[x][y + 1] != 0){
                if ((map[x][y + 1] < 0) || (map[x][y + 1] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x, y + 1));
                } else {
                    map[x][y + 1] = 0;
                    ret.add(new Point(x, y + 1));
                }
            }
        }
        //(x - 1, y - 1)
        if ((x - 1 >= 0) && (y - 1 >= 0)){
            if (map[x - 1][y - 1] != 0){
                if ((map[x - 1][y - 1] < 0) || (map[x - 1][y - 1] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x - 1, y - 1));
                } else {
                    map[x - 1][y - 1] = 0;
                    ret.add(new Point(x - 1, y - 1));
                }
            }
        }
        //(x - 1, y + 1)
        if ((x - 1 >= 0) && (y + 1 < MAP_SIZE)){
            if (map[x - 1][y + 1] != 0){
                if ((map[x - 1][y + 1] < 0) || (map[x - 1][y + 1] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x - 1, y + 1));
                } else {
                    map[x - 1][y + 1] = 0;
                    ret.add(new Point(x - 1, y + 1));
                }
            }
        }
        //(x + 1, y - 1)
        if ((x + 1 < MAP_SIZE) && (y - 1 >= 0)){
            if (map[x + 1][y - 1] != 0){
                if ((map[x + 1][y - 1] < 0) || (map[x + 1][y - 1] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x + 1, y - 1));
                } else {
                    map[x + 1][y - 1] = 0;
                    ret.add(new Point(x + 1, y - 1));
                }
            }
        }
        //(x + 1, y + 1)
        if ((x + 1 < MAP_SIZE) && (y + 1 < MAP_SIZE)){
            if (map[x + 1][y + 1] != 0){
                if ((map[x + 1][y + 1] < 0) || (map[x + 1][y + 1] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x + 1, y + 1));
                } else {
                    map[x + 1][y + 1] = 0;
                    ret.add(new Point(x + 1, y + 1));
                }
            }
        }

        return ret;
    }

    private ArrayList<Point> triggerB(int x, int y){

        ArrayList<Point> ret = new ArrayList<Point>();
        Point point;

        //(x, y)
        if (map[x][y] != 0){
            map[x][y] = 0;
            ret.add(new Point(x, y));
        }

        //column
        for (int i = 0; i < MAP_SIZE; i++){
            if (map[i][y] != 0){
                if ((map[i][y] < 0) || (map[i][y] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(i, y));
                } else {
                    map[i][y] = 0;
                    ret.add(new Point(i, y));
                }
            }
        }

        //row
        for (int j = 0; j < MAP_SIZE; j++){
            if (map[x][j] != 0){
                if ((map[x][j] < 0) || (map[x][j] == MATCH_TYPE + 1)){
                    triggerPoints.add(new Point(x, j));
                } else {
                    map[x][j] = 0;
                    ret.add(new Point(x, j));
                }
            }
        }

        return ret;
    }

    private ArrayList<Point> normalDealing(){

        ArrayList<Point> ret = new ArrayList<Point>();

        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (checkInline(i, j)){
                    ret.add(new Point(i, j));
                }
            }
        }


        checkPoints = (ArrayList<Point>)(ret.clone());

        for (Point p: specialPoints){
            checkEachPoint(p, true);
            checkPoints.remove(p);
            specialPoints.remove(p);
        }

        while (checkPoints.size() != 0){
            Point p = checkPoints.get(0);
            checkEachPoint(p, false);
            checkPoints.remove(p);
        }

        HashSet<Point> hashSet = new HashSet<Point>();
        for (Point p: ret){
            hashSet.add(p);
        }

        ret.clear();
        for (Point p: hashSet){
            int x = (int)(p.getX());
            int y = (int)(p.getY());
            boolean flag = true;
            for (Point q: ret){
                int tempx = (int)(q.getX());
                int tempy = (int)(q.getY());
                if ((tempx == x) && (tempy == y)){
                    flag = false;
                    break;
                }
            }
            if (flag) ret.add(p);
        }
        return ret;
    }

    private void checkEachPoint(Point p, boolean isSpecial){
        int x = (int)(p.getX());
        int y = (int)(p.getY());

        linePoints = numbersInline(x, y);
        columnPoints = numbersIncolumn(x, y);

        int lineSize = linePoints.size();
        int columnSize = columnPoints.size();

        System.out.println(x + " " + y + ":" + lineSize + " " + columnSize);
        boolean canCreateB = ((lineSize >= 5)
                || (columnSize >= 5)
                || ((lineSize >= 3) && (columnSize >= 4))
                || ((lineSize >= 4) && (columnSize >= 3)));
        boolean canCreateA = ((lineSize == 4)
                || (columnSize == 4)
                || ((lineSize == 3) && (columnSize == 3)));

        if (canCreateB){
            createColumnTriggerB[y]++;
            createLineTriggerB[x]++;
        } else if (canCreateA){
            createColumnTriggerA[y]++;
            createLineTriggerA[x]++;
        }

        for (Point q: linePoints){
            int tempx = (int)(q.getX());
            int tempy = (int)(q.getY());
            for (Point r: checkPoints){
                if ((tempx == (int)(r.getX()))
                        && (tempy == (int)(r.getY()))){
                    if ((map[tempx][tempy] < 0) || (map[tempx][tempy] == MATCH_TYPE + 1)){
                        triggerPoints.add(new Point(tempx, tempy));
                    } else {
                        map[tempx][tempy] = 0;
                    }
                    checkPoints.remove(r);
                    break;
                }
            }
        }

        for (Point q: columnPoints){
            int tempx = (int)(q.getX());
            int tempy = (int)(q.getY());
            for (Point r: checkPoints){
                if ((tempx == (int)(r.getX()))
                        && (tempy == (int)(r.getY()))){
                    if ((map[tempx][tempy] < 0) || (map[tempx][tempy] == MATCH_TYPE + 1)){
                        triggerPoints.add(new Point(tempx, tempy));
                    } else {
                        map[tempx][tempy] = 0;
                    }
                    checkPoints.remove(r);
                    break;
                }
            }
        }


        if ((map[x][y] < 0) || (map[x][y] == MATCH_TYPE + 1)){
            boolean flag = true;
            for (Point q: triggerPoints){
                int tempx = (int)(q.getX());
                int tempy = (int)(q.getY());
                if ((x == tempx) && (y == tempy)){
                    flag = false;
                    break;
                }
            }
            if (flag) triggerPoints.add(new Point(x, y));
        } else {
            if ((lineSize >= 3) || (columnSize >= 3)){
                map[x][y] = 0;
            }
        }
    }

    private ArrayList<Point> triggerColor(){

        int constantColor = random.nextInt(MATCH_TYPE) + 1;

        ArrayList<Point> ret = new ArrayList<Point>();
        Point point;

        //(x, y)
        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (map[i][j] == constantColor){
                    point = new Point(i, j);
                    ret.add(point);
                    map[i][j] = 0;
                }
            }
        }

        return ret;
    }
}
