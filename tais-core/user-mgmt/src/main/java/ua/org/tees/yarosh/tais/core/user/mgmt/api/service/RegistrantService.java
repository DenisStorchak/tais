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
    Registrant createRegistration(Registrant registrant);

    Registrant getRegistration(String login);

    Registrant updateRegistration(Registrant registrant) throws RegistrantNotFoundException;

    void deleteRegistration(String login);

    List<Registrant> findAllRegistrants();

    List<Registrant> findAllTeachers();

    boolean loginExists(String login);

    List<StudentGroup> listStudentGroups();

    boolean isStudentGroupExists(String id);

    StudentGroup createStudentGroup(StudentGroup studentGroup);

    StudentGroup findStudentGroup(String id);

    List<Registrant> findRegistrantsByStudentGroup(String id);
}
