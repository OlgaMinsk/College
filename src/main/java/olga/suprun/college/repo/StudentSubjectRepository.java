package olga.suprun.college.repo;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.StudentSubject;
import olga.suprun.college.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {
    Set<StudentSubject> getStudentSubjectByStudent (Student student);
    Set<StudentSubject> getStudentSubjectBySubject(Subject subject);
    //StudentSubject findBySubjectAndStudent(Subject subject, Student student);
    StudentSubject findBySubjectAndStudent(Subject subject, Student student);
    void deleteById(Long id);
}
