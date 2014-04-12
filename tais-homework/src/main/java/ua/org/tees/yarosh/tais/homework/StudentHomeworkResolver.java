package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.api.HomeworkResolver;
import ua.org.tees.yarosh.tais.homework.api.ManualTaskResolver;
import ua.org.tees.yarosh.tais.homework.api.QuestionsSuiteResolver;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

@Service
public class StudentHomeworkResolver implements HomeworkResolver {

    private QuestionsSuiteResolver questionsSuiteResolver;
    private ManualTaskResolver manualTaskResolver;

    @Autowired
    public void setQuestionsSuiteResolver(QuestionsSuiteResolver questionsSuiteResolver) {
        this.questionsSuiteResolver = questionsSuiteResolver;
    }

    @Autowired
    public void setManualTaskResolver(ManualTaskResolver manualTaskResolver) {
        this.manualTaskResolver = manualTaskResolver;
    }

    @Override
    public int resolve(QuestionsSuiteReport report) {
        return questionsSuiteResolver.resolve(report);
    }

    @Override
    public void resolve(ManualTaskReport task) {
        manualTaskResolver.resolve(task);
    }
}
