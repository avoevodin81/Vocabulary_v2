package vocabulary.frame;

import vocabulary.manager.AllWordsManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Test on 09.05.2016.
 */
public class AllWordsFrame extends JFrame {
    private final String[] title = {"Id", "English", "Russian", "New", "#Num"};
    private String tableName = "vocabulary";

    public AllWordsFrame() throws HeadlessException {
        super("All words");
        setSize(800, 800);
        setLocationRelativeTo(null);

        AllWordsManager allWordsManager = new AllWordsManager();
        String[][] temp = allWordsManager.getAllTableWords(tableName);

        JTable table = new JTable(temp, title);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
