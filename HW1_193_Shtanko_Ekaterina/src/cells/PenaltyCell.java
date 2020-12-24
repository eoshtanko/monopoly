/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package cells;

import players.Player;
import players.User;
import tools.Values;

/**
 * Класс, соответствующий "штрафной" клетке на игральном поле. Содержит описание
 * действий игрока при попадании на подобную клетку.
 */
public class PenaltyCell extends Cell {
    /**
     * Минимальное допустимое значение penaltyCoeff
     */
    private static final double MIN_PENALTY_COEFF = 0.01;

    /**
     * Максимальное допустимое значение penaltyCoeff
     */
    private static final double MAX_PENALTY_COEFF = 0.1;

    /**
     * Коэффициент для расчета штрафа.
     * Я приняла решение сразу округлять его до трех знаков после запятой.
     * Подробно обосновала я это решение в комментарии к методу Values.round.
     */
    private static final double penaltyCoeff = Values.round(Values.genDouble(MIN_PENALTY_COEFF, MAX_PENALTY_COEFF));

    /**
     * Конструктор PenaltyCell
     *
     * @param x значение координаты x с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за строки(за высоту, за ось OY)
     * @param y значение координаты y с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за столбцы(за ширину, ось OX)
     */
    public PenaltyCell(int x, int y) {
        super(x, y);
    }

    /**
     * Возврщает информацию о штрафном коэффициенте.
     *
     * @return коэффициент штрафа
     */
    public static double getPenaltyCoeff() {
        return penaltyCoeff;
    }

    /**
     * Вызывется при попадании игрока на соответствующую клетку и списывает со счета штраф.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    @Override
    public void stepIn(Player player) throws Exception {
        System.out.printf("\tThis is Penalty cell(%d, %d)!\n\n", getXOutput(), getYOutput());
        // Из-за округления при очень малых значения money, penalty может принимать значение 0.
        // В slack такое поведение сказали не счить ошибкой.
        int penalty = (int) Math.round(player.getMoney() * penaltyCoeff);
        if (player instanceof User) {
            System.out.printf("You lost %d$.\n", penalty);
        } else {
            System.out.printf("Bot lost %d$.\n", penalty);
        }
        player.setMoney(player.getMoney() - penalty);
    }

    /**
     * Возвращает символ отображения штрафной клетки на карте.
     *
     * @return символ отображения клетки на карте
     */
    @Override
    public String toString() {
        return "%";
    }
}
