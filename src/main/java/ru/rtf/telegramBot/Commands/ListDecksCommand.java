package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

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

    @Override
    public String execute(DeckManager usersDecks, String[] params) {

        //сообщение пользователю о выполнении
        if (usersDecks.getDecks().isEmpty())
            return "У Вас пока нет ни одной колоды, создайте первую /create_deck <название>";
        return "Ваши колоды:\n" + collectionDeckToSting(usersDecks.getDecks());
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
            decksToSting.append(deck);
            decksToSting.append("\n");
        }
        return decksToSting.toString();
    }
}
