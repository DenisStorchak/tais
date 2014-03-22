package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.ui.Table;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 11:55
 */
public class UnratedReportsTable extends Table {
    public UnratedReportsTable() {
        setCaption("Непроверенные отчеты");
        setWidth("100%");
        setPageLength(0);
        addStyleName("plain");
        addStyleName("borderless");
        setSortEnabled(false);
        setRowHeaderMode(RowHeaderMode.HIDDEN);
    }
}
