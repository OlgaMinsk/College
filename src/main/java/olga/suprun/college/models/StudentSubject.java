package olga.suprun.college.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(mappedBy = "studentSubject",
            cascade = CascadeType.ALL)
    private List<StudentsGrade> grade = new LinkedList<>();

    public StudentSubject() {

    }

    public StudentSubject(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public StudentSubject(Student student, Subject subject, List<StudentsGrade> grade) {
        this.student = student;
        this.subject = subject;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<StudentsGrade> getGrade() {
        return grade;
    }

    public void setGrade(List<StudentsGrade> grade) {
        this.grade = grade;
    }


    /*
        public void addMark(Mark mark) {
            StudentsGrade studentsGrade = new StudentsGrade(this, mark);
            this.grade.add(studentsGrade);
            mark.getGrade().add(studentsGrade);
        }

        public void removeMark(Mark mark) {
            StudentsGrade studentsGrade = new StudentsGrade(this, mark);
            this.grade.remove(studentsGrade);
            mark.getGrade().remove(studentsGrade);
            studentsGrade.setMark(null);
            studentsGrade.setStudentSubject(null);
        }


     */

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
