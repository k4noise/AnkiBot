package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования класса карт
 */
public class CardTest {
    /**
     * Тесты для проверки работы конструктора
     * парамеры конструктора не должны быть пустыми
     */
    @Test
    @DisplayName("Проверка конструктора")
    void testCardInitEmptyTwoParams() {
        String messageEmptyError = "Термин и определение не могут быть пустыми";

        IllegalArgumentException exceptionEmptyTerm = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Card("", "abс"),
                "Карта с пустым термином не может быть создана"
        );
        Assertions.assertEquals(messageEmptyError, exceptionEmptyTerm.getMessage());

        IllegalArgumentException exceptionEmptyDef = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Card("term", ""),
                "Карта с пустым определением не может быть создана"
        );
        Assertions.assertEquals(messageEmptyError, exceptionEmptyDef.getMessage());
    }

    /**
     * Создание карты из существующей с новым термином и старым определением
     */
    @Test
    @DisplayName("изменение термина (новая карта)")
    void testChangeTerm() {
        Card card = new Card("hero", "person who kill people");
        card.changeTerm("villain");
        Assertions.assertEquals("villain", card.getTerm());
    }

    /**
     * Изменение определения
     */
    @Test
    @DisplayName("изменение определения")
    void testChangeDefinition() {
        Card card = new Card("hero", "person who kill people");
        Card changedCard = new Card(card.getTerm(), card.getDefinition());
        changedCard.changeDefinition("person who saves people");
        Assertions.assertEquals(card, changedCard,
                "При изменении определения карта считается такой же");
        Assertions.assertEquals(card.getTerm(), changedCard.getTerm(),
                "Термины должны совпадать");
        Assertions.assertEquals("person who saves people", changedCard.getDefinition(),
                "Определение должно совпадать с переданным значением");
    }
}