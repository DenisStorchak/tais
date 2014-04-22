package ua.org.tees.yarosh.tais.homework.models;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ManualAchievement {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "manualTaskId")
    private ManualTask manualTask;
    private Integer grade;
    @ManyToOne
    @JoinColumn(name = "examinerId")
    private Registrant examiner;
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToOne
    @JoinColumn(name = "diary_id")
    private AchievementDiary achievementDiary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManualTask getManualTask() {
        return manualTask;
    }

    public void setManualTask(ManualTask personalTask) {
        this.manualTask = personalTask;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Registrant getExaminer() {
        return examiner;
    }

    public void setExaminer(Registrant examiner) {
        this.examiner = examiner;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public AchievementDiary getAchievementDiary() {
        return achievementDiary;
    }

    public void setAchievementDiary(AchievementDiary achievementDiary) {
        this.achievementDiary = achievementDiary;
    }
}
