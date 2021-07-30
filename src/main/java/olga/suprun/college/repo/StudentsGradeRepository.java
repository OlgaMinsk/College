package olga.suprun.college.repo;

import olga.suprun.college.models.Mark;
import olga.suprun.college.models.StudentSubject;
import olga.suprun.college.models.StudentsGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentsGradeRepository extends JpaRepository<StudentsGrade, Long> {
 List<StudentsGrade> findStudentsGradeByStudentSubject(StudentSubject studentSubject);
 List<StudentsGrade> findAllByStudentSubject(StudentSubject studentSubject);

}
