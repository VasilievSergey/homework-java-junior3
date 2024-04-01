package gb.model.entity;

import gb.model.annotation.Column;
import gb.model.annotation.Id;
import gb.model.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {
    @Id()
    @Column(name = "id" )
    private int id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "secondname")
    private String secondname;
    @Column(name = "age")
    private int age;

}