package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.api.ManualTaskResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.ManualTaskResultRepository;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

import static ua.org.tees.yarosh.tais.homework.configuration.CacheNames.MANUAL_TASK;

@Service
public class StudentManualTaskResolver implements ManualTaskResolver {

    @Autowired
    private ManualTaskResultRepository resultRepository;

    @Override
    @CacheEvict(MANUAL_TASK)
    public void resolve(ManualTaskReport task) {
        resultRepository.save(task);
    }
}
