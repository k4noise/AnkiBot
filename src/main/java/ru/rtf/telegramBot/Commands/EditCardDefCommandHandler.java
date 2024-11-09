package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды изменения определения карточки
 * <p>/edit_card_def название колоды:термин = новое определение</p>
 */
public class EditCardDefCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     * 3.новое определение
     */
    private final int COUNT_PARAMS = 3;

    @Override
    public String execute(DeckManager usersDecks, String[] params) {
        //обработка параметров
        String deckName = params[0];
        String term = params[1];
        String newDef = params[2];

        //попытка изменить определение карты
        try {
            Deck userDeck = usersDecks.getDeck(deckName);
            userDeck.updateCardDefinition(term, newDef);
            //сообщение пользователю о выполнении
            return String.format("Определение карты было успешно изменено: %s", userDeck.getCard(term).toString());
        } catch (NoSuchElementException e) {
            // не существует колода или карта
            return MessageComandError.formatted(e.getMessage());
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
