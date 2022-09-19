package view;

import service.UserService;

import java.util.Scanner;

public class UserMenuView {
    private static final Scanner scanner = new Scanner(System.in);
    static UserView userView = new UserView();

    public static void launch() {
        boolean isTrue = true;
        do {
            menuUserManager();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        userView.showUser(UserService.getInstance().findAll(), InputOption.SHOW);
                        break;
                    case 2:
                        userView.addUser();
                        break;
                    case 3:
                        userView.updateUser();
                        break;
                    case 4:
                        userView.removeUser();
                        break;
                    case 5:
                        userView.findUser();
                        break;
                    case 6:
                        isTrue = false;
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
    public static void menuUserManager() {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒ Quản lý tài khoản ☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("☒     1. Hiện thị danh sách tài khoản.  ☒");
        System.out.println("☒     2. Thêm tài khoản.                ☒");
        System.out.println("☒     3. Chỉnh sửa tài khoản            ☒");
        System.out.println("☒     4. Xóa tài khoản.                 ☒");
        System.out.println("☒     5. Tìm kiếm tài khoản.            ☒");
        System.out.println("☒     6. Quay lại                       ☒");
        System.out.println("☒     0. Thoát.                         ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }
}
