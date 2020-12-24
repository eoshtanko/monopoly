/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package game;

import cells.*;
import players.*;
import tools.Values;
import tools.InputInitialData;
import tools.MessageTools;

import java.io.IOException;

/**
 * Основной класс игры.
 * Содержит точку входа.
 * Содержит метод для хода, а так же методы необходимые для отображения
 * информации о ходе.
 */
public class Game {
    /**
     * Игрок. Бот
     */
    public static Bot bot = new Bot();

    /**
     * Игрок. Пользователь
     */
    public static User user = new User();

    /**
     * Изначальный баланс
     */
    public static int startBalance;

    /**
     * Игральное поле
     */
    private static Cell[][] field;

    /**
     * Счетсик шагов
     */
    private static int countOfSteps = 1;

    /**
     * main - точка входа.
     * ("Все методы должны иметь javadoc-комментарии.") :)
     *
     * @param args аргументы командной строки
     *             (верные данные: <height> <width> <money>, где 6 <= height <= 30,
     *             6 <= height <= 30, 500 <= money <= 15000)
     */
    public static void main(String[] args) {
        try {
            InputInitialData.inputData(args);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка при запуске приложения. Корректное число аргументов - 3.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при запуске приложения. Аргументы должны быть целочисленными.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Ошибка при запуске приложения.");
            return;
        }
        System.out.println("\n\t\tSTART THE GAME!\n");
        field = Field.genField();
        Field.showField();
        try {
            showCoeff();
            firstStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Выводит информацию о коэффициентах.
     */
    static void showCoeff() {
        System.out.printf("\nYour start balance, like your opponent's, is %d$.\nCoefficients:\n" +
                        "Penalty coefficient ~ %.3f\n" +
                        "Credit coefficient ~ %.3f\n" +
                        "Debt coefficient ~ %.3f\n\n", startBalance, PenaltyCell.getPenaltyCoeff(),
                BankOffice.getCreditCoeff(), BankOffice.getDebtCoeff());
    }

    /**
     * Определяет, бот или пользователь делает первых ход и "запускает" игру.
     *
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    static void firstStep() throws Exception {
        System.out.println("\t\tLet's start!");
        if (Values.genBool()) {
            System.out.println("\nYou take the first step.");
            MessageTools.continuation();
            showStep(user);
        } else {
            System.out.println("\nYour opponent takes the first step.");
            MessageTools.continuation();
            showStep(bot);
        }
    }

    /**
     * Оформляет вывод хода на консоль и запускает новый ход
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     *                   (это рано или поздно случится и бесконечной рекурсии
     *                   не возникнет)
     */
    static void showStep(Player player) throws Exception {
        System.out.println("\n----------------------------------------------");
        System.out.printf("\t\t   STEP %d\n", countOfSteps);
        System.out.printf("\nStep by %s! Roll the dice...\n", player.getClass().getSimpleName());
        countOfSteps++;
        // Ход
        step(player);
        System.out.println("\n\t\tEND OF THE STEP");
        System.out.println("----------------------------------------------\n");
        Field.showField();
        info();
        if (player instanceof Bot)
            showStep(user);
        else
            showStep(bot);
    }

    /**
     * Осуществляет ход игрока.
     *
     * @param player игрок
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    static void step(Player player) throws Exception {
        int numberOfSteps = (Values.genInt(1, 6) + (Values.genInt(1, 6)));
        System.out.printf("%d steps forward!\n", numberOfSteps);
        player.moveForward(numberOfSteps);
        Cell beforeStepIn;
        do {
            beforeStepIn = field[player.getX()][player.getY()];
            beforeStepIn.stepIn(player);
        } while (beforeStepIn instanceof Taxi);
    }

    /**
     * Выводит информацию о завершившемся ходе.
     *
     * @throws IOException при работе с System.in в continuation
     */
    static void info() throws IOException {
        System.out.println("\n\n\t\t ---- INFO ----");
        showUserInfo();
        showBotInfo();
        MessageTools.continuation();
    }

    /**
     * Выводит информацию о состоянии игрока после завершившегося хода.
     */
    static void showUserInfo() {
        System.out.printf("\nYou are in the cell (%d, %d).\nYour balance: %d" +
                "\nYour debt: %d\nYou own shops: \n", user.getXOutput(), user.getYOutput(), user.getMoney(), BankOffice.getUserDebt());
        if (user.getPossessions().size() != 0)
            for (Shop item : user.getPossessions()) {
                System.out.printf("\tShop: (%d, %d) - Price = %d$ Compensation = %d$\n", item.getXOutput(), item.getYOutput(), item.getN(), item.getK());
            }
        else {
            System.out.println("\tYou don't have any shops at the moment.");
        }
    }

    /**
     * Выводит информацию о состоянии бота после завершившегося хода.
     */
    static void showBotInfo() {
        System.out.printf("\nYour opponent is in the cell (%d, %d).\nOpponent's balance: %d" +
                "\nOpponent owns shops: \n", bot.getXOutput(), bot.getYOutput(), bot.getMoney());
        if (bot.getPossessions().size() != 0)
            for (Shop item : bot.getPossessions()) {
                System.out.printf("\tShop: (%d, %d) - Price = %d$ Compensation = %d$\n", item.getXOutput(), item.getYOutput(), item.getN(), item.getK());
            }
        else {
            System.out.println("\tYour opponent doesn't have any shops at the moment.");
        }
    }
}
