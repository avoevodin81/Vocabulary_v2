package vocabulary.frame;

import vocabulary.frame.MainFrame;
import vocabulary.manager.URLOpener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class MainFrame2 extends MainFrame {



    private Button openBrowserButton;

    @Override
    protected void setFrame() {
        setSize(700, 700);
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));
    }

    @Override
    protected void setElements() {
        initComboBox();
        super.setElements();
        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new GridLayout(1, 1, 10, 10));
        openBrowserButton = new Button("Open Browser");
        urlPanel.add(openBrowserButton);

        super.getContentPane().add(urlPanel);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        openBrowserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (word == null) {
                        JOptionPane.showMessageDialog(null, "Select the word");
                    } else {
                        URLOpener.openURL(word.getEng());
                    }
                } catch (NullPointerException z) {
                    z.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Something went wrong! " + z.toString());
                }
            }
        });
    }

    private void initComboBox () {
        ArrayList<String> filters = new ArrayList<>();
        try (Scanner scn = new Scanner(new File("Filters.txt"))) {
            while (scn.hasNext()){
                filters.add(scn.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
        filter = filters.toArray(new String[filters.size()]);
        comboBox = new JComboBox(filter);
    }
}
