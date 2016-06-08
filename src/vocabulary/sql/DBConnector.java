package vocabulary.sql;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Test on 09.05.2016.
 */
public class DBConnector {

    private String host;
    private String root;
    private String password;
    private String nameDB;
    private String url;
    private Connection mysqlConnect;

    private Properties properties = new Properties();

    public DBConnector() {
        initData();
        initURL();
        initProperties();
        init();
    }

    public DBConnector(String host, String root, String password, String nameDB) {
        this.host = host;
        this.root = root;
        this.password = password;
        this.nameDB = nameDB;
        initURL();
        initProperties();
        init();

    }

    public DBConnector(String url, Properties properties) {
        this.url = url;
        this.properties = properties;
        init();
    }

    public Connection getMysqlConnect() {
        return mysqlConnect;
    }

    private void initData() {
        try (Scanner scn = new Scanner(new File("DBProperties.txt"))) {
            host = scn.next();
            root = scn.next();
            password = scn.next();
            nameDB = scn.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
    }

    private void initURL() {
        url = "jdbc:mysql://" + host + "/" + nameDB;
    }

    public void initProperties() {
        properties.setProperty("user", root);
        properties.setProperty("password", password);
        properties.setProperty("characterEncoding", "UTF-8");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("useSSL", "true");
    }

    public void init() {
        if (mysqlConnect == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                mysqlConnect = DriverManager.getConnection(url, properties);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
            }
        }
    }

    public void exitDB() {
        try {
            mysqlConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
    }

    public ResultSet query(String query) {
        ResultSet result = null;
        try {
            Statement stmt = mysqlConnect.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
        return result;
    }


    public void updateQuery(String query) {
        try {
            Statement stmt = mysqlConnect.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
        }
    }
}
