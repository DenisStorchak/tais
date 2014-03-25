package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.ui.Table;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 11:55
 */
public class PlainBorderlessTable extends Table {
    public PlainBorderlessTable(String caption) {
        setCaption(caption);
        setWidth("100%");
        setPageLength(0);
        addStyleName("plain");
        addStyleName("borderless");
        setSortEnabled(false);
        setRowHeaderMode(RowHeaderMode.HIDDEN);
    }
}
