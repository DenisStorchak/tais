package ua.org.tees.yarosh.tais.attendance.schedule.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import java.util.Date;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("select l from Lesson l where l.studentGroup = :studentGroup and l.date between :periodFrom and :periodTo")
    List<Lesson> findLessonsWithinPeriod(@Param("periodFrom") Date periodFrom,
                                         @Param("periodTo") Date periodTo,
                                         @Param("studentGroup") StudentGroup studentGroup);

    @Query("select l from Lesson l where l.teacher = :teacher")
    List<Lesson> findLessonsByTeacher(@Param("teacher") Registrant teacher);

    @Query("delete from Lesson l where l.studentGroup = :studentGroup")
    void deleteSchedule(@Param("studentGroup") StudentGroup studentGroup);
}
