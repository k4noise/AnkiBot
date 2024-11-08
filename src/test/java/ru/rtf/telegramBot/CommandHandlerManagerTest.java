package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
