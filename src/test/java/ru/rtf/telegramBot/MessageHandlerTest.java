package ru.rtf.telegramBot;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.commands.*;
import ru.rtf.telegramBot.learning.SessionManager;

import java.util.Arrays;
import java.util.Map;

/**
 * Тесты для класса управления командами
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageHandlerTest {
    /**
     * id нового пользователя
     */
    private final Long newChatId = 123456789L;
    /**
     * Хранилище команд
     */
    private Map<String, CommandHandler> commands;
    /**
     * Экземпляр обработчика сообщений
     */
    private MessageHandler messageHandler;

    /**
     * Создание менеджера управления с командами
     */
    @BeforeAll
    void initCommandManager(@Mock StartCommandHandler startCommandHandler,
                            @Mock RenameDeckCommandHandler renameDeckCommandHandler) {
        Mockito.when(renameDeckCommandHandler.getParamsCount()).thenReturn(2);

        SessionManager manager = new SessionManager();
        commands = Map.of(
                "/start", startCommandHandler,
                "/rename_deck", renameDeckCommandHandler,
                // следующие обработчики без моков для модификации внутреннего состояния
                "/create_deck", new CreateDeckCommandHandler(),
                "/create_card", new CreateCardCommandHandler(),
                "/check", new LearnCheckCommandHandler(manager)
        );
        messageHandler = new MessageHandler(commands, manager);
    }

    /**
     * Тест попытки вызова неизвестной команды
     */
    @Test
    @DisplayName("Обработка неизвестной команды")
    void testUnknownCommand() {
        String message = messageHandler.handle(newChatId, "/unknown");
        Assertions.assertEquals("Команда /unknown не распознана", message);
    }

    /**
     * Тест корректного вызова команды без параметров
     */
    @Test
    @DisplayName("Обработка команды без параметров")
    void testHandleCommandNoParams() {
        CommandHandler commandHandlerMock = commands.get("/start");
        Mockito.when(commandHandlerMock.handle(Mockito.any(DeckManager.class), Mockito.anyLong(), Mockito.any()))
                .thenReturn("Добро пожаловать в AnkiBot");

        String message = messageHandler.handle(newChatId, "/start");
        Assertions.assertEquals("Добро пожаловать в AnkiBot", message);

        Mockito.verify(commandHandlerMock, Mockito.times(1))
                .handle(Mockito.any(DeckManager.class), Mockito.eq(newChatId), Mockito.isNull());
    }

    /**
     * Тест корректного вызова команды с корректным количеством параметров
     */
    @Test
    @DisplayName("Обработка команды с параметрами")
    void testHandleCommandWithRightParamsCount() {
        CommandHandler commandHandlerMock = commands.get("/rename_deck");
        Mockito.when(commandHandlerMock.handle(Mockito.any(DeckManager.class), Mockito.anyLong(), Mockito.notNull()))
                .thenReturn("Колода была успешно переименована");

        String message = messageHandler.handle(newChatId, "/rename_deck Deck := Deck2");
        Assertions.assertEquals("Колода была успешно переименована", message);

        Mockito.verify(commandHandlerMock, Mockito.times(1))
                .handle(
                        Mockito.any(DeckManager.class),
                        Mockito.eq(newChatId),
                        Mockito.argThat(args -> {
                            String[] expectedArgs = {"Deck", "Deck2"};
                            return Arrays.equals(expectedArgs, args);
                        })
                );
    }

    /**
     * Тест некорректного вызова команды с некорректным количеством параметров (1 вместо 2)
     */
    @Test
    @DisplayName("Обработка команды с частью аргументов")
    void testHandleCommandWithWrongParamsCount() {
        CommandHandler commandHandlerMock = commands.get("/rename_deck");

        String message = messageHandler.handle(newChatId, "/rename_deck Deck");
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
    @DisplayName("Обработка команды с параметрами без параметров")
    void testHandleCommandWithNoParams() {
        CommandHandler commandHandlerMock = commands.get("/rename_deck");

        String message = messageHandler.handle(newChatId, "/rename_deck");
        Assertions.assertEquals("""
                Ошибка параметров команды
                 Проверьте на соответствие шаблону \\(/help\\)""", message);

        Mockito.verify(commandHandlerMock, Mockito.never()).handle(Mockito.any(DeckManager.class),
                Mockito.eq(newChatId), Mockito.isNull());
    }

    /**
     * Тест обработки сообщений в режиме обучения
     * <p>Проверяется корректность обработки сообщения по входу и выходу из режима обучения
     * и выполнение команды из режима обучения</p>
     */
    @Test
    @DisplayName("Обработка сообщения в режиме обучения")
    void testHandleAnswersInLearning() {
        messageHandler.handle(newChatId, "/create_deck Deck");
        messageHandler.handle(newChatId, "/create_card Deck : term = def");

        String startMessage = messageHandler.handle(newChatId, "/check typing : Deck");
        Assertions.assertEquals("""
                Вы начали обучение в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин (регистр не учитывается)
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: Определение - "def"
                Введите соответствующий термин:""", startMessage);

        CommandHandler commandHandlerMock = commands.get("/start");
        Mockito.when(commandHandlerMock.handle(Mockito.any(DeckManager.class), Mockito.anyLong(), Mockito.any()))
                .thenReturn("Добро пожаловать в AnkiBot");
        String reactionInCommandMessage = messageHandler.handle(newChatId, "/start");
        Assertions.assertEquals("Добро пожаловать в AnkiBot", reactionInCommandMessage);

        String reactionInAnswerMessage = messageHandler.handle(newChatId, "term");
        Assertions.assertEquals("""
                Верно! Правильный ответ:
                "term" = def
                Вы прошли все карточки в колоде!""",
                reactionInAnswerMessage,
                "Сообщение должно быть обработано как ответ в режиме обучения"
        );

        String reactionInMessageNoCommand = messageHandler.handle(newChatId, "term");
        Assertions.assertEquals(
                "Команда term не распознана",
                reactionInMessageNoCommand,
                "Сообщение должно быть обработано как команда"
        );
    }
}
