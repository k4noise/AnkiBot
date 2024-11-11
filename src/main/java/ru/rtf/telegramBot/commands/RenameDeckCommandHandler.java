package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды переименования колоды
 * <p>/rename_deck старое название := новое название</p>
 */
public class RenameDeckCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.старое имя колоды
     * 2.новое имя колоды
     */
    private final int COUNT_PARAMS = 2;

    @Override
    public String handle(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String oldDeckName = params[0];
        String newDeckName = params[1];

        //попытка изменить имя колоды
        try {
            usersDecks.updateDeckName(oldDeckName, newDeckName);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }
        //сообщение пользователю о выполнении
        return "Колода успешно переименована: " + oldDeckName + " -> " + newDeckName;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
