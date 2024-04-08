import java.util.Scanner;

public class Calculator {
    private static boolean isValidInput(String exp) {
        exp = exp.replaceAll("\\s", "");  // Удаляем пробелы из ввода
        return exp.matches("^[\\d\\+\\-*/IVXLCDM]*$");
    }

    private static int calc(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }
    }

    public static void main(String[] args) {
        try {
            String[] actions = {"+", "-", "/", "*"};
            String[] regexActions = {"\\+", "-", "/", "\\*"};
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input:");
            String exp = scanner.nextLine();

            // Проверяем, является ли строка математической операцией
            if (!isValidInput(exp)) {
                throw new IllegalArgumentException("Ошибка: строка не является математической операцией");
            }

            // Проверяем количество операторов в выражении
            int operatorCount = 0;
            for (String action : regexActions) {
                operatorCount += exp.split(action).length - 1;
            }

            if (operatorCount != 1) {
                throw new IllegalArgumentException("Ошибка: формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }

            // Находим индекс оператора
            int actionIndex = -1;
            for (int i = 0; i < actions.length; i++) {
                if (exp.contains(actions[i])) {
                    actionIndex = i;
                    break;
                }
            }

            // Разделяем выражение на операнды
            String[] data = exp.split(regexActions[actionIndex]);
            int num1, num2;
            String data0 = data[0].replaceAll("\\s+", "");
            String data1 = data[1].replaceAll("\\s+", "");

            boolean a = RomanNumeral.isRoman(data0);
            boolean b = RomanNumeral.isRoman(data1);

            // Проверяем, используются ли одновременно разные системы счисления
            if (a != b) {
                throw new IllegalArgumentException("Ошибка: используются одновременно разные системы счисления");
            }

            // Преобразуем операнды в числа
            if (a) {
                num1 = RomanNumeral.romanToArabic(data0);
                num2 = RomanNumeral.romanToArabic(data1);
            } else {
                num1 = Integer.parseInt(data0);
                num2 = Integer.parseInt(data1);
            }

            // Проверяем диапазон чисел
            if ((num1 > 10) || (num1 < 1) || (num2 > 10) || (num2 < 1)) {
                throw new IllegalArgumentException("Ошибка: Должны использоваться числа от 1 до 10");
            }

            // Выполняем операцию
            int result = calc(num1, num2, actions[actionIndex].charAt(0));

            // Проверяем результат на отрицательное значение
            if (a && result <= 0) {
                throw new IllegalArgumentException("Ошибка: в римской системе нет отрицательных чисел");
            }

            // Выводим результат
            System.out.println("Output:");
            if (a) {
                String result1 = RomanNumeral.arabicToRoman(result);
                System.out.println(result1);
            } else {
                System.out.println(result);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Output:");
            System.out.println(e.getMessage());
        }
    }
}