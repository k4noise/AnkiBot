package ru.rtf.telegramBot.commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.NoSuchElementException;

/**
 * Обработчик команды
 * Добавление новой карты в конкретную колоду пользователя
 */
public class CreateCardCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     * 3.определение
     */
    private static final int COUNT_PARAMS = 3;

    @Override
    public String handle(DeckManager usersDecks, String[] params) {
        String deckName = params[0];
        String term = params[1];

        Card newCard;
        try{
            newCard = new Card(term, params[2]);
        }catch (IllegalArgumentException e){
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }

        //попытка добавить карту в колоду
        try {
            usersDecks.getDeck(deckName).addCard(newCard);

        } catch (NoSuchElementException | IllegalArgumentException eNoSuch) {
            return MESSAGE_COMMAND_ERROR.formatted(eNoSuch.getMessage());
        }
        //сообщение пользователю о выполнении
        return "Карта с термином %s была успешно добавлена в колоду %s"
                .formatted(newCard.getTerm(), deckName);
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
