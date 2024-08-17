package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Epic")
class EpicTest {

    @Test
    @DisplayName("Тест на совпадение двух эпиков")
    public void equals_returnTrue_withFullCopy() {
        //given
        Epic epic = getEpic();

        //when
        Epic compareEpic = new Epic(epic.getId(), epic.getName(), epic.getStatus(), epic.getDescription());

        //then
        assertEpicEquals(epic, compareEpic);
    }

    @Test
    @DisplayName("Тест добавления подзадач в эпик, с проверкой на дубликаты")
    public void addSubTask_notAddDuplicates() {
        //given
        Epic epic = getEpic();
        int subTaskId = 5;
        int expectedSizeList = 1;

        //when
        epic.addSubTask(subTaskId);
        epic.addSubTask(subTaskId);

        //then
        assertEquals(epic.getSubTaskIds().size(), expectedSizeList, "Количество подзадач в Эпике " +
                "отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест удаления подзадач из эпика")
    public void removeSubTask_shouldReturnTrue() {
        //given
        Epic epic = getEpic();
        int subTaskId = 5;

        //when
        epic.addSubTask(subTaskId);
        epic.addSubTask(subTaskId);
        epic.removeSubTask(subTaskId);

        //then
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