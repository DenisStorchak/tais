package ua.org.tees.yarosh.tais.core.common.dto;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:17
 */
public class PersonalTask {
    private GeneralTask generalTask;
    private String grade;

    public GeneralTask getGeneralTask() {
        return generalTask;
    }

    public void setGeneralTask(GeneralTask generalTask) {
        this.generalTask = generalTask;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
