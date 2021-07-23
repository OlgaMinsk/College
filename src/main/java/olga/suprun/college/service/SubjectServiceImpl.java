package olga.suprun.college.service;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
import olga.suprun.college.repo.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;
    StudentSubjectService studentSubjectService;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              StudentSubjectService studentSubjectService) {
        this.subjectRepository = subjectRepository;
        this.studentSubjectService = studentSubjectService;
    }

    @Override
    public void saveSubject(Subject subject) {
        this.subjectRepository.save(subject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw new RuntimeException("Subject not found by id " + id + ". So we can't delete it");
        }
    }

    @Override
    public boolean subjectExistsById(Long id) {
        return subjectRepository.existsById(id);
    }

    @Override
    public boolean existsSubjectBySubjectName(String subject) {
        return subjectRepository.existsSubjectByName(subject);
    }

    @Override
    public Subject getSubjectById(Long id) {
        if (subjectRepository.existsById(id)) {
            return subjectRepository.findById(id).get();
        } else {
            throw new RuntimeException("Subject not found by id " + id);
        }
    }

    @Override
    public Page<Subject> findPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.subjectRepository.findAll(pageable);
    }

    @Override
    public Long getSubjectIdBySubjectName(String subjectName) {
        if (subjectRepository.existsSubjectByName(subjectName)) {
            Subject subject = subjectRepository.findSubjectByName(subjectName);
            return subject.getId();
        } else {
            return null;
        }
    }

    @Override
    public Set<Student> getStudentsBySubject(Subject subject) {
        return studentSubjectService.getStudentsBySubject(subject);
    }
}
