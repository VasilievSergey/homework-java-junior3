package gb.model.db;

import java.sql.SQLException;

public interface DataBaseUsing {
    void connect(String url) throws SQLException;
    void connect(String dataBaseURL, String user, String password) throws SQLException;
    void closeConnection() throws SQLException;
    Boolean isConnection() throws SQLException;
    void sqlExecute(String usersMessage) throws SQLException;
    String sqlExecuteQuery(String usersMessage);
    void sqlExecuteUpdate(String usersMessage) throws SQLException;
    void sqlExecuteUpdate(String usersMessage, String value) throws SQLException;

    void sqlExecuteUpdate(String usersMessage1, String value1, String usersMessage2, String value2) throws SQLException;

}