package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;

import java.nio.file.Path;

public interface HomeworkResolver {
    Path resolve(byte[] bytes, PersonalTask task);
}
