package ua.org.tees.yarosh.tais.core.user.mgmt.converters;

import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Converter;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.models.RegistrantEntity;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:13
 */
@Service
public class RegistrantToRegistrantEntityConverter implements Converter<Registrant, RegistrantEntity> {
    @Override
    public RegistrantEntity from(Registrant from) {
        RegistrantEntity registrantEntity = new RegistrantEntity();
        registrantEntity.setName(from.getName());
        registrantEntity.setLogin(from.getLogin());
        registrantEntity.setPassword(from.getPassword());
        registrantEntity.setPatronymic(from.getPatronymic());
        registrantEntity.setSurname(from.getSurname());
        return registrantEntity;
    }

    @Override
    public Registrant to(RegistrantEntity from) {
        Registrant registrant = new Registrant();
        registrant.setLogin(from.getLogin());
        registrant.setPassword(from.getPassword());
        registrant.setPatronymic(from.getPatronymic());
        registrant.setSurname(from.getSurname());
        registrant.setName(from.getName());
        return registrant;
    }
}
