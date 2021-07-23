package olga.suprun.college.service;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;

import java.util.Set;

public interface StudentSubjectService {
    Set<Student> getStudentsBySubject(Subject subject);

    Set<Subject> getSubjectsByStudent(Student student);

    void addSubjectToStudent(Subject subject, Student student);
    void deleteSubjectOfStudent(Subject subject, Student student);
}

