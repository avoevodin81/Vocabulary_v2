package vocabulary.manager;

import vocabulary.Words.Word;
import vocabulary.sql.DBConnector;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Test on 11.05.2016.
 */
public class AllWordsManager {

    private DBConnector dbConnector;
    private ArrayList<Word> words;
    private ResultSet res;
    private Word word;
    private int count;

    public int getCount(String tableName) {
        dbConnector = new DBConnector();
        res = dbConnector.query("SELECT COUNT(*) FROM " + tableName);
        try {
            while (res.next()) {
                count = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
        return count;
    }

    public String[][] getAllTableWords(String tableName) {
        initList(tableName);
        String[][] mass = new String[words.size()][5];
        TreeMap<String, Word> map = new TreeMap<>();
        for (Word x : words) {
            map.put(x.getEng(), x);
        }
        int i = 0;
        for (Map.Entry<String, Word> x : map.entrySet()) {
            mass[i] = new String[]{String.valueOf(x.getValue().getId()), x.getValue().getEng(), x.getValue().getRus(), String.valueOf(x.getValue().isNew()), String.valueOf(++i)};
            //i++;
        }
        return mass;
    }

    public Boolean isInBase(Word word, String tableName) {
        dbConnector = new DBConnector();

        res = dbConnector.query("Select * From " + tableName);
        try {
            while (res.next()) {
                if (res.getString(2).equals(word.getEng()) || res.getString(3).equals(word.getRus())) {
                    dbConnector.exitDB();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
        dbConnector.exitDB();
        return false;
    }

    public Word searchWord(int id, String tableName) {
        dbConnector = new DBConnector();

        res = dbConnector.query(String.format("Select * From %s Where wordID = %d", tableName, id));
        try {
            while (res.next()) {
                if (res.getInt(1) == id)
                    this.word = new Word(res.getInt(1), res.getString(2), res.getString(3), res.getBoolean(4));
                dbConnector.exitDB();
                return this.word;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
        dbConnector.exitDB();
        return null;
    }

    public ArrayList<Word> loadAndShuffle(String tableName, String tipeWord) {
        initList(tableName);
        for (int i = 0; i < words.size(); i++) {
            if (tipeWord.equals("New")) {
                if (!words.get(i).isNew()) {
                    words.remove(i);
                    i--;
                }
            } else if (tipeWord.equals("Old")) {
                if (words.get(i).isNew()) {
                    words.remove(i);
                    i--;
                }
            }
        }
        if (words != null) {
            Collections.shuffle(words);
        }
        return words;
    }

    private void initList(String tableName) {
        dbConnector = new DBConnector();

        words = new ArrayList<>();

        res = dbConnector.query("Select * From " + tableName);
        try {
            while (res.next()) {
                words.add(new Word(res.getInt(1), res.getString(2), res.getString(3), res.getBoolean(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
        dbConnector.exitDB();
    }
}