package olga.suprun.college.repo;

import olga.suprun.college.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsSubjectByName(String subjectName);

    Subject findSubjectByName(String subjectName);
}
