package gb.model;

import gb.model.db.BaseDB;

import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBC {
    private final BaseDB h2 = new BaseDB();
    private final BaseDB postgres = new BaseDB();
    private static final String NODBERROR = "\nБаза данных не создавалась: ";
    private static final String DBCONNECTIONERROR = "\nОшибка подключения к базе данных:";


    public String setH2Connection(String h2Schema) {
        try {
            h2.connect("jdbc:h2:" + h2Schema);
        } catch (SQLException e) {
            return DBCONNECTIONERROR + e.getMessage();
        }
        return String.format("Вы успешно подключились к базе данных H2.\n" +
                "Наименование: %s.", h2Schema);
    }

    public String setPostgresConnection(String port) {
        try {
            postgres.connect("jdbc:postgresql:" + port, "admin", "admin");
        } catch (SQLException e) {
            return DBCONNECTIONERROR + e.getMessage();
        }
        return String.format("""

                Вы успешно подключились к базе данных Postgresql.
                По адресу: %s.""", port);
    }

    public String closeH2Connection() {
        try {
            h2.closeConnection();
            return "\nСоединение с базой данных Н2 успешно закрыто.";
        } catch (SQLException e) {
            return "\nОшибка закрытия соединения:\n" + e.getMessage();
        }
    }

    public String closePostgresConnection() {
        try {
            if (postgres.isConnection()) {
                try {
                    postgres.closeConnection();
                    return "\nСоединение с базой данных Postgres успешно закрыто.";
                } catch (SQLException e) {
                    return "\nОшибка закрытия соединения:\n" + e.getMessage();
                }
            }
        } catch (SQLException e) {
            return "\nОшибка закрытия соединения:\n" + e.getMessage();
        }
        return "\nCоединение с БД отсутствует:\n";
    }

    private BaseDB getDbInstance(String choiceDB) {
        return switch (choiceDB.toLowerCase()) {
            case "h2" -> h2;
            case "postgres" -> postgres;
            default -> null;
        };
    }

    public String sqlExecute(String choiceDB, String usersMessage) {
        BaseDB db = getDbInstance(choiceDB);
        if (db != null) {
            try {
                if (db.isConnection()) {
                    try {
                        db.sqlExecute(usersMessage);
                        return "\n" + choiceDB + ": Запрос успешно выполнен:\n" + usersMessage;
                    } catch (SQLException e) {
                        return "\n" + choiceDB + ": Ошибка выполнения запроса:\n" + e.getMessage();
                    }
                }
            } catch (SQLException e) {
                return DBCONNECTIONERROR + e.getMessage();
            }
        }
        return NODBERROR + choiceDB;
    }

    public String sqlExecuteQuery(String choiceDB, String usersMessage) {
        BaseDB db = getDbInstance(choiceDB);
        if (db != null) {
            try {
                if (db.isConnection()) {
                    return "\n" + choiceDB + ": Запрос успешно выполнен:\n" + usersMessage
                            + "\n" + db.sqlExecuteQuery(usersMessage);
                }
            } catch (SQLException e) {
                return DBCONNECTIONERROR + e.getMessage();
            }
        }
        return NODBERROR + choiceDB;
    }

    public ResultSet sqlExecuteQueryEntity(String choiceDB, String usersMessage) throws SQLException{
        BaseDB db = getDbInstance(choiceDB);
        if (db != null) {
            try {
                if (db.isConnection()) {
                    return db.sqlExecuteQueryEntity(usersMessage);
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
        throw new SQLException(NODBERROR + choiceDB);
    }


    public String sqlExecuteUpdate(String choiceDB, String usersMessage) {
        BaseDB db = getDbInstance(choiceDB);
        if (db != null) {
            try {
                if (db.isConnection()) {
                    try {
                        db.sqlExecuteUpdate(usersMessage);
                        return "\n" + choiceDB + ": Запрос на изменение записи успешно выполнен:\n" + usersMessage;
                    } catch (SQLException e) {
                        return "\n" + choiceDB + ": Ошибка выполнения запроса на изменение записи:\n" + e.getMessage();
                    }
                }
            } catch (SQLException e) {
                return DBCONNECTIONERROR + e.getMessage();
            }
        }
        return NODBERROR + choiceDB;
    }


    public String sqlExecuteUpdate(String choiceDB, String usersMessage, String value) {
        BaseDB db = getDbInstance(choiceDB);
        if (db != null) {
            try {
                if (db.isConnection()) {
                    try {
                        db.sqlExecuteUpdate(usersMessage, value);
                        return "\n" + choiceDB + ": Запрос на удаление записи успешно выполнен:\n";
                    } catch (SQLException e) {
                        return "\n" + choiceDB + ": Ошибка выполнения запроса на удаление записи:\n" + e.getMessage();
                    }
                }
            } catch (SQLException e) {
                return DBCONNECTIONERROR + e.getMessage();
            }
        }

        return NODBERROR + choiceDB;
    }

    public String sqlExecuteUpdate(String choiceDB
            , String usersMessage1
            , String value1
            , String usersMessage2
            , String value2) {
        BaseDB db = getDbInstance(choiceDB);
        if (db != null) {
            try {
                if (db.isConnection()) {
                    try {
                        db.sqlExecuteUpdate(usersMessage1, value1, usersMessage2, value2);
                        return "\n" + choiceDB + ": Запрос на изменение записи успешно выполнен:\n";
                    } catch (SQLException e) {
                        return "\n" + choiceDB + ": Ошибка выполнения запроса на изменение записи:\n" + e.getMessage();
                    }
                }
            } catch (SQLException e) {
                return DBCONNECTIONERROR + e.getMessage();
            }
        }
        return NODBERROR + choiceDB;
    }


}