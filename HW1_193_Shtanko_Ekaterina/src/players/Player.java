/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package players;

import cells.BankOffice;
import cells.Shop;
import game.Field;

import java.util.ArrayList;

/**
 * Класс, соответствующий игроку, реализующий основную логику связанную с его поведением
 * и хранящий основную информацию о нем.
 * От него унаследованы классы, соответствующие боту и пользователю.
 */
public class Player {
    int money;
    int x, y;

    /**
     * Все магазины, находящиеся во владении игрока.
     */
    private ArrayList<Shop> possessions = new ArrayList<>();

    /**
     * Предоставляет информацию о всех магазинах, находящиеся во владении игрока.
     *
     * @return все магазины, находящиеся во владении игрока.
     */
    public ArrayList<Shop> getPossessions() {
        return possessions;
    }

    /**
     * Предоставляет информацию о количестве денег игрока.
     *
     * @return количество денег игрока
     */
    public int getMoney() {
        return money;
    }

    /**
     * Устанавливает значение количества денег игрока.
     *
     * @param value новое значение количества денег
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    public void setMoney(int value) throws Exception {
        money = value;
        if (money < 0) {
            if (this instanceof User) {
                User.EndOfTheGame();
            }
            Bot.EndOfTheGame();
        }
    }

    /**
     * Предоставляет информацию о значении координаты x с точки зрения использования
     * двумерного массива(первый аргумент при обращении к двумерному массиву,
     * отвечает за строки(за высоту, за ось OY)
     *
     * @return значение координаты x с точки зрения использования
     * двумерного массива
     */
    public int getX() {
        return x;
    }

    /**
     * Предоставляет информацию о значении координаты y с точки зрения использования
     * двумерного массива(первый аргумент при обращении к двумерному массиву,
     * отвечает за столбцы(за ширину, ось OX)
     *
     * @return значение координаты y с точки зрения использования
     * двумерного массива
     */
    public int getY() {
        return y;
    }

    /**
     * Предоставляет информацию о значении координаты x для вывода(т.е. не с точки зрения использования
     * двумерного массива).
     *
     * @return значение координаты x для вывода(т.е. не с точки зрения использования
     * двумерного массива)
     */
    public int getXOutput() {
        // Решение ниже может показаться нелогичным. Хочу его обосновать.
        // Для реализации игрового поля был использован двумерный массив
        // При создании и обращении к двумерному массиву первый аргумент [x, y]
        // отвечает за строки(за высоту, за ось OY), второй - за столбцы(за ширину, ось OX).
        // Выводить же мне нужно сначала координату на OX, потом - OY
        return y;
    }

    /**
     * Предоставляет информацию о значении координаты y для вывода(т.е. не с точки зрения использования
     * двумерного массива).
     *
     * @return значение координаты y для вывода(т.е. не с точки зрения использования
     * двумерного массива)
     */
    public int getYOutput() {
        // Решение ниже может показаться нелогичным. Хочу его обосновать.
        // Для реализации игрового поля был использован двумерный массив
        // При создании и обращении к двумерному массиву первый аргумент [x, y]
        // отвечает за строки(за высоту, за ось OY), второй - за столбцы(за ширину, ось OX).
        // Выводить же мне нужно сначала координату на OX, потом - OY
        return x;
    }

    /**
     * Передвижение игрока вперед по полю по часовой стрелке.
     *
     * @param cells количества клеток, на которое игрок передвинется вперед
     */
    public void moveForward(int cells) {
        while (cells != 0) {
            while ((x == 0) && (y < Field.width - 1) && (cells != 0)) {
                y++;
                cells--;
            }
            while ((y == Field.width - 1) && (x < Field.hight - 1) && (cells != 0)) {
                x++;
                cells--;
            }
            while ((x == Field.hight - 1) && (y > 0) && (cells != 0)) {
                y--;
                cells--;
            }
            while ((y == 0) && (x > 0) && (cells != 0)) {
                x--;
                cells--;
            }
        }
    }

    /**
     * Действие игрока - покупка магазина.
     * Проверяет достаточно ли средств для покупки персонажем магазина, если да осуществляет покупку, если нет - выводит
     * соответсвующее сообщение.
     *
     * @param shop магазин для покупки
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    public void buyShop(Shop shop) throws Exception {
        if (shop.getN() > money) {
            System.out.println("But.. Not enough money to buy this shop :(");
        } else {
            setMoney(getMoney() - shop.getN());
            possessions.add(shop);
            if (this instanceof User) {
                BankOffice.setUserExpences(BankOffice.getUserExpences() + shop.getN());
            }
            shop.setOwner(this);
            System.out.printf("The shop was purchased for %d$.\n\n", shop.getN());
        }
    }
}