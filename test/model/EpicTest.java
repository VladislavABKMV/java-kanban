package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Epic")
class EpicTest {

    @Test
    @DisplayName("Тест на совпадение двух эпиков")
    public void equals_returnTrue_withFullCopy() {
        Epic epic = getEpic();
        Epic compareEpic = new Epic(epic.getId(), epic.getName(), epic.getStatus(), epic.getDescription());
        
        assertEpicEquals(epic, compareEpic);
    }

    /*
    /  Дело в том, что в моей реализации Эпик знал о существовании подзадач,
    /  ибо при добавлении/удалении подзадачи его методы принимали подзадачу,
    /  после извлекали id, и уже после добавляли/удаляли id из списка
    /
    /  А наследника класса подзадач я сделал потому,
    /  что наставник порекомендовал максимально избегать в тестах одного класса использование других классов
    */
    @Test
    @DisplayName("Тест добавления подзадач в эпик, с проверкой на дубликаты")
    public void addSubTask_notAddDuplicates() {
        Epic epic = getEpic();
        int subTaskId = 5;
        int expectedSizeList = 1;

        epic.addSubTask(subTaskId);
        epic.addSubTask(subTaskId);

        assertEquals(epic.getSubTaskIds().size(), expectedSizeList, "Количество подзадач в Эпике " +
                "отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест удаления подзадач из эпика")
    public void removeSubTask_shouldReturnTrue() {
        Epic epic = getEpic();
        int subTaskId = 5;

        epic.addSubTask(subTaskId);
        epic.addSubTask(subTaskId);
        epic.removeSubTask(subTaskId);

        assertTrue(epic.getSubTaskIds().isEmpty(), "Ожидается отсутствие подзадач");
    }

    private static void assertEpicEquals(Epic epic, Epic copyEpic) {
        assertEquals(epic.getId(), copyEpic.getId(), "id " + "должны совпадать.");
        assertEquals(epic.getName(), copyEpic.getName(), "name  " + "должны совпадать.");
        assertEquals(epic.getStatus(), copyEpic.getStatus(), "status " + "должны совпадать.");
        assertEquals(epic.getDescription(), copyEpic.getDescription(), "description " + "должны совпадать.");
    }

    private  static Epic getEpic() {
        return new Epic(0, "name", Status.NEW, "description");
    }
}