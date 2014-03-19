package ua.org.tees.yarosh.tais.attendance.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.org.tees.yarosh.tais.attendance.api.AttendanceService;
import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.attendance.web.dto.FprintScannerToken;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 22:25
 */
@RestController
@RequestMapping("attendance/v1")
public class AttendanceWebController {

    private AttendanceService attendanceService;

    @Autowired
    public void setAttendanceService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @RequestMapping(value = "token", method = RequestMethod.GET)
    public FprintScannerToken getToken() {
        return attendanceService.createAndSaveAccessToken();
    }

    @RequestMapping(value = "fprint", method = RequestMethod.PUT)
    public FPrintRegistrationStatus appendFprint(@RequestBody String fprint) {
        return attendanceService.appendPrint(fprint);
    }

    @RequestMapping(value = "visit", method = RequestMethod.POST)
    public RecognizedRegistrant recognizeAndAttend(@RequestBody String fprint) {
        return attendanceService.recognizeAndAttend(fprint);
    }
}
