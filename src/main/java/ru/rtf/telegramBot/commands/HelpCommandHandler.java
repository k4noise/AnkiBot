package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

/**
 * Обработчик команды справки
 */
public class HelpCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    @Override
    public String handle(DeckManager usersDecks, Long id, String[] params) {
        String help = """
                Anki — это телеграм бот для изучения карточек — их термина и определения, которое может использоваться для изучения новых языков, памяти, подготовки к экзаменам и других целях
                
                Операции с колодами:
                    Создание колоды:
                        `/create\\_deck` название
                    Переименование колоды:
                        `/rename\\_deck` название :\\= новое\\_название
                    Удаление колоды:
                        `/delete\\_deck` название
                    Просмотр карточек колоды:
                        `/list\\_cards` название
                Операции с карточками:
                    Создание карточки:
                        `/create\\_card` название\\_колоды : термин \\= определение
                    Редактирование термина:
                        `/edit\\_card\\_term` название\\_колоды : термин :\\= новое\\_название
                    Редактирование определения:
                        `/edit\\_card\\_def` название\\_колоды : термин \\= новое\\_определение
                    Удаление карточки:
                        `/delete\\_card` название\\_колоды : термин
                    Просмотр карточки:
                        `/list\\_card` название\\_колоды : термин
                Обучение:
                    Режим "соответствие":
                        `/check match:` название\\_колоды
                    Режим "ввод термина":
                        `/check typing:` название\\_колоды
                    Режим "карточки":
                        `/check memory:` название\\_колоды
                    Выход из любого режима обучения:
                        /end\\_check
                """;
        return help;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
