package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды изменения термина карты
 */
public class EditCardTermCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     * 3.новый термин
     */
    private final int COUNT_PARAMS = 3;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String deckName = params[0];
        String term = params[1];
        String newTerm = params[2];

        //попытка изменить термин карты
        try {
            Deck userDeck = usersDecks.getDeck(deckName);
            userDeck.updateCardTerm(term, newTerm);
            //сообщение пользователю о выполнении
            return String.format("Термин карты был успешно изменен: %s", userDeck.getCard(newTerm).toString());
        } catch (NoSuchElementException | IllegalArgumentException eNoSuch) {
            return MessageComandError.formatted(eNoSuch.getMessage());
        }
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}