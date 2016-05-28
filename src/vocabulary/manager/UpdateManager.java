package vocabulary.manager;

import vocabulary.Words.Word;
import vocabulary.sql.DBConnector;

/**
 * Created by Test on 11.05.2016.
 */
public class UpdateManager {

    public void update(Word word, String tableName) {
        DBConnector dbConnector = new DBConnector();
        dbConnector.updateQuery(String.format("UPDATE %s SET english = '%s' WHERE wordID = %d", tableName, comaSearch(word.getEng()), word.getId()));
        dbConnector.updateQuery(String.format("UPDATE %s SET russian = '%s' WHERE wordID = %d", tableName, comaSearch(word.getRus()), word.getId()));
        dbConnector.updateQuery(String.format("UPDATE %s SET new = %b WHERE wordID = %d", tableName, word.isNew(), word.getId()));
        dbConnector.exitDB();
    }

    private String comaSearch(String s) {
        return s.replaceAll("\'", "\'\'");
    }
}
