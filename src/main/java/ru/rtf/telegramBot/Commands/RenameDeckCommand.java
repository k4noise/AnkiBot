package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

import java.util.NoSuchElementException;

/**
 * Команда переименования колоды
 * <p>/rename-deck старое название := новое название</p>
 */
public class RenameDeckCommand implements Command {

    /**
     * Количество параметров команды
     * 1.старое имя колоды
     * 2.новое имя колоды
     */
    private final int COUNT_PARAMS = 2;

    @Override
    public String execute(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String oldDeckName = params[0];
        String newDeckName = params[1];

        //попытка изменить имя колоды
        try {
            usersDecks.updateDeckName(oldDeckName, newDeckName);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return e.getMessage();
        }
        //сообщение пользователю о выполнении
        return "Колода успешно переименована: " + oldDeckName + " -> " + newDeckName;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
