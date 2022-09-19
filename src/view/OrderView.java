package view;

import model.Order;
import model.OrderItem;
import model.User;
import service.*;
import utils.AppUtils;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private final IOrderService orderService;
    private final IOrderItemService orderItemService;
    private final IUserService userService;

    private static final Scanner scanner = new Scanner(System.in);
    UserView userView = new UserView();
    ProductView productView = new ProductView();
    OrderItemView orderItemView = new OrderItemView();

    public OrderView() {
        orderService = OrderService.getInstance();
        orderItemService = OrderItemService.getInstance();
        userService = UserService.getInstance();
    }

    public void showOrder(List<Order> orders, InputOption option) {

        System.out.println("|============================================================= Danh Sách Bán Hàng ==============================================================================|");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-5s%-9s | %-8s%-18s | %-6s%-10s | %-4s%-12s | %-7s%-15s | %-4s%-18s | %-2s%-18s |\n",
                "", "Id",
                "", "Tên khách hàng",
                "", "Số điện thoại",
                "", "Địa chỉ",
                "", "Tổng tiền",
                "", "Ngày tạo",
                "", "Ngày cập nhật"
        );
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        for (Order order : orders) {
            System.out.printf("| %-2s%-12s | %-3s%-23s | %-3s%-16s | %-2s%-14s | %-4s%-18s | %-2s%-20s | %-2s%-18s |\n",
                    "", order.getId(),
                    "", order.getFullName(),
                    "", order.getPhone(),
                    "", order.getAddress(),
                    "", AppUtils.doubleToVND(order.getGrandTotal()),
                    "", InstantUtils.instantToString(order.getCreatedAt()),
                    "", order.getUpdatedAt() == null ? "" : InstantUtils.instantToString(order.getUpdatedAt())
            );
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        if (option == InputOption.SHOW) {
            showAllItemOfOrder();
        }
    }

    public void showAllItemOfOrder() {
        boolean isTrue = true;
        long orderId;
        do {
            System.out.println("Chọn 'e' để sửa \\t|\\t 'r' để trở lại.");
            System.out.print(" ➱ ");
            String option = scanner.nextLine();
            switch (option) {
                case "e":
                    orderItemView.updateOrderItem();
                    break;
                case "r":
                    isTrue = false;
                    break;
                default:
                    System.out.println(" ❌ Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                    break;
            }
        } while (isTrue);
    }

    public void addOrder(long userId) {
        long orderId = 0;
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String fullName = inputFullName(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                Order order = new Order(id, fullName, phone, address);
                order.setUserId(userId);
                orderService.add(order);
                System.out.println("Đơn hàng đã được tạo thành công!");
                AppUtils.pressEnterToContinue();
                orderItemView.addOrderItem(order.getId());
                orderId = id;
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isRetryOrder(orderId));
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập địa chỉ ( Ví Dụ: Huế)");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập địa chỉ mà bạn muốn thay đổi: ");
                System.out.print(" ➱ ");
                break;
        }
        String address;
        while (!ValidateUtils.isAddressValid(address = scanner.nextLine())) {
            System.out.println("Địa chỉ không đúng định dạng, Vui lòng nhập lại!");
            System.out.print(" ➱ ");
        }
        return address;
    }

    private String inputFullName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập tên người dùng (Ghi hoa chữ cái đầu, VD: Trần Văn A)");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập tên mà bạn muốn thay đổi: ");
                System.out.print(" ➱ ");
                break;
        }
        String fullName;
        while (!ValidateUtils.isFullNameValid(fullName = scanner.nextLine())) {
            System.out.println("Nhập họ và tên (Ghi hoa chữ cái đầu, VD: Trần Văn A)");
            System.out.print(" ➱ ");
        }
        return fullName;
    }

    private String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số điện thoại của bạn theo đúng định dạng sau: =>{ Số điện thoại bao gồm 10 chữ số, số đầu tiên là (0 hoăc +84) số thứ 2 là 1 trong các số sau (3,7,8,9)} , Ví Du: 0 323 456 789 hoặc +84 956 943 000)");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập số điện thoại muốn thay đổi: ");
                System.out.print(" ➱ ");
                break;
        }
        String phone;
        while (!ValidateUtils.isPhoneValid(phone = scanner.nextLine())) {
            System.out.println("Nhập số điện thoại của bạn theo đúng định dạng sau: =>{ Số điện thoại bao gồm 10 chữ số, số đầu tiên là (0 hoăc +84) số thứ 2 là 1 trong các số sau (3,7,8,9)} , Ví Du: 0 323 456 789 hoặc +84 956 943 000)");
            System.out.print(" ➱ ");
        }
        return phone;
    }

    public void updateOrder(long userId) {
        User user = userService.findById(userId);
        int option;
        boolean isTrue = true;
        do {
            try {
                showOrder(orderService.findAll(), InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);
                Order order = orderService.findById(id);
                menuUpdateOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        updateFullName(order);
                        break;
                    case 2:
                        updatePhone(order);
                        break;
                    case 3:
                        updateAddress(order);
                        break;
                    case 4:
                        updateOrderItem(order);
                        break;
                    case 5:
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
                isTrue = option != 6 && AppUtils.isRetry(InputOption.UPDATE);

            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);

    }

    private void updateAddress(Order order) {
        String address = inputAddress(InputOption.UPDATE);
        order.setAddress(address);
        orderService.update(order);
        System.out.println("Cập nhật địa chỉ mới thành công!");
        AppUtils.pressEnterToContinue();
    }

    private void updateOrderItem(Order order) {
        boolean isTrue = true;
        String option;
        do {
            try {
                if (orderItemService.findByOrderId(order.getId()) != null) {
                    orderItemView.showOrderItem(orderItemService.findByOrderId(order.getId()), InputOption.UPDATE);
                    System.out.println(" Chọn 'c' để tiếp tục \t|\t  'e' để sửa sản phẩm \t|\t 'd' để xóa ");
                    System.out.print(" ➱ ");
                    option = scanner.nextLine();
                    switch (option) {
                        case "c":
                            orderItemView.addOrderItem(order.getId());
                            isTrue = false;
                            break;
                        case "e":
                            orderItemView.updateOrderItem();
                            isTrue = false;
                            break;
                        case "d":
                            orderItemView.removeOrderItem();
                            isTrue = false;
                            break;
                        default:
                            System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                            break;
                    }
                } else {
                    System.out.println("Chưa có sản phẩm bào được thêm!");
                    System.out.println(" Chọn 'y' để thêm sản phẩm \t|\t 'q' để quay lại");
                    System.out.print(" ➱ ");
                    option = scanner.nextLine();
                    switch (option) {
                        case "y":
                            orderItemView.addOrderItem(order.getId());
                            break;
                        case "q":
                            isTrue = false;
                            break;
                        default:
                            System.out.println(" ❌Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                            System.out.print(" ➱ ");
                            break;
                    }
                    isTrue = false;
                }
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
    }
    private void updatePhone(Order order) {
        String phone = inputPhone(InputOption.UPDATE);
        order.setPhone(phone);
        orderService.update(order);
        System.out.println("Cập nhật số điện thoại thành công!");
        AppUtils.pressEnterToContinue();
    }

    private void updateFullName(Order order) {
        String fullName = inputFullName(InputOption.UPDATE);
        order.setFullName(fullName);
        orderService.update(order);
        System.out.println("Cập nhật tên khách hàng thành công!");
        AppUtils.pressEnterToContinue();
    }

    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case SHOW:
                System.out.println("Nhập Id đơn hàng: ");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập Id đơn hàng bạn muốn chỉnh sửa: ");
                System.out.print(" ➱ ");
                break;
            case DELETE:
                System.out.println("Nhập Id đơn hàng bạn muốn xóa: ");
                System.out.print(" ➱ ");
                break;
        }
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = orderService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("❌ Chúng tôi không thấy tài khoản của bạn ! Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
        return id;
    }

    public void deleteOrder() {
        boolean isRetry = true;
        do {
            showOrder(orderService.findAll(), InputOption.DELETE);
            long id = inputId(InputOption.DELETE);
            Order order =orderService.findById(id);
            int option;
            boolean isTrue = true;
            do {
                try {
                    RemoveOrder();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            orderService.delete(id);
                            System.out.println("Bạn đã xóa hóa đơn thành công!");
                            AppUtils.pressEnterToContinue();
                            isTrue = false;
                            break;
                        case 2:
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
        } while (isRetry == AppUtils.isRetry(InputOption.DELETE));
    }

    public void findOrder(long userId) {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuFindOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        findByOrderId(userId);
                        break;
                    case 2:
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
        } while (isTrue == AppUtils.isRetry(InputOption.FIND));
    }
    private void findByOrderId(long userId) {
        User user = userService.findById(userId);
        showOrder(orderService.findAll(), InputOption.FIND);
        System.out.print("Nhập id hóa đơn cần tìm: ");
        System.out.print(" ➱ ");
        long value = Long.parseLong(scanner.nextLine());
        Order order = orderService.findById(value);
        if (order != null) {
            List<Order> ordersFind = new ArrayList<>();
            ordersFind.add(order);
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("❌ Chúng tôi không thấy tài khoản của bạn ! Vui lòng nhập lại!");
            System.out.print(" ➱ ");
        }
    }

    private static void menuFindOrder() {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒ TÌM KIẾM ☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("░                                     ░");
        System.out.println("░   1. Tìm kiếm theo Id.              ░");
        System.out.println("░   2. Trở lại.                       ░");
        System.out.println("░   0. Thoát.                         ░");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    private static void RemoveOrder() {

        System.out.println("-----------------------Xóa đơn hàng------------------");
        System.out.println("            1. Có.    2. Không.     0. Thoát.      ");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    private void menuUpdateOrder() {
        System.out.println("☒☒☒☒☒☒☒ CHỈNH SỬA ĐƠN HÀNG ☒☒☒☒☒☒☒☒☒");
        System.out.println("☒    1. Chỉnh sửa tên khách hàng.  ☒");
        System.out.println("☒    2. Chỉnh sửa số điện thoại.   ☒");
        System.out.println("☒    3. Chỉnh sửa địa chỉ.         ☒");
        System.out.println("☒    4. Trở lại.                   ☒");
        System.out.println("☒    0. Thoát.                     ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    public boolean isRetryOrder(long orderId) {
        do {
            System.out.println(" Chọn 'c': tiếp tục   \t|\t 'q': hủy và quay lại \t|\t'e': thoát.");
            System.out.print(" ➱ ");
            String option = scanner.nextLine();
            switch (option) {
                case "c":
                    return true;
                case "q":
                    retunrAllthings(orderId);
                    return false;
                case "e":
                    retunrAllthings(orderId);
                    System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                    System.exit(0);
                    break;
                default:
                    System.out.println(" ❌Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                    break;
            }
        } while (true);
    }

    public void retunrAllthings(long orderId) {
        try {
            List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
            for (OrderItem orderItem : orderItems) {
                orderItemView.removeOrderByItem(orderItem.getId());
            }
            orderService.delete(orderId);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<Order> findAllByUserId(long userId) {
        return orderService.findOrderByUserId(userId);
    }
}