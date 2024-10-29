package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тесты для команды просмотра имеющихся у пользователя колод
 */
public class ListDecksCommandTest {
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private ListDecksCommand listDecksCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = Mockito.mock(UserDecksData.class);
        listDecksCommand = new ListDecksCommand(senderMessages, userDecksData);
    }

    /**
     * тест на неинициализированном списке колод
     */
    @Test
    void testExecutionWithNoDecksNull() {
        Long chatId = 987654321L;

        // Настраиваем userDecksData, чтобы возвращал null для данного chatId (нет колод)
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(null);

        // попытка выполнить команду
        listDecksCommand.execution(chatId, "/list-decks");

        // Проверка, что отправляется корректное сообщение
        Mockito.verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
        //Проверка, что сообщение для ненулевого количества колод не отправилось
        Mockito.verify(senderMessages, Mockito.never()).sendMessage(chatId, "Ваши колоды:");
    }

    /**
     * тест на пустом списке колод
     */
    @Test
    void testExecutionWithNoDecksEmpty() {
        Long chatId = 987654321L;

        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(new DeckManager());

        // попытка выполнить команду
        listDecksCommand.execution(chatId, "/list-decks");

        // Проверка, что отправляется корректное сообщение
        Mockito.verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
        //Проверка, что сообщение для ненулевого количества колод не отправилось
        Mockito.verify(senderMessages, Mockito.never()).sendMessage(chatId, "Ваши колоды:");
    }

    /**
     * тест на непустом списке колод
     */
    @Test
    void testExecutionWithDecks() {
        Long chatId = 987654321L;

        DeckManager deckManager = new DeckManager();
        deckManager.addDeck("first");
        deckManager.addDeck("second");

        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        // попытка выполнить команду
        listDecksCommand.execution(chatId, "/list-decks");

        Mockito.verify(senderMessages).sendMessage(chatId, "Ваши колоды:");
        Mockito.verify(senderMessages).sendMessage(chatId, "first\nsecond\n");
    }
}