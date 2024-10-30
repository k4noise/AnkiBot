package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

import java.util.NoSuchElementException;

/**
 * Команда удаления карты из колоды
 */
public class DeleteCardCommand implements Command {

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     */
    private final int COUNT_PARAMS = 2;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        String deckName = params[0];
        String term = params[1];

        //попытка удалить карту из колоды
        try {
            usersDecks.getDeck(deckName).removeCard(term);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return e.getMessage();
        }
        //сообщение пользователю о выполнении
        return String.format("Карта с термином \"%s\" была успешно удалена из колоды %s", term, deckName);

    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
