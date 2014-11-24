package dropbox.server.Util;

import dropbox.server.Base.Queriable;

import java.sql.*;

/**
 * Created by micky on 2014. 11. 21..
 */
public class DatabaseConnector {
    private static DatabaseConnector connector = null;

    public static DatabaseConnector getConnector() {
        if(connector == null) {
            connector = new DatabaseConnector();
        }

        return connector;
    }

    public final static String DB_ADDR = "jdbc:postgresql://10.0.30.59:5432/dropbox";
    public final static String ID = "postgres";
    public final static String PASSWORD = "qufdmltnarufdldu";

    private Connection conn;


    private DatabaseConnector() {
        connect();
    }

    /**
     * 데이터 베이스와 연결
     */
    private void connect() {
        try {
            conn = DriverManager.getConnection(DB_ADDR, ID, PASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String selectquery) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(selectquery);
        return rs;
    }

    public boolean modify(String query) {
        return modify(query, true);
    }

    public boolean modify(String query, boolean commit) {
        boolean result = false;
        Logger.logging("modify query:" +query);
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            result = true;
            if(commit) commit();
        } catch (SQLException e) {
            Logger.errorLogging(e);
        }

        return result;
    }

    public boolean insert(Queriable infoObject) {
        return modify(infoObject.getInsertQueryString());
    }

    public boolean insert(Queriable infoObject, boolean commit) {
        return modify(infoObject.getInsertQueryString(), commit);
    }

    public void commit() throws SQLException {
        conn.commit();
    }
    public void rollback() throws SQLException {
        conn.rollback();
    }

}
