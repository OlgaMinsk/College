package olga.suprun.college.service;

import olga.suprun.college.models.Mark;
import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface MarkService {
    List<Mark> getAll();

    boolean existsById(Long id);

    boolean existsByMarkName(String markName);

    Mark findByMarkName(String markName);
}
