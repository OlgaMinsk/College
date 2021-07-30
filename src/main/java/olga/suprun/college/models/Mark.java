package olga.suprun.college.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "marks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "mark")
        })
public class Mark {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "mark",
            nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "mark",
            cascade = CascadeType.ALL)
    private List<StudentsGrade> grade = new LinkedList<>();

    public List<StudentsGrade> getGrade() {
        return grade;
    }

    public void setGrade(List<StudentsGrade> grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Mark: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mark mark = (Mark) o;
        return Objects.equals(this.name, mark.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
