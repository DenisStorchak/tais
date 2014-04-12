package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 22:12
 */
public class ReportRatedEvent {

    private ManualTaskReport report;
    private Registrant examiner;
    private int grade;

    public ManualTaskReport getReport() {
        return report;
    }

    public Registrant getExaminer() {
        return examiner;
    }

    public int getGrade() {
        return grade;
    }

    public ReportRatedEvent(ManualTaskReport manualTaskReport, Registrant examiner, int grade) {
        this.report = manualTaskReport;
        this.examiner = examiner;
        this.grade = grade;
    }
}
