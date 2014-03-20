package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.homework.models.AchievementDiary;

@Repository
public interface AchievementDiaryRepository extends JpaRepository<AchievementDiary, String> {
}
