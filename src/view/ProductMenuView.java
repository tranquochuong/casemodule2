package view;


import service.ProductService;
import service.UserService;

import java.util.Scanner;
public class ProductMenuView {
    private static final Scanner scanner = new Scanner(System.in);
    static ProductView productView = new ProductView();

        public static void launch() {
            boolean isTrue = true;
            do {
                menuProductManager();
                try {
                    int option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            productView.showProduct(ProductService.getInstance().findAll(), InputOption.SHOW);
                            break;
                        case 2:
                            productView.addProduct();
                            break;
                        case 3:
                            productView.updateProduct();
                            break;
                        case 4:
                            productView.removeProduct();
                            break;
                        case 5:
                            productView.findProduct();
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
    private static void menuProductManager() {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒ Quản lý sản phẩm ☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("☒        1. Hiện danh sách sản phẩm.    ☒");
        System.out.println("☒        2. Thêm sản phẩm.              ☒");
        System.out.println("☒        3. Chỉnh sửa sản phẩm.         ☒");
        System.out.println("☒        4. Xóa sản phẩm.               ☒");
        System.out.println("☒        5. Tìm kiếm sản phẩm.          ☒");
        System.out.println("☒        6. Quay lại.                   ☒");
        System.out.println("☒        0. Thoát.                      ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

}