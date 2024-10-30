package ru.rtf.telegramBot.Commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.NoSuchElementException;

/**
 * Добавление новой карты в конкретную колоду пользователя
 * /create-card название колоды: термин - определение
 */
public class CreateCardCommand implements Command {
    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;

    /**
     * Количество параметров команды
     * 1.имя колоды
     * 2.термин
     * 3.определение
     */
    private final int COUNT_PARAMS = 3;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public CreateCardCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    @Override
    public void execution(Long chatId, String[] params) {

        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        String deckName = params[0];
        Card newCard = new Card(params[1], params[2]);

        //попытка добавить карту в колоду
        try {
            userDeckManager.getDeck(deckName).addCard(newCard);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        senderMessages.sendMessage(chatId, String.format("Карта с термином %s была успешно добавлена в колоду %s", newCard.getTerm(), deckName));
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
