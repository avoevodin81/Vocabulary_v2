package vocabulary.manager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Test on 18.05.2016.
 */
public class AllWordsFrameUniversalManager extends AbstractTableModel {

    private Object[][] contents;
    private String[] columnNames;
    private Class[] columnClasses;

    public AllWordsFrameUniversalManager(Connection connection, String tableName) throws SQLException {
        super();
        getTableContents(connection, tableName);
        connection.close();
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object[][] getContents() {
        return contents;
    }

    private void getTableContents(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();

        ResultSet rs = meta.getColumns(null, null, tableName, null);

        ArrayList colNamesList = new ArrayList();
        ArrayList colTypesList = new ArrayList();

        while (rs.next()){
            colNamesList.add(rs.getString("COLUMN_NAME"));
            int dbType = rs.getInt("DATA_TYPE");

            switch (dbType){
                case Types.INTEGER:
                    colTypesList.add(Integer.class);
                    break;
                case Types.BOOLEAN:
                    colTypesList.add(Boolean.class);
                    break;
                default:
                    colTypesList.add(String.class);
                    break;
            }
        }

        columnNames = new String[colNamesList.size()];
        colNamesList.toArray(columnNames);

        columnClasses = new Class[colTypesList.size()];
        colTypesList.toArray(columnClasses);

        Statement statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM " + tableName);

        ArrayList rowList = new ArrayList();

        while (rs.next()){
            ArrayList cellList = new ArrayList();

            for (int i = 0; i < columnClasses.length; i++){
                Object cellValue = null;

                if (columnClasses[i] == String.class) cellValue = rs.getString(columnNames[i]);
                else if (columnClasses[i] == Boolean.class)  cellValue = rs.getBoolean(columnNames[i]);
                else cellValue = rs.getInt(columnNames[i]);

                cellList.add(cellValue);
            }

            Object[] cells = cellList.toArray();
            rowList.add(cells);
        }

        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++){
            contents[i] = (Object[]) rowList.get(i);
        }
        rs.close();
        statement.close();
    }

    @Override
    public int getRowCount() {
        return contents.length;
    }

    @Override
    public int getColumnCount() {
        if (contents.length == 0){
            return 0;
        }
        return contents[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return contents[rowIndex][columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
