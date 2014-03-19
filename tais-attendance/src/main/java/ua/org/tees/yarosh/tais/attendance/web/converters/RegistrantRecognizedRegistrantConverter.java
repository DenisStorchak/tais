package ua.org.tees.yarosh.tais.attendance.web.converters;

import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.Converter;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:40
 */
@Service
public class RegistrantRecognizedRegistrantConverter implements Converter<Registrant, RecognizedRegistrant> {
    @Override
    public RecognizedRegistrant convert(Registrant from) {
        RecognizedRegistrant recognizedRegistrant = new RecognizedRegistrant();
        recognizedRegistrant.setName(from.getName());
        recognizedRegistrant.setSurname(from.getSurname());
        recognizedRegistrant.setStudentGroup(String.valueOf(from.getGroup().getId()));
        return recognizedRegistrant;
    }
}
