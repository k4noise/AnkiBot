package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.NoSuchElementException;

/**
 * Класс команды удаления колоды у пользователя /delete-deck название колоды
 */
public class DeleteDeckCommand implements Command {

    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;
    /**
     * Количество аргументов в сообщении пользователя
     * 1. сама команда (/delete-deck)
     * 2. название колоды
     */
    private final int COUNT_ARGS = 2;

    public DeleteDeckCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    /**
     * выполнить команду
     *
     * @param chatId в каком чате выполнить
     * @param text   текст вызова команды
     */
    @Override
    public void execution(Long chatId, String text) {
        //есть ли у пользователя список колод?
        if (heckingForEmptiness(chatId)) {
            //выделить имя колоды из сообщения
            String nameDeck = ReturnNameDeck(chatId, text);
            //удалить колоду
            DeckManager deckManager = userDecksData.getUserDecks(chatId);
            try {
                deckManager.removeDeck(nameDeck);
            } catch (NoSuchElementException e) {
                senderMessages.sendMessage(chatId, e.getMessage());
                return;
            }
            senderMessages.sendMessage(chatId, "Колода " + nameDeck + " удалена");
        }
    }

    /**
     * выделяет имя колоды из сообщения пользователя
     * выводит пользователю ошибку при неудаче
     *
     * @return имя новой колоды (аргумент следующий за именем команды)
     */
    private String ReturnNameDeck(Long chatId, String message) {
        if (message.split(" ").length < COUNT_ARGS) {
            senderMessages.sendMessage(chatId, "Команда отменена. Нужно ввести имя колоды.");
            return null;
        }
        return message.split(" ")[1];
    }

    /**
     * Проверяет, есть ли у пользователя колоды
     *
     * @return логический ответ
     */
    private boolean heckingForEmptiness(Long chatId) {
        DeckManager deckManager = userDecksData.getUserDecks(chatId);
        if (deckManager == null || deckManager.getDecks().isEmpty()) {
            senderMessages.sendMessage(chatId,
                    "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
            return false;
        }
        return true;
    }
}