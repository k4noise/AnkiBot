package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.ParserMessageComand;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * команда изменения определения карточки
 */
public class EditCardDefCommand implements Command {
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
     * 1.имя колоды
     * 2.термин
     * 3.новое определение
     */
    private final int COUNT_PARAMS = 3;

    public EditCardDefCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
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

        //обработка параметров
        String deckName = params[0];
        String term = params[1];
        String newDef = params[2];

        //попытка изменить определение карты
        try {
            //TODO реализация
        } catch (IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        //TODO вместо термина показывать всю карточку
        senderMessages.sendMessage(chatId, deckName + ": " + term);
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