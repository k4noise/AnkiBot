package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.Collection;

/**
 * Класс просмотра всех колод /list-decks
 */
public class ListDecksCommand implements Command {

    /**
     * Количество параметров команды
     * нет параметров
     */
    public final int COUNT_PARAMS = 0;
    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public ListDecksCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);
        Collection<Deck> userDecks = userDeckManager.getDecks();

        //сообщение пользователю о выполнении
        if (userDecks.isEmpty())
            senderMessages.sendMessage(chatId,
                    "У Вас пока нет ни одной колоды, создайте первую /create-deck <название>");
        else {
            String decksText = LineNamesDecks(userDecks);
            senderMessages.sendMessage(chatId, "Ваши колоды:\n" + decksText);
        }
    }


    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }

    /**
     * Создает строку из имен колод
     *
     * @return имена колод через перевод строки
     */
    private String LineNamesDecks(Collection<Deck> decks) {
        StringBuilder names = new StringBuilder();
        for (Deck deck : decks) {
            names.append(deck.getName())
                    .append(": ")
                    .append(deck.getCardsCount())
                    .append(" карт")
                    .append("\n");
        }
        return names.toString();
    }

}
