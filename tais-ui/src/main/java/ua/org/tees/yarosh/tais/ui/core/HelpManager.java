/**
 * DISCLAIMER
 *
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 *
 * @author jouni@vaadin.com
 *
 */

package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class HelpManager {

    private List<HelpOverlay> overlays = new ArrayList<>();

    public void closeAll() {
        for (HelpOverlay overlay : overlays) {
            overlay.close();
        }
        overlays.clear();
    }

    public void showHelpFor(View view) {
        // showHelpFor(view.getClass());
    }

    public void showHelpFor(Class<? extends View> view) {
        // if (view == DashboardView.class) {
        //
        // } else if (view == SalesView.class) {
        // addOverlay(
        // "Add new data sets",
        // "You can add new data sets to the graph by choosing a title from the combo box and clicking \"Add\".",
        // "timeline-add");
        // addOverlay("Clear graph", "Clear all data sets from the graph",
        // "timeline-clear");
        // addOverlay(
        // "Browse",
        // "The Timeline component allows you to browse through and zoom the data sets infinitely.",
        // "timeline-browse").center();
        // } else if (view == TransactionsView.class) {
        // addOverlay(
        // "Unlimited Data",
        // "You can scroll through any number of rows in the table with blazing speed",
        // "table-lazy").center();
        // addOverlay(
        // "Filter",
        // "Live filter the table contents (in this demo you can only filter the country field",
        // "table-filter");
        // addOverlay(
        // "Create a report",
        // "You can select some rows from the table, and then either drag them over to the \"Reports\" tab in the sidebar or click this button or open the context menu for the items and select \"Create Report\"",
        // "table-rows");
        // } else if (view == ScheduleView.class) {
        // addOverlay("Drag'n'drop",
        // "You can drag'n'drop the events to change the schedule. You can also click on the events to get more information of the event.",
        // "event").center();
        // }
    }

    public HelpOverlay addOverlay(String caption, String text, String style, UI ui) {
        HelpOverlay o = new HelpOverlay();
        o.setCaption(caption);
        o.addComponent(new Label(text, ContentMode.HTML));
        o.setStyleName(style);
        ui.addWindow(o);
        overlays.add(o);
        o.center();
        return o;
    }

}
