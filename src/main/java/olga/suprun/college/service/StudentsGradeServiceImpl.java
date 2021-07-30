package olga.suprun.college.service;

import olga.suprun.college.models.*;
import olga.suprun.college.repo.StudentsGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StudentsGradeServiceImpl implements StudentsGradeService {
    private StudentsGradeRepository studentsGradeRepository;
    private StudentSubjectService studentSubjectService;

    @Autowired
    public StudentsGradeServiceImpl(StudentsGradeRepository studentsGradeRepository, StudentSubjectService studentSubjectService) {
        this.studentsGradeRepository = studentsGradeRepository;
        this.studentSubjectService = studentSubjectService;
    }

    @Override
    public void addMarkToStudentInSubject(Student student, Subject subject, Mark mark) throws Exception {
        if (studentSubjectService.existsByStudentAndSubject(student, subject)) {
            StudentSubject studentSubject = studentSubjectService.findBySubjectAndStudent(subject, student);
            StudentsGrade studentsGrade = new StudentsGrade(studentSubject, mark);
            studentsGradeRepository.save(studentsGrade);
        } else {
            throw new Exception("Student " + student.toString() + " is not studying the subject " + subject.getName());
        }
    }

    @Override
    public List<Mark> getMarksOfStudentInSubject(Student student, Subject subject) {
        StudentSubject studentSubject = studentSubjectService.findBySubjectAndStudent(subject, student);
        List<StudentsGrade> grades = studentsGradeRepository.findStudentsGradeByStudentSubject(studentSubject);
        List<Mark> marks = new LinkedList<>();
        for (StudentsGrade grade : grades) {
            marks.add(grade.getMark());
        }
        return marks;
    }
}
