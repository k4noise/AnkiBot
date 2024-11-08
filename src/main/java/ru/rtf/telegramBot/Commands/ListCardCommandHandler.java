package ru.rtf.telegramBot.Commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды
 * Вывода карточки из колоды
 */
public class ListCardCommandHandler implements CommandHandler {
    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     */
    private final int COUNT_PARAMS = 2;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String deckName = params[0];
        String term = params[1];

        //попытка найти карту
        try {
            Card card = usersDecks.getDeck(deckName).getCard(term);
            //сообщение пользователю о выполнении
            return String.format(card.toString());
        } catch (NoSuchElementException e) {
            return MessageComandError.formatted(e.getMessage());
        }
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}