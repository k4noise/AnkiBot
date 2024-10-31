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
class CommandManagerTest {
    /**
     * Команды
     */
    private final Map<String, Command> commands = new LinkedHashMap<>();
    /**
     * Менеджер управления командами
     */
    private CommandManager commandManager;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        uploadCommands();
        commandManager = new CommandManager(commands, userDecksData);
    }

    /**
     * Возвращение экземпляра одной из команд
     */
    @Test
    void testReturnsCorrectCommand() {
        Command startCommand = commandManager.getByName("/start");
        Assertions.assertNotNull(startCommand);
        Assertions.assertTrue(startCommand instanceof StartCommand);
    }

    /**
     * Попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> commandManager.getByName("/unknown")
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
        String res = commandManager.execute(chatId, "/rename_deck old name:=new name");
        Assertions.assertEquals("Колода успешно переименована: old name -> new name", res);
    }

    /**
     * Тест на несоответствие параметров
     */
    @Test
    void testNoCorrectCountParams() {
        Long chatId = 123456789L;
        String res = commandManager.execute(chatId, "/rename_deck old name:=");
        Assertions.assertEquals("Ошибка параметров команды.\n Проверьте на соответствие шаблону (/help)", res);
    }

    /**
     * Добавление команд, их имени и экземпляра соответствующего класса в список команд
     */
    private void uploadCommands() {
        commands.put("/start", new StartCommand());
        commands.put("/help", new HelpCommand());
        // команды для работы с колодами
        commands.put("/list_decks", new ListDecksCommand());
        commands.put("/create_deck", new CreateDeckCommand());
        commands.put("/rename_deck", new RenameDeckCommand());
        commands.put("/delete_deck", new DeleteDeckCommand());
        // команды для работы с картами
        commands.put("/list_cards", new ListCardsCommand());
        commands.put("/create_card", new CreateCardCommand());
        commands.put("/edit_card_term", new EditCardTermCommand());
        commands.put("/edit_card_def", new EditCardDefCommand());
        commands.put("/delete_card", new DeleteCardCommand());
        commands.put("/list_card", new ListCardCommand());
    }
}
