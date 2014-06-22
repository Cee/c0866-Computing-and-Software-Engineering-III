package excalibur.game.logic.syslogic;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.DialogCreator;


/**
 * Created by Xc on 2014/4/22.
 */
public class DataUtils {

    private int gameCount;
    private int totalScore;
    private int maxCombo;
    private int maxScore;

    private static String DB_PATH = "person.db";

    private double averageScore;

    private ArrayList<GameRecord> gameRecords = new ArrayList<>();

    private static DataUtils du;

    private int gold;

    private int experience;
    private int level;


    public static void main(String[] args) {
//        Pattern p = Pattern.compile("\\d+\\.\\d+");
//        Matcher m = p.matcher("78.5a");
//        m.find();
//        System.out.println(m.group());

        System.out.println(Constant.NetWork.SERVERIP);
    }

    /**
     * @return DataUtils 的静态引用
     */
    public static DataUtils getInstance() {

        if (du == null) {
            du = new DataUtils();
        }
        return du;
    }

    /**
     * 保存数据
     */
    public void saveData() {
//        try {
//            File saveFile = new File(path);
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile));
//            oos.writeObject(gameRecords);
//            oos.flush();
//            oos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 用于增加记录
     *
     * @param score 一局的得分
     * @param combo 一局的最大combo
     */
    public void addRecord(int score, int combo) {
        Calendar calendar = Calendar.getInstance();
        int time = (int) (calendar.getTimeInMillis() / 1000);

        addRecordInner(time, score, combo);

    }

    private void addRecordInner(int time, int score, int combo) {
        gameRecords.add(new GameRecord(time, score, combo));
        Connection c = null;
        Statement stmt = null;

        gold += score / 10;

        addExperience(score / 10);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);


            stmt = c.createStatement();

            stmt.execute(String.format("INSERT INTO GAME_RECORD (TIME,SCORE,COMBO) VALUES (%d,%d,%d)", time, score, combo));

            stmt.execute(String.format("update player set val = %d where prop = \'gold\'", gold));
            stmt.execute(String.format("update player set val = %d where prop = \'exp\'", experience));

            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        update();
    }

    private void addExperience(int add_experience) {
        int newExp = experience + add_experience;
        int newLevel = (int) Math.sqrt(newExp / 100);
        experience = newExp;
        if (newLevel > level) {
            levelUp(newLevel);
        }

    }

    public void updateRanking(ArrayList<String> nameList, ArrayList<Integer> scoreList) {
        if (nameList.size() != scoreList.size())
            return;
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();

            stmt.execute("drop table if exists ranking");
            stmt.execute("create table if not exists 'ranking' (name charset , score int )");

//            ResultSet rs = stmt.executeQuery("select * from ranking order by score desc")
//            "insert into ranking ( name,score ) values ( 'mmm', 1)";
            for (int i = 0; i < nameList.size(); ++i) {
                stmt.execute(String.format("insert into ranking ( name,score ) values ( '%s', %d)",
                        nameList.get(i), scoreList.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RankingTupe> getRankingList() {
        Connection c = null;
        Statement stmt = null;

        ArrayList<RankingTupe> list = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();

            stmt.execute("create table if not exists 'ranking' (name charset , score int )");

            ResultSet rs = stmt.executeQuery("select * from ranking order by score desc");
            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                list.add(new RankingTupe(name, score));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public class RankingTupe {
        public String name;
        public int score;

        public RankingTupe(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    private void levelUp(int newLevel) {
        level = newLevel;
        if (level>=5) {
        	ArrayList<Shipin> shipins = getShipin();
        	for (int i = 1; i <= (level/5>=4?4:level/5); i++) {
        		for (Shipin shipin : shipins) {
					if (shipin.name.equals("Unname"+i)&&!shipin.isOwned) {
						gainShipin("Unname"+i);
			            DialogCreator.oneButtonDialog("饰品解锁", "解锁饰品"+i);
			            break;
					}
				}
			}
            
        }
        setValue("level", level);
    }

    public void addRecordByWaiGua(int day, int score, int combo) {
        day = day * 3600 * 24;
        day += Calendar.getInstance().getTimeInMillis() / 1000;
        addRecordInner(day, score, combo);
    }

    private DataUtils() {
        loadData();
    }


    @SuppressWarnings("unchecked")
    private void loadData() {

        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            createTables();
        }

        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);

            stmt = c.createStatement();


            ResultSet rs = stmt.executeQuery("SELECT * FROM GAME_RECORD ORDER BY TIME");

            while (rs.next()) {
                int score = rs.getInt("SCORE");
                int time = rs.getInt("TIME");
                int combo = rs.getInt("COMBO");
                gameRecords.add(new GameRecord(time, score, combo));
            }

            rs = stmt.executeQuery("SELECT * FROM PLAYER WHERE PROP = 'gold'");
            while (rs.next()) {
                gold = rs.getInt("val");
            }

            rs = stmt.executeQuery("SELECT * FROM PLAYER WHERE PROP = 'exp'");
            while (rs.next()) {
                experience = rs.getInt("val");
            }

            rs = stmt.executeQuery("SELECT * FROM PLAYER WHERE PROP = 'level'");
            while (rs.next()) {
                level = rs.getInt("val");
            }


            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        update();
    }


    public boolean consumeGold(int g) {
        if (gold >= g) {
            gold -= g;
            setValue("gold", gold);
            return true;
        } else {
            return false;
        }

    }

    public void updataShipin(Shipin s) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();

            stmt.execute(String.format("update shipin set current_effect = %d  where name = '%s'", s.current_effect, s.name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gainShipin(String sname) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();

            stmt.execute(String.format("update shipin set isowned = 1  where name = '%s'", sname));
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();

            stmt.execute("create table game_record (" +
                    "time   int not null," +
                    "combo  int not null," +
                    "score  int not null" +
                    ")");

            stmt.execute("create table player ( prop charset , val int )");

            stmt.execute("insert into player (prop , val) values ('gold' , 0  )");
            stmt.execute("insert into player (prop , val) values ('exp' , 0  )");
            stmt.execute("insert into player (prop , val) values ('level' , 0  )");

            stmt.execute("insert into player (prop , val) values ('bestRecord' , 0  )");
            stmt.execute("insert into player (prop , val) values ('remainTime' , 0  )");
            stmt.execute("insert into player (prop , val) values ('lastRecord' , 0  )");
            stmt.execute("insert into player (prop , val) values ('lastTime' , 0  )");


            stmt.execute("create table shipin( type int , isowned boolean , name charset ,  init_effect int , max_effect int , current_effect int , effect_type int)");

            ArrayList<Shipin> sps = new ArrayList<>();
            sps.add(new Shipin(Shipin.Type.background, false, "Unname1", 0, 0, 0, Shipin.Effect.noeffect));
            sps.add(new Shipin(Shipin.Type.icon, false, "Unname2", 0, 0, 0, Shipin.Effect.noeffect));
            sps.add(new Shipin(Shipin.Type.cursor, false, "Unname3", 0, 0, 0, Shipin.Effect.noeffect));
            sps.add(new Shipin(Shipin.Type.background, false, "Unname4", 0, 0, 0, Shipin.Effect.noeffect));
            sps.add(new Shipin(Shipin.Type.icon, false, "Unname5", 0, 0, 0, Shipin.Effect.noeffect));
            sps.add(new Shipin(Shipin.Type.cursor, false, "Unname6", 10, 20, 10, Shipin.Effect.socreUp));
            sps.add(new Shipin(Shipin.Type.icon, false, "Unname7", 6, 20, 6, Shipin.Effect.timeUp));
            for (Shipin sp : sps) {
                stmt.execute(String.format("insert into shipin (type , isowned , name , init_effect , max_effect , current_effect , effect_type ) values (%d,%s,'%s',%d,%d,%d,%d )",
                        sp.type, sp.isOwned ? "1" : "0", sp.name, sp.init_effect, sp.max_effect, sp.current_effect, sp.effect_type));
            }


            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }


    }

    public void saveSelectedShipin(Shipin[] shipins) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();
            stmt.execute("drop table if exists selectedshipin");
            stmt.execute("create table if not exists selectedshipin (name charset) ");
            for (Shipin shipin : shipins) {
                if (shipin != null) {
                    stmt.execute("insert into selectedshipin (name) values ('" + shipin.name + "')");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Shipin[] getSelectShipin() {
        Connection c;
        Statement stmt;
        Shipin[] shipins = new Shipin[3];

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();
            stmt.execute("create table if not exists selectedshipin (name charset)");
            ResultSet rs = stmt.executeQuery("select * from selectedshipin");
            String[] names = new String[3];
            int i = 0;
            while (rs.next()) {
                names[i++] = rs.getString("name");
                System.out.println(names[i-1]);

            }

            for(i =0 ; i < 3; ++i){
                if (names[i] ==null){
                    continue;
                }
                rs = stmt.executeQuery("select * from shipin where name ='"+ names[i] +"'");
                rs.next();
                int type = rs.getInt("type");
                boolean isOwned = rs.getBoolean("isowned");
                String name = rs.getString("name");
                int init_effect = rs.getInt("init_effect");
                int max_effect = rs.getInt("max_effect");
                int current_effect = rs.getInt("current_effect");
                int effect_type = rs.getInt("effect_type");

                Shipin s = new Shipin(type, isOwned, name, init_effect, max_effect, current_effect, effect_type);
                shipins[s.type] = s;
            }

            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shipins;
    }


    private void setValue(String valName, int val) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();
            stmt.execute(String.format("update player set val = %d where prop = \'%s\'", val, valName));

            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    private int getValue(String valName) {
        Connection c;
        Statement stmt;

        int ret = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select val from player where prop = '%s'", valName));

            while (rs.next()) {
                ret = rs.getInt("val");
            }


            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return ret;
    }

    private void update() {
        totalScore = 0;
        maxCombo = 0;
        gameCount = 0;
        maxScore = 0;
        for (GameRecord gameRecord : gameRecords) {
            totalScore += gameRecord.score;
            gameCount += 1;
            maxCombo = maxCombo > gameRecord.combo ? maxCombo : gameRecord.combo;
            maxScore = maxScore > gameRecord.score ? maxScore : gameRecord.score;
        }

        averageScore = (double) totalScore / gameCount;

    }


    //getters below

    public ArrayList<Shipin> getShipin() {
        ArrayList<Shipin> shipins = new ArrayList<>();
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from shipin");
            while (rs.next()) {
                int type = rs.getInt("type");
                boolean isOwned = rs.getBoolean("isowned");
                String name = rs.getString("name");
                int init_effect = rs.getInt("init_effect");
                int max_effect = rs.getInt("max_effect");
                int current_effect = rs.getInt("current_effect");
                int effect_type = rs.getInt("effect_type");

                shipins.add(new Shipin(type, isOwned, name, init_effect, max_effect, current_effect, effect_type));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return shipins;
    }

    public int getGameCount() {
        return gameCount;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getGold() {
        return gold;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getBestRecord() {
        return getValue("bestRecord");
    }

    public int getRemainTime() {
        return getValue("remainTime");
    }

    public int getLastRecord() {
        return getValue("lastRecord");
    }

    public int getLastTime() {
        return getValue("lastTime");
    }

    public void setBestRecord(int val) {
        if (val>=5&&val<=10&&val%5==0) {
            gainShipin("Unname"+(val/5+4));
            DialogCreator.oneButtonDialog("饰品解锁", "解锁饰品"+(val/5+4));
        }
        setValue("bestRecord", val);
    }

    public void setRemainTime(int val) {
        setValue("remainTime", val);
    }

    public void setLastRecord(int val) {
        setValue("lastRecord", val);
    }

    public void setLastTime(int val) {
        setValue("lastTime", val);
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public int getMaxScore() {
        return maxScore;
    }

    /**
     * @param fromDay 开始的日期（不包括） 与今日的差值，若是今天的前一天则为-1，若是今天，则为0
     * @param endDay  结束的日期（包括） 计算方式同 fromDay
     * @return 从开始日期到结束日期每天游戏局数的ArrayList
     */
    public ArrayList<Integer> getDailyGameCount(int fromDay, int endDay) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 0; i < endDay - fromDay; ++i) {
            ret.add(0);
        }
        int today = (int) (Calendar.getInstance().getTimeInMillis() / 1000 / 3600 / 24);


        for (GameRecord gameRecord : gameRecords) {
            int day = gameRecord.time / 3600 / 24 - today + endDay - fromDay - 1;
            if (day >= 0 && day < (endDay - fromDay)) {
                ret.set(day, ret.get(day) + 1);
            }

        }

        return ret;
    }

    public ArrayList<Integer> getScorePerGame(int count) {
        ArrayList<Integer> ret = new ArrayList<>();
        int from = gameRecords.size() - count;
        for (int i = from; i < gameRecords.size(); ++i) {
            if (i >= 0) {
                ret.add(gameRecords.get(i).score);
            }
        }
        return ret;
    }

    public ArrayList<Integer> getAverageScorePerDay(int fromDay, int endDay) {
        ArrayList<Integer> ret = new ArrayList<>();
        ArrayList<Integer> counts = getDailyGameCount(fromDay, endDay);
        for (int i = 0; i < endDay - fromDay; ++i) {
            ret.add(0);
        }
        int today = (int) (Calendar.getInstance().getTimeInMillis() / 1000 / 3600 / 24);


        for (GameRecord gameRecord : gameRecords) {
            int day = gameRecord.time / 3600 / 24 - today + endDay - fromDay - 1;
            if (day >= 0 && day < (endDay - fromDay)) {
                ret.set(day, ret.get(day) + gameRecord.score);
            }
        }

        for (int i = 0; i < endDay - fromDay; ++i) {
            try {
                ret.set(i, ret.get(i) / counts.get(i));
            } catch (ArithmeticException e) {
//                e.printStackTrace();
                ret.set(i, 0);
            }
        }

        return ret;
    }


    static public class Shipin{
        public static class Effect {
            public static int noeffect = 0;
            public static int socreUp = 1;
            public static int timeUp = 2;
        }



        public static String[] discription={"无效果","得分加成<br>起始效果：百分之%d<br>最高效果：百分之%d<br>当前效果：百分之%d","游戏时间增加<br>起始效果：%d秒<br>最高效果：%d秒<br>当前效果%d秒"};
        public static String[] types = {"背景","图案","鼠标"};

        public static class Type {
            public static int background = 0;
            public static int icon = 1;
            public static int cursor = 2;
        }

        public int type;
        public boolean isOwned;
        public String name;
        public int init_effect;
        public int max_effect;
        public int current_effect;
        public int effect_type;

        public Shipin(int type, boolean isOwned, String name, int init_effect, int max_effect, int current_effect, int effect_type) {
            this.name = name;
            this.type = type;
            this.effect_type = effect_type;
            this.current_effect = current_effect;
            this.init_effect = init_effect;
            this.max_effect = max_effect;
            this.isOwned = isOwned;
        }


    }

    class GameRecord implements Serializable {
        public static final long serialVersionUID = 233333L;
        int score;
        int combo;
        int time;

        public GameRecord(int time, int score, int combo) {
            this.score = score;
            this.combo = combo;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("time:%d, Score:%d, Combo:%d", time, score, combo);
        }
    }

}
