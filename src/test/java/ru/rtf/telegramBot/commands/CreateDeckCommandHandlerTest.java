package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды создания новой колоды
 */
class CreateDeckCommandHandlerTest {
    /**
     * Обработчик команды для создания новой колоды
     */
    private CreateDeckCommandHandler createDeckCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private Long chatId = 1L;

    /**
     * Создание нового экземпляра обработчика команд и менеджера колод для каждого теста
     */
    @BeforeEach
    void setUp() {
        createDeckCommandHandler = new CreateDeckCommandHandler();
        deckManager = new DeckManager();
    }

    /**
     * Корректное добавление колоды
     */
    @Test
    void testSimpleAddDeck() {
        String message = createDeckCommandHandler.handle(deckManager, chatId, new String[]{"Deck"});

        Assertions.assertEquals(1, deckManager.getDecks().size());
        Assertions.assertEquals("Колода Deck успешно добавлена", message);
    }

    /**
     * Тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        deckManager.addDeck("name");

        String ans = createDeckCommandHandler.handle(deckManager, chatId, new String[]{"name"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем name существует в менеджере""", ans);
    }
}