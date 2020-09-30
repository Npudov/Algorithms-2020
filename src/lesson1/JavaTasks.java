package lesson1;

import kotlin.NotImplementedError;

import java.lang.Math;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    /*
    время: O(N * logN)
    память: S(N)
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        List<Integer> listTime = new ArrayList<>();
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("hh:mm:ss a"); // 12-hours format
        DateTimeFormatter formatter24 = DateTimeFormatter.ofPattern("HH:mm:ss"); // 24-hours format
        File fileRead = new File(inputName);
        File fileWrite = new File(outputName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileRead));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWrite));
        while (bufferedReader.ready()) {
            String str = bufferedReader.readLine();
            String time = formatter24.format(formatter12.parse(str));
            String[] hours24Time = time.split(":");
            int seconds = Integer.parseInt(hours24Time[0]) * 3600 + Integer.parseInt(hours24Time[1]) * 60 + Integer.parseInt(hours24Time[2]);
            listTime.add(seconds);
        }
        Collections.sort(listTime);
        for (int i = 0; i < listTime.size(); i++) {
            int element = listTime.get(i);
            int hours = element / 3600;
            int minutes = (element - hours * 3600) / 60;
            int sec = element - hours * 3600 - minutes * 60;
            String format24hours = String.format("%02d:%02d:%02d", hours, minutes, sec);
            String answer = formatter12.format(formatter24.parse(format24hours));
            bufferedWriter.write(answer);
            if (i != listTime.size() - 1) {
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    /*
    время: O(n + k) где n - количество элементов в массиве, а k - количество повторений элемента в массиве
    память: S(N)
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int maxCount = (273 + 500 + 1) * 10;
        int delta = 2730;
        int [] countArray = new int[maxCount + 1];
        int cnt = 0; // количество элементов(температур) в файле
        File fileRead = new File(inputName);
        File fileWrite = new File(outputName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileRead));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWrite));
        while (bufferedReader.ready()) {
            cnt++;
            String str = bufferedReader.readLine();
            int temperature = (int) Math.floor((Float.parseFloat(str) * 10));
            countArray[temperature + delta]++;
        }
        int curIndex = 0;
        for (int i = 0; i < countArray.length; i++) {
            int cntTemperature = countArray[i];
            if (cntTemperature == 0) continue;
            for (int j = 0; j < cntTemperature; j++) {
                bufferedWriter.write((i - delta) / 10.0 + "\n");
                curIndex++;
            }
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    /*
    время: O(N*logN)
    память: S(N)
    */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        int cnt = 1;
        int maxCount  = -1;
        int minNumber = 0;
        List<Integer> list = new ArrayList<>(); //исходный массив последовательности
        List<Integer> sortList = new ArrayList<>(); //отсортированный массив последовательности
        List<Integer> minDigit = new ArrayList<>();
        File fileRead = new File(inputName);
        File fileWrite = new File(outputName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileRead));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWrite));
        while (bufferedReader.ready()) {
            int number = Integer.parseInt(bufferedReader.readLine());
            list.add(number);
            sortList.add(number);
        }
        Collections.sort(sortList);
        for (int i = 0; i < sortList.size() - 1; i++) {
            int curElement = sortList.get(i);
            int nextElement = sortList.get(i + 1);
            if (curElement != nextElement) {
                maxCount = getMaxCount(cnt, maxCount, minDigit, curElement);
                cnt = 1;
            }
            else {
                if ((i == sortList.size() - 2) && (curElement == nextElement)) {
                    maxCount = getMaxCount(cnt, maxCount, minDigit, curElement);
                }
                cnt++;
            }
        }
        Collections.sort(minDigit);
        minNumber = minDigit.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != minNumber) {
                bufferedWriter.write(list.get(i) + "\n");
            }
        }
        for (int i = 0; i < maxCount; i++) {
            bufferedWriter.write(minNumber + "\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    private static int getMaxCount(int cnt, int maxCount, List<Integer> minDigit, int curElement) {
        if (cnt > maxCount) {
            maxCount = cnt;
            minDigit.clear();
            minDigit.add(curElement);
        }
        if (cnt == maxCount) {
            minDigit.add(curElement);
        }
        return maxCount;
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
