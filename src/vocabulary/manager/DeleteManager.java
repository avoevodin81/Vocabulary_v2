package vocabulary.manager;

import vocabulary.Words.Word;
import vocabulary.sql.DBConnector;

/**
 * Created by Test on 11.05.2016.
 */
public class DeleteManager {

    private Word word;

    public DeleteManager(Word word) {
        this.word = word;
    }

    public void deleteWord(String tableName){
        DBConnector dbConnector = new DBConnector();
        dbConnector.updateQuery(String.format("DELETE FROM %s WHERE wordId = %d", tableName, word.getId()));
        dbConnector.exitDB();
    }
}
