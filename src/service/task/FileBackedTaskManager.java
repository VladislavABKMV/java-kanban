package service.task;

import exception.ManagerSaveException;
import model.Epic;
import model.SubTask;
import model.Task;
import service.converter.Converter;
import service.converter.TaskConverter;
import service.history.HistoryManager;
import service.history.InMemoryHistoryManager;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final Converter converter;
    private final File file;

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
        converter = new TaskConverter();
    }

    public FileBackedTaskManager(File file) {
        super(new InMemoryHistoryManager());
        this.file = file;
        converter = new TaskConverter();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.load();
        return manager;
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        super.updateEpic(updateEpic);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task createTask(Task task) {
        task = super.createTask(task);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic = super.createEpic(epic);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        subTask = super.createSubTask(subTask);

        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return subTask;
    }

    private void save() throws ManagerSaveException {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("id,type,name,status,description,epic");
            writer.newLine();

            for (Task task : tasks.values()) {
                writeTask(writer, task);
            }

            for (Epic epic : epics.values()) {
                writeTask(writer, epic);
            }

            for (SubTask subTask : subTasks.values()) {
                writeTask(writer, subTask);
            }
        } catch (IOException e) {
            throw new ManagerSaveException(String.format("Ошибка в файле: %s", file.getAbsolutePath()));
        }
    }

    private void writeTask(BufferedWriter writer, Task task) throws IOException {
        String line = converter.toString(task);

        writer.append(line);
        writer.newLine();
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

        linkedSubTask();
        idCount = maxId;
    }

    private Task putTask(String line) {
        final Task task = converter.fromString(line);

        switch (task.getType()) {
            case TASK -> tasks.put(task.getId(), task);
            case EPIC -> epics.put(task.getId(), (Epic) task);
            case SUB_TASK -> subTasks.put(task.getId(), (SubTask) task);
            // default trow
        }

        return task;
    }

    private void linkedSubTask() {
        for (SubTask subTask : subTasks.values()) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.addSubTask(subTask.getId());
        }
    }
}
