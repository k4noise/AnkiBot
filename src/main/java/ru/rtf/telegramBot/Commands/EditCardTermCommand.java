package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Команда изменения термина карты
 * /edit-card-term название колоды: термин
 * ожидает ответ пользователя
 */
public class EditCardTermCommand implements Command {
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
     * 3.новый термин
     */
    private final int COUNT_PARAMS = 3;

    public EditCardTermCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
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
        String newTerm = params[2];

        //попытка изменить термин карты
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
