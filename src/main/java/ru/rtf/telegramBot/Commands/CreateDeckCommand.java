package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Класс команды добавления новой колоды
 */
public class CreateDeckCommand implements Command {

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
     * 1.имя новой колоды
     */
    private final int COUNT_PARAMS = 1;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public CreateDeckCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }


    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        String deckName = params[0];

        //попытка добавить колоду
        try {
            //TODO изменить реализацию
            userDeckManager.addDeck(deckName);
        } catch (IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        senderMessages.sendMessage(chatId, "колода " + deckName + " добавлена");
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
