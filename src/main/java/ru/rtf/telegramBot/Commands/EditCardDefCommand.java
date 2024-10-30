package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.NoSuchElementException;

/**
 * Команда изменения определения карточки
 */
public class EditCardDefCommand implements Command {
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
     * 3.новое определение
     */
    private final int COUNT_PARAMS = 3;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public EditCardDefCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        //обработка параметров
        String deckName = params[0];
        String term = params[1];
        String newDef = params[2];

        //попытка изменить определение карты
        try {
            Deck userDeck = userDeckManager.getDeck(deckName);
            userDeck.updateCardDefinition(term, newDef);
            //сообщение пользователю о выполнении
            senderMessages.sendMessage(
                    chatId,
                    String.format("Определение карты было успешно изменено: %s", userDeck.getCard(term).toString())
            );

        } catch (NoSuchElementException | IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
        }
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
