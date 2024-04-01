package gb.model.db;

import java.sql.*;

public class BaseDB implements DataBaseUsing{
    private volatile Connection connection;

    @Override
    public void connect(String url) throws SQLException {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void connect(String url, String user, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public Boolean isConnection() throws SQLException{
        try {
            return connection != null || connection.isValid(5);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void sqlExecute(String usersMessage) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(usersMessage);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public String sqlExecuteQuery(String usersMessage){
        StringBuilder sb = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(usersMessage);
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                sb.append(resultSet.getMetaData().getColumnLabel(i)).append("\t");
            }
            sb.append("\n");
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    sb.append(resultSet
                            .getObject(i)
                            .getClass()
                            .cast(resultSet.getObject(i))).append("\t");
                }
                sb.append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public  ResultSet sqlExecuteQueryEntity(String usersMessage) throws SQLException{
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(usersMessage);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void sqlExecuteUpdate(String usersMessage) throws SQLException{
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(usersMessage);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void sqlExecuteUpdate(String usersMessage, String value) throws SQLException{
        try (PreparedStatement stmt = connection.prepareStatement(usersMessage + "$1" )) {
            stmt.setNString(1, value);
            stmt.executeUpdate();
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void sqlExecuteUpdate(String usersMessage1, String value1, String usersMessage2, String value2) throws SQLException{
        try (PreparedStatement stmt = connection.prepareStatement(usersMessage1 + "$1"
                + usersMessage2 + "$2")) {
            stmt.setNString(1, value1);
            stmt.setNString(2, value2);
            stmt.executeUpdate();
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}