package ua.org.tees.yarosh.tais.homework.models;

import org.hibernate.annotations.Cascade;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
public class AchievementDiary {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "ownerId")
    private Registrant owner;
    @Cascade(ALL)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "autoAchievementsID")
    private List<AutoAchievement> autoAchievements;
    @Cascade(ALL)
    @OneToMany(fetch = FetchType.EAGER)
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
