package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды переименования колоды
 */
public class RenameDeckCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.старое имя колоды
     * 2.новое имя колоды
     */
    private final int COUNT_PARAMS = 2;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String oldDeckName = params[0];
        String newDeckName = params[1];

        //попытка изменить имя колоды
        try {
            usersDecks.updateDeckName(oldDeckName, newDeckName);
        } catch (NoSuchElementException eNoSuch) {
            return handleDeckError(oldDeckName, false);
        } catch (IllegalArgumentException eIllegalArg) {
            return handleDeckError(newDeckName, true);
        }
        //сообщение пользователю о выполнении
        return "Колода успешно переименована: " + oldDeckName + " -> " + newDeckName;
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
