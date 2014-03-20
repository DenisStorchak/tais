package ua.org.tees.yarosh.tais.attendance.fprint.storchak;

import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.FPrintRecognizer;
import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

@Service
public class FPrintRecognizingService implements FPrintRecognizer {
    @Override
    public FPrintRegistrationStatus appendPrint(String print, String accessToken) {
        return null;
    }

    @Override
    public Registrant recognizeAndAttend(String print, String accessToken) {
        return null;
    }
}
