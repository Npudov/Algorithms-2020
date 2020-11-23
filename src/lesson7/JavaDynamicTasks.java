package lesson7;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    /* Алгоритм Нидлмана-Вунша
    время: O(len(first)*len(second))
    память: O(len(first) * len(second))
     */
    public static String longestCommonSubSequence(String first, String second) {
        if (first == null || second == null || first.length() == 0 || second.length() == 0) return "";
        if (first.equals(second)) return first;
        char[] x = first.toCharArray();
        char[] y = second.toCharArray();
        List<Character> maxSubstring = new ArrayList<>();
        int[][] matrix = new int[first.length() + 1][second.length() + 1];
        for (int indexFirst = 0; indexFirst < first.length(); indexFirst++) {
            for (int indexSecond = 0; indexSecond < second.length(); indexSecond++) {
                if (x[indexFirst] == y[indexSecond]) {
                    matrix[indexFirst + 1][indexSecond + 1] = matrix[indexFirst][indexSecond] + 1;
                }
                else {
                    matrix[indexFirst + 1][indexSecond + 1] = max(matrix[indexFirst][indexSecond + 1], matrix[indexFirst + 1][indexSecond]);
                }
            }
        }
        int indexX = first.length() - 1;
        int indexY = second.length() - 1;
        while (indexX >= 0 && indexY >= 0) {
            if (x[indexX] == y[indexY]) {
                maxSubstring.add(x[indexX]);
                indexX--;
                indexY--;
            }
            else if (matrix[indexX][indexY + 1] > matrix[indexX + 1][indexY]) {
                indexX--;
            }
            else {
                indexY--;
            }
        }
        StringBuilder result = new StringBuilder();
        if (maxSubstring.isEmpty()) return "";
        for (int i = maxSubstring.size() - 1; i >= 0; i--) {
            result.append(maxSubstring.get(i));
        }
        return result.toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    /*
    время: O(n * m)
    память: O(n*m)
     */
    public static int shortestPathOnField(String inputName) throws IOException {
        List<String> list = new ArrayList<>();
        File fileRead = new File(inputName);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileRead))) {
            while (bufferedReader.ready()) {
                String str = bufferedReader.readLine();
                list.add(str);
            }
        }
        Integer lines = list.size();
        Integer columns = list.get(0).split(" ").length;
        Integer[][] inputArray = new Integer[lines][columns];
        Integer[][] weight = new Integer[lines][columns];
        for (int i = 0; i < lines; i++) {
            String[] string = list.get(i).split(" ");
            for (int j = 0; j < columns; j++) {
                inputArray[i][j] = Integer.parseInt(string[j]);
            }
        }

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 0 && j == 0) {
                    weight[i][j] = 0;
                }
                else if (j == 0) {
                    weight[i][j] = weight[i - 1][j] + inputArray[i][j]; //можем придти только сверху-вниз
                }
                else if (i == 0) {
                    weight[i][j] = weight[i][j - 1] + inputArray[i][j]; // можем придти только слева направо
                }
                else {
                    weight[i][j] = min(weight[i - 1][j], min(weight[i][j - 1], weight[i - 1][j - 1])) + inputArray[i][j];
                }
            }
        }
        return weight[lines - 1][columns - 1];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
