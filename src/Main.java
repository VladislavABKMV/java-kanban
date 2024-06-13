import model.Status;
import model.SubTask;
import model.Task;
import model.Epic;
import service.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = inMemoryTaskManager.createTask(new Task("Task1",  Status.NEW, "TaskDesc1"));
        Epic epic1 = inMemoryTaskManager.createEpic(new Epic("Epic1", Status.NEW,"EpicDesc1"));
        SubTask subTaskFirstForEpic1 = inMemoryTaskManager.createSubTask(new SubTask("SubTask1", Status.NEW,
                "SubTaskDesk1", epic1.getId()));
        SubTask subTaskSecondForEpic1 = inMemoryTaskManager.createSubTask(new SubTask("SubTask2", Status.NEW,
                "SubTaskDesk2", epic1.getId()));

//        Check create
        System.out.println("Check create");
        System.out.println("new Task: " + task1.toString());
        System.out.println("new Epic: " + epic1.toString());
        System.out.println("new SubTask 1 For Epic: " + subTaskFirstForEpic1.toString());
        System.out.println("new SubTask 2 For Epic: " + subTaskSecondForEpic1.toString());
        System.out.println("===========================================================");

//        Check getTask
        System.out.println("Check getTask");
        System.out.println("Get Epic: " + inMemoryTaskManager.getEpic(epic1.getId()).toString());
        System.out.println("Get Task: " + inMemoryTaskManager.getTask(task1.getId()).toString());
        System.out.println("Get Task: " + inMemoryTaskManager.getTask(task1.getId()).toString());
        System.out.println("Get Task: " + inMemoryTaskManager.getTask(task1.getId()).toString());
        System.out.println("Get Task: " + inMemoryTaskManager.getTask(task1.getId()).toString());
        System.out.println("Get Epic: " + inMemoryTaskManager.getEpic(epic1.getId()).toString());
        System.out.println("Get SubTask 1: " + inMemoryTaskManager.getSubTask(subTaskFirstForEpic1.getId()).toString());
        System.out.println("Get SubTask 2: " + inMemoryTaskManager.getSubTask(subTaskSecondForEpic1.getId()).toString());
        System.out.println("===========================================================");

//        Check getAll
        System.out.println("Check getAll");
        System.out.println("Tasks" + inMemoryTaskManager.getAllTask());
        System.out.println("Epics" + inMemoryTaskManager.getAllEpic());
        System.out.println("Subtasks" + inMemoryTaskManager.getAllSubTask());
        System.out.println("===========================================================");

//        Check All SubTask for Epic
        System.out.println("Check All SubTask for Epic");
        System.out.println("Get SubTask for Epic: " + inMemoryTaskManager.getSubTasksForEpic(epic1));
        System.out.println("===========================================================");

//        Check Update
        inMemoryTaskManager.updateTask(new Task(task1.getId(), "TaskUpdate1",  Status.IN_PROGRESS,
                "TaskDescUpdate1"));
        inMemoryTaskManager.updateEpic(new Epic(epic1.getId(), "EpicUpdate1",  Status.DONE,
                "TaskDescUpdate1"));
        inMemoryTaskManager.updateSubTask(new SubTask(subTaskFirstForEpic1.getId(), "TaskUpdate1",  Status.IN_PROGRESS,
                "TaskDescUpdate1", epic1.getId()));

        System.out.println("Check update Task");
        System.out.println("Old Task: " + task1);
        System.out.println("New Task: " + inMemoryTaskManager.getTask(task1.getId()).toString());
        System.out.println("Check update SubTask");
        System.out.println("Old SubTask1: " + subTaskFirstForEpic1);
        System.out.println("New SubTask1.: " + inMemoryTaskManager.getSubTask(subTaskFirstForEpic1.getId()));
        System.out.println("Check update Epic");
        System.out.println("New Epic: " + inMemoryTaskManager.getEpic(epic1.getId()).toString());
        System.out.println("===========================================================");


//        Check delete
        System.out.println("Check delete");
        System.out.println("Tasks" + inMemoryTaskManager.getAllTask());
        System.out.println("Epics" + inMemoryTaskManager.getAllEpic());
        System.out.println("Subtasks" + inMemoryTaskManager.getAllSubTask());

        inMemoryTaskManager.deleteTask(task1.getId());
        inMemoryTaskManager.deleteSubTask(subTaskFirstForEpic1.getId());
        System.out.println("After removal subTask1: " + inMemoryTaskManager.getSubTasksForEpic(epic1));
        System.out.println("subTasks after removal subTask1: " + inMemoryTaskManager.getAllSubTask());
        inMemoryTaskManager.deleteEpic(epic1.getId());

        System.out.println("Tasks" + inMemoryTaskManager.getAllTask());
        System.out.println("Epics" + inMemoryTaskManager.getAllEpic());
        System.out.println("Subtasks" + inMemoryTaskManager.getAllSubTask());
        System.out.println("===========================================================");


//        Check deleteAll
        inMemoryTaskManager.createTask(new Task("Task2",  Status.NEW, "TaskDesc2"));
        inMemoryTaskManager.createTask(new Task("Task3",  Status.NEW, "TaskDesc3"));

        Epic epic2 = inMemoryTaskManager.createEpic(new Epic("Epic2", Status.NEW,"EpicDesc2"));
        inMemoryTaskManager.createSubTask(new SubTask("SubTask2", Status.NEW, "SubTaskDesk2", epic2.getId()));
        inMemoryTaskManager.createSubTask(new SubTask("SubTask2", Status.NEW, "SubTaskDesk2", epic2.getId()));

        Epic epic3 = inMemoryTaskManager.createEpic(new Epic("Epic3", Status.NEW,"EpicDesc3"));
        inMemoryTaskManager.createSubTask(new SubTask("SubTask3", Status.DONE, "SubTaskDesk3", epic3.getId()));
        inMemoryTaskManager.createSubTask(new SubTask("SubTask3", Status.DONE, "SubTaskDesk3", epic3.getId()));

        System.out.println("Check delete All");
        System.out.println("Tasks" + inMemoryTaskManager.getAllTask());
        System.out.println("Epics" + inMemoryTaskManager.getAllEpic());
        System.out.println("Subtasks" + inMemoryTaskManager.getAllSubTask());

        inMemoryTaskManager.deleteAllTask();
        inMemoryTaskManager.deleteAllEpic();
        inMemoryTaskManager.deleteAllSubTask();

        System.out.println("Tasks" + inMemoryTaskManager.getAllTask());
        System.out.println("Epics" + inMemoryTaskManager.getAllEpic());
        System.out.println("Subtasks" + inMemoryTaskManager.getAllSubTask());
        System.out.println("===========================================================");

        System.out.println("History: " + inMemoryTaskManager.getHistory());
    }
}
