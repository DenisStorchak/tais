package ua.org.tees.yarosh.tais.core.user.mgmt.api.service;

import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:50
 */
public interface RegistrantService {
    public Registrant createRegistration(Registrant registrant);

    public Registrant getRegistration(String login);

    public Registrant updateRegistration(Registrant registrant) throws RegistrantNotFoundException;

    public void deleteRegistration(String login);

    public List<Registrant> findAllRegistrants();

    public List<Registrant> findAllTeachers();

    public boolean loginExists(String login);

    public List<StudentGroup> listStudentGroups();

    public boolean isStudentGroupExists(String id);

    public StudentGroup createStudentGroup(StudentGroup studentGroup);

    public StudentGroup findStudentGroup(String id);
}
