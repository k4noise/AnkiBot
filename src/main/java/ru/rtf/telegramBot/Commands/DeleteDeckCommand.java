package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

import java.util.NoSuchElementException;

/**
 * Команда удаления колоды у пользователя
 * <p>/delete_deck название колоды</p>
 */
public class DeleteDeckCommand implements Command {

    /**
     * Количество параметров команды
     * 1.имя колоды
     */
    private final int COUNT_PARAMS = 1;

    @Override
    public String execute(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String deckName = params[0];

        //попытка удалить колоду
        try {
            usersDecks.removeDeck(deckName);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return e.getMessage();
        }
        //сообщение пользователю о выполнении
        return String.format("Колода %s была успешно удалена", deckName);
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}