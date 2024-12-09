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
 * Тесты для класса обработки сообщений
 * <p>Если сообщение - команда, то тестируется передача параметров сообщения нужному обработчику</p>
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
                "/check", new LearnCheckCommandHandler(manager),
                "/end_check", new EndCheckCommandHandler(manager)
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
                Ошибка параметров команды.
                 Проверьте на соответствие шаблону (/help)""", message);

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
                Ошибка параметров команды.
                 Проверьте на соответствие шаблону (/help)""", message);

        Mockito.verify(commandHandlerMock, Mockito.never()).handle(Mockito.any(DeckManager.class),
                Mockito.eq(newChatId), Mockito.isNull());
    }

    /**
     * Тест обработки правильного ответа в режиме обучения и команды на примере завершения сессии
     */
    @Test
    @DisplayName("Обработка правильного ответа и команды выхода")
    void testHandleAnswerAndCommand() {
        messageHandler.handle(newChatId, "/create_deck Deck");
        messageHandler.handle(newChatId, "/create_card Deck : term = def");
        messageHandler.handle(newChatId, "/create_card Deck : term2 = def2");

        String startMessage = messageHandler.handle(newChatId, "/check typing : Deck");
        Assertions.assertEquals("""
                Вы начали обучение в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин (регистр не учитывается)
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: Определение - "def"
                Введите соответствующий термин:""", startMessage);

        String reactionInRightAnswer = messageHandler.handle(newChatId, "term");
        Assertions.assertEquals("""
                        Верно! Правильный ответ:
                        "term" = def
                        Определение - "def2"
                        Введите соответствующий термин:""",
                reactionInRightAnswer,
                "Сообщение должно быть обработано как ответ в режиме обучения и быть правильным ответом"
        );

        String reactionInCommandMessage = messageHandler.handle(newChatId, "/end_check");
        Assertions.assertEquals("""
                Вы досрочно завершили сессию
                Вы помните 100% терминов из показанных
                """, reactionInCommandMessage);

        String reactionNoCommandMessage = messageHandler.handle(newChatId, "term2");
        Assertions.assertEquals(
                "Команда term2 не распознана",
                reactionNoCommandMessage,
                "Сообщение не должно обрабатываться как ответ"
        );
    }

    /**
     * Тест обработки неправильного ответа и реакции на любой другой ответ для режима с однозначным ответом
     */
    @Test
    @DisplayName("Обработка неправильного и рандомного ответов")
    void testHandleWrongAndRandomAnswer() {
        messageHandler.handle(newChatId, "/create_deck Deck2");
        messageHandler.handle(newChatId, "/create_card Deck2 : term = def");
        messageHandler.handle(newChatId, "/create_card Deck2 : term2 = def2");
        messageHandler.handle(newChatId, "/check memory : Deck2");

        String reactionInWrongAnswer = messageHandler.handle(newChatId, "0");
        Assertions.assertEquals("""
                        Неверно. Правильный ответ:
                        "term" = def
                        Термин - "term2\"""",
                reactionInWrongAnswer,
                "Ответ должен быть неправильным"
        );

        String reactionInRandomPhraseAnswer = messageHandler.handle(newChatId, "term");
        Assertions.assertEquals("""
                        Неверно. Правильный ответ:
                        "term2" = def2
                        Вы прошли все карточки в колоде!
                        Вы помните 0% терминов из показанных
                        """,
                reactionInRandomPhraseAnswer,
                "Рандомная фраза - по-прежнему неправильный ответ"
        );
    }

    /**
     * Тест обработки неправильного и правильного ответов
     */
    @Test
    @DisplayName("Обработка неправильного и правильного ответов")
    void testHandleWrongAndRightAnswer() {
        messageHandler.handle(newChatId, "/create_deck Deck3");
        messageHandler.handle(newChatId, "/create_card Deck3 : term = def");
        messageHandler.handle(newChatId, "/create_card Deck3 : term2 = def2");
        messageHandler.handle(newChatId, "/check typing : Deck3");

        String reactionInWrongAnswerMessage = messageHandler.handle(newChatId, "term2");
        Assertions.assertEquals("""
                        Неверно. Правильный ответ:
                        "term" = def
                        Определение - "def2"
                        Введите соответствующий термин:""",
                reactionInWrongAnswerMessage,
                "Ответ должен быть неправильным"
        );

        String reactionInRightAnswerMessage = messageHandler.handle(newChatId, "term2");
        Assertions.assertEquals("""
                        Верно! Правильный ответ:
                        "term2" = def2
                        Вы прошли все карточки в колоде!
                        Вы помните 50% терминов из показанных
                        """,
                reactionInRightAnswerMessage,
                "Ответ должен быть неправильным"
        );
    }
}
