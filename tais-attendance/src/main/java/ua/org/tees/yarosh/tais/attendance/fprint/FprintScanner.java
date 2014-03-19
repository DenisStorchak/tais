package ua.org.tees.yarosh.tais.attendance.fprint;

import javax.persistence.Entity;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:56
 */
@Entity
public class FprintScanner {
    private Long id;
    private String auditory;
    private String accessToken;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
