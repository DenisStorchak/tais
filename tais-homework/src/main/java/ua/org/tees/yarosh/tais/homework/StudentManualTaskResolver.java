package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.CacheNames;
import ua.org.tees.yarosh.tais.homework.api.ManualTaskResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.ManualTaskReportRepository;
import ua.org.tees.yarosh.tais.homework.events.ManualTaskResolvedEvent;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

@Service
public class StudentManualTaskResolver implements ManualTaskResolver {

    private ManualTaskReportRepository resultRepository;
    private AsyncEventBus eventBus;

    @Autowired
    public void setResultRepository(ManualTaskReportRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Autowired
    public void setEventBus(AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    @CacheEvict(value = CacheNames.MANUAL_TASK_RESULTS, allEntries = true)
    public void resolve(ManualTaskReport task) {
        resultRepository.save(task);
        eventBus.post(new ManualTaskResolvedEvent(task));
    }
}
