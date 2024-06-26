import model.Status;
import model.SubTask;
import model.Task;
import model.Epic;
import service.task.TaskManager;
import service.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.createTask(new Task("Task1",  Status.NEW, "TaskDesc1"));
        taskManager.createTask(new Task("Task2",  Status.NEW, "TaskDesc2"));
        taskManager.createTask(new Task("Task3",  Status.NEW, "TaskDesc3"));


        Epic epic1 = taskManager.createEpic(new Epic("Epic1", Status.NEW,"EpicDesc1"));
        taskManager.createSubTask(new SubTask("SubTask1", Status.NEW,
                "SubTaskDesk1", epic1.getId()));
        taskManager.createSubTask(new SubTask("SubTask2", Status.NEW,
                "SubTaskDesk2", epic1.getId()));

        Epic epic2 = taskManager.createEpic(new Epic("Epic2", Status.NEW,"EpicDesc2"));
        taskManager.createSubTask(new SubTask("SubTask2", Status.NEW, "SubTaskDesk2", epic2.getId()));
        taskManager.createSubTask(new SubTask("SubTask2", Status.DONE, "SubTaskDesk2", epic2.getId()));

        Epic epic3 = taskManager.createEpic(new Epic("Epic3", Status.NEW,"EpicDesc3"));
        taskManager.createSubTask(new SubTask("SubTask3", Status.DONE, "SubTaskDesk3", epic3.getId()));
        taskManager.createSubTask(new SubTask("SubTask3", Status.DONE, "SubTaskDesk3", epic3.getId()));

        printAllTasks(taskManager);

    }

    private static void printAllTasks(TaskManager manager) {

        System.out.println("Задачи:");
        for (Task task : manager.getAllTask()) {
            System.out.println(manager.getTask(task.getId()));
        }

        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpic()) {
            System.out.println(manager.getEpic(epic.getId()));

            for (SubTask subTask : manager.getSubTasksForEpic(epic)) {
                System.out.println("--> " + manager.getSubTask(subTask.getId()));
            }
        }
        System.out.println("Подзадачи:");
        for (Task subTask : manager.getAllSubTask()) {
            System.out.println(manager.getSubTask(subTask.getId()));
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
