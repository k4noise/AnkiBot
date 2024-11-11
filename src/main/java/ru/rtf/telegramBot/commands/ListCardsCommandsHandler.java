package ru.rtf.telegramBot.commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды
 * Вывода списка всех карт колоды
 */
public class ListCardsCommandsHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     */
    public final int COUNT_PARAMS = 1;

    @Override
    public String handle(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String deckName = params[0];

        Deck deck;
        //попытка найти колоду
        try {
            deck = usersDecks.getDeck(deckName);
        } catch (NoSuchElementException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }
        String cardsDescription = deck.getCardsDescription();
        if (cardsDescription.isEmpty())
            return deckName + ":\n" + "В этой колоде пока нет карточек";
        return String.format("%s:\n%s", deckName, cardsDescription);
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}