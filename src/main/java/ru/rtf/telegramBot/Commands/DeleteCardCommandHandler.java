package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды удаления карты из колоды
 */
public class DeleteCardCommandHandler implements CommandHandler {

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
