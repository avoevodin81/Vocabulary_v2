package vocabulary.frame;

import vocabulary.Words.Word;
import vocabulary.manager.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * Created by Test on 09.05.2016.
 */
public class ChangeWordFrame extends JFrame {
    protected ArrayList<Word> words;
    protected Word word;
    protected JLabel label = new JLabel();
    protected JTextField rusF = new JTextField();
    protected JTextField engF = new JTextField();
    protected JComboBox comboBox = new JComboBox(new String[]{"true", "false", "delete"});
    protected Button changeButton = new Button("Edit/Add/Delete");
    protected String tableName = "vocabulary";

    public ChangeWordFrame(Word word) throws HeadlessException {
        super("Change");
        this.word = word;

        label = new JLabel(String.valueOf(word.getId()));
        engF = new JTextField(word.getEng());
        rusF = new JTextField(word.getRus());

        init();

        if (word.isNew()) {
            comboBox.setSelectedIndex(0);
        } else comboBox.setSelectedIndex(1);

        setElements();
        addListeners();
        setVisible(true);
    }

    public ChangeWordFrame(ArrayList<Word> words) throws HeadlessException {
        super("New");
        this.words = words;
        init();
        setElements();
        addListeners();
        setVisible(true);
    }

    private void init() {

        label.setHorizontalAlignment(SwingConstants.CENTER);
        engF.setHorizontalAlignment(JTextField.CENTER);
        rusF.setHorizontalAlignment(JTextField.CENTER);
        if (engF.getText().equals("")) {
            engF.setForeground(Color.GRAY);
            engF.setText("English word");
        }
        if (rusF.getText().equals("")) {
            rusF.setForeground(Color.GRAY);
            rusF.setText("Russian word");
        }
        if (label.getText().equals("")){
            label.setText("id");
        }

    }

    protected void setElements() {

        setSize(700, 90);
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5));

        panel.add(label);
        panel.add(engF);
        panel.add(rusF);
        panel.add(comboBox);
        panel.add(changeButton);

        getContentPane().add(panel);
    }

    public Word getWord() {
        return word;
    }

    protected void addListeners() {
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedItem().toString().equals("delete")) {
                    new DeleteManager(word).deleteWord(tableName);
                    JOptionPane.showMessageDialog(null, String.format("Word (%s - %s) is deleted", word.getEng(), word.getRus()));
                    label.setText("");
                    rusF.setText("");
                    engF.setText("");
                    comboBox.setSelectedIndex(2);
                    setVisible(false);
                    dispose();
                } else if (word == null) {
                    String eng = engF.getText();
                    String rus = rusF.getText();
                    if (eng.equals("") || rus.equals("")) {
                        JOptionPane.showMessageDialog(null, "Not all fields are filled");
                    } else {
                        word = new Word(1, engF.getText(), rusF.getText(), Boolean.parseBoolean(comboBox.getSelectedItem().toString()));
                        if (new AllWordsManager().isInBase(word, tableName)) {
                            JOptionPane.showMessageDialog(null, String.format("Word (%s or %s) is already in the dictionary!", engF.getText(), rusF.getText()));
                            word = null;
                            return;
                        }
                        new InsertManager().insert(word, tableName);
                        JOptionPane.showMessageDialog(null, String.format("Word (%s - %s) is added", word.getEng(), word.getRus()));
                        words.add(word);
                        setVisible(false);
                        dispose();
                    }
                } else {
                    new UpdateManager().update(new Word(Integer.parseInt(label.getText()), engF.getText(), rusF.getText(), Boolean.parseBoolean(comboBox.getSelectedItem().toString())), tableName);
                    JOptionPane.showMessageDialog(null, String.format("Word (%s - %s) is updated", word.getEng(), word.getRus()));
                    word = null;
                    setVisible(false);
                    dispose();
                }
            }
        });
        engF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (engF.getText().equals("English word")) {
                    engF.setForeground(Color.BLACK);
                    engF.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (engF.getText().equals("")) {
                    engF.setForeground(Color.GRAY);
                    engF.setText("English word");
                }
            }
        });
        rusF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (rusF.getText().equals("Russian word")) {
                    rusF.setForeground(Color.BLACK);
                    rusF.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (rusF.getText().equals("")) {
                    rusF.setForeground(Color.GRAY);
                    rusF.setText("Russian word");
                }
            }
        });
    }
}
