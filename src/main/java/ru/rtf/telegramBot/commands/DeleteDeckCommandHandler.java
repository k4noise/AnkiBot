package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды удаления колоды у пользователя
 * <p>/delete_deck название колоды</p>
 */
public class DeleteDeckCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     */
    private static final int COUNT_PARAMS = 1;

    @Override
    public String handle(DeckManager usersDecks, Long id, String[] params) {
        //обработка параметров
        String deckName = params[0];

        //попытка удалить колоду
        try {
            usersDecks.removeDeck(deckName);
        } catch (NoSuchElementException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }
        //сообщение пользователю о выполнении
        return String.format("Колода %s была успешно удалена", deckName);
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}