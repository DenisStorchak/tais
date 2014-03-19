package ua.org.tees.yarosh.tais.attendance.fprint;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:56
 */
@Entity
public class FprintScanner {
    @Id
    private String auditory;
    private String accessToken;

    public FprintScanner() {
    }

    public FprintScanner(String auditory, String accessToken) {
        this.auditory = auditory;
        this.accessToken = accessToken;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
