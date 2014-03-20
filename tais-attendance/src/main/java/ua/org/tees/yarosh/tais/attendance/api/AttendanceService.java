package ua.org.tees.yarosh.tais.attendance.api;

import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.attendance.web.dto.FprintScannerToken;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:46
 */
public interface AttendanceService {
    FprintScannerToken createAndSaveAccessToken();

    FPrintRegistrationStatus appendPrint(String print, String accessToken);

    RecognizedRegistrant recognizeAndAttend(String print, String accessToken);
}
