import model.Status;
import model.SubTask;
import model.Task;
import model.Epic;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = taskManager.create(new Task("Task1",  Status.NEW, "TaskDesc1"));
        Epic epic1 = taskManager.createEpic(new Epic("Epic1", Status.NEW,"EpicDesc1"));
        SubTask subTaskFirstForEpic1 = taskManager.createSubTask(new SubTask("SubTask1", Status.NEW,
                "SubTaskDesk1", epic1));
        SubTask subTaskSecondForEpic1 = taskManager.createSubTask(new SubTask("SubTask2", Status.NEW,
                "SubTaskDesk2", epic1));

//        Check create
        System.out.println("Check create");
        System.out.println("new Task: " + task1.toString());
        System.out.println("new Epic: " + epic1.toString());
        System.out.println("new SubTask 1 For Epic: " + subTaskFirstForEpic1.toString());
        System.out.println("new SubTask 2 For Epic: " + subTaskSecondForEpic1.toString());
        System.out.println("===========================================================");

//        Check get
        System.out.println("Check get");
        System.out.println("Get Task: " + taskManager.get(task1.getId()).toString());
        System.out.println("Get Epic: " + taskManager.getEpic(epic1.getId()).toString());
        System.out.println("Get SubTask 1: " + taskManager.getSubTask(subTaskFirstForEpic1.getId()).toString());
        System.out.println("Get SubTask 2: " + taskManager.getSubTask(subTaskSecondForEpic1.getId()).toString());
        System.out.println("===========================================================");

//        Check getAll
        System.out.println("Check getAll");
        System.out.println("Tasks" + taskManager.getAll());
        System.out.println("Epics" + taskManager.getAllEpic());
        System.out.println("Subtasks" + taskManager.getAllSubTask());
        System.out.println("===========================================================");

//        Check All SubTask for Epic
        System.out.println("Check All SubTask for Epic");
        System.out.println("Get SubTask for Epic: " + taskManager.getSubTasksForEpic(epic1));
        System.out.println("===========================================================");

//        Check Update
        taskManager.update(new Task(task1.getId(), "TaskUpdate1",  Status.IN_PROGRESS,
                "TaskDescUpdate1"));
        taskManager.updateEpic(new Epic(epic1.getId(), "EpicUpdate1",  Status.DONE,
                "TaskDescUpdate1"));
        taskManager.updateSubTask(new SubTask(subTaskFirstForEpic1.getId(), "TaskUpdate1",  Status.IN_PROGRESS,
                "TaskDescUpdate1", epic1));

        System.out.println("Check update Task");
        System.out.println("Old Task: " + task1);
        System.out.println("New Task: " + taskManager.get(task1.getId()).toString());
        System.out.println("Check update SubTask");
        System.out.println("Old SubTask1: " + subTaskFirstForEpic1);
        System.out.println("New SubTask1.: " + taskManager.getSubTask(subTaskFirstForEpic1.getId()));
        System.out.println("Check update Epic");
        System.out.println("New Epic: " + taskManager.getEpic(epic1.getId()).toString());
        System.out.println("===========================================================");


//        Check delete
        System.out.println("Check delete");
        System.out.println("Tasks" + taskManager.getAll());
        System.out.println("Epics" + taskManager.getAllEpic());
        System.out.println("Subtasks" + taskManager.getAllSubTask());

        taskManager.delete(task1.getId());
        taskManager.deleteSubTask(subTaskFirstForEpic1.getId());
        System.out.println("After removal subTask1: " + taskManager.getSubTasksForEpic(epic1));
        System.out.println("subTasks after removal subTask1: " + taskManager.getAllSubTask());
        taskManager.deleteEpic(epic1.getId());

        System.out.println("Tasks" + taskManager.getAll());
        System.out.println("Epics" + taskManager.getAllEpic());
        System.out.println("Subtasks" + taskManager.getAllSubTask());
        System.out.println("===========================================================");


//        Check deleteAll
        taskManager.create(new Task("Task2",  Status.NEW, "TaskDesc2"));
        taskManager.create(new Task("Task3",  Status.NEW, "TaskDesc3"));

        Epic epic2 = taskManager.createEpic(new Epic("Epic2", Status.NEW,"EpicDesc2"));
        taskManager.createSubTask(new SubTask("SubTask2", Status.NEW, "SubTaskDesk2", epic2));
        taskManager.createSubTask(new SubTask("SubTask2", Status.NEW, "SubTaskDesk2", epic2));

        Epic epic3 = taskManager.createEpic(new Epic("Epic3", Status.NEW,"EpicDesc3"));
        taskManager.createSubTask(new SubTask("SubTask3", Status.DONE, "SubTaskDesk3", epic3));
        taskManager.createSubTask(new SubTask("SubTask3", Status.DONE, "SubTaskDesk3", epic3));

        System.out.println("Check delete All");
        System.out.println("Tasks" + taskManager.getAll());
        System.out.println("Epics" + taskManager.getAllEpic());
        System.out.println("Subtasks" + taskManager.getAllSubTask());

        taskManager.deleteAll();
        taskManager.deleteAllEpic();
        taskManager.deleteAllSubTask();

        System.out.println("Tasks" + taskManager.getAll());
        System.out.println("Epics" + taskManager.getAllEpic());
        System.out.println("Subtasks" + taskManager.getAllSubTask());
        System.out.println("===========================================================");
    }
}
