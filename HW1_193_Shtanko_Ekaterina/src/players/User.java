/**
 * @author <a href="mailto:eoshtanko@edu.hse.ru"> Ekaterina Shtanko</a>
 */
package players;

/**
 * Класс, соответствующий игроку - пользователю.
 * Несмотря на почти полное отстувие реализуемой логики(даже единственный
 * метод EndOfTheGame можно было реализовать в классе Player), этот класс мне все же
 * показался необходимым. Пользователь, как элемент программы, - отдельная сущность,
 * отдельный тип и требует создания отдельного класса.
 */
public class User extends Player {
    /**
     * При отрицательном значении money пользователя выводит информацию об окончании игры
     * и завершает игру, выбрасывая исключение.
     *
     * @throws Exception при завершении игры(один из игроков - банкрот)
     *                   Исключение генерируется при присвоении
     *                   отрицательного значения money игрока
     */
    static void EndOfTheGame() throws Exception {
        throw new Exception("\nYou don't have any money left. You are bankrupt." +
                "\n\n\t\t\tGAME OVER!\n\tYou, dear man, have lost!" +
                "\n\tRobots have surpassed humans!");
    }
}
