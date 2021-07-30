package olga.suprun.college.service;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.StudentSubject;
import olga.suprun.college.models.Subject;
import olga.suprun.college.repo.StudentSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentSubjectServiceImpl implements StudentSubjectService {
    private StudentSubjectRepository studentSubjectRepository;

    @Autowired
    public StudentSubjectServiceImpl(StudentSubjectRepository studentSubjectRepository) {
        this.studentSubjectRepository = studentSubjectRepository;
    }

    @Override
    public Set<Student> getStudentsBySubject(Subject subject) {
        Set<StudentSubject> studentSubjects = studentSubjectRepository.getStudentSubjectBySubject(subject);
        Set<Student> students = new HashSet<>();
        for (StudentSubject studentSubject : studentSubjects
        ) {
            students.add(studentSubject.getStudent());
        }
        return students;
    }

    @Override
    public Set<Subject> getSubjectsByStudent(Student student) {
        Set<StudentSubject> studentSubjects = studentSubjectRepository.getStudentSubjectByStudent(student);
        Set<Subject> subjects = new HashSet<>();
        for (StudentSubject studentSubject :
                studentSubjects) {
            subjects.add(studentSubject.getSubject());
        }
        return subjects;
    }

    @Override
    public void addSubjectToStudent(Subject subject, Student student) {
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setSubject(subject);
        studentSubject.setStudent(student);
        studentSubjectRepository.save(studentSubject);
    }

    @Override
    public void deleteSubjectOfStudent(Subject subject, Student student) {
        StudentSubject studentSubject = new StudentSubject();
        studentSubject = studentSubjectRepository.findBySubjectAndStudent(subject, student);
        studentSubjectRepository.deleteById(studentSubject.getId());
    }

    @Override
    public boolean existsByStudentAndSubject(Student student, Subject subject) {
        return studentSubjectRepository.existsStudentSubjectByStudentAndSubject(student,subject);
    }

    @Override
    public StudentSubject findBySubjectAndStudent(Subject subject, Student student) {
        return studentSubjectRepository.findBySubjectAndStudent(subject, student);
    }

}
