package olga.suprun.college.service;

import olga.suprun.college.models.Mark;
import olga.suprun.college.repo.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MarkServiceImpl implements MarkService {
    private MarkRepository markRepository;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public List<Mark> getAll() {
        return markRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return markRepository.existsById(id);
    }

    @Override
    public boolean existsByMarkName(String markName) {
        return markRepository.existsByName(markName);
    }

    @Override
    public Mark findByMarkName(String markName) {
        return markRepository.findByName(markName);
    }
}
