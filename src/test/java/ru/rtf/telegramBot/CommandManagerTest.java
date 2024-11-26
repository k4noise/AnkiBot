package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.commands.CreateCardCommandHandler;
import ru.rtf.telegramBot.commands.StartCommandHandler;

import java.util.Arrays;
import java.util.Map;

/**
 * Тесты для класса управления командами
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandManagerTest {
    /**
     * id нового пользователя
     */
    private final Long newChatId = 123456789L;
    /**
     * Хранилище команд
     */
    private Map<String, CommandHandler> commands;
    /**
     * Экземпляр менеджера обработчика команд
     */
    private CommandManager commandManager;

    /**
     * Создание менеджера управление с 2 командами - без параметров (<code>/start</code>)
     * и с 3 параметрами (<code>/create_card</code>)
     */
    @BeforeAll
    void initCommandManager(@Mock StartCommandHandler startCommandHandler,
                            @Mock CreateCardCommandHandler createCardCommandHandler) {
        Mockito.when(createCardCommandHandler.getParamsCount()).thenReturn(3);
        commands = Map.of(
                "/start", startCommandHandler,
                "/create_card", createCardCommandHandler
        );
        commandManager = new CommandManager(commands);
    }

    /**
     * Попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        String message = commandManager.handle(newChatId, "/unknown");
        Assertions.assertEquals("Команда /unknown не распознана", message);
    }

    /**
     * Тест корректного вызова команды без параметров
     */
    @Test
    void testHandleCommandNoParams() {
        CommandHandler commandHandlerMock = commands.get("/start");
        Mockito.when(commandHandlerMock.handle(Mockito.any(DeckManager.class), Mockito.anyLong(), Mockito.any()))
                .thenReturn("Добро пожаловать в AnkiBot");

        String message = commandManager.handle(newChatId, "/start");
        Assertions.assertEquals("Добро пожаловать в AnkiBot", message);

        Mockito.verify(commandHandlerMock, Mockito.times(1))
                .handle(Mockito.any(DeckManager.class), Mockito.eq(newChatId), Mockito.isNull());
    }

    /**
     * Тест корректного вызова команды с корректным количеством параметров
     */
    @Test
    void testHandleCommandWithRightParamsCount() {
        CommandHandler commandHandlerMock = commands.get("/create_card");
        Mockito.when(commandHandlerMock.handle(Mockito.any(DeckManager.class), Mockito.anyLong(), Mockito.notNull()))
                .thenReturn("Карта была успешно добавлена в колоду");

        String message = commandManager.handle(newChatId, "/create_card Deck : term = def");
        Assertions.assertEquals("Карта была успешно добавлена в колоду", message);

        Mockito.verify(commandHandlerMock, Mockito.times(1))
                .handle(
                        Mockito.any(DeckManager.class),
                        Mockito.eq(newChatId),
                        Mockito.argThat(args -> {
                            String[] expectedArgs = {"Deck", "term", "def"};
                            return Arrays.equals(expectedArgs, args);
                        })
                );
    }

    /**
     * Тест некорректного вызова команды с некорректным количеством параметров (1 вместо 3)
     */
    @Test
    void testHandleCommandWithWrongParamsCount() {
        CommandHandler commandHandlerMock = commands.get("/create_card");

        String message = commandManager.handle(newChatId, "/create_card Deck");
        Assertions.assertEquals("""
                Ошибка параметров команды
                 Проверьте на соответствие шаблону \\(/help\\)""", message);

        Mockito.verify(commandHandlerMock, Mockito.never()).handle(Mockito.any(DeckManager.class),
                Mockito.eq(newChatId), Mockito.argThat(args -> {
                    String[] expectedArgs = {"Deck"};
                    return Arrays.equals(expectedArgs, args);
                }));
    }

    /**
     * Тест некорректного вызова команды без параметров
     */
    @Test
    void testHandleCommandWithNoParams() {
        CommandHandler commandHandlerMock = commands.get("/create_card");

        String message = commandManager.handle(newChatId, "/create_card");
        Assertions.assertEquals("""
                Ошибка параметров команды
                 Проверьте на соответствие шаблону \\(/help\\)""", message);

        Mockito.verify(commandHandlerMock, Mockito.never()).handle(Mockito.any(DeckManager.class),
                Mockito.eq(newChatId), Mockito.isNull());
    }
}
