package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;

import java.nio.file.Path;

public interface HomeworkResolver {
    void resolve(PersonalTask task);
}
