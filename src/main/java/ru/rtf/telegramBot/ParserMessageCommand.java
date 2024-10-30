package ru.rtf.telegramBot;

import java.util.Arrays;

/**
 * Обработка сообщения пользователя
 */
public class ParserMessageCommand {
    /**
     * Хранит в себе составляющие сообщения
     */
    private final String[] messageParts;

    /**
     * Создать экземпляр обработчика сообщения
     *
     * @param text Сообщение пользователя
     */
    public ParserMessageCommand(String text) {
        messageParts = parseUsersMessage(text);
    }

    /**
     * Вернуть имя команды
     * всегда должно стоять первым в сообщении
     *
     * @return имя команды
     */
    public String nameCommand() {
        if (messageParts == null || messageParts.length == 0)
            return null;
        return messageParts[0];
    }

    /**
     * Возвращает массив параметров для команды без ее названия
     *
     * @return массив параметров для команды
     */
    public String[] paramsCommand() {
        if (messageParts == null || messageParts.length <= 1)
            return null;
        return Arrays.copyOfRange(messageParts, 1, messageParts.length);
    }

    /**
     * Парсит сообщение пользователя на "слова"
     *
     * @param text сообщение пользователя
     * @return массив слов
     */
    public String[] parseUsersMessage(String text) {
        if (text == null || text.isEmpty() || text.trim().isEmpty())
            return null;

        //разделение по первому пробелу
        String[] commandWithParams = text.trim().split(" ", 2);
        if (commandWithParams.length > 1) {
            String[] partsParams = Arrays.stream(commandWithParams[1].split("[:=]"))
                    .map(String::trim)
                    .filter(item -> !item.isEmpty())
                    .toArray(String[]::new);
            //объединение команды и разделенных параметров
            String[] parts = new String[1 + partsParams.length];
            System.arraycopy(commandWithParams, 0, parts, 0, 1);
            System.arraycopy(partsParams, 0, parts, 1, partsParams.length);
            return parts;
        }
        return commandWithParams;
    }
}
