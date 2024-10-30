package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.Collection;

/**
 * Выводит список всех карт колоды
 */
public class ListCardsCommands implements Command {

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
     */
    public final int COUNT_PARAMS = 1;

    public ListCardsCommands(SenderMessages senderMessages, UserDecksData userDecksData) {
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
     * возвращает строковое представление карточек колоды
     * @param deck колода
     * @return строка с карточками колоды
     */
    private String deckListCardToString(Deck deck){
        //TODO
        return null;
    }
//Формат вывода
//    Deck:
//      term - def
//      term - def

    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
