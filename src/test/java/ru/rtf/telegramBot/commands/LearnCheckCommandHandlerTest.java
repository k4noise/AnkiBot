package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.*;
import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.learning.SessionManager;

/**
 * Тест обработчика команды начала сессии обучения
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LearnCheckCommandHandlerTest {
    /**
     * Идентификатор чата
     */
    private final Long chatId = 1L;
    /**
     * Обработчик команды для начала режима обучения
     */
    private LearnCheckCommandHandler learnCheckCommandHandler;
    /**
     * Менеджер сессий пользователей
     */
    private SessionManager sessionManager;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;

    /**
     * Инициализировать колоду с картами и хранилища для обработчика команды
     */
    @BeforeAll
    void setUp() {
        deckManager = new DeckManager();
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck")
                .addCard(new Card("term", "def"));

        sessionManager = new SessionManager();
        learnCheckCommandHandler = new LearnCheckCommandHandler(sessionManager);
    }

    /**
     * Завершить активную сессию, если имеется
     */
    @BeforeEach
    void endActiveSession() {
        if (sessionManager.hasActive(chatId)) {
            sessionManager.end(chatId);
        }
    }

    /**
     * Тест успешности запуска режима "соответствие"
     */
    @Test
    @DisplayName("Запуск режима \"соответствие\"")
    void testStartMatchLearningCheck() {
        String message = learnCheckCommandHandler.handle(deckManager, chatId, new String[]{"match", "Deck"});
        Assertions.assertEquals("""
                Вы начали обучение в режиме соответствия
                Показывается термин и определение, ваша задача - определить, соответствует ли термин определению
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: Утверждение:
                term - def
                1 - верно, 0 - неверно""", message);
    }

    /**
     * Тест успешности запуска режима "ввод"
     */
    @Test
    @DisplayName("Запуск режима \"ввод\"")
    void testStartTypingLearningCheck() {
        String message = learnCheckCommandHandler.handle(deckManager, chatId, new String[]{"typing", "Deck"});
        Assertions.assertEquals("""
                Вы начали обучение в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин (регистр не учитывается)
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: Определение - "def"
                Введите соответствующий термин:""", message);
    }

    /**
     * Тест успешности запуска режима оценки запоминания
     */
    @Test
    @DisplayName("Запуск режима оценки запоминания")
    void testStartMemoryLearningCheck() {
        String message = learnCheckCommandHandler.handle(deckManager, chatId, new String[]{"memory", "Deck"});
        Assertions.assertEquals("""
                Вы начали обучение в режиме карточки
                Вам показывается термин, ваша задача - вспомнить определение
                После оценить, насколько хорошо вы помните определение
                0 - не помню  1 - частично помню  2 - помню
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: Термин - "term\"""", message);
    }

    /**
     * Тест невозможности запуска новой сессии при наличии активной
     */
    @Test
    @DisplayName("Начать сессию в сессии")
    void testStartSessionInSession() {
        learnCheckCommandHandler.handle(deckManager, chatId, new String[]{"memory", "Deck"});
        String message = learnCheckCommandHandler.handle(deckManager, chatId, new String[]{"memory", "Deck"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Имеется активная сессия обучения""", message);
    }

    /**
     * Тест запуска несуществующего режима
     */
    @Test
    @DisplayName("Запуск несуществующего режима")
    void testStartNonExistCheck() {
        String message = learnCheckCommandHandler.handle(deckManager, chatId, new String[]{"wrong", "Deck"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Неизвестный режим обучения""", message);
    }
}