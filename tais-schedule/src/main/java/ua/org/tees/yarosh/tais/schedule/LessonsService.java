package ua.org.tees.yarosh.tais.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.schedule.api.*;
import ua.org.tees.yarosh.tais.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LessonsService implements ScheduleService, TeacherService, DisciplineService, ClassroomService {

    private LessonRepository lessonRepository;
    private DisciplineRepository disciplineRepository;
    private ClassroomRepository classroomRepository;

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setDisciplineRepository(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Autowired
    public void setClassroomRepository(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public void saveOrReplaceSchedule(List<Lesson> lessonList) {
        if (!lessonList.isEmpty()) {
            StudentGroup scheduleOwner = lessonList.get(0).getStudentGroup();
            for (Lesson lesson : lessonList) {
                if (lesson.getStudentGroup().getId().equals(scheduleOwner.getId())) {
                    lessonRepository.save(lesson);
                }
            }
        }
    }

    @Override
    public List<Lesson> findSchedule(Date periodFrom, Date periodTo, StudentGroup studentGroup) {
        return lessonRepository.findLessonsInPeriod(periodFrom, periodTo, studentGroup);
    }

    private List<Lesson> selectAllThenFilter(Date periodFrom, Date periodTo, StudentGroup studentGroup) {
        return lessonRepository.findLessonsByStudentGroup(studentGroup).stream()
                .filter(l -> l.getDate().after(periodFrom) && l.getDate().before(periodTo)).collect(toList());
    }

    @Override
    public List<Discipline> findDisciplinesByTeacher(Registrant teacher) {
        return lessonRepository.findLessonsByTeacher(teacher).stream().map(Lesson::getDiscipline).collect(toList());
    }

    @Override
    public List<Discipline> findAllDisciplines() {
        return disciplineRepository.findAll();
    }

    @Override
    public Discipline createDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline findDiscipline(Long id) {
        return disciplineRepository.findOne(id);
    }

    @Override
    public Discipline findDisciplineByName(String name) {
        return disciplineRepository.findOneByName(name);
    }

    @Override
    public List<Discipline> findDisciplinesByTeacher(String login) {
        return disciplineRepository.findDisciplinesByTeacher(login);
    }

    @Override
    public List<Classroom> findAllClassrooms() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom createClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom findClassroom(String id) {
        return classroomRepository.findOne(id);
    }
}
