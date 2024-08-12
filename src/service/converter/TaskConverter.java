package service.converter;

import model.*;

public class TaskConverter implements Converter {
    @Override
    public String toString(Task task) {
        return String.format("%d,%s,%s,%s,%s,%d", task.getId(), task.getType(), task.getName(), task.getStatus(),
                task.getDescription(), task.getEpicId());
    }

    @Override
    public Task fromString(String string) {
        final String[] taskInfo = string.split(",");
        TaskType type = TaskType.valueOf(taskInfo[1]);

        return switch (type) {
            case TASK -> new Task(Integer.parseInt(taskInfo[0]), taskInfo[2], Status.valueOf(taskInfo[3]), taskInfo[4]);
            case SUB_TASK -> new SubTask(Integer.parseInt(taskInfo[0]), taskInfo[2], Status.valueOf(taskInfo[3]),
                    taskInfo[4], Integer.parseInt(taskInfo[5]));
            case EPIC -> new Epic(Integer.parseInt(taskInfo[0]), taskInfo[2], Status.valueOf(taskInfo[3]), taskInfo[4]);
        };
    }
}
