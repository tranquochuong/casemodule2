package view;

import model.Order;
import model.OrderItem;
import model.Product;
import service.*;
import utils.AppUtils;
import utils.InstantUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderItemView {
    private final IOrderItemService orderItemService;
    private final IProductService productService;
    private final IOrderService orderService;
    private static final Scanner scanner = new Scanner(System.in);
    ProductView productView = new ProductView();


    public OrderItemView() {
        orderItemService = OrderItemService.getInstance();
        productService = ProductService.getInstance();
        orderService = OrderService.getInstance();
    }

    public void showOrderItem(List<OrderItem> orderItems, InputOption option) {
        System.out.println("|---------------------------------------------Hàng Hóa---------------------------------------------------------|");
        System.out.println("|--------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-5s%-9s | %-11s%-19s | %-7s%-11s | %-2s%-10s | %-5s%-17s |\n",
                "", "OrderId",
                "", "Tên Sản Phẩm",
                "", "Giá",
                "", "Số Lượng",
                "", "Thành tiền"
        );
        System.out.println("|--------------------------------------------------------------------------------------------------------------|");
        for (OrderItem orderItem : orderItems) {
            System.out.printf("| %-2s%-12s | %-2s%-28s | %-2s%-16s | %-4s%-8s | %-2s%-20s  |\n",
                    "", orderItem.getId(),
                    "", productService.findProductById(orderItem.getProductId()).getName(),
                    "", AppUtils.doubleToVND(orderItem.getPrice()),
                    "", orderItem.getQuantity(),
                    "", AppUtils.doubleToVND(orderItem.getPrice() * orderItem.getQuantity())
            );
        }
        System.out.println("|--------------------------------------------------------------------------------------------------------------|");
        if (option != InputOption.UPDATE && option != InputOption.DELETE && option != InputOption.FIND) {
            AppUtils.pressEnterToContinue();
        }
    }

    public void addOrderItem(long orderId) {
        do {
            try {
                productView.showProduct(productService.findAll(), InputOption.UPDATE);
                long id = System.currentTimeMillis() / 1000;
                long productId = inputProductId(InputOption.ADD);
                int quantity = inputQuantity(InputOption.ADD, productId);
                Product product = productService.findProductById(productId);
                double price = product.getPrice();
                OrderItem newOrderItem = new OrderItem(id, price, quantity, productId, orderId);
                List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
                if (orderItems == null) {
                    orderItemService.add(newOrderItem);
                } else {
                    int count = 0;
                    for (OrderItem orderItem : orderItems) {
                        if (orderItem.getProductId() == productId) {
                            int temp = quantity + orderItem.getQuantity();
                            orderItem.setQuantity(temp);
                            orderItemService.update(orderItem);
                            count++;
                        }
                    }
                    if (count == 0) {
                        orderItemService.add(newOrderItem);
                    }
                }
                showOrderItem(orderItemService.findByOrderId(orderId), InputOption.UPDATE);
                setProductQuantity(productId, -orderItemService.findById(id).getQuantity());
                setGrandTotal(orderId);
            } catch (Exception e) {
                System.out.println("Nhập sai cú pháp! Vui lòng nhập lại");
                System.out.println(e.getMessage());
            }
        } while (FuncionOrderItem(orderId));
    }
    private boolean FuncionOrderItem(long orderId) {
        do {
            System.out.println("'a' thanh toán và in hóa đơn \t|\t 'e' để thoát.");
            System.out.print(" ➱ ");
            String option = scanner.nextLine();
            switch (option) {
                case "a":
                    setGrandTotal(orderId);
                    showBill(orderId);
                    break;
                case "t":
                    List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
                    for (OrderItem orderItem : orderItems) {
                        setProductQuantity(orderItem.getProductId(), orderItem.getQuantity());
                    }
                    System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                    break;
            }
        } while (true);
    }
        public void showBill ( long orderId){
            List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
            Order order = orderService.findById(orderId);

            System.out.println(" |---------------------------------------------- Hóa đơn -------------------------------------------|");
            System.out.println(" |                                                                                                  |");
            System.out.printf(" |   Thời gian: %40s                                             |\n", InstantUtils.instantToString(order.getCreatedAt()));
            System.out.printf(" |   Khách hàng: %-40s                                           |\n", order.getFullName());
            System.out.printf(" |   Số điện thoại: %-40s                                        |\n", order.getPhone());
            System.out.printf(" |   Địa chỉ: %-40s                                              |\n", order.getAddress());
            System.out.println(" |                                                                                                  |");
            System.out.println(" |--------------------------------------------------------------------------------------------------|");
            System.out.printf(" |  %-11s%-19s | %-7s%-11s | %-1s%-9s | %-2s%-27s|\n",
                    "", "Tên sản phẩm",
                    "", "Giá",
                    "", "Số lượng",
                    "", "Thành tiền"
            );
            System.out.println(" |--------------------------------------------------------------------------------------------------|");
            for (int i = 0; i < orderItems.size(); i++) {
                OrderItem orderItem = orderItems.get(i);
                System.out.printf("|%-11s%-19s | %-7s%-11s | %-1s%-9s | %-2s%-27s|\n",
                        "", productService.findProductById(orderItem.getProductId()).getName(),
                        "", AppUtils.doubleToVND(orderItem.getPrice()),
                        "", orderItem.getQuantity(),
                        "", AppUtils.doubleToVND(orderItem.getPrice() * orderItem.getQuantity())
                );
            }
            System.out.println("|---------------------------------------------------------------------------------------------------|");
            System.out.println("|                                                                                                   |");
            System.out.printf("|                                       Tổng tiền: %-25s        |\n", AppUtils.doubleToVND(order.getGrandTotal()));
            System.out.println("|                                                                                                   |");
            System.out.println("|---------------------------------------------------------------------------------------------------|");

        }

        private int inputQuantity (InputOption option,long productId){
            Product product = productService.findProductById(productId);
            switch (option) {
                case ADD:
                    System.out.println("Nhập số lượng sản phẩm : ");
                    System.out.print(" ➱ ");
                    break;
                case UPDATE:
                    System.out.println("Nhập số lượng sản phẩm mới: ");
                    System.out.print(" ➱ ");
                    break;
            }
            int quantity;
            do {
                quantity = AppUtils.retryParseInt();
                if (quantity < 0) {
                    System.out.println("Số lượng sản phẩm không thể âm, vui lòng nhập lại!");
                }
                if (quantity > product.getQuantity()) {
                    System.out.printf("Số lượng %s vượt quá %s sản phẩm hiện có! Vui lòng nhập lại!\n", product.getName(), product.getQuantity());
                }

            } while (quantity < 0 || quantity > product.getQuantity());
            return quantity;
        }

        private long inputId (InputOption option){
            long id;
            switch (option) {
                case ADD:
                    System.out.println("Nhập id : ");
                    System.out.print(" ➱ ");
                    break;
                case UPDATE:
                    System.out.println("Nhập id muốn chỉnh sửa: ");
                    System.out.print(" ➱ ");
                    break;
                case DELETE:
                    System.out.println("Nhập Id muốn xóa: ");
                    System.out.print(" ➱ ");
                    break;
            }
            boolean isTrue = true;
            do {
                id = AppUtils.retryParseLong();
                boolean isFindId = orderItemService.existById(id);
                if (isFindId) {
                    isTrue = false;
                } else {
                    System.out.println("Không tìm thấy Id của bạn! Vui lòng nhập lại");
                }
            } while (isTrue);
            return id;
        }

        private long inputProductId (InputOption option){
            switch (option) {
                case ADD:
                    System.out.println("Nhập id sản phẩm: ");
                    System.out.print(" ➱ ");
                    break;
                case UPDATE:
                    System.out.println("Nhập id sản phẩm muốn chỉnh sửa: ");
                    System.out.print(" ➱ ");
                    break;
                case DELETE:
                    System.out.println("Nhập Id sản phẩm muốn xóa: ");
                    System.out.print(" ➱ ");
                    break;
            }
            long id;
            boolean isTrue = true;
            do {
                id = AppUtils.retryParseLong();
                boolean isFindId = productService.existById(id);
                if (isFindId) {
                    isTrue = false;
                } else {
                    System.out.println("Không tìm thấy Id của bạn! Vui lòng nhập lại");
                }
            } while (isTrue);
            return id;
        }

        public void updateOrderItem () {
            int option;
            boolean isTrue = true;
            do {
                try {
                    long id = inputId(InputOption.UPDATE);
                    OrderItem orderItem = orderItemService.findById(id);
                    menuUpdateOrderItem();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            updateProductId(orderItem);
                            isTrue = false;
                            break;
                        case 2:
                            updateQuantity(orderItem);
                            isTrue = false;
                            break;
                        case 3:
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

        private void updateProductId (OrderItem orderItem){
            do {
                try {
                    productView.showProduct(productService.findAll(), InputOption.SHOW);
                    long productId = inputProductId(InputOption.UPDATE);
                    orderItem.setProductId(productId);
                    Product product = productService.findProductById(productId);
                    orderItem.setPrice(product.getPrice());
                    orderItemService.update(orderItem);
                    System.out.println("Chỉnh sửa sản phẩm thành công!");
                    setGrandTotal(orderItem.getOrderId());
                    List<OrderItem> orderItems = new ArrayList<>();
                    orderItems.add(orderItem);
                    showOrderItem(orderItems, InputOption.UPDATE);
                    AppUtils.pressEnterToContinue();
                } catch (Exception e) {
                    System.out.println(" ❌Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                }
            } while (AppUtils.isRetry(InputOption.UPDATE));
        }

        private void updateQuantity (OrderItem orderItem){
            do {
                try {
                    int oldQuantity = orderItem.getQuantity();
                    int newQuantity = inputQuantity(InputOption.UPDATE, orderItem.getProductId());
                    setProductQuantity(orderItem.getProductId(), newQuantity - oldQuantity);
                    orderItem.setQuantity(newQuantity);
                    orderItemService.update(orderItem);
                    setGrandTotal(orderItem.getOrderId());
                    System.out.print("Chỉnh sửa số lượng sản phầm thành công");
                    setGrandTotal(orderItem.getOrderId());
                    List<OrderItem> orderItems = new ArrayList<>();
                    orderItems.add(orderItem);
                    showOrderItem(orderItems, InputOption.UPDATE);
                    AppUtils.pressEnterToContinue();
                } catch (Exception e) {
                    System.out.println(" ❌Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                }
            } while (AppUtils.isRetry(InputOption.UPDATE));
        }

        private static void menuUpdateOrderItem () {
            System.out.println("======= CHỈNH SỬA SẢN PHẨM ========");
            System.out.println("|      1. Đổi sản phẩm.           |");
            System.out.println("|      2. Chỉnh sửa số lượng.     |");
            System.out.println("|      3. Quay lại.               |");
            System.out.println("|      0. Thoát.                  |");
            System.out.println("=---------------------------------=");
            System.out.println("Nhập lựa chọn: ");
            System.out.print(" ➱ ");
        }

        public void removeOrderItem () {
            long id = inputId(InputOption.DELETE);
            int option;
            boolean isTrue = true;
            do {
                try {
                    menuDeleteOrderItem();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1: {
                            OrderItem orderItem = orderItemService.findById(id);
                            setProductQuantity(orderItem.getProductId(), orderItem.getQuantity());
                            orderItemService.removeById(id);
                            System.out.println("Bạn đã xóa sản phẩm thành công!");
                            setGrandTotal(orderItem.getOrderId());
                            isTrue = false;
                            break;
                        }
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
        }

        private static void menuDeleteOrderItem () {
            System.out.println(" Xóa sản phẩm");
            System.out.println("      1. Có.        2. Không.     0. Thoát.   ");
            System.out.println("Nhập lựa chọn: ");
            System.out.print(" ➱ ");
        }

        public void setGrandTotal ( long orderId){
            Order order = orderService.findById(orderId);
            List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
            if (orderItems == null) {
                order.setGrandTotal(0);
            } else {
                double grandTotal = 0;
                for (OrderItem orderItem : orderItems) {
                    grandTotal = grandTotal + orderItem.getTotal();
                }
                order.setGrandTotal(grandTotal);
            }
            orderService.update(order);
        }

        public void removeOrderByItem ( long orderItemId){
            setProductQuantity(orderItemService.findById(orderItemId).getProductId(),
                    orderItemService.findById(orderItemId).getQuantity());
            orderItemService.removeById(orderItemId);
        }

        public void setProductQuantity ( long productId, int quantityDifference){
            Product product = productService.findProductById(productId);
            product.setQuantity(product.getQuantity() + quantityDifference);
            productService.update(product);
        }
    }
