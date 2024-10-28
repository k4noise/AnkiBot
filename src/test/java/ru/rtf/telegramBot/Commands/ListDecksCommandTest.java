package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;


import static org.mockito.Mockito.*;

public class ListDecksCommandTest {
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private ListDecksCommand listDecksCommand;

    @BeforeEach
    void setUp() {
        senderMessages = mock(SenderMessages.class);
        userDecksData = mock(UserDecksData.class);
        listDecksCommand = new ListDecksCommand(senderMessages, userDecksData);
    }

    /**
     * тест на неинициализированном списке колод
     */
    @Test
    void testExecutionWithNoDecksNull() {
        Long chatId = 987654321L;

        // Настраиваем userDecksData, чтобы возвращал null для данного chatId (нет колод)
        when(userDecksData.getUserDecks(chatId)).thenReturn(null);

        // попытка выполнить команду
        listDecksCommand.execution(chatId, "/list-decks");

        // Проверка, что отправляется корректное сообщение
        verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
        //Проверка, что сообщение для ненулевого количества колод не отправилось
        verify(senderMessages, never()).sendMessage(chatId, "Ваши колоды:");
    }

    /**
     * тест на пустом списке колод
     */
    @Test
    void testExecutionWithNoDecksEmpty() {
        Long chatId = 987654321L;

        when(userDecksData.getUserDecks(chatId)).thenReturn(new DeckManager());

        // попытка выполнить команду
        listDecksCommand.execution(chatId, "/list-decks");

        // Проверка, что отправляется корректное сообщение
        verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
        //Проверка, что сообщение для ненулевого количества колод не отправилось
        verify(senderMessages, never()).sendMessage(chatId, "Ваши колоды:");
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

        when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        // попытка выполнить команду
        listDecksCommand.execution(chatId, "/list-decks");

        verify(senderMessages).sendMessage(chatId, "Ваши колоды:");
        verify(senderMessages).sendMessage(chatId, "first\nsecond\n");
    }
}
