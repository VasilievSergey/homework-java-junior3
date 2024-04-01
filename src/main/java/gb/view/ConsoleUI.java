package gb.view;

import gb.presenter.Presenter;
import gb.view.keyboard.StringInputtedByUser;

/**
 * Консольное взаимодействие
 */
public class ConsoleUI implements View{
    private final Presenter presenter;
    private final StringInputtedByUser sqlByUser;

    /**
     * sqlByUser - тестовый заменитель ввода данных с консоли
     */
    public ConsoleUI(){
        presenter = new Presenter(this);
        sqlByUser = new StringInputtedByUser();
    }

    /**
     * Вывод в консоль, полученных результатов
     * @param answer
     */
    @Override
    public void printAnswer(String answer) {
        System.out.println(answer);
    }

    /**
     * Последоватьный Стартер программы:
     * - устанавливается соединение с Н2;
     * - устанавливается соединение с PostgreSQL;
     * - выполняются запросы от пользователя;
     * - закрывается соединение с Н2;
     * - закрывается соединение с PostgreSQL;
     */
    public void run() {
        setH2Connection();
        setPostgresConnection();
        getQueriesFromUser();
        closeH2Connection();
        closePostgresConnection();
    }

    /**
     * Установка соединения с Н2
     */
    private void setH2Connection() {
        presenter.setH2Connection(sqlByUser.connectToH2());
    }

    /**
     * Установка соединения с PostgreSQL;
     */
    private void setPostgresConnection() {
        presenter.setPostgresConnection(sqlByUser.connectToPostgres());
    }

    /**
     * Закрытие соединения с Н2
     */
    private void closeH2Connection() {
        presenter.closeH2Connection();
    }

    /**
     * Закрытие соединнения с PostgreSQL;
     */

    private void closePostgresConnection() {
        presenter.closePostgresConnection();
    }

    /**
     * Выполнение запросов от пользовавтеля
     */
    private void getQueriesFromUser() {

        sqlExecute(sqlByUser.choiceH2(), sqlByUser.dropTable());
        sqlExecute(sqlByUser.choicePostgres(), sqlByUser.dropTable());

        sqlExecute(sqlByUser.choiceH2(), sqlByUser.createTable());
        sqlExecute(sqlByUser.choicePostgres(), sqlByUser.createTable());

        sqlExecute(sqlByUser.choiceH2(), sqlByUser.insertData());
        sqlExecute(sqlByUser.choicePostgres(), sqlByUser.insertData());


        sqlExecuteQuery(sqlByUser.choiceH2(), sqlByUser.selectAll());
        sqlExecuteQuery(sqlByUser.choicePostgres(), sqlByUser.selectAll());

        sqlExecuteUpdate(sqlByUser.choiceH2(), sqlByUser.deleteRandomRowByString());
        sqlExecuteQuery(sqlByUser.choiceH2(), sqlByUser.selectAll());
        sqlExecuteUpdate(sqlByUser.choicePostgres(), sqlByUser.deleteRandomRowByString());
        sqlExecuteQuery(sqlByUser.choicePostgres(), sqlByUser.selectAll());

        sqlExecuteUpdate(sqlByUser.choiceH2(), sqlByUser.updateRowByString());
        sqlExecuteQuery(sqlByUser.choiceH2(), sqlByUser.selectAll());
        sqlExecuteUpdate(sqlByUser.choicePostgres(), sqlByUser.updateRowByString());
        sqlExecuteQuery(sqlByUser.choicePostgres(), sqlByUser.selectAll());

        sqlExecuteUpdate(sqlByUser.choiceH2()
                , sqlByUser.deleteByKeyValue()[0]
                , sqlByUser.deleteByKeyValue()[1]);
        sqlExecuteQuery(sqlByUser.choiceH2(), sqlByUser.selectAll());

        sqlExecuteUpdate(sqlByUser.choicePostgres()
                , sqlByUser.deleteByKeyValue()[0]
                , sqlByUser.deleteByKeyValue()[1]);
        sqlExecuteQuery(sqlByUser.choicePostgres(), sqlByUser.selectAll());

        sqlExecuteUpdate(sqlByUser.choiceH2()
                , sqlByUser.updateRowByKV()[0]
                , sqlByUser.updateRowByKV()[1]
                , sqlByUser.updateRowByKV()[2]
                , sqlByUser.updateRowByKV()[3]);
        sqlExecuteQuery(sqlByUser.choiceH2(), sqlByUser.selectAll());

        sqlExecuteUpdate(sqlByUser.choicePostgres()
                , sqlByUser.updateRowByKV()[0]
                , sqlByUser.updateRowByKV()[1]
                , sqlByUser.updateRowByKV()[2]
                , sqlByUser.updateRowByKV()[3]);
        sqlExecuteQuery(sqlByUser.choicePostgres(), sqlByUser.selectAll());

        getAllStudents(sqlByUser.choiceH2());
        getStudentByID(sqlByUser.choiceH2(), 4);

        addAndSaveNewStudent(sqlByUser.choiceH2(), sqlByUser.newStudent());
        getAllStudents(sqlByUser.choiceH2());

    }


    private void sqlExecute(String choiceDB, String usersMessage) {
        presenter.sqlExecute(choiceDB, usersMessage);
    }

    private void sqlExecuteQuery(String choiceDB, String usersMessage) {
        presenter.sqlExecuteQuery(choiceDB, usersMessage);
    }

    private void sqlExecuteUpdate(String choiceDB, String usersMessage) {
        presenter.sqlExecuteUpdate(choiceDB, usersMessage);
    }

    private void sqlExecuteUpdate(String choiceDB, String usersMessage, String value) {
        presenter.sqlExecuteUpdate(choiceDB, usersMessage, value);
    }

    private void sqlExecuteUpdate(String choiceDB, String usersMessage1, String value1, String usersMessage2, String value2) {
        presenter.sqlExecuteUpdate(choiceDB, usersMessage1, value1, usersMessage2, value2);
    }

    private void getAllStudents(String choiceDB) {
        presenter.getAllStudents(choiceDB);
    }

    private void getStudentByID(String choiceDB, int id) {
        presenter.getStudentByID(choiceDB, id);
    }

    private void addAndSaveNewStudent(String choiceDB
            , String[] newStudent) {
        presenter.addAndSaveNewStudent(choiceDB, newStudent);
    }


}