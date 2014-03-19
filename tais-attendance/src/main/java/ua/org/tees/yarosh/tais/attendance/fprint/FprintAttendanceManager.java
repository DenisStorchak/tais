package ua.org.tees.yarosh.tais.attendance.fprint;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.AttendanceService;
import ua.org.tees.yarosh.tais.attendance.api.FPrintScannerRepository;
import ua.org.tees.yarosh.tais.attendance.web.converters.DTOConverter;
import ua.org.tees.yarosh.tais.attendance.web.dto.FPrintRegistrationStatus;
import ua.org.tees.yarosh.tais.attendance.web.dto.FprintScannerToken;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:44
 */
@Service
public class FprintAttendanceManager implements AttendanceService {

    private FPrintScannerExpectationHolder expectationHolder;
    private FPrintScannerRepository scannerRepository;
    private DTOConverter converter;

    @Autowired
    public void setExpectationHolder(FPrintScannerExpectationHolder expectationHolder) {
        this.expectationHolder = expectationHolder;
    }

    @Autowired
    public void setScannerRepository(FPrintScannerRepository scannerRepository) {
        this.scannerRepository = scannerRepository;
    }

    @Autowired
    public void setConverter(DTOConverter converter) {
        this.converter = converter;
    }

    @Override
    public FprintScannerToken createAndSaveAccessToken() {
        String accessToken = RandomStringUtils.random(15);
        String auditory = expectationHolder.getExpectingScannerAuditory();
        FprintScanner persistedScanner = scannerRepository.findOne(auditory);
        if (persistedScanner == null) {
            scannerRepository.save(new FprintScanner(auditory, accessToken));
        } else {
            persistedScanner.setAccessToken(accessToken);
            scannerRepository.saveAndFlush(persistedScanner);
        }
        return converter.convert(accessToken);
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
