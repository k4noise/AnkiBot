package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.Set;

/**
 * Тесты для команды переименование колоды
 */
class RenameDeckCommandTest {

    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private RenameDeckCommand renameDeckCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = Mockito.mock(UserDecksData.class);
        renameDeckCommand = new RenameDeckCommand(senderMessages, userDecksData);
    }


    /**
     * тест на корректных данных
     */
    @Test
    void testCorrectNames() {
        Long chatId = 987654321L;
        String commandText = "/rename-deck OldName NewName";

        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(deckManager.getDecks()).thenReturn(Set.of(new Deck("OldName")));
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        renameDeckCommand.execution(chatId, commandText);

        // Проверяем, что метод updateDeckName был вызван с правильными аргументами

        Mockito.verify(deckManager).updateDeckName("OldName", "NewName");
        Mockito.verify(senderMessages).sendMessage(chatId, "Переименование OldName -> NewName");
    }

    /**
     * тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/rename-deck OldDeck"; // Только старое имя, новое не указано
        renameDeckCommand.execution(chatId, commandText);
        Mockito.verify(senderMessages).sendMessage(chatId,
                "Команда отменена. Команда должна соответствовать шаблону:\n /rename-deck <название колоды> <новое название>");
    }

    /**
     * тест с пустой коллекцией колод
     */
    @Test
    void testExecutionWithNoDecks() {
        Long chatId = 987654321L;
        String commandText = "/rename-deck OldDeck NewDeck";
        //колоды не созданы
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(null);
        renameDeckCommand.execution(chatId, commandText);
        Mockito.verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");

        //пустая коллекция колод
        Long chatIdEmpty = 123456789L;
        Mockito.when(userDecksData.getUserDecks(chatIdEmpty)).thenReturn(new DeckManager());
        renameDeckCommand.execution(chatIdEmpty, commandText);
        Mockito.verify(senderMessages).sendMessage(chatIdEmpty, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
    }
}

