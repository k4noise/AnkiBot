package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

/**
 * Обработчик команды добавления новой колоды
 */
public class CreateDeckCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя новой колоды
     */
    private final int COUNT_PARAMS = 1;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        String deckName = params[0];

        //попытка добавить колоду
        try {
            usersDecks.addDeck(deckName);
        } catch (IllegalArgumentException e) {
            return handleDeckError(deckName, true);
        }
        //сообщение пользователю о выполнении
        return "Колода " + deckName + " успешно добавлена";
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
