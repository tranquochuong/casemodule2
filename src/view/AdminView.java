package view;

import utils.AppUtils;

import java.util.Scanner;

public class AdminView {
    public static void launch(long userId) {
        Scanner scanner = new Scanner(System.in);
        boolean isTrue = true;
        do {
            try {
                menuAdminOption();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        UserMenuView.launch();
                        break;
                    case 2:
                        ProductMenuView.launch();
                        break;
                    case 3:
                        OrderMenuView.launch(userId);
                        break;
                    case 0:
                        System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(" ❌Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                        System.out.print(" ➱ ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
    }
    private static void menuAdminOption() {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒ MENU ☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("☒         1. Quản lý tài khoản.        ☒");
        System.out.println("☒         2. Quản lý sản phẩm.         ☒");
        System.out.println("☒         3. Quản lý đơn hàng.         ☒");
        System.out.println("☒         0. Thoát.                    ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("➱ ");
    }
}