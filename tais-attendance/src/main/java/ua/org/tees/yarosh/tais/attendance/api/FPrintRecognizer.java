package ua.org.tees.yarosh.tais.attendance.api;

import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

public interface FPrintRecognizer {
    /**
     * Append print to registrant profile. Access token should be linked with registrant
     *
     * @param print
     * @param accessToken
     * @return
     */
    FPrintRegistrationStatus appendPrint(String print, String accessToken);

    Registrant recognizeAndAttend(String print, String accessToken);
}
