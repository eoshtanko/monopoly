/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package cells;

import game.Game;
import players.*;
import tools.Values;
import tools.MessageTools;

/**
 * Класс, соответствующий клетке магазина на игральном поле. Содержит описание
 * действий игрока при попадании на подобную клетку.
 */
public class Shop extends Cell {
    /**
     * Минимальное возможное значение коэффициента увеличения стоимости улучшения магазина
     */
    private static final double MIN_IMPROVEMENT_COEFF = 0.1;

    /**
     * Максимальное возможное значение коэффициента увеличения стоимости улучшения магазина
     */
    private static final double MAX_IMPROVEMENT_COEFF = 2;

    /**
     * Минимальное возможное значение коэффициента увеличения компенсации магазина
     */
    private static final double MIN_COMPENSATION_COEFF = 0.1;

    /**
     * Максимальное возможное значение коэффициента увеличения компенсации магазина
     */
    private static final double MAX_COMPENSATION_COEFF = 1;

    /**
     * Минимальное возможное значение стоимости магазина
     */
    private static final int MIN_N = 50;

    /**
     * Максимальное возможное значение стоимости магазина
     */
    private static final int MAX_N = 500;

    /**
     * Минимальное возможное значение компенсации, выплачиваемая владельцу магазина
     */
    private static final double MIN_K_COEFF = 0.5;

    /**
     * Максимальное возможное значение компенсации, выплачиваемая владельцу магазина
     */
    private static final double MAX_K_COEFF = 0.9;

    /**
     * Коэффициент увеличения стоимости улучшения магазина
     */
    private final double improvementCoeff;

    /**
     * Коэффициент увеличения компенсации магазина
     */
    private final double compensationCoeff;

    /**
     * Стоимость магазина
     */
    private int N;

    /**
     * Компенсация, выплачиваемая владельцу магазина
     */
    private int K;

    /**
     * Владелец магазина
     */
    private Player owner;

    /**
     * Конструктор Shop
     *
     * @param x значение координаты x с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за строки(за высоту, за ось OY)
     * @param y значение координаты y с точки зрения использования
     *          двумерного массива(первый аргумент при обращении к двумерному массиву,
     *          отвечает за столбцы(за ширину, ось OX)
     */
    public Shop(int x, int y) {
        super(x, y);
        N = Values.genInt(MIN_N, MAX_N);
        K = (int) Math.round(N * Values.genDouble(MIN_K_COEFF, MAX_K_COEFF));
        improvementCoeff = Values.genDouble(MIN_IMPROVEMENT_COEFF, MAX_IMPROVEMENT_COEFF);
        compensationCoeff = Values.genDouble(MIN_COMPENSATION_COEFF, MAX_COMPENSATION_COEFF);
    }

    /**
     * Геттер. Предоставляет значение стоимости магазина.
     *
     * @return стоимость магазина
     */
    public int getN() {
        return N;
    }

    /**
     * Геттер. Предоставляет значение компенсации, выплачиваемой владельцу магазина
     *
     * @return стоимость магазина
     */
    public int getK() {
        return K;
    }

    /**
     * Сеттер. Устанавливает значение поля владельца магазина.
     *
     * @param owner владелец магазина
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Определяет поведение игрока при попадании на клетку магазина и вызывает соответствующий метод.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    @Override
    public void stepIn(Player player) throws Exception {
        System.out.printf("\t  This is shop cell(%d, %d)!\nInfo:\nPrice = %d$ Compensation = %d$\n\n", getXOutput(), getYOutput(), N, K);
        if (owner == player) {
            playerIsOwner(player);
        } else if (owner == null) {
            hasNoOwner(player);
        } else {
            opponentIsOwner(player);
        }
    }

    /**
     * Вызывается, если игрок попавший на клетку - владелец магазина.
     * Определяет к какому именно типу принадлежит игрок и вызывает соответствующий
     * метод.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void playerIsOwner(Player player) throws Exception {
        if (player instanceof User) {
            userIsOwner(player);
        } else {
            botIsOwner(player);
        }
    }

    /**
     * Вызывается, если игрок, попавший на данное поле,- пользователь и он является владельцем магазина.
     * Предлагает улучшить магазин.
     *
     * @param player игрок(пользователь)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void userIsOwner(Player player) throws Exception {
        String input;
        String output = String.format("\nIt's yours. Price: %d, Compensation: %d." +
                "\nWould you like to upgrade it for %d$ (Your balance is %d$)? " +
                "\nInput 'Yes’ if you agree or ‘No’ otherwise.", N, K, (int) Math.round(improvementCoeff * N), player.getMoney());
        System.out.println(output);
        input = MessageTools.yesOrNoReadFromConsole(output);
        if (input.equals("Yes")) {
            upgrade(player);
        } else {
            System.out.println("\nYou chose to do nothing.\n");
        }
    }

    /**
     * Вызывается, если игрок, попавший на данное поле,- бот и он является владельцем магазина.
     * Определяет случайным образом, улучить ли магазин.
     *
     * @param player игрок(бот)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void botIsOwner(Player player) throws Exception {
        if (Values.genBool()) {
            System.out.println("\nBot decided to upgrade this shop.\n");
            upgrade(player);
        } else {
            System.out.println("\nBot decided not to upgrade this shop.\n");
        }
    }

    /**
     * В случае если у игрока достаточно денег улучшает магазин.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void upgrade(Player player) throws Exception {
        int improvementCost = (int) Math.round(improvementCoeff * N);
        if (player.getMoney() >= improvementCost) {
            player.setMoney(player.getMoney() - improvementCost);
            if (player instanceof User) {
                BankOffice.setUserExpences(BankOffice.getUserExpences() + improvementCost);
            }
            N += (int) Math.round(improvementCoeff * N);
            K += (int) Math.round(compensationCoeff * K);
            System.out.printf("The store has been upgraded.\nNow it's price is %d and it's compensation is %d\n", N, K);
        } else {
            System.out.println("But... Not enough money to upgraded this shop :( ");
        }
    }

    /**
     * Вызывается, если магазин на данной клетке не имеет владельца.
     * Определяет к какому именно типу принадлежит игрок и вызывает соответствующий
     * метод.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void hasNoOwner(Player player) throws Exception {
        if (player instanceof User) {
            userHasNoOwner(player);
        } else {
            botHasNoOwner(player);
        }
    }

    /**
     * Вызывается, если магазин на данной клетке не имеет владельца и игрок попавший на нее - пользователь.
     * Определяет, считывая входные данные, совершать ли покупку.
     *
     * @param player игрок(пользователь)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void userHasNoOwner(Player player) throws Exception {
        String input;
        String output = String.format("Would you like to buy it for %d$? (Your balance is %d$)" +
                "\nInput 'Yes’ if you agreeor ‘No’ otherwise.", N, player.getMoney());
        System.out.println(output);
        input = MessageTools.yesOrNoReadFromConsole(output);
        if (input.equals("Yes")) {
            player.buyShop(this);
        } else {
            System.out.println("You decided not to buy this shop.");
        }
    }

    /**
     * Вызывается, если магазин на данной клетке не имеет владельца и игрок попавший на нее - бот.
     * Определяет случайным образом, совершить ли покупку магазина.
     *
     * @param player игрок(бот)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void botHasNoOwner(Player player) throws Exception {
        if (Values.genBool()) {
            System.out.println("Bot decided to buy this shop.");
            player.buyShop(this);
        } else {
            System.out.println("\nBot decided not to buy this shop.");
        }
    }

    /**
     * Вызывается, если магазин на данной клетке - собственность противника.
     * Определяет к какому именно типу принадлежит игрок и вызывает соответствующий
     * метод.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void opponentIsOwner(Player player) throws Exception {
        if (player instanceof User) {
            userOpponentIsOwner(player);
        } else {
            botOpponentIsOwner(player);
        }
    }

    /**
     * Вызывается, если магазин на данной клетке - собственность противника, а именно бота
     * Снимает со счета компенсацию.
     *
     * @param player игрок(пользователь)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void userOpponentIsOwner(Player player) throws Exception {
        System.out.printf("Your opponent owns it.\n" +
                "You must pay compensation in the amount of %d$.\n\n", K);
        player.setMoney(player.getMoney() - K);
        Game.bot.setMoney(Game.bot.getMoney() + K);
        System.out.printf("Compensation of %d$ has been paid.\n", K);
    }

    /**
     * Вызывается, если магазин на данной клетке - собственность противника, а именно пользователя
     * Снимает со счета компенсацию.
     *
     * @param player игрок(бот)
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    void botOpponentIsOwner(Player player) throws Exception {
        System.out.printf("Bot is in your shop.\n" +
                "It must pay compensation in the amount of %d$.\n\n", K);
        player.setMoney(player.getMoney() - K);
        Game.user.setMoney(Game.user.getMoney() + K);
        System.out.printf("Compensation of %d$ has been paid.\n", K);
    }

    /**
     * Возвращает символ отображения магазина на карте.
     *
     * @return символ отображения магазина на карте.
     */
    @Override
    public String toString() {
        if (owner instanceof Bot) {
            return "O";
        }
        if (owner instanceof User) {
            return "M";
        }
        return "S";
    }
}
