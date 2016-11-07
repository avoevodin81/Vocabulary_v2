package vocabulary.manager;

import vocabulary.Words.Word;
import vocabulary.sql.DBConnector;

/**
 * Created by Test on 11.05.2016.
 */
public class InsertManager {

    public void insert(Word word, String tableName){
        DBConnector dbConnector = new DBConnector();
        dbConnector.updateQuery(String.format("INSERT INTO %s (english, russian, new) values ('%s', '%s', '%s')", tableName, comaSearch(word.getEng()), comaSearch(word.getRus()), word.getFilter()));
        dbConnector.exitDB();
    }

    protected String comaSearch(String s) {
        return s.replaceAll("\'", "\'\'");
    }
}
