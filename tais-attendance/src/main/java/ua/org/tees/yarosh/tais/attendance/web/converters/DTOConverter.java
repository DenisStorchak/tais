package ua.org.tees.yarosh.tais.attendance.web.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.AttendanceConverter;
import ua.org.tees.yarosh.tais.attendance.api.Converter;
import ua.org.tees.yarosh.tais.attendance.web.dto.FprintScannerToken;
import ua.org.tees.yarosh.tais.attendance.web.dto.RecognizedRegistrant;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:44
 */
@Service
public class DTOConverter implements AttendanceConverter {

    private Converter<String, FprintScannerToken> accessTokenFprintScannerTokenConverter;
    private Converter<Registrant, RecognizedRegistrant> registrantRecognizedRegistrantConverter;

    @Autowired
    public void setAccessTokenFprintScannerTokenConverter(Converter<String, FprintScannerToken> accessTokenFprintScannerTokenConverter) {
        this.accessTokenFprintScannerTokenConverter = accessTokenFprintScannerTokenConverter;
    }

    @Autowired
    public void setRegistrantRecognizedRegistrantConverter(Converter<Registrant, RecognizedRegistrant> registrantRecognizedRegistrantConverter) {
        this.registrantRecognizedRegistrantConverter = registrantRecognizedRegistrantConverter;
    }

    @Override
    public FprintScannerToken convert(String accessToken) {
        return null;
    }

    @Override
    public RecognizedRegistrant convert(Registrant registrant) {
        return null;
    }
}
