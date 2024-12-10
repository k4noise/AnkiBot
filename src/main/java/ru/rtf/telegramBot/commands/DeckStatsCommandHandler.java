package ru.rtf.telegramBot.commands;

import ru.rtf.telegramBot.learning.CardLearningStatus;
import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;
import ru.rtf.telegramBot.StatsCalculator;

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
    /**
     * Калькулятор статистики
     */
    private final StatsCalculator statsCalculator = new StatsCalculator();

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {
        String deckName = params[0];
        Deck deck;
        try {
            deck = usersDecks.getDeck(deckName);
        } catch (NoSuchElementException e) {
            return MESSAGE_COMMAND_ERROR.formatted(e.getMessage());
        }

        EnumMap<CardLearningStatus, Integer> cardsStatusCount = statsCalculator.getCardsCountByStatus(deck);
        String cardsStatusString =
                """
                        Полностью изучено:  %d
                        Частично изучено:   %d
                        Не изучено: %d
                        """.formatted(
                        cardsStatusCount.getOrDefault(CardLearningStatus.STUDIED, 0),
                        cardsStatusCount.getOrDefault(CardLearningStatus.PARTIALLY_STUDIED, 0),
                        cardsStatusCount.getOrDefault(CardLearningStatus.NOT_STUDIED, 0));
        return deck.getDescription() + "\n" + cardsStatusString;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
