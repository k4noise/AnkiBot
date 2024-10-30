package ru.rtf.telegramBot.Commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Добавление новой карты в конкретную колоду пользователя
 * /create-card название колоды: термин - определение
 */
public class CreateCardCommand implements Command {
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
     * 3.определение
     */
    private final int COUNT_PARAMS = 3;

    public CreateCardCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
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
        Card newCard = new Card(params[1], params[2]);

        //попытка добавить карту в колоду
        try {
            //TODO реализация
        } catch (IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        senderMessages.sendMessage(chatId, deckName + ": + " + newCard);
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
