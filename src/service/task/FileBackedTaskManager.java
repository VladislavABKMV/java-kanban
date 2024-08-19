package service.task;

import exception.ManagerSaveException;
import model.Epic;
import model.SubTask;
import model.Task;
import service.converter.TaskConverter;
import service.history.HistoryManager;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    private FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(HistoryManager historyManager, File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(historyManager, file);
        manager.load();
        return manager;
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        super.updateEpic(updateEpic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        save();
    }

    @Override
    public Task createTask(Task task) {
        task = super.createTask(task);

        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic = super.createEpic(epic);

        save();
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        subTask = super.createSubTask(subTask);

        save();
        return subTask;
    }

    private void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(TaskConverter.header);
            writer.newLine();

            for (Task task : tasks.values()) {
                String line = TaskConverter.toString(task);

                writer.append(line);
                writer.newLine();
            }

            for (Epic epic : epics.values()) {
                String line = TaskConverter.toString(epic);

                writer.append(line);
                writer.newLine();
            }

            for (SubTask subTask : subTasks.values()) {
                String line = TaskConverter.toString(subTask);

                writer.append(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException(String.format("Ошибка в файле: %s", file.getAbsolutePath()));
        }
    }

    private void load() {
        int maxId = 0;

        try (final BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            reader.readLine();

            while (true) {
               String line = reader.readLine();

                if (line == null) {
                    break;
                }

                Task task = putTask(line);
                maxId = Math.max(maxId, task.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        linkSubTask();
        idCount = maxId;
    }

    private Task putTask(String line) {
        final Task task = TaskConverter.fromString(line);

        switch (task.getType()) {
            case TASK -> tasks.put(task.getId(), task);
            case EPIC -> epics.put(task.getId(), (Epic) task);
            case SUB_TASK -> subTasks.put(task.getId(), (SubTask) task);
        }

        return task;
    }

    private void linkSubTask() {
        for (SubTask subTask : subTasks.values()) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.addSubTask(subTask.getId());
        }
    }
}
