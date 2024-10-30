package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Выводит список всех карт колоды
 */
public class ListCardsCommands implements Command {

    /**
     * Количество параметров команды
     * 1.имя колоды
     */
    public final int COUNT_PARAMS = 1;
    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public ListCardsCommands(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        //обработка параметров
        String deckName = params[0];

        //попытка найти колоду
        try {
            //TODO реализация
        } catch (IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        //TODO
        String deckCards = deckListCardToString(new Deck(""));//исправить
        senderMessages.sendMessage(chatId, deckName + ":\n" + deckCards);
    }

    /**
     * Возвращает строковое представление карточек колоды
     *
     * @param deck колода
     * @return строка с карточками колоды
     */
    private String deckListCardToString(Deck deck) {
        //TODO
        return null;
    }
//Формат вывода
//    Deck:
//      term - def
//      term - def

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
