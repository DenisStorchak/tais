package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 22:11
 */
public class ReportRegisteredEvent {

    private ManualTaskReport manualTaskReport;

    public ManualTaskReport getManualTaskReport() {
        return manualTaskReport;
    }

    public ReportRegisteredEvent(ManualTaskReport report) {
        manualTaskReport = report;
    }
}
