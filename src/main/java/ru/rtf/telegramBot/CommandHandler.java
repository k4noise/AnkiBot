package ru.rtf.telegramBot;

import ru.rtf.DeckManager;

/**
 * Интерфейс для обработчиков команд бота
 */
public interface CommandHandler {

    /**
     * Выполняет команду.
     *
     * @param usersDecks - колоды пользователя
     * @param params     - параметры необходимые команде
     * @return сообщение об успешном завершении или об ошибке
     */
    String execution(DeckManager usersDecks, String[] params);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    int getCountParams();

    /**
     * Формирует сообщение об ошибке для колоды
     *
     * @param deckName        имя колоды
     * @param existsInManager true, если ошибка вызвана дублированием;
     *                        false, если колода не найдена
     * @return сообщение об ошибке
     */
    default String handleDeckError(String deckName, boolean existsInManager) {
        return existsInManager
                ? "Колода с именем " + deckName + " уже существует в менеджере"
                : "Колода с именем " + deckName + " не существует в менеджере";
    }

    /**
     * Формирует сообщение об ошибке для карты
     *
     * @param term         термин карты
     * @param deckName     имя колоды, в которой проверяется карта
     * @param existsInDeck true, если ошибка вызвана дублированием;
     *                     false, если карта не найдена
     * @return
     */
    default String handleCardError(String term, String deckName, boolean existsInDeck) {
        return existsInDeck
                ? "Карта с термином %s уже существует в колоде %s".formatted(term, deckName)
                : "Карта с термином %s не существует в колоде %s".formatted(term, deckName);
    }
}
