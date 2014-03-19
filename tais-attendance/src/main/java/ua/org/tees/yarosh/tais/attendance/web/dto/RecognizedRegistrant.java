package ua.org.tees.yarosh.tais.attendance.web.dto;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:35
 */
public class RecognizedRegistrant {
    private String surname;
    private String name;
    private String studentGroup;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
}
