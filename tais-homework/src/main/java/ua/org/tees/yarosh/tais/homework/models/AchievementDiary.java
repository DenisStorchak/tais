package ua.org.tees.yarosh.tais.homework.models;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;
import java.util.List;

@Entity
public class AchievementDiary {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "ownerId")
    private Registrant owner;
    @OneToMany
    @JoinColumn(name = "autoAchievementsID")
    private List<AutoAchievement> autoAchievements;
    @OneToMany
    @JoinColumn(name = "manualAchievementsID")
    private List<ManualAchievement> manualAchievements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Registrant getOwner() {
        return owner;
    }

    public void setOwner(Registrant owner) {
        this.owner = owner;
    }

    public List<AutoAchievement> getAutoAchievements() {
        return autoAchievements;
    }

    public void setAutoAchievements(List<AutoAchievement> autoAchievements) {
        this.autoAchievements = autoAchievements;
    }

    public List<ManualAchievement> getManualAchievements() {
        return manualAchievements;
    }

    public void setManualAchievements(List<ManualAchievement> manualAchievements) {
        this.manualAchievements = manualAchievements;
    }
}
