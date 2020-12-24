/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package tools;

import game.Field;
import game.Game;

/**
 * Класс, для считывания и обработки изначально введенных в командную
 * строку данных.
 * Я приняла решение выделить метод считывания в отдельный класс, так как
 * это решение показалось мне корректным с точки зрения архитектуры. Данный
 * метод не соотносятся напрямую с логикой проведения игры.
 * Он - просто интструмент и должн быть отделено.
 */
public class InputInitialData {
    /**
     * Минимальное допустимое значение высоты поля
     */
    private static final double MIN_HIGHT = 6;

    /**
     * Максимальное допустимое значение высоты поля
     */
    private static final double MAX_HIGHT = 30;

    /**
     * Минимальное допустимое значение ширины поля
     */
    private static final double MIN_WIDTH = 6;

    /**
     * Максимальное допустимое значение ширины поля
     */
    private static final double MAX_WIDTH = 30;

    /**
     * Минимальное допустимое значение изначального кол-ва денег
     */
    private static final double MIN_START_BALANCE = 500;

    /**
     * Максимальное допустимое значение изначального кол-ва денег
     */
    private static final double MAX_START_BALANCE = 15000;

    /**
     * Считывает значения командной строки и присваивает их соответствующим членам.
     *
     * @param args аргументы командной строки
     * @throws Exception ArrayIndexOutOfBoundsException
     *                   если необходимые для коррекной работы программы аргументы отсутствуют или их < 3
     *                   NumberFormatException
     *                   если необходимые для коррекной работы программы аргументы имеют нечисловые значения
     *                   IllegalArgumentException
     *                   если введенные аргументы несоотвтствуют коррекным границам
     *                   (верные данные: <height> <width> <money>, где 6 <= height <= 30,
     *                   6 <= height <= 30, 500 <= money <= 15000)
     *                   или если их > 3
     */
    public static void inputData(String[] args) throws Exception {
        if (args.length > 3) {
            throw new IllegalArgumentException("Ошибка при запуске приложения. Корректное число аргументов - 3.");
        }
        Field.hight = Integer.parseInt(args[0]);
        Field.width = Integer.parseInt(args[1]);
        Game.startBalance = Integer.parseInt(args[2]);
        if (Field.hight < MIN_HIGHT || Field.hight > MAX_HIGHT) {
            throw new IllegalArgumentException("Ошибка при запуске приложения. Высота поля должна находиться в границах от 6 до 30.");
        }
        if (Field.width < MIN_WIDTH || Field.width > MAX_WIDTH) {
            throw new IllegalArgumentException("Ошибка при запуске приложения. Ширина поля должна находиться в границах от 6 до 30.");
        }
        if (Game.startBalance < MIN_START_BALANCE || Game.startBalance > MAX_START_BALANCE) {
            throw new IllegalArgumentException("Ошибка при запуске приложения. Стартовый капитал должен находиться в границах от 500 до 15000.");
        }
        Game.bot.setMoney(Game.startBalance);
        Game.user.setMoney(Game.startBalance);
    }
}
