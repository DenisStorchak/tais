package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.api.ManualTaskResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.ManualTaskResultRepository;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskResult;

@Service
public class StudentManualTaskResolver implements ManualTaskResolver {

    @Autowired
    private ManualTaskResultRepository resultRepository;

    @Override
    public void resolve(ManualTaskResult task) {
        resultRepository.save(task);
    }
}
