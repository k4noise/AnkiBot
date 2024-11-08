package ru.rtf.telegramBot;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EmptySource;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Commands.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Тесты для класса управления командами
 */
class CommandHandlerManagerTest {

    private final Map<String, CommandHandler> commands = new LinkedHashMap<>();
    private CommandHandlerManager commandHandlerManager;
    private UserDecksData userDecksData;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        uploadCommands();
        commandHandlerManager = new CommandHandlerManager(commands, userDecksData);
    }

    /**
     * Возвращение экземпляра одной из команд
     */
    @Test
    void testReturnsCorrectCommand() {
        CommandHandler startCommandHandler = commandHandlerManager.getCommand("/start");
        Assertions.assertNotNull(startCommandHandler);
        Assertions.assertTrue(startCommandHandler instanceof StartCommandHandler);
    }

    /**
     * Попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> commandHandlerManager.getCommand("/unknown")
        );
        Assertions.assertEquals("Команда /unknown не распознана", exception.getMessage());
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testCorrectCountParams() {
        Long chatId = 123456789L;
        userDecksData.addUser(chatId);
        DeckManager decks = userDecksData.getUserDecks(chatId);
        decks.addDeck("old name");
        String res = commandHandlerManager.execution(chatId, "/rename_deck old name:=new name");
        Assertions.assertEquals("Колода успешно переименована: old name -> new name", res);
    }

    /**
     * Тест на несоответствие параметров
     */
    @Test
    void testNoCorrectCountParams() {
        Long chatId = 123456789L;
        String res = commandHandlerManager.execution(chatId, "/rename_deck old name:=");
        Assertions.assertEquals("Ошибка параметров команды.\n Проверьте на соответствие шаблону (/help)", res);
    }

    /// Проверка работы команд

    /**
     * Тест на добавление колод и их вывод пользователю
     */
    @Test
    void createDeckAndPrintDecksTest() {
        Long chatId = 123456789L;
        String messageToUserEmpty = commandHandlerManager.execution(chatId, "/list_decks");
        Assertions.assertEquals("У Вас пока нет ни одной колоды, создайте первую /create_deck <название>",
                messageToUserEmpty);
        commandHandlerManager.execution(chatId, "/create_deck First Deck");
        commandHandlerManager.execution(chatId, "/create_deck Another");
        String messageToUserListDeck = commandHandlerManager.execution(chatId, "/list_decks");
        Assertions.assertEquals("""
                Ваши колоды:
                First Deck: 0 карт
                Another: 0 карт""", messageToUserListDeck);
    }

    /**
     * Тест на корректное переименование колоды у пользователя
     */
    @Test
    void renameDeckTest() {
        Long chatId = 123456789L;
        commandHandlerManager.execution(chatId, "/create_deck wrong name");
        String messageToUserListDeck = commandHandlerManager.execution(chatId, "/list_decks");
        Assertions.assertEquals("""
                Ваши колоды:
                wrong name: 0 карт""", messageToUserListDeck);
        commandHandlerManager.execution(chatId, "/rename_deck wrong name := correct name");
        String anotherMessageToUser = commandHandlerManager.execution(chatId, "/list_decks");
        Assertions.assertEquals("""
                Ваши колоды:
                correct name: 0 карт""", anotherMessageToUser);
    }

    /**
     * Тест на корректное удаление колоды у пользователя
     */
    @Test
    void deleteDeckTest() {
        Long chatId = 15461521L;
        commandHandlerManager.execution(chatId, "/create_deck DelName");
        commandHandlerManager.execution(chatId, "/create_deck AnotherName");
        commandHandlerManager.execution(chatId, "/delete_deck DelName");
        String messageToUserListDecks = commandHandlerManager.execution(chatId, "/list_decks");
        Assertions.assertEquals("""
                Ваши колоды:
                AnotherName: 0 карт""", messageToUserListDecks);
    }

    /**
     * Тест на корректное добавление карты в колоду и вывод карт колоды пользователю
     */
    @Test
    void createCardAndPrintCardsTest() {
        Long chatId = 15461521L;
        commandHandlerManager.execution(chatId, "/create_deck SomeDeck");
        String messageToUserListCards = commandHandlerManager.execution(chatId, "/list_cards SomeDeck");
        Assertions.assertEquals("""
                SomeDeck:
                В этой колоде пока нет карточек""", messageToUserListCards);
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: first = first def");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: second = second def");
        String messageToUserListCardsWithCards = commandHandlerManager.execution(chatId, "/list_cards SomeDeck");
        Assertions.assertEquals("""
                SomeDeck:
                "first" = first def
                "second" = second def
                """, messageToUserListCardsWithCards);
    }

    /**
     * Тест на корректное удаление карточки
     */
    @Test
    void deleteCardTest() {
        Long chatId = 15461521L;
        commandHandlerManager.execution(chatId, "/create_deck SomeDeck");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: first = first def");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: second = second def");

        String messageFindCard = commandHandlerManager.execution(chatId, "/list_card SomeDeck: first");
        Assertions.assertEquals("\"first\" = first def", messageFindCard);

        commandHandlerManager.execution(chatId, "/delete_card SomeDeck: first");

        String messageNoFindCard = commandHandlerManager.execution(chatId, "/list_card SomeDeck: first");
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином first не существует в колоде""", messageNoFindCard);

        String messageToUserListCards = commandHandlerManager.execution(chatId, "/list_cards SomeDeck");
        Assertions.assertEquals("""
                SomeDeck:
                "second" = second def
                """, messageToUserListCards);
    }

    /**
     * Тест на корректное изменение определения карточки и вывод одиночной карточки
     */
    @Test
    void editCardDefAndPrintCardTest() {
        Long chatId = 15461521L;
        commandHandlerManager.execution(chatId, "/create_deck SomeDeck");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: first = first def");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: second = second def");
        String messageFirstCard = commandHandlerManager.execution(chatId, "/list_card SomeDeck: first");
        Assertions.assertEquals("\"first\" = first def", messageFirstCard);

        //изменение определения
        commandHandlerManager.execution(chatId, "/edit_card_def SomeDeck: first = another def");
        String messageFirstCardWithAnotherDef = commandHandlerManager.execution(chatId,
                "/list_card SomeDeck: first");
        Assertions.assertEquals("\"first\" = another def", messageFirstCardWithAnotherDef);
    }

    /**
     * Тест на корректное изменение термина карточки и вывод одиночной карточки
     */
    @Test
    void editCardTermAndPrintCardTest() {
        Long chatId = 15461521L;
        commandHandlerManager.execution(chatId, "/create_deck SomeDeck");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: first = first def");
        commandHandlerManager.execution(chatId, "/create_card SomeDeck: second = second def");

        //изменение термина
        commandHandlerManager.execution(chatId, "/edit_card_term SomeDeck: second = last");
        String messageCardWithAnotherTerm = commandHandlerManager.execution(chatId,
                "/list_card SomeDeck: last");
        Assertions.assertEquals("\"last\" = second def", messageCardWithAnotherTerm);
        //обращение к старому значению
        String messageCardWithOldTerm = commandHandlerManager.execution(chatId,
                "/list_card SomeDeck: second");
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином second не существует в колоде""", messageCardWithOldTerm);
    }

    /**
     * Добавление команд, их имени и экземпляра соответствующего класса в список команд
     */
    private void uploadCommands() {
        commands.put("/start", new StartCommandHandler());
        commands.put("/help", new HelpCommandHandler());
        // команды для работы с колодами
        commands.put("/list_decks", new ListDecksCommandHandler());
        commands.put("/create_deck", new CreateDeckCommandHandler());
        commands.put("/rename_deck", new RenameDeckCommandHandler());
        commands.put("/delete_deck", new DeleteDeckCommandHandler());
        // команды для работы с картами
        commands.put("/list_cards", new ListCardsCommandsHandler());
        commands.put("/create_card", new CreateCardCommandHandler());
        commands.put("/edit_card_term", new EditCardTermCommandHandler());
        commands.put("/edit_card_def", new EditCardDefCommandHandler());
        commands.put("/delete_card", new DeleteCardCommandHandler());
        commands.put("/list_card", new ListCardCommandHandler());
    }
}
