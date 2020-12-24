/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package cells;

import players.Player;
import players.User;

/**
 * Класс, соответствующий "пустой" клетке на игральном поле. Содержит описание
 * действий игрока при попадании на подобную клетку.
 */
public class EmptyCell extends Cell {
    /**
     * Конструктор EmptyCell
     *
     * @param x значение координаты x с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за строки(за высоту, за ось OY)
     * @param y значение координаты y с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за столбцы(за ширину, ось OX)
     */
    public EmptyCell(int x, int y) {
        super(x, y);
    }

    /**
     * Вызывется при попадании игрока на соответствующую клетку и выводит сообщение о бездействии.
     *
     * @param player игрок
     */
    @Override
    public void stepIn(Player player) {
        System.out.printf("\t    This is Empty cell(%d, %d)!\n\n", getXOutput(), getYOutput());
        if (player instanceof User) {
            System.out.println("Just relax there.");
        } else {
            System.out.println("Bot is relaxing...");
        }
    }

    /**
     * Возвращает символ отображения пустой клетки на карте.
     *
     * @return символ отображения клетки на карте
     */
    @Override
    public String toString() {
        return "E";
    }
}
