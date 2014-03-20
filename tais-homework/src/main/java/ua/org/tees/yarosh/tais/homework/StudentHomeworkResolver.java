package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;
import ua.org.tees.yarosh.tais.homework.api.AutoExamResolver;
import ua.org.tees.yarosh.tais.homework.api.HomeworkResolver;
import ua.org.tees.yarosh.tais.homework.api.PersonalTaskResolver;

@Service
public class StudentHomeworkResolver implements HomeworkResolver {

    private AutoExamResolver autoExamResolver;
    private PersonalTaskResolver personalTaskResolver;

    @Autowired
    public void setAutoExamResolver(AutoExamResolver autoExamResolver) {
        this.autoExamResolver = autoExamResolver;
    }

    @Autowired
    public void setPersonalTaskResolver(PersonalTaskResolver personalTaskResolver) {
        this.personalTaskResolver = personalTaskResolver;
    }

    @Override
    public void resolve(QuestionsSuiteResult result) {
        autoExamResolver.resolve(result);
    }

    @Override
    public void resolve(PersonalTask task) {
        personalTaskResolver.resolve(task);
    }
}
