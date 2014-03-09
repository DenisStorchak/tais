package ua.org.tees.yarosh.tais.core.common.dto;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:20
 */
public class StudentGroup {
    private long id;
    private List<GeneralTask> generalTaskList;
    private List<Registrant> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GeneralTask> getGeneralTaskList() {
        return generalTaskList;
    }

    public void setGeneralTaskList(List<GeneralTask> generalTaskList) {
        this.generalTaskList = generalTaskList;
    }

    public List<Registrant> getStudents() {
        return students;
    }

    public void setStudents(List<Registrant> students) {
        this.students = students;
    }
}
