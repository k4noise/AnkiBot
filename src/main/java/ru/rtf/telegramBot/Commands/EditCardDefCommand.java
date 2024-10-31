package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

import java.util.NoSuchElementException;

/**
 * Команда изменения определения карточки
 * <p>/edit_card_def название колоды:термин = новое определение</p>
 */
public class EditCardDefCommand implements Command {

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
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
