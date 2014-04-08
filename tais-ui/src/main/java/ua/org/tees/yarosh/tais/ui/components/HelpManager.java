/**
 * DISCLAIMER
 *
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 *
 * @author jouni@vaadin.com
 *
 */

package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.components.windows.HelpOverlay;

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
