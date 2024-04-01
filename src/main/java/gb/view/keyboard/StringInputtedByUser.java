package gb.view.keyboard;

/**
 * Тестовый заменитель ввода данных от пользователя
 */
public class StringInputtedByUser {

    public String connectToH2() {
        return "mem:test";
    }

    public String connectToPostgres() {
        return "//5.44.168.153/";
    }
    public String choiceH2() {
        return "H2";
    }
    public String choicePostgres() {
        return "postgres";
    }


    public String createTable() {
        return """
                    CREATE TABLE students (
                        id SERIAL PRIMARY KEY,
                        firstName VARCHAR(30),
                        secondName VARCHAR(30),
                        age INT
                    )
                    """;
    }

    public String dropTable() {
        return "DROP TABLE IF EXISTS students";
    }

    public String insertData() {
        return """
                INSERT INTO students (firstName, secondName, age)
                VALUES
                    ('Ivan', 'Ivanov', 20),
                    ('Petr', 'Petrov', 25),
                    ('Oleg', 'Olegov', 30),
                    ('Semen', 'Semenov', 35),
                    ('Egor', 'Egorov', 40),
                    ('Olga', 'Olgova', 45),
                    ('Vera', 'Verova', 50)
                """;
    }

    public String selectAll() {
        return """
                SELECT
                    id,
                    firstName,
                    secondName,
                    age
                FROM students
                """;
    }

    public String deleteRandomRowByString() {
        return """
                DELETE FROM students
                WHERE id = 3
                """;
    }

    public String updateRowByString() {
        return """
                UPDATE students
                SET secondname = 'Ivanova'
                WHERE firstname = 'Olga';
                """;
    }

    public String[] deleteByKeyValue() {
        return new String[]{"""
                DELETE FROM students
                WHERE id =
                """, "5"};

    }

    public String[] updateRowByKV() {
        return new String[] {"UPDATE students SET secondname =", "Petrova",
                " WHERE firstname = ", "Olga"};
    }

    public String[] newStudent() {
        return new String[] {"Mikola", "Nikolaevich", "23"};
    }
}