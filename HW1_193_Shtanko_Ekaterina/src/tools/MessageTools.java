/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Класс, содержащий методы взаимодействия программы с пользователем через консоль.
 * Я приняла решение выделить данные методы в отдельный класс, так как:
 * 1) Это позволит избежать повторение кода, сделать код чище.
 * 2) Это решение показалось мне корректным с точки зрения архитектуры. Данные
 * методы не соотносятся напрямую с логикой специфических, специализированных классов программы.
 * Они - просто интструменты и должны быть отделены.
 */
public class MessageTools {
    /**
     * Проверяет, возможно ли преобразовать строку в числовой формат
     * (парсится ли она).
     *
     * @param input строка для проверки
     * @return true если строка парсится, иначе false
     */
    public static boolean tryParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Считывает строку из консоли.
     *
     * @return строка, считанная из консоли
     * @throws IOException при работе с BufferedReader и System.in
     */
    public static String readFromConsole() throws IOException {
        Reader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }

    /**
     * Останавливает ход игры пока пользователь не нажмет Enter.
     *
     * @throws IOException при работе с System.in
     */
    static public void continuation() throws IOException {
        System.out.println("\nTo continue the game - Enter");
        System.in.read();
    }

    /**
     * Метод, считывает данные введенные пользователм в консоль. Причем, эти данные должны
     * соответствовать одному из двух: строка "Yes" или строка "No".
     * В случае ввода пользователем иного значения, метод будет выводить
     * соответствующую информацию и попытка ввода будет повторяться вновь.
     *
     * @param output строка с информацией для пользователя для повторного вывода
     *               в случае некорректных данных
     * @return одно из двух: "Yes" или "No"
     * @throws IOException при работе с BufferedReader и System.in
     */
    public static String yesOrNoReadFromConsole(String output) throws IOException {
        String input;
        while (!(input = readFromConsole()).equals("Yes") && !input.equals("No")) {
            System.out.println("\n\t\tWrong input!\n" + output);
        }
        return input;
    }

    /**
     * Метод, считывает данные введенные пользователм в консоль. Причем, эти данные должны
     * соответствовать одному из двух: либо быть строкой "No", либо положительным целым числом меньше
     * определенного значения(maxNum).
     * В случае ввода пользователем иного значения, метод будет выводить
     * соответствующую информацию и попытка ввода будет повторяться вновь.
     *
     * @param output строка с информацией для пользователя для повторного вывода
     *               в случае некорректных данных
     * @return одно из двух: положительное целое число меньше
     * определенного значения(maxNum) или "No"
     * @throws IOException при работе с BufferedReader и System.in
     */
    public static String numberOrNoReadFromConsole(String output, int maxNum) throws IOException {
        String input;
        int num;
        while (!(input = MessageTools.readFromConsole()).equals("No")
                && !(MessageTools.tryParseInt(input)
                && (num = Integer.parseInt(input)) > 0
                && num <= maxNum)) {
            System.out.println("\n\t\tWrong input!\n" + output);
        }
        return input;
    }
}
