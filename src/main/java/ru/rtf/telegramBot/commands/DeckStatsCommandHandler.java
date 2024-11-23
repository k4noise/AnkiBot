package ru.rtf.telegramBot.commands;

import ru.rtf.Card;
import ru.rtf.CardLearningStatus;
import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

import java.util.EnumMap;
import java.util.NoSuchElementException;

/**
 * Обработчик команды получения статистики колоды
 * <p>/deck_stats название колоды</p>
 */
public class DeckStatsCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * 1.имя колоды
     */
    private final static int COUNT_PARAMS = 1;

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {
        String deckName = params[0];
        Deck deck;
        try {
            deck = usersDecks.getDeck(deckName);
        } catch (NoSuchElementException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }

        EnumMap<CardLearningStatus, Integer> cardsStatusCount = new EnumMap<>(CardLearningStatus.class);
        for (CardLearningStatus status : CardLearningStatus.values()) {
            cardsStatusCount.put(status, 0);
        }
        for (Card card : deck.getCards()) {
            cardsStatusCount.merge(card.getStatus(), 1, Integer::sum);
        }
        String cardsStatusString =
                """
                        Полностью изучено:  %d
                        Частично изучено:   %d
                        Не изучено: %d
                        """.formatted(
                        cardsStatusCount.get(CardLearningStatus.STUDIED),
                        cardsStatusCount.get(CardLearningStatus.PARTIALLY_STUDIED),
                        cardsStatusCount.get(CardLearningStatus.NOT_STUDIED));
        return deck + "\n" + cardsStatusString;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
