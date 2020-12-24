/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package game;

import cells.*;
import tools.Values;

/**
 * Класс, соответствующий игральному полю.
 * Содержит игральное поле и описывающие его значения(высоту и ширину).
 * Содержит методы, "заполняющие" игровое поле.
 * Содержит метод демонстрации игрального поля.
 */
public class Field {
    /**
     * Высота поля
     */
    public static int hight;

    /**
     * Ширина поля
     */
    public static int width;

    /**
     * Игровое поле
     */
    private static Cell[][] field;

    /**
     * Выводит поле.
     */
    static void showField() {
        System.out.println("\n\t\tGAME FIELD\n");
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j] != null) {
                    System.out.print(field[i][j] + " ");
                } else System.out.print("  ");
            }
            System.out.println();
        }
    }

    /**
     * Заполняет поле.
     *
     * @return заполненное поле
     */
    static Cell[][] genField() {
        field = new Cell[hight][width];
        fullEmptyCells();
        fullOffices();
        fullTaxies();
        fullPenaltyCells();
        fullShops();
        return field;
    }

    /**
     * Заполняет угловые клетки(EmptyCells).
     */
    static void fullEmptyCells() {
        field[0][0] = new EmptyCell(0, 0);
        field[hight - 1][width - 1] = new EmptyCell(hight - 1, width - 1);
        field[0][width - 1] = new EmptyCell(0, width - 1);
        field[hight - 1][0] = new EmptyCell(hight - 1, 0);
    }

    /**
     * Заполняет клетки офисов банков (на каждой линии располагается 1 офис).
     */
    static void fullOffices() {
        int rand;
        field[0][rand = Values.genInt(1, width - 2)] = new BankOffice(0, rand);
        field[hight - 1][rand = Values.genInt(1, width - 2)] = new BankOffice(hight - 1, rand);
        field[rand = Values.genInt(1, hight - 2)][width - 1] = new BankOffice(rand, width - 1);
        field[rand = Values.genInt(1, hight - 2)][0] = new BankOffice(rand, 0);
    }

    /**
     * Заполняет клетки такси (от 0 до 2 такси на каждой линии).
     */
    static void fullTaxies() {
        fullTaxiesHorizontalLine(0);
        fullTaxiesHorizontalLine(hight - 1);
        fullTaxiesVerticalLine(0);
        fullTaxiesVerticalLine(width - 1);
    }

    /**
     * Заполняет клетки такси, располагающиеся на горизонтальных линиях.
     *
     * @param x первая координата при обращении к двумерному массиву
     */
    static void fullTaxiesHorizontalLine(int x) {
        int amountOfTaxis = Values.genInt(0, 2);
        int y;
        for (int i = 0; i < amountOfTaxis; i++) {
            y = Values.genInt(1, width - 2);
            if (field[x][y] == null) {
                field[x][y] = new Taxi(x, y);
            } else {
                i--;
            }
        }
    }

    /**
     * Заполняет клетки такси, располагающиеся на вертикальных линиях.
     *
     * @param y вторая координата при обращении к двумерному массиву
     */
    static void fullTaxiesVerticalLine(int y) {
        int amountOfTaxis = Values.genInt(0, 2);
        int x;
        for (int i = 0; i < amountOfTaxis; i++) {
            x = Values.genInt(1, hight - 2);
            if (field[x][y] == null) {
                field[x][y] = new Taxi(x, y);
            } else {
                i--;
            }
        }
    }

    /**
     * Заполняет Penalty клетки.
     */
    static void fullPenaltyCells() {
        fullPenaltyCellsHorizontalLine(0);
        fullPenaltyCellsHorizontalLine(hight - 1);
        fullPenaltyCellsVerticalLine(0);
        fullPenaltyCellsVerticalLine(width - 1);
    }

    /**
     * Заполняет клетки Penalty, располагающиеся на горизонтальных линиях.
     *
     * @param x первая координата при обращении к двумерному массиву
     */
    static void fullPenaltyCellsHorizontalLine(int x) {
        int amountOfPenaltyCells = Values.genInt(0, 2);
        int y;
        for (int i = 0; i < amountOfPenaltyCells; i++) {
            y = Values.genInt(1, width - 2);
            if ((field[x][y] == null) || (field[x][y] instanceof Taxi)) {
                field[x][y] = new PenaltyCell(x, y);
            } else {
                i--;
            }
        }
    }

    /**
     * Заполняет клетки Penalty, располагающиеся на вертикальных линиях.
     *
     * @param y вторая координата при обращении к двумерному массиву
     */
    static void fullPenaltyCellsVerticalLine(int y) {
        int amountOfPenaltyCells = Values.genInt(0, 2);
        int x;
        for (int i = 0; i < amountOfPenaltyCells; i++) {
            x = Values.genInt(1, hight - 2);
            if ((field[x][y] == null) || (field[x][y] instanceof Taxi)) {
                field[x][y] = new PenaltyCell(x, y);
            } else {
                i--;
            }
        }
    }

    /**
     * Заполняет клетки магазинов.
     */
    static void fullShops() {
        fullShopsHorizontalLine(0);
        fullShopsHorizontalLine(hight - 1);
        fullShopsVerticalLine(0);
        fullShopsVerticalLine(width - 1);
    }

    /**
     * Заполняет клетки магазинов, располагающиеся на горизонтальных линиях.
     *
     * @param x первая координата при обращении к двумерному массиву
     */
    static void fullShopsHorizontalLine(int x) {
        for (int y = 1; y < width - 1; y++) {
            if (field[x][y] == null) {
                field[x][y] = new Shop(x, y);
            }
        }
    }

    /**
     * Заполняет клетки магазинов, располагающиеся на вертикальных линиях.
     *
     * @param y вторая координата при обращении к двумерному массиву
     */
    static void fullShopsVerticalLine(int y) {
        for (int x = 1; x < hight - 1; x++) {
            if (field[x][y] == null) {
                field[x][y] = new Shop(x, y);
            }
        }
    }
}
