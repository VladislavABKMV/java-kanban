package service.converter;

import model.*;

public class TaskConverter {

    public final static String HEADER = "id,type,name,status,description,epicId";

    public static String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "," + "null";
    }

    public static String toString(Epic task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "," + "null";
    }

    public static String toString(SubTask task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getEpicId();
    }


    public static Task fromString(String string) {
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
