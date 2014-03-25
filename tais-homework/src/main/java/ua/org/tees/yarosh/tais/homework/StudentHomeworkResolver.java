package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.api.HomeworkResolver;
import ua.org.tees.yarosh.tais.homework.api.ManualTaskResolver;
import ua.org.tees.yarosh.tais.homework.api.QuestionsSuiteResolver;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

import static ua.org.tees.yarosh.tais.core.common.CacheNames.MANUAL_TASK;
import static ua.org.tees.yarosh.tais.core.common.CacheNames.QUESTIONS_SUITE;

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
    @CacheEvict(QUESTIONS_SUITE)
    public void resolve(QuestionsSuiteResult result) {
        questionsSuiteResolver.resolve(result);
    }

    @Override
    @CacheEvict(MANUAL_TASK)
    public void resolve(ManualTaskReport task) {
        manualTaskResolver.resolve(task);
    }
}
