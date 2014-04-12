package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.AchievementDiary;

@Repository
public interface AchievementDiaryRepository extends JpaRepository<AchievementDiary, Long> {
    @Query("select a from AchievementDiary a where a.owner = :owner")
    AchievementDiary findOne(@Param("owner") Registrant owner);
}
