package ua.org.tees.yarosh.tais.attendance.api;

import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:46
 */
public interface AttendanceService {
    String createAccessToken();

    FPrintRegistrationStatus appendPrint(String print);

    RecognizedRegistrant recognizeAndAttend(String print);
}
