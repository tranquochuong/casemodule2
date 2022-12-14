package utils;

import view.InputOption;

import java.text.DecimalFormat;
import java.util.Scanner;

public class AppUtils {
    static Scanner scanner = new Scanner(System.in);

    public static String retryString() {
        String result;
        while (((result = scanner.nextLine()).trim()).isEmpty()) {
            System.out.println("Chuỗi rỗng hoặc chỉ chứa khoảng trống!");
            System.out.print("Vui lòng nhập lại: ");
        }
        return result;
    }

    public static long retryParseLong() {
        long result;
        do {
            try {
                result = Long.parseLong(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Nhập sai! vui lòng nhập lại");
            }
        } while (true);
    }

    public static double retryParseDouble() {
        double result;
        do {
            try {
                result = Double.parseDouble(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Sai cú pháp, vui lòng nhập lại!");
            }
        } while (true);
    }

    public static void pressEnterToContinue() {
        System.out.print("Nhấn Enter để tiếp tục. ");
        String s = scanner.nextLine();
    }
    public static String doubleToVND(double value) {
        String patternVND = "###,### vn₫";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }

    public static boolean isRetry(InputOption inputOption) {
        do {
            switch (inputOption) {
                case ADD:
                    System.out.println(" Chọn 'y' để tiếp tục  \t|\t 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case UPDATE:
                    System.out.println(" Chọn 'y' để tiếp tục  \t|\t 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case DELETE:
                    System.out.println(" Chọn 'y' để tiếp tục  \t|\t 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case SHOW:
                    System.out.println(" Chọn 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case FIND:
                    System.out.println(" Chọn 'y' để tiếp tục  \t|\t 'q' để quay lại\t|\t 't' để thoát.");
                    break;
                default:
                    throw new IllegalStateException( inputOption+" Nhập không đúng " );
            }

            System.out.print(" => ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    return true;
                case "q":
                    return false;
                case "t":
                    System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                    System.exit(0);
                    break;
                default:
                    System.out.println(" ❌ Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                    break;
            }
        } while (true);
    }
    public static int retryParseInt() {
        int result;
        do {
            try {
                result = Integer.parseInt(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Sai cú pháp, vui lòng nhập lại!");
            }
        } while (true);
    }

}