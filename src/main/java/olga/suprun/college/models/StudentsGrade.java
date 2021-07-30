package olga.suprun.college.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students_grade")
public class StudentsGrade {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_subject_id")
    private StudentSubject studentSubject;
    @ManyToOne
    @JoinColumn(name = "marks_id")
    private Mark mark;

    public StudentsGrade() {
    }

    public StudentsGrade(StudentSubject studentSubject, Mark mark) {
        this.studentSubject = studentSubject;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentSubject getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(StudentSubject studentSubject) {
        this.studentSubject = studentSubject;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentsGrade studentGrade = (StudentsGrade) o;
        return Objects.equals(studentSubject, studentGrade.getStudentSubject()) &&
                Objects.equals(mark, studentGrade.getMark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentSubject, mark);
    }
}
