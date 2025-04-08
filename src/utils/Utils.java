package src.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Utils {
    public static LocalDateTime getDateFromConsole(Scanner scanner) {
        LocalDateTime date = null;
        while (date == null) {
            System.out.print("Enter date (yyyy-MM-dd HH:mm): ");
            String input = scanner.nextLine();
            try{
                date = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }catch (DateTimeParseException e) {
                System.out.println("Invalid date format!");
            }
        }
        return date;
    }
}
