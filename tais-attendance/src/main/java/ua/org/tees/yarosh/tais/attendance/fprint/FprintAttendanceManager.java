package ua.org.tees.yarosh.tais.attendance.fprint;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.AttendanceService;
import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:44
 */
@Service
public class FprintAttendanceManager implements AttendanceService {

    private FPrintScannerExpectationHolder expectationHolder;

    @Autowired
    public void setExpectationHolder(FPrintScannerExpectationHolder expectationHolder) {
        this.expectationHolder = expectationHolder;
    }

    @Override
    public String createAccessToken() {
        return RandomStringUtils.random(15);
    }

    @Override
    public FPrintRegistrationStatus appendPrint(String print) {
        return null;
    }

    @Override
    public RecognizedRegistrant recognizeAndAttend(String print) {
        return null;
    }
}
