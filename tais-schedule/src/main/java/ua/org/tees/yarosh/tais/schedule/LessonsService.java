package ua.org.tees.yarosh.tais.schedule;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.schedule.api.*;
import ua.org.tees.yarosh.tais.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
public class LessonsService implements ScheduleService, TeacherService, DisciplineService, ClassroomService {

    private static final int SCHEDULE_DIAPASON = 89;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd (hh:mm:ss)");
    private LessonRepository lessonRepository;
    private DisciplineRepository disciplineRepository;
    private ClassroomRepository classroomRepository;
    private RegistrantService registrantService;

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

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
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

    @Override
    public Lesson findCurrentOrNextLesson(String studentGroupId) throws LessonsNotFoundException {
        StudentGroup studentGroup = registrantService.findStudentGroup(studentGroupId);
        if (studentGroup == null) {
            throw new IllegalArgumentException(format("Student group with id [%s] not found", studentGroupId));
        }
        DateTime from = new DateTime();
        from.minusMinutes(SCHEDULE_DIAPASON);
        DateTime to = new DateTime();
        to.plusMinutes(SCHEDULE_DIAPASON);
        List<Lesson> lessons = findSchedule(from.toDate(), to.toDate(), studentGroup);
        if (lessons.isEmpty()) {
            throw new LessonsNotFoundException();
        }
        if (lessons.size() > 1) {
            throw new IllegalStateException(format("[%s] lessons found between [%s] and [%s]",
                    lessons.size(), SDF.format(from.toDate()), SDF.format(to.toDate())));
        }
        return lessons.get(0);
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
