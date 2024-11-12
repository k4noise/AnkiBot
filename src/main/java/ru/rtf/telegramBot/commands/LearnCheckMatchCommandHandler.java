package ru.rtf.telegramBot.commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;
import ru.rtf.telegramBot.learning.SessionManager;
import ru.rtf.telegramBot.learning.mode.MatchLearning;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Обработчик команды режима обучения "соответствие"
 */
public class LearnCheckMatchCommandHandler implements CommandHandler {
    /**
     * Количество параметров команды
     * 1.имя колоды
     */
    private final int COUNT_PARAMS = 1;
    /**
     * Менеджер сессий пользователе
     */
    private final SessionManager sessionManager;

    public LearnCheckMatchCommandHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {
        try {
            String deckName = params[0];
            Collection<Card> cardsToLearn = usersDecks.getDeck(deckName).getCards();
            return sessionManager.start(chatId, new MatchLearning(cardsToLearn));

        } catch (IllegalArgumentException | NoSuchElementException exception) {
            return MESSAGE_COMMAND_ERROR.formatted(exception.getMessage());
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
