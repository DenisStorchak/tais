package ua.org.tees.yarosh.tais.attendance.api;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:38
 */
public interface Converter<F, T> {
    T convert(F from);
}
