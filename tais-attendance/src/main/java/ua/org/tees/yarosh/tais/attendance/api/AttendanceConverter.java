package ua.org.tees.yarosh.tais.attendance.api;

import ua.org.tees.yarosh.tais.attendance.web.dto.FprintScannerToken;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:42
 */
public interface AttendanceConverter {
    FprintScannerToken convert(String accessToken);

    RecognizedRegistrant convert(Registrant registrant);
}
