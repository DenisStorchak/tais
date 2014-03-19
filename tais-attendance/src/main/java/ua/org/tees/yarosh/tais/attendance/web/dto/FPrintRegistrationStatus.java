package ua.org.tees.yarosh.tais.attendance.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 22:48
 */
public class FPrintRegistrationStatus {
    private Integer remaining;
    private Integer saved;

    @JsonProperty("remaining")
    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    @JsonProperty("saved")
    public Integer getSaved() {
        return saved;
    }

    public void setSaved(Integer saved) {
        this.saved = saved;
    }
}
