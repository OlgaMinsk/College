package olga.suprun.college.service;

import olga.suprun.college.models.Student;

import olga.suprun.college.models.Subject;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface StudentService {
    void saveStudent(Student student);

    void deleteStudentById(Long id);

    boolean studentExistsById(Long id);

    Student getStudentById(Long id);

    Page<Student> findPage(int pageNo, int pageSize);

    void addSubjectToStudent(Long subjectId, Long studentId);

    Set<Subject> getSubjectsByStudent(Student student);
    void deleteSubjectOfStudent (Long subjectId, Long studentId);

}
