package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * класс команды /create-deck (название колоды)
 */
public class CreateDeckCommand implements Command {

    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;

    public CreateDeckCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    /**
     * Добавляет новую колоду
     *
     * @param chatId в каком чате выполнить
     * @param text   текст вызова команды
     */
    @Override
    public void execution(Long chatId, String text) {
        //есть ли у пользователя список колод?
        if (!userDecksData.containsUser(chatId))
            userDecksData.addUser(chatId);
        //выделить имя новой колоды из сообщения
        String nameDeck = ReturnNameDeck(chatId, text);
        if (nameDeck != null) {
            //добавить новую колоду пользователю
            DeckManager deckManager = userDecksData.getUserDecks(chatId);
            try {
                deckManager.addDeck(nameDeck);
            } catch (IllegalArgumentException e) {
                senderMessages.sendMessage(chatId, e.getMessage());
            }
        }
    }

    /**
     * выделяет имя новой колоды из сообщения пользователя
     * выводит пользователю ошибку при неудаче
     *
     * @return имя новой колоды
     */
    private String ReturnNameDeck(Long chatId, String message) {
        if (message.split(" ").length < 2) {
            senderMessages.sendMessage(chatId, "Команда отменена. Нужно ввести имя колоды.");
            return null;
        }
        return message.split(" ")[1];
    }
}
