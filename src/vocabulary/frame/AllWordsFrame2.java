package vocabulary.frame;

import vocabulary.manager.AllWordsFrameUniversalManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableStringConverter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Test on 19.05.2016.
 */
public class AllWordsFrame2 extends JFrame {

    private Connection connection;
    private String tableName;
    private final JTextField filterText = new JTextField("Input text");
    private TableRowSorter<TableModel> sorter;
    private Thread thread;

    public AllWordsFrame2(Connection connection, String tableName) throws HeadlessException {
        super("All Words");
        this.connection = connection;
        this.tableName = tableName;
        setFrame();
    }

    private void setFrame() {
        AllWordsFrameUniversalManager manager = null;
        try {
            manager = new AllWordsFrameUniversalManager(connection, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame();

        TableModel model = new DefaultTableModel(manager.getContents(), manager.getColumnNames()) {
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };

        JTable table = new JTable(model);
        sorter = new TableRowSorter<TableModel>(
                model);
        table.setRowSorter(sorter);
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Filter");
        panel.add(label, BorderLayout.WEST);

        filterText.setForeground(Color.GRAY);
        panel.add(filterText, BorderLayout.CENTER);

        JButton button = new JButton("Filter");
        //panel.add(button, BorderLayout.EAST);
        frame.add(panel, BorderLayout.NORTH);

        filterText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (filterText.getText().equals("Input text")) {
                    filterText.setForeground(Color.BLACK);
                    filterText.setText("");
                    //System.out.println("Вход в блок");
                }
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!thread.isInterrupted()) {
                            int i = filterText.getText().length();
                            //System.out.println("Вход в поток");
                            while (!thread.isInterrupted()) {
                                if (i != filterText.getText().length()) {
                                    useFilter();
                                    i = filterText.getText().length();
                                }
                                try {
                                    thread.sleep(10);
                                } catch (InterruptedException e1) {
                                    System.out.println("Поток закрывается? " + thread.isAlive());
                                    //e1.printStackTrace();
                                }
                            }
                        }
                    }
                });
                thread.start();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (filterText.getText().equals("")) {
                    filterText.setForeground(Color.GRAY);
                    filterText.setText("Input text");
                }
                useFilter();
                thread.interrupt();
            }
        });
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                useFilter();
            }
        });
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                stopThread();
                if (connection != null){
                    try {
                        connection.close();
                        System.out.println("Соединение с базой закрыто");
                        System.out.println(thread.isAlive());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                //System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        //frame.add(button, BorderLayout.SOUTH);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private synchronized void useFilter() {
        String text = filterText.getText().toLowerCase();
        if (text.equals("input text")) text = "";
        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setStringConverter(new TableStringConverter() {
                @Override
                public String toString(TableModel model, int row, int column) {
                    return model.getValueAt(row, column).toString().toLowerCase();
                }
            });
            sorter.setRowFilter(RowFilter.regexFilter(text));
            try {
                sorter
                        .setRowFilter(RowFilter
                                .regexFilter(text));
            } catch (PatternSyntaxException pse) {
                System.err.println("Bad regex pattern");
            }
        }
    }

    public synchronized void stopThread() {
        if (thread != null) {
            thread.interrupt();
        }
        //System.out.println("Главное меню остановило поток");
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //System.out.println(thread.isAlive());
    }

}
