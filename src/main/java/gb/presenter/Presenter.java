package gb.presenter;

import gb.model.Enyity2JDBC;
import gb.model.JDBC;
import gb.view.View;


public class Presenter {
    private final View view;
    private final JDBC jdbc;
    private final Enyity2JDBC enyity2JDBC;
    public Presenter(View view) {
        this.view = view;
        this.jdbc = new JDBC();
        this.enyity2JDBC = new Enyity2JDBC(jdbc);
    }

    public void setH2Connection(String h2Schema) {
        String answer = jdbc.setH2Connection(h2Schema);
        view.printAnswer(answer);
    }

    public void setPostgresConnection(String s) {
        String answer = jdbc.setPostgresConnection(s);
        view.printAnswer(answer);
    }

    public void closeH2Connection() {
        String answer = jdbc.closeH2Connection();
        view.printAnswer(answer);
    }

    public void closePostgresConnection() {
        String answer = jdbc.closePostgresConnection();
        view.printAnswer(answer);
    }



    public void sqlExecute(String choiceDB, String usersMessage) {
        String answer = jdbc.sqlExecute(choiceDB, usersMessage);
        view.printAnswer(answer);
    }

    public void sqlExecuteQuery(String choiceDB, String usersMessage) {
        String answer = jdbc.sqlExecuteQuery(choiceDB, usersMessage);
        view.printAnswer(answer);
    }


    public void sqlExecuteUpdate(String choiceDB, String usersMessage) {
        String answer = jdbc.sqlExecuteUpdate(choiceDB, usersMessage);
        view.printAnswer(answer);
    }

    public void sqlExecuteUpdate(String choiceDB, String usersMessage, String value) {
        String answer = jdbc.sqlExecuteUpdate(choiceDB, usersMessage, value);
        view.printAnswer(answer);
    }
    public void sqlExecuteUpdate(String choiceDB, String usersMessage1, String value1, String usersMessage2, String value2) {
        String answer = jdbc.sqlExecuteUpdate(choiceDB, usersMessage1, value1, usersMessage2, value2);
        view.printAnswer(answer);
    }

    public void getAllStudents(String choiceDB) {
        String answer = enyity2JDBC.getAllStudents(choiceDB);
        view.printAnswer(answer);
    }


    public void getStudentByID(String choiceDB, int id) {
        String answer = enyity2JDBC.getStudentById(choiceDB, id);
        view.printAnswer(answer);
    }

    public void addAndSaveNewStudent(String choiceDB, String[] newStudent) {
        String answer = enyity2JDBC.addAndSaveNewStudent(choiceDB, newStudent);
        view.printAnswer(answer);
    }
}