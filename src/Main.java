import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите имя входного файла: ");
            String inputFileName = scanner.nextLine();

            System.out.print("Введите имя выходного файла: ");
            String outputFileName = scanner.nextLine();

            if (inputFileName.isEmpty() || outputFileName.isEmpty()) {
                throw new IllegalArgumentException("Имена файлов не могут быть пустыми.");
            }

            File inputFile = new File(inputFileName);
            if (!inputFile.exists()) {
                throw new FileNotFoundException("Файл " + inputFileName + " не существует.");
            }

            Map<Character, Integer> frequencyMap = new HashMap<>();

            for (char ch = 'A'; ch <= 'Z'; ch++) {
                frequencyMap.put(ch, 0);
                frequencyMap.put(Character.toLowerCase(ch), 0);
            }

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName))) {
                int character;
                while ((character = reader.read()) != -1) {
                    char ch = (char) character;
                    if (frequencyMap.containsKey(ch)) {
                        frequencyMap.put(ch, frequencyMap.get(ch) + 1);
                    }
                }
            } catch (IOException e) {
                throw new IOException("Ошибка при чтении файла " + inputFileName + ": " + e.getMessage(), e);
            }

            try {
                writeResultsToFile(outputFileName, frequencyMap);
                System.out.println("Результаты успешно записаны в файл " + outputFileName);
            } catch (IOException e) {
                throw new IOException("Ошибка при записи в файл " + outputFileName + ": " + e.getMessage(), e);
            }

        } catch (IllegalArgumentException | FileNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла неожиданная ошибка: " + e.getMessage());
        }
    }

    private static void writeResultsToFile(String outputFileName, Map<Character, Integer> frequencyMap) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName))) {
            for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        }
    }
}
