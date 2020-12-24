/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package cells;

import players.Player;
import players.User;
import tools.Values;
import tools.MessageTools;

/**
 * Класс, соответствующий клетке офиса банка на игральном поле. Содержит описание
 * действий игрока при попадании на подобную клетку.
 */
public class BankOffice extends Cell {
    /**
     * Минимальное допустимое значение debtCoeff
     */
    private static final double MIN_DEBT_COEFF = 1;

    /**
     * Максимальное допустимое значение debtCoeff
     */
    private static final double MAX_DEBT_COEFF = 3;

    /**
     * Минимальное допустимое значение  creditCoeff
     */
    private static final double MIN_CREDIT_COEFF = 0.002;

    /**
     * Максимальное допустимое значение creditCoeff
     */
    private static final double MAX_CREDIT_COEFF = 0.2;

    /**
     * Коэффициент для расчета максимального кредита.
     * Я приняла решение сразу округлять его до трех знаков после запятой.
     * Подробно обосновала я это решение в комментарии к методу Values.round.
     */
    private static final double creditCoeff = Values.round(Values.genDouble(MIN_CREDIT_COEFF, MAX_CREDIT_COEFF));

    /**
     * Коэффициент на который увеличивается сумма, которую игрок должен банку
     * в сравнению с той, что он взял.
     * Я приняла решение сразу округлять его до трех знаков после запятой.
     * Подробно обосновала я это решение в комментарии к методу Values.round.
     */
    private static final double debtCoeff = Values.round(Values.genDouble(MIN_DEBT_COEFF, MAX_DEBT_COEFF));

    /**
     * Деньги, потраченные игроком на покупку и улчшение магазинов в течении игры
     */
    private static int userExpences;

    /**
     * Долг игрока(пользователя) перпед банком
     */
    private static int userDebt;

    /**
     * Конструктор BankOffice
     *
     * @param x значение координаты x с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за строки(за высоту, за ось OY)
     * @param y значение координаты y с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за столбцы(за ширину, ось OX)
     */
    public BankOffice(int x, int y) {
        super(x, y);
    }

    /**
     * Предоставляет информацию о коэффициенте для расчета максимального кредита.
     *
     * @return коэффициент для расчета максимального кредита
     */
    public static double getCreditCoeff() {
        return creditCoeff;
    }

    /**
     * Предоставляет информацию о коэффициенте, на который увеличивается сумма, которую игрок должен банку
     * в сравнению с той, что он взял.
     *
     * @return коэффициент, на который увеличивается сумма, которую игрок должен банку
     * в сравнению с той, что он взял
     */
    public static double getDebtCoeff() {
        return debtCoeff;
    }

    /**
     * Предоставляет информацию о деньгах, потраченных игроком на покупку и улчшение магазинов в течении игры
     *
     * @return деньги, потраченные игроком на покупку и улчшение магазинов в течении игры
     */
    public static int getUserExpences() {
        return userExpences;
    }

    /**
     * Устанавливает значение денег, потраченных игроком на покупку и улчшение магазинов в течении игры
     *
     * @param value новое значение денег, потраченныех игроком на покупку и улчшение магазинов в течении игры
     */
    public static void setUserExpences(int value) {
        userExpences = value;
    }

    /**
     * Предоставляет информацию о долге игрока(пользователя) перпед банком.
     *
     * @return долг игрока(пользователя) перпед банком
     */
    public static int getUserDebt() {
        return userDebt;
    }

    /**
     * Вызывется при попадании игрока на клетку офиса банка и вызывает соответствующий типу игрока матод.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    @Override
    public void stepIn(Player player) throws Exception {
        System.out.printf("\tThis is Bank office cell(%d, %d)!\n\n", getXOutput(), getYOutput());
        if (player instanceof User) {
            stepInOfficeUser(player);
        } else {
            System.out.println("Bot cannot use Bank's services.");
        }
    }

    /**
     * Вызывется при попадании пользователя на клетку офиса банка и взависимости от того, имеет ли
     * игрок кредит, вызывает либо метод, реализующий получение кредита, либо метод выплаты
     * существующего кредита.
     *
     * @param player игрок(пользователь)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    void stepInOfficeUser(Player player) throws Exception {
        if (userDebt == 0) {
            takeCredit(player);
        } else {
            payDebt(player);
        }
    }

    /**
     * Реализует получение игроком кредита.
     *
     * @param player иргрок(пользователь)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоени
     *                   отрицательного значения money игрока
     */
    void takeCredit(Player player) throws Exception {
        int maxCredit = (int) Math.round(userExpences * creditCoeff);
        if (maxCredit != 0) {
            String output = String.format("You are in the bank office and you don't have any debts for the moment.\n" +
                    "Would you like to get a credit? " +
                    "\n(Your balance is %d$)\n" +
                    "Input how many you want to get(The amount must be less or equal %d$) or ’No’\n", player.getMoney(), maxCredit);
            System.out.println(output);
            String mes = MessageTools.numberOrNoReadFromConsole(output, maxCredit);
            if (!mes.equals("No")) {
                int credit = Integer.parseInt(mes);
                int debt = (int) Math.round(debtCoeff * credit);
                System.out.printf("\nGreat! You took out %d$ in credit.\n" +
                        "Now you owe the Bank %d$, which is %d$ more than you borrowed.\n" +
                        "See you soon. We'll be waiting for you ;)\n", credit, debt, debt - credit);
                userDebt = debt;
                player.setMoney(player.getMoney() + credit);
            }
        } else {
            System.out.println("\nYou have no debt and also you haven't spent your money\n" +
                    "by buying shops yet so you can't get a loan. Goodbye!\n");
        }
    }

    /**
     * Реализует выплату игроком долга.
     *
     * @param player иргрок(пользователь)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    void payDebt(Player player) throws Exception {
        System.out.printf("Your debt to the Bank is %d$. You must pay it now.\n", userDebt);
        player.setMoney(player.getMoney() - userDebt);
        userDebt = 0;
        System.out.print("You paid your debt to the Bank.\n");
    }

    /**
     * Возвращает символ отображения банка на карте.
     *
     * @return символ отображения клетки на карте
     */
    @Override
    public String toString() {
        return "$";
    }
}
