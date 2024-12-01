package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;


/**
 * Режим обучения "Ввод термина"
 */
public class TypingLearning extends LearningSession {

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public TypingLearning(Collection<Card> cards) {
        super(cards);
    }

    @Override
    public String formQuestion() {
        String questionText = """
                Определение - "%s"
                Введите соответствующий термин:""";
        Card currentCard = allCards.peek();
        return questionText.formatted(currentCard.getDefinition());
    }

    @Override
    public boolean checkAnswer(String answer) {
        Card currentCard = allCards.peek();
        return answer.equalsIgnoreCase(currentCard.getTerm());
    }

    @Override
    public String getDescription() {
        return """
                в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин (регистр не учитывается)""";
    }
}
