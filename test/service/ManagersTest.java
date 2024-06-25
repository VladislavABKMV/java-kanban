package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Managers")
public class ManagersTest {

    @Test
    @DisplayName("Проверка работоспособности метода GetDefault")
    void shouldGetDefaultNotNull() {
        assertNotNull(Managers.getDefault(), "Метод не возвращает менеджер задач");
    }

    @Test
    @DisplayName("Проверка работоспособности метода GetDefaultHistory")
    void shouldGetDefaultHistoryNotNull() {
        assertNotNull(Managers.getDefaultHistory(), "Метод не возвращает менеджер историй");
    }
}
