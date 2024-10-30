package ru.rtf.telegramBot.Commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

import java.util.NoSuchElementException;

/**
 * Добавление новой карты в конкретную колоду пользователя
 * /create-card название колоды: термин - определение
 */
public class CreateCardCommand implements Command {

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     * 3.определение
     */
    private final int COUNT_PARAMS = 3;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        String deckName = params[0];
        Card newCard = new Card(params[1], params[2]);

        //попытка добавить карту в колоду
        try {
            usersDecks.getDeck(deckName).addCard(newCard);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return e.getMessage();
        }
        //сообщение пользователю о выполнении
        return String.format("Карта с термином %s была успешно добавлена в колоду %s", newCard.getTerm(), deckName);
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
