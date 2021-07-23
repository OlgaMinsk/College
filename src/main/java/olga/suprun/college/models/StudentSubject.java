package olga.suprun.college.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "student_subject",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"students_id", "subjects_id"})
        }
)
public class StudentSubject {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "students_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subjects_id")
    private Subject subject;

    public StudentSubject() {

    }

    public StudentSubject(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentSubject studentSubject = (StudentSubject) o;
        return Objects.equals(student, studentSubject.getStudent()) && Objects.equals(subject, studentSubject.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, subject);
    }
}
