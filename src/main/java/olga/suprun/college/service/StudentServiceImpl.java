package olga.suprun.college.service;

import olga.suprun.college.models.Mark;
import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
import olga.suprun.college.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private SubjectService subjectService;
    private StudentSubjectService studentSubjectService;
    private StudentsGradeService studentsGradeService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              SubjectService subjectService,
                              StudentSubjectService studentSubjectService,
                              StudentsGradeService studentsGradeService) {
        this.studentRepository = studentRepository;
        this.subjectService = subjectService;
        this.studentSubjectService = studentSubjectService;
        this.studentsGradeService = studentsGradeService;
    }

    @Override
    public void saveStudent(Student student) {
        this.studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        if (studentRepository.findById(id).isPresent()) {
            this.studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student not found by id " + id + ". So we can't delete it");
        }
    }

    @Override
    public boolean studentExistsById(Long id) {
        return studentRepository.existsById(id);
    }

    @Override
    public Student getStudentById(Long id) {
        if (studentRepository.getById(id) != null) {
            return studentRepository.getById(id);
        } else {
            throw new RuntimeException("Student not found by id " + id);
        }
    }

    @Override
    public Page<Student> findPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.studentRepository.findAll(pageable);
    }

    @Override
    public void addSubjectToStudent(Long subjectId, Long studentId) {
        studentSubjectService.addSubjectToStudent(subjectService.getSubjectById(subjectId), getStudentById(studentId));
    }

    @Override
    public Set<Subject> getSubjectsByStudent(Student student) {
        return studentSubjectService.getSubjectsByStudent(student);
    }

    @Override
    public void deleteSubjectOfStudent(Long subjectId, Long studentId) {
        studentSubjectService.deleteSubjectOfStudent(subjectService.getSubjectById(subjectId), this.getStudentById(studentId));
    }

    @Override
    public void addMarkToStudentInSubject(Student student, Subject subject, Mark mark) throws Exception {
        studentsGradeService.addMarkToStudentInSubject(student, subject, mark);
    }

    @Override
    public List<Mark> getMarksOfStudentInSubject(Student student, Subject subject) {
        List<Mark> marks =studentsGradeService.getMarksOfStudentInSubject(student, subject);
        return marks;
    }

}
