package gb.model;

import gb.model.annotation.Column;
import gb.model.annotation.Table;
import gb.model.entity.Student;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Enyity2JDBC {

    private final JDBC jdbc;

    public Enyity2JDBC(JDBC jdbc) {
        this.jdbc = jdbc;
    }


    public String getAllStudents(String db) {
        try {
            ResultSet resultSet = jdbc.sqlExecuteQueryEntity(db, selectInstanceAll(Student.class));

            List<Student> studentList;
            try {
                studentList = getStudents(resultSet);
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                return e.getMessage();
            }
            StringBuilder sb = new StringBuilder();
            for (Student student :studentList) {
                sb.append(student.toString()).append("\n");
            }
            resultSet.close();
            return sb.toString();
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    private List<Student> getStudents(ResultSet resultSet) throws SQLException, NoSuchFieldException, IllegalAccessException {
        List<Student> studentList = new ArrayList<>();
        while (resultSet.next()) {
            Student student =new Student();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                String name = resultSet.getMetaData().getColumnName(i).toLowerCase();
                Field field = student.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(student, resultSet.getObject(i));
            }
            studentList.add(student);
        }
        return studentList;
    }

    private String selectInstanceAll(Class<Student> studentClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        Field[] fields = studentClass.getDeclaredFields();
        for (Field field: fields) {
            sb.append(field.getDeclaredAnnotation(Column.class).name());
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(" FROM ")
                .append(studentClass.getAnnotation(Table.class).name());
        return sb.toString();
    }

    public String getStudentById(String db, int id) {
        try {
            ResultSet resultSet = jdbc.sqlExecuteQueryEntity(db, selectInstanceById(Student.class, id));

            List<Student> studentList;
            try {
                studentList = getStudents(resultSet);
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                return e.getMessage();
            }
            StringBuilder sb = new StringBuilder();
            for (Student student :studentList) {
                sb.append(student.toString()).append("\n");
            }
            resultSet.close();
            return sb.toString();
        } catch (SQLException e) {
            return e.getMessage();
        }
    }


    private String selectInstanceById(Class<Student> studentClass, int id ) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        Field[] fields = studentClass.getDeclaredFields();
        for (Field field: fields) {
            sb.append(field.getDeclaredAnnotation(Column.class).name());
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(" FROM ")
                .append(studentClass.getAnnotation(Table.class).name())
                .append(" WHERE id = ")
                .append(id);
        return sb.toString();
    }

    public String addAndSaveNewStudent(String choiceDB, String[] newStudent) {
        Student student;
        try {
            student = new Student(-1, newStudent[0]
                    , newStudent[1]
                    , Integer.parseInt(newStudent[2]));
        } catch (RuntimeException e) {
            return "Введенные данные студента некорректны " + e.getMessage();
        }

        StringBuilder sb_main = new StringBuilder();
        StringBuilder sb_fields = new StringBuilder();
        StringBuilder sb_value = new StringBuilder();
        Field[] fields = student.getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            sb_fields.append(fields[i].getDeclaredAnnotation(Column.class).name());
            if (i < fields.length - 1) {
                sb_fields.append(", ");
            }
            if (fields[i].getType().equals(String.class)) {
                sb_value.append("'");
            }
            try {
                sb_value.append(fields[i].get(student));
            } catch (IllegalAccessException e) {
                return e.getMessage();
            }
            if (fields[i].getType().equals(String.class)) {
                sb_value.append("'");
            }
            if (i < fields.length - 1) {
                sb_value.append(", ");
            }
        }
        sb_main.append("INSERT INTO ")
                .append(student.getClass().getAnnotation(Table.class).name())
                .append(" (")
                .append(sb_fields)
                .append(") VALUES (")
                .append(sb_value)
                .append(")");

        return jdbc.sqlExecuteUpdate(choiceDB, sb_main.toString());
    }

}