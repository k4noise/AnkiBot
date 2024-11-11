package ru.rtf.telegramBot.learning;

/**
 * Режимы обучения
 */
public enum LearningMode {
    /**
     * Режим ввода термина по определению
     */
    TYPING,
    /**
     * Режим оценки запоминания карточек по термину
     */
    MEMORY,
    /**
     * Режим сопоставления термина определению
     */
    MATCH
}
