package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.*;

/**
 * Режим обучения "соответствие"
 */
public class MatchLearning extends LearningSession {
    /**
     * Количество баллов, которое добавляется карточке при правильном ответе
     */
    protected static final int RIGHT_ANSWER_SCORE_ADDITION = 1;

    /**
     * Все определения карт
     */
    private final List<String> allDefinitions;
    /**
     * Индекс рандомного определения
     */
    private int randomDefinitionIndex;

    /**
     * Экземпляр генератора случайных чисел
     */
    private final Random random;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public MatchLearning(Collection<Card> cards) {
        super(cards);
        allDefinitions = cards.stream()
                .map(Card::getDefinition)
                .toList();
        random = new Random();
    }

    @Override
    public String formQuestion() {
        randomDefinitionIndex = generateNextRandomDefinitionIndex();

        return """
                Утверждение:
                %s - %s
                1 - верно, 0 - неверно""".formatted(getActiveCard().getTerm(), allDefinitions.get(randomDefinitionIndex));
    }

    @Override
    public AnswerStatus checkAnswer(String answer) {
        Card currentCard = getActiveCard();
        boolean isCorrectDefinition = Objects.equals(currentCard.getDefinition(), allDefinitions.get(randomDefinitionIndex));

        boolean isRightUserAnswer;
        switch (answer) {
            case "0":
                isRightUserAnswer = !isCorrectDefinition;
                break;
            case "1":
                isRightUserAnswer = isCorrectDefinition;
                break;
            default:
                return AnswerStatus.WRONG;
        }

        return isRightUserAnswer ? AnswerStatus.RIGHT : AnswerStatus.WRONG;
    }

    @Override
    public String getDescription() {
        return """
                в режиме соответствия
                Показывается термин и определение, ваша задача - определить, соответствует ли термин определению""";
    }

    @Override
    public int getRightAnswerScoreAddition() {
        return RIGHT_ANSWER_SCORE_ADDITION;
    }

    /**
     * Генерирует случайный индекс для определения из списка всех определений
     */
    private int generateNextRandomDefinitionIndex() {
        return random.nextInt(allDefinitions.size());
    }
}