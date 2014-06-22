package Test;

/**
 * Created by Cee on 3/20/14.
 */
public class Map {

    public static int MAP_SIZE = 9;
    public static int MATCH_TYPE = 5;
    int[][] map = new int[9][9];

    public static void main(String[] args) {
        Map test = new Map();
        test.init();
        test.print();
        test.tryFill(0);
    }


    public int[][] triggerA(int x, int y, int[][] map) {

                                                            map[x][y] = 0;          //x,y
        if (x - 1 >= 0)                                     map[x - 1][y] = 0;      //x-1,y
        if (y - 1 >= 0)                                     map[x][y - 1] = 0;      //x,y-1
        if (x + 1 <= MAP_SIZE)                              map[x + 1][y] = 0;      //x+1,y
        if (y + 1 <= MAP_SIZE)                              map[x][y + 1] = 0;      //x,y+1
        if ((x - 1 >= 0) && (y - 1 >= 0))                   map[x - 1][y - 1] = 0;  //x-1,y-1
        if ((x - 1 >= 0) && (y + 1 <= MAP_SIZE))            map[x - 1][y + 1] = 0;  //x-1,y+1
        if ((x + 1 <= MAP_SIZE) && (y - 1 >= 0))            map[x + 1][y - 1] = 0;  //x+1,y-1
        if ((x + 1 <= MAP_SIZE) && (y + 1 <= MAP_SIZE))     map[x + 1][y + 1] = 0;  //x+1,y+1

        return map;
    }


    public void init(){
        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                map[i][j] = -1;
            }
        }
    }


    public void tryFill(int newIndex) {
        if( newIndex < MAP_SIZE * MAP_SIZE) {
            int line = newIndex / MAP_SIZE;
            int column = newIndex % MAP_SIZE;
            for(int i = 1;i <= MATCH_TYPE; ++i) {
                map[line][column] = i;
                if (!hasPairs(line, column)){
                    tryFill(newIndex+1);
                }
            }
            map[line][column] = -1;
        }
        else {
            if (check())
                print();
        }
    }

    public boolean check() {

        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                if (hasPairs(i, j))
                    return false;
            }
        }
        return true;

    }

    public boolean check(int line, int column) {
        //纵向检测
        if(line >= 2)
            if(map[line][column]==map[line-1][column]&&map[line][column]==map[line-2][column])
                return true;
        if(line <= MAP_SIZE - 3)
            if(map[line][column]==map[line+1][column]&&map[line][column]==map[line+2][column])
                return true;

        //横向检测
        if(column >= 2)
            if(map[line][column]==map[line][column-1]&&map[line][column]==map[line][column-2])
                return true;
        if(column <= MAP_SIZE - 3)
            if(map[line][column]==map[line][column+1]&&map[line][column]==map[line][column+2])
                return true;

        return false;
    }

    public void swap(int a, int b){
        int temp = a;
        a = b;
        b = temp;
    }

    public boolean hasPairs(int line, int column)
    {
        if(check(line, column))
            return true;

        if(line > 0 && line + 1 < MAP_SIZE)
        {
            swap(map[line][column],map[line-1][column]);
            if(map[line][column]!=-1)
                if(check(line,column))
                {
                    swap(map[line][column],map[line+1][column]);
                    return true;
                }
            if(map[line-1][column]!=-1)
                if(check(line-1,column))
                {
                    swap(map[line][column],map[line+1][column]);
                    return true;
                }
            swap(map[line][column],map[line+1][column]);
        }

        if(line<MAP_SIZE-1)
        {
            swap(map[line][column],map[line+1][column]);
            if(map[line][column]!=-1)
                if(check(line,column))
                {
                    swap(map[line][column],map[line+1][column]);
                    return true;
                }
            if(map[line+1][column]!=-1)
                if(check(line+1,column))
                {
                    swap(map[line][column],map[line+1][column]);
                    return true;
                }
            swap(map[line][column],map[line+1][column]);
        }

        if(column>0 && column + 1 < MAP_SIZE)
        {
            swap(map[line][column],map[line][column-1]);
            if(map[line][column]!=-1)
                if(check(line,column))
                {
                    swap(map[line][column],map[line][column-1]);
                    return true;
                }
            if(map[line][column-1]!=-1)
                if(check(line,column-1))
                {
                    swap(map[line][column],map[line][column-1]);
                    return true;
                }
            swap(map[line][column],map[line][column-1]);
        }

        if(column<MAP_SIZE-1)
        {
            swap(map[line][column],map[line][column+1]);
            if(map[line][column]!=-1)
                if(check(line,column))
                {
                    swap(map[line][column],map[line][column+1]);
                    return true;
                }
            if(map[line][column+1]!=-1)
                if(check(line,column+1))
                {
                    swap(map[line][column],map[line][column+1]);
                    return true;
                }
            swap(map[line][column],map[line][column+1]);
        }

        return false;
    }

    public void print(){
        for (int i = 0; i < MAP_SIZE; i++){
            for (int j = 0; j < MAP_SIZE; j++){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
