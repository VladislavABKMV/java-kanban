package service.converter;

import model.Task;

public interface Converter {
    String toString(Task task);

    Task fromString(String string);
}
