package vocabulary.manager;

import vocabulary.Words.Word;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Test on 12.05.2016.
 */
public class Import {
    private String tableName;

    public Import(String tableName) {
        this.tableName = tableName;
    }

    public void importFile() {
        int i = 0;
        JFileChooser fileChooser = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter("TXT file", "txt");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.addChoosableFileFilter(fileFilter);

        int ret = fileChooser.showDialog(null, "Select file");

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileReader fr = new FileReader(file)) {
                String s = "";
                while (fr.ready()) {
                    char temp = (char) fr.read();
                    if (temp == '\r') {
                        i++;
                        String[] strings = s.split(";");
                        Word word = new Word(1, strings[0], strings[1], Boolean.parseBoolean(strings[2]));
                        new InsertManager().insert(word, tableName);
                        s = "";
                    } else if (temp == '\n') {
                        continue;
                    } else if (temp == '\'') {
                        s += "\'" + temp;
                    } else s += "" + temp;
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong! " + e1.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong! " + e1.toString());
            }
            JOptionPane.showMessageDialog(null, "File is loaded. " + i + " words a added.");
        }
    }


}
