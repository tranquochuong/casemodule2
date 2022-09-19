package view;

import model.Product;

import service.IProductService;
import service.ProductService;

import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private final IProductService productService;

    private static final Scanner scanner = new Scanner(System.in);

    public ProductView() {
        productService = ProductService.getInstance();
    }

    public void addProduct() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String productName = inputProductName(InputOption.ADD);
                double price = inputPrice(InputOption.ADD);
                int quantity = inputQuantity(InputOption.ADD);
                Product product = new Product(id, productName, price, quantity);
                productService.add(product);
                System.out.println("✅ Thêm sản phẩm thành công! ✅");
                AppUtils.pressEnterToContinue();
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }


    public void updateProduct() {
        int option;
        boolean isTrue = true;
        do {
            try {
                showProduct(productService.findAll(), InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);
                Product newproduct = productService.findById(id);
                menuUpdateProduct();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        updateName(newproduct);
                        break;
                    case 2:
                        updatePrice(newproduct);
                        break;
                    case 3:
                        updateQuantity(newproduct);
                        break;
                    case 4:
                        break;
                    case 0:
                        System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(" ❌ Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                        System.out.print(" ➱ ");
                        break;
                }
                isTrue = option != 4 && AppUtils.isRetry(InputOption.UPDATE);
            } catch (Exception ex) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
    }

    private void menuUpdateProduct() {
        System.out.println("☒☒☒☒☒☒☒ CHỈNH SỬA SẢN PHẨM ☒☒☒☒☒☒☒");
        System.out.println("☒     1. Chỉnh sửa tên.          ☒");
        System.out.println("☒     2. Chỉnh sửa giá.          ☒");
        System.out.println("☒     3. Chỉnh sửa số lượng.     ☒");
        System.out.println("☒     4. Quay lại.               ☒");
        System.out.println("☒     0. Thoát.                  ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    public void removeProduct() {
        boolean isRetry = true;
        do {
            showProduct(ProductService.getInstance().findAll(), InputOption.DELETE);
            long id = inputId(InputOption.DELETE);
            Product product = productService.findProductById(id);
            int option;
            boolean isTrue = true;
            do {
                try {
                    menuRemoveProduct();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            System.out.println("✅ Ban đã xóa sản phẩm thành công!");
                            productService.removeById(id);
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
                } catch (Exception ex) {
                    System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                    System.out.print(" ➱ ");
                }
            } while (isTrue);
        } while (isRetry == AppUtils.isRetry(InputOption.DELETE));
    }

    private void menuRemoveProduct() {
        System.out.println("---------------Xóa sản phẩm---------- ");
        System.out.println("    1. Có.   2. Không.   0. Thoát.    ");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    public void findProduct() {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuFindProduct();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        findByProductId();
                        break;
                    case 2:
                        findByProductName();
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
            } catch (Exception ex) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
                break;
            }
        } while (isTrue == AppUtils.isRetry(InputOption.FIND));
    }

    private void menuFindProduct() {
        System.out.println("☒☒☒ Chọn loại tìm kiếm sản phẩm ☒☒☒");
        System.out.println("☒                                 ☒");
        System.out.println("☒    1. Tìm sản phẩm theo Id.     ☒");
        System.out.println("☒    2. Tìm sản phẩm theo tên.    ☒");
        System.out.println("☒    3. Quay lại                  ☒");
        System.out.println("☒    0. Thoát.                    ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");

    }

    private void findByProductName() {
        showProduct(productService.findAll(), InputOption.FIND);
        System.out.print("Nhập tên sản phẩm bạn cần tìm: ");
        String productName = scanner.nextLine();
        List<Product> productsFind = productService.findByName(productName);
        if (productsFind != null) {
            showProduct(productsFind, InputOption.FIND);
        } else {
            System.out.println("❌ Chúng tôi không thấy tài khoản của bạn ! Vui lòng nhập lại!");
            System.out.print(" ➱ ");
        }
    }

    private void findByProductId() {
        showProduct(productService.findAll(), InputOption.FIND);
        System.out.print("Nhập Id sản phẩm bạn cần tìm: ");
        long id = Long.parseLong(scanner.nextLine());
        Product product = productService.findById(id);
        if (product != null) {
            List<Product> productsFind = new ArrayList<>();
            productsFind.add(product);
            showProduct(productsFind, InputOption.FIND);
        } else {
            System.out.println("❌ Chúng tôi không thấy tài khoản của bạn ! Vui lòng nhập lại!");
            System.out.print(" ➱ ");
        }
    }

    private void updateQuantity(Product newProduct) {
        int quantity = inputQuantity(InputOption.UPDATE);
        newProduct.setQuantity(quantity);
        productService.update(newProduct);
        System.out.println("✅ Số lượng sản phẩm đã được cập nhật thành công!");
        AppUtils.pressEnterToContinue();
    }

    private void updatePrice(Product newProduct) {
        double price = inputPrice(InputOption.UPDATE);
        newProduct.setPrice(price);
        productService.update(newProduct);
        System.out.println("✅ Giá sản phẩm đã được cập nhật thành công!");
        AppUtils.pressEnterToContinue();
    }

    private void updateName(Product newProduct) {
        String name = inputProductName(InputOption.UPDATE);
        newProduct.setName(name);
        productService.update(newProduct);
        System.out.println("✅ Tên sản phẩm đã được cập nhật thành công!");
        AppUtils.pressEnterToContinue();
    }

    private int inputQuantity(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số lượng sản phẩm bạn cần: ");
                break;
            case UPDATE:
                System.out.println("Nhập số lượng sản phẩm bạn muốn thay đổi: ");
                break;
        }
        int quantity;
        do {
            quantity = AppUtils.retryParseInt();
            if (quantity < 0 || quantity > 1000)
                System.out.println("Số lượng sản phẩm không thể <0 và lớn hơn 1000. Vui lòng nhập lại!");
        } while (quantity < 0 || quantity > 1000);
        return quantity;
    }

    private double inputPrice(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập giá sản phẩm: ");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập giá sản phẩm cần thay đổi: ");
                System.out.print(" ➱ ");
                break;
        }
        double price;
        do {
            price = AppUtils.retryParseDouble();
            if (price < 0 || price > 1000000000)
                System.out.println("Giá sản phẩm không thể < 0 và lớn hơn 1.000.000.000 không. Vui lòng nhập lại!");
            System.out.print(" ➱ ");
        } while (price < 0 || price > 1000000000);
        return price;
    }
    private String inputProductName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập tên sản phẩm");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập tên sản phẩm mới: ");
                System.out.print(" ➱ ");
                break;
        }
        String productName;
        do {
            if (!ValidateUtils.isProductNameValid(productName = scanner.nextLine())) {
                System.out.println("Nhập tên sản phẩm");
                System.out.print(" ➱ ");
                break;
            }
            productName = productName.trim();
            if (productService.existsByName(productName)) {
                System.out.println("Tên sản phẩm này đã tồn tại. Vui lòng nhập lại");
                System.out.print(" ➱ ");
                break;
            }
            break;
        } while (true);
        return productName;
    }
    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case UPDATE:
                System.out.println("Nhập Id sản phẩm bạn muốn sửa: ");
                System.out.print(" ➱ ");
                break;
            case DELETE:
                System.out.println("Nhập Id sản phẩm bạn muốn xóa: ");
                System.out.print(" ➱ ");
                break;
        }
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = productService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("❌ Chúng tôi không thấy tài khoản của bạn ! Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
        return id;
    }

    public void showProduct(List<Product> products, InputOption option) {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒ MENU Product ☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-5s%-9s | %-16s%-16s | %-10s%-14s | %-4s%-14s | %-4s%-18s | %-2s%-20s |\n",
                "", "productId",
                "", "Tên sản phẩm",
                "", "Gía",
                "", "Số lượng",
                "", "Ngày tạo",
                "", "Ngày cập nhật"
        );
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------|");
        for (Product product : products) {
            System.out.printf("| %-2s%-12s | %-4s%-28s | %-4s%-20s | %-5s%-13s | %-2s%-20s | %-2s%-20s |\n",
                    "", product.getId(),
                    "", product.getName(),
                    "", AppUtils.doubleToVND(product.getPrice()),
                    "", product.getQuantity(),
                    "", InstantUtils.instantToString(product.getCreatedAt()),
                    "", product.getUpdatedAt() == null ? "" : InstantUtils.instantToString(product.getUpdatedAt())
            );
        }
        System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------------|");
        if (option != InputOption.UPDATE && option != InputOption.DELETE && option != InputOption.FIND) {
            AppUtils.pressEnterToContinue();
        }
    }

}