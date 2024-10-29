package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.ParserMessageComand;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * класс команды добавления новой колоды
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

    /**
     * Количество параметров команды
     * 1.имя новой колоды
     */
    private final int COUNT_PARAMS = 1;

    public CreateDeckCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    /**
     * выполнить команду
     *
     * @param chatId идентификатор чата
     * @param params параметры команды без ее имени
     */
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

    /**
     * возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
