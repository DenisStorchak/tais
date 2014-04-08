package ua.org.tees.yarosh.tais.ui.components.layouts;

import ua.org.tees.yarosh.tais.ui.core.api.Initable;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

/**
 * @author Timur Yarosh
 *         Date: 06.04.14
 *         Time: 14:09
 */
public class DashboardView extends DashboardLayout implements Initable {

    public DashboardView() {
        super();
    }

    public DashboardView(boolean horizontalDash) {
        super(horizontalDash);
    }

    @Override
    public void init() {
        TaisView taisView = getClass().getAnnotation(TaisView.class);
        setBackgroundCaption(taisView.value());
    }
}
