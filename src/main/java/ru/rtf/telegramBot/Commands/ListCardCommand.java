package ru.rtf.telegramBot.Commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

import java.util.NoSuchElementException;

/**
 * Команда просмотра карточки из колоды
 * <p>/list_card название колоды : термин</p>
 */
public class ListCardCommand implements Command {
    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     */
    private final int COUNT_PARAMS = 2;

    @Override
    public String execute(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String deckName = params[0];
        String term = params[1];

        //попытка найти карту
        try {
            Card card = usersDecks.getDeck(deckName).getCard(term);
            //сообщение пользователю о выполнении
            return String.format(card.toString());
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
