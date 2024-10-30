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

            String deckCards = deckListCardToString(userDeckManager.getDeck(deckName));
            senderMessages.sendMessage(chatId, deckCards);

        } catch (IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
        }
    }

    /**
     * Возвращает строковое представление карточек колоды
     * Формат вывода
     *     Deck:
     *       term - def
     *       term - def
     *
     * @param deck колода
     * @return строка с карточками колоды
     */
    private String deckListCardToString(Deck deck) {
        return deck.getName() + ":\n" + deck.getCardsDescription();
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
