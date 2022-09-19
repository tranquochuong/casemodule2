package view;

import service.OrderService;
import java.util.Scanner;

public class OrderMenuView {
    private static final Scanner scanner = new Scanner(System.in);
    static OrderView orderView = new OrderView();


    public OrderMenuView() {
    }
    public static void launch(long userId) {
        int option;
        boolean isTrue = true;
        do {
            menuOrder();
            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        orderView.showOrder(OrderService.getInstance().findAll(), InputOption.SHOW);
                        break;
                    case 2:
                        orderView.addOrder(userId);
                        break;
                    case 3:
                        orderView.updateOrder(userId);
                        break;
                    case 4:
                        orderView.deleteOrder();
                    case 5:
                        orderView.findOrder(userId);
                        break;
                    case 6:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" ➱ ");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
    }

    public static void menuOrder() {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒ MENU ORDER ☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("☒       1. Hiện danh sách đơn hàng.     ☒");
        System.out.println("☒       2. Thêm đơn hàng.               ☒");
        System.out.println("☒       3. Chỉnh sửa đơn hàng.          ☒");
        System.out.println("☒       4. Xóa đơn hàng.                ☒");
        System.out.println("☒       5. Tìm theo Id                  ☒");
        System.out.println("☒       6. Quay lại                     ☒");
        System.out.println("☒       0. Thoát.                       ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

}