/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package players;

/**
 * Класс, соответствующий игроку - боту.
 * Несмотря на почти полное отстувие реализуемой логики(даже единственный
 * метод EndOfTheGame можно было реализовать в классе Player), этот класс мне все же
 * показался необходимым. Бот, как элемент программы, - отдельная сущность,
 * отдельный тип и требует создания отдельного класса.
 */
public class Bot extends Player {
    /**
     * При отрицательном значении money бота выводит информацию об окончании игры
     * и завершает игру, выбрасывая исключение.
     *
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при попытке присвоения
     *                   отрицательного значения money игрока
     */
    static void EndOfTheGame() throws Exception {
        throw new Exception("\nBot doesn't have any money left. It is a bankrupt." +
                "\n\n\t\t\tGAME OVER!\n\tYou, dear man, have won!" +
                "\n\tStill, a human is much smarter than a machine... ");
    }
}
