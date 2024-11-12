package ru.rtf.telegramBot.commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.Collection;

/**
 * Обработчик команды просмотра всех колод пользователя
 */
public class ListDecksCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * нет параметров
     */
    public static final int COUNT_PARAMS = 0;

    @Override
    public String handle(DeckManager usersDecks, String[] params) {

        //сообщение пользователю о выполнении
        if (usersDecks.getDecks().isEmpty())
            return "У Вас пока нет ни одной колоды, создайте первую /create_deck <название>";
        return "Ваши колоды:" + collectionDeckToSting(usersDecks.getDecks());
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }

    /**
     * Выводит колоды в виде их строковых представлений
     *
     * @param decks колоды
     * @return строка из строкового представления колод
     */
    private String collectionDeckToSting(Collection<Deck> decks) {
        StringBuilder decksToSting = new StringBuilder();
        for (Deck deck : decks) {
            decksToSting.append("\n");
            decksToSting.append(deck);
        }
        return decksToSting.toString();
    }
}
