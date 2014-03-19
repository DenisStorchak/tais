package ua.org.tees.yarosh.tais.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.GroupTask;
import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.StudentGroupRepository;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.api.persistence.GeneralTaskRepository;
import ua.org.tees.yarosh.tais.homework.api.persistence.PersonalTaskRepository;
import ua.org.tees.yarosh.tais.homework.api.persistence.TaskHolderRepository;

@Service
public class TeacherHomeworkManager implements HomeworkManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherHomeworkManager.class);
    @Autowired
    private GeneralTaskRepository generalTaskRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private PersonalTaskRepository personalTaskRepository;
    @Autowired
    private TaskHolderRepository taskHolderRepository;

    @Override
    public long createGeneralTask(GroupTask task) {
        LOGGER.info("Try to create group task [{}]", task.getDescription());
        GroupTask persistedTask = generalTaskRepository.save(task);

        StudentGroup studentGroup = studentGroupRepository.findOne(task.getStudentGroup().getId());
        for (Registrant student : studentGroup.getStudents()) {
            student.getPersonalTaskHolder().getPersonalTaskList().add(new PersonalTask(persistedTask));
            taskHolderRepository.saveAndFlush(student.getPersonalTaskHolder());
        }
        return persistedTask.getId();
    }

    @Override
    public void enableGroupTask(long id) {
        GroupTask groupTask = generalTaskRepository.findOne(id);
        if (groupTask.isEnabled()) {
            switchGroupTaskState(groupTask, true);
        }
    }

    @Override
    public void disableGroupTask(long id) {
        GroupTask groupTask = generalTaskRepository.findOne(id);
        if (!groupTask.isEnabled()) {
            switchGroupTaskState(groupTask, false);
        }
    }

    private void switchGroupTaskState(GroupTask groupTask, boolean enable) {
        groupTask.setEnabled(enable);
        generalTaskRepository.saveAndFlush(groupTask);
        StudentGroup studentGroup = studentGroupRepository.findOne(groupTask.getStudentGroup().getId());
        for (Registrant student : studentGroup.getStudents()) {
            PersonalTask personalTask = personalTaskRepository.findByGroupTask(groupTask);
            if (enable) {
                student.getPersonalTaskHolder().getPersonalTaskList().add(personalTask);
            } else {
                student.getPersonalTaskHolder().getPersonalTaskList().remove(personalTask);
            }
            taskHolderRepository.saveAndFlush(student.getPersonalTaskHolder());
        }
    }
}
