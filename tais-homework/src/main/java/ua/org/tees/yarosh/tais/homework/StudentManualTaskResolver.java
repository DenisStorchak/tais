package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.CacheNames;
import ua.org.tees.yarosh.tais.homework.api.ManualTaskResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.ManualTaskReportRepository;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

@Service
public class StudentManualTaskResolver implements ManualTaskResolver {

    @Autowired
    private ManualTaskReportRepository resultRepository;

    @Override
    @CacheEvict(value = CacheNames.MANUAL_TASK_RESULTS, key = "#task.id")
    public void resolve(ManualTaskReport task) {
        resultRepository.save(task);
    }
}
