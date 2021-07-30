package olga.suprun.college.repo;

import olga.suprun.college.models.Mark;
import olga.suprun.college.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    boolean existsById(Long id);

    boolean existsByName(String name);

    Mark findByName(String name);

}
