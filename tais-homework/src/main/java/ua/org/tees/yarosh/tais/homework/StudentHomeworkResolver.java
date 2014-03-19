package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;
import ua.org.tees.yarosh.tais.homework.api.HomeworkResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.PersonalTaskRepository;

@Service
public class StudentHomeworkResolver implements HomeworkResolver {

    @Autowired
    private PersonalTaskRepository personalTaskRepository;

    @Override
    public void resolve(PersonalTask task) {
        personalTaskRepository.saveAndFlush(task);
    }
}
