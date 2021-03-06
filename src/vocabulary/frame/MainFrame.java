package vocabulary.frame;

import vocabulary.Words.Word;
import vocabulary.manager.AllWordsManager;
import vocabulary.manager.Import;
import vocabulary.manager.InsertManager;
import vocabulary.sql.DBConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Test on 09.05.2016.
 */
public class MainFrame extends JFrame {

    protected Button editButton = new Button("Edit/Add/Delete");
    protected Button importButton = new Button("Import");
    protected Button loadButton = new Button("Load");
    private Button remain = new Button("Remain");
    private Button vocSize = new Button("Total size");
    private Button engB = new Button("English word");
    private Button rusB = new Button("Russian word");
    private Button nextB = new Button("Next word");
    private Button repeatB = new Button("Repeat word");
    private Button showAllB = new Button("Show all");
    protected Button loadIDB = new Button("Load ID");
    protected JLabel engL = new JLabel();
    protected JLabel rusL = new JLabel();
    protected String[] filter = new String[]{"All", "New", "Old"};
    protected JComboBox comboBox = new JComboBox(filter);
    protected ArrayList<Word> wordList = new ArrayList<>();
    protected Word word = null;
    protected String tableName = "vocabulary";

    public MainFrame() {
        super("Vocabulary_v3.0.0");
        setFrame();
        setElements();
        addListeners();
        setVisible(true);
    }

    protected void setFrame() {

        setSize(700, 700);
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
    }

    protected void setElements() {
        JPanel langPanel = new JPanel();
        langPanel.setLayout(new GridLayout(1, 2, 10, 10));
        engL.setHorizontalAlignment(JLabel.CENTER);
        rusL.setHorizontalAlignment(JLabel.CENTER);
        langPanel.add(engL);
        langPanel.add(rusL);

        JPanel selectL = new JPanel();
        selectL.setLayout(new GridLayout(1, 2, 10, 10));
        selectL.add(engB);
        selectL.add(rusB);

        JPanel nextW = new JPanel();
        nextW.setLayout(new GridLayout(1, 2, 10, 10));
        nextW.add(repeatB);
        nextW.add(nextB);

        JPanel showAll = new JPanel();
        showAll.setLayout(new GridLayout(1, 2, 10, 10));
        showAll.add(showAllB);
        showAll.add(loadIDB);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 10, 10));
        mainPanel.add(loadButton);
        mainPanel.add(comboBox);
        comboBox.setSelectedIndex(1);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10));
        panel.add(editButton);
        panel.add(importButton);

        panel.add(remain);
        panel.add(vocSize);

        getContentPane().add(langPanel);
        getContentPane().add(selectL);
        getContentPane().add(nextW);
        getContentPane().add(showAll);
        getContentPane().add(mainPanel);
        getContentPane().add(panel);
    }


    protected void addListeners() {
        loadIDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(null, "Input Word ID");
                try {
                    int id = Integer.parseInt(s);
                    word = new AllWordsManager().searchWord(id, "vocabulary");
                    if (word != null) {
                        engL.setText(word.getEng());
                        rusL.setText(word.getRus());
                        JOptionPane.showMessageDialog(null, "The word is selected!");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "The word is not founded!");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "You need to input number!");
                }
            }
        });
        showAllB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //AllWordsFrame frame = new AllWordsFrame();
                AllWordsFrame2 frame = new AllWordsFrame2(new DBConnector().getMysqlConnect(), tableName);
            }
        });
        repeatB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordList.size() != 0 || word != null) {
                    wordList.add(word);
                    word = new Word(1, null, null, null);
                    engL.setText("");
                    rusL.setText("");
                    word = null;
                }
            }
        });
        nextB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engL.setText("");
                rusL.setText("");
                if (wordList.size() != 0) {
                    word = null;
                } else JOptionPane.showMessageDialog(null, "No words! Load the words!");
            }
        });
        rusB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordList.size() == 0 && word == null) {
                    JOptionPane.showMessageDialog(null, "No words! Load the words!");
                    return;
                }
                if (word == null) {
                    word = wordList.get(0);
                    wordList.remove(0);
                    rusL.setText(word.getRus());
                } else {
                    rusL.setText(word.getRus());
                    //word = null;
                }
            }
        });
        engB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordList.size() == 0 && word == null) {
                    JOptionPane.showMessageDialog(null, "No words! Load the words!");
                    return;
                }
                if (word == null) {
                    word = wordList.get(0);
                    wordList.remove(0);
                    engL.setText(word.getEng());
                } else {
                    engL.setText(word.getEng());
                    //word = null;
                }
            }
        });
        vocSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, new AllWordsManager().getTotalSize(tableName, filter));
            }
        });
        remain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, wordList.size() + " words are remained.");
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = null;
                engL.setText("");
                rusL.setText("");
                String s = comboBox.getSelectedItem().toString();
                AllWordsManager allWordsManager = new AllWordsManager();
                wordList = new ArrayList();
                wordList.addAll(allWordsManager.loadAndShuffle(tableName, s));
                JOptionPane.showMessageDialog(null, wordList.size() + " words are loaded");
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeWordFrame changeWord;
                if (word == null) {
                    changeWord = new ChangeWordFrame(wordList, filter);
                } else {
                    changeWord = new ChangeWordFrame(word, filter);

                    word = null;
                    engL.setText("");
                    rusL.setText("");
                }

            }
        });
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Import i = new Import(tableName);
                i.importFile();
            }
        });
    }
}
