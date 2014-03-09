package ua.org.tees.yarosh.tais.core.common.api;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:05
 */
public interface Converter<F, T> {
    T from(F from);

    F to(T from);
}
