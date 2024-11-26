package ru.rtf.telegramBot.commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды
 * Вывода карточки из колоды
 * <p>/list_card название колоды : термин</p>
 */
public class ListCardCommandHandler implements CommandHandler {
    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     */
    private static final int COUNT_PARAMS = 2;

    @Override
    public String handle(DeckManager usersDecks, Long id, String[] params) {
        //обработка параметров
        String deckName = params[0];
        String term = params[1];

        //попытка найти карту
        try {
            Card card = usersDecks.getDeck(deckName).getCard(term);
            //сообщение пользователю о выполнении
            return String.format(card.toString());
        } catch (NoSuchElementException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
