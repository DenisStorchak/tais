package ua.org.tees.yarosh.tais.attendance.web.converters;

import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.Converter;
import ua.org.tees.yarosh.tais.attendance.web.dto.FprintScannerToken;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:38
 */
@Service
public class AccessTokenFprintScannerTokenConverter implements Converter<String, FprintScannerToken> {
    @Override
    public FprintScannerToken convert(String from) {
        return new FprintScannerToken(from);
    }
}
