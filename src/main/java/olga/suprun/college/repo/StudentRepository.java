package olga.suprun.college.repo;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

}