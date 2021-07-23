package olga.suprun.college.service;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface SubjectService {
    void saveSubject(Subject subject);

    void deleteSubjectById(Long id);

    boolean subjectExistsById(Long id);

    boolean existsSubjectBySubjectName(String subject);

    Subject getSubjectById(Long id);

    Page<Subject> findPage(int pageNo, int pageSize);

    Long getSubjectIdBySubjectName(String subjectName);

    Set<Student> getStudentsBySubject(Subject  subject);
}
