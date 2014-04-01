package ua.org.tees.yarosh.tais.ui.core.mvp;

public interface PresenterFactory {
    <P extends Presenter> P getPresenter(Class<P> clazz);
}
