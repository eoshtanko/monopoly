/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package cells;

import players.Player;
import players.User;
import tools.Values;

/**
 * Класс, соответствующий клетке такси на игральном поле. Содержит описание
 * действий игрока при попадании на подобную клетку.
 */
public class Taxi extends Cell {
    /**
     * Минимальное допустимое число клеток, на которое перемещает такси
     */
    private static final int MIN_TAXI_DISTANCE = 3;

    /**
     * Максимальное допустимое число клеток, на которое перемещает такси
     */
    private static final int MAX_TAXI_DISTANCE = 5;

    /**
     * Конструктор Taxi
     *
     * @param x значение координаты x с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за строки(за высоту, за ось OY)
     * @param y значение координаты y с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за столбцы(за ширину, ось OX)
     */
    public Taxi(int x, int y) {
        super(x, y);
    }

    /**
     * При попадании игрока на клетку реализует задачу такси, передвигает игрока на рандомное число(от 3 до 5)
     * клеток вперед.
     *
     * @param player игрок
     */
    @Override
    public void stepIn(Player player) {
        System.out.printf("\t  This is Taxi cell(%d, %d)!\n\n", getXOutput(), getYOutput());
        int taxiDistance = Values.genInt(MIN_TAXI_DISTANCE, MAX_TAXI_DISTANCE);
        if (player instanceof User) {
            System.out.printf("You are shifted forward by %d cells.\n\n", taxiDistance);
        } else {
            System.out.printf("Bot is shifted forward by %d cells.\n\n", taxiDistance);
        }
        player.moveForward(taxiDistance);
    }

    /**
     * Возвращает символ отображения такси на карте.
     *
     * @return символ отображения клетки на карте
     */
    @Override
    public String toString() {
        return "T";
    }
}
