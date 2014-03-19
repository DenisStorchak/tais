package ua.org.tees.yarosh.tais.attendance.web.dto;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:36
 */
public class FprintScannerToken {
    private String accessToken;

    public FprintScannerToken() {
    }

    public FprintScannerToken(String accessToken) {

        this.accessToken = accessToken;
    }
}
