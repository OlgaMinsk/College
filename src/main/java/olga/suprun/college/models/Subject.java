package olga.suprun.college.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subjects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "subject")
        }
)
public class Subject {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "subject",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "subject",
            cascade = CascadeType.ALL)
    private Set<StudentSubject> students = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String subject) {
        this.name = subject;
    }

    public Set<StudentSubject> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentSubject> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Subject: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subject subject = (Subject) o;
        return Objects.equals(this.name, subject.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

