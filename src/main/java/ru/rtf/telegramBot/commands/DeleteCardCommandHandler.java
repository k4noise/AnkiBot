package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды удаления карты из колоды
 * <p>/delete_card название колоды : термин</p>
 */
public class DeleteCardCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     */
    private static final int COUNT_PARAMS = 2;

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {
        String deckName = params[0];
        String term = params[1];

        //попытка удалить карту из колоды
        try {
            usersDecks.getDeck(deckName).removeCard(term);
        } catch (NoSuchElementException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }
        //сообщение пользователю о выполнении
        return String.format("Карта с термином \"%s\" была успешно удалена из колоды %s", term, deckName);

    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
