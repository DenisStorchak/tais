package ua.org.tees.yarosh.tais.core.user.mgmt.api.service;

import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:50
 */
public interface RegistrantService {
    public Registrant createRegistration(Registrant registrant);

    public Registrant getRegistration(String login) throws RegistrantNotFoundException;

    public Registrant updateRegistration(Registrant registrant) throws RegistrantNotFoundException;

    public void deleteRegistration(String login);

    public boolean loginExists(String login);
}
