package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Команда удаления карты из колоды
 */
public class DeleteCardCommand implements Command {

    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     */
    private final int COUNT_PARAMS = 2;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public DeleteCardCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        String deckName = params[0];
        String term = params[1];

        //попытка удалить карту из колоды
        try {
            //TODO реализация
        } catch (IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        //TODO вместо термина показывать всю карточку
        senderMessages.sendMessage(chatId, deckName + ": - \"" + term + "\"");
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
