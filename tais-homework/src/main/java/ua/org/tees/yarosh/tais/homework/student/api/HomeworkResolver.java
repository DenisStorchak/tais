package ua.org.tees.yarosh.tais.homework.student.api;

import ua.org.tees.yarosh.tais.core.common.dto.PersonalTask;

import java.nio.file.Path;

public interface HomeworkResolver {
    Path resolve(byte[] bytes, PersonalTask task);
}
