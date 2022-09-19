package view;

import model.User;
import service.IUserService;
import service.UserService;
import utils.AppUtils;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private final IUserService userService;
    private static final Scanner scanner = new Scanner(System.in);

    public UserView() {
        userService = UserService.getInstance();
    }


    public void addUser() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String username = inputUserName();
                String password = inputPassWord();
                String fullName = inputFullName(InputOption.ADD);
                String email = inputEmail(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                User user = new User(id, username, password, fullName, email, phone, address);
                userService.add(user);
                System.out.println("✅ Thêm tài khoản thành công!");

                AppUtils.pressEnterToContinue();
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    public void updateUser() {
        int option;
        boolean isTrue = true;
        do {
            try {
                showUser(UserService.getInstance().findAll(), InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);
                User newUser = userService.findById(id);
                menuUpdateUser();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        updateFullName(newUser);
                        break;
                    case 2:
                        updateEmail(newUser);
                        break;
                    case 3:
                        updatePhone(newUser);
                        break;
                    case 4:
                        updateAddress(newUser);
                        break;
                    case 5:
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
                isTrue = option != 5 && AppUtils.isRetry(InputOption.UPDATE);
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
    }

    public void removeUser() {
        boolean isRetry = true;
        do {
            showUser(UserService.getInstance().findAll(), InputOption.DELETE);
            long id = inputId(InputOption.DELETE);
            User user = userService.findById(id);
            int option;
            boolean isTrue = true;
            do {
                try {
                    menuDeleteUser();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            System.out.println("✅  Bạn đã xóa tài khoản thành công!");
                            userService.removeById(id);
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
                            System.out.println("❌ Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
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

    public void findUser() {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuFindUser();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        findById();
                        break;
                    case 2:
                        findByUserName();
                        break;
                    case 3:
                        isTrue = false;
                        break;
                    case 0:
                        System.exit(0);
                        System.out.println(" ❤️ Cám ơn bạn đã ghé thăm! Hẹn gặp lại bạn làn sau!  ❤️");
                        break;
                    default:
                        System.out.println("❌ Lựa chọn của bạn không đúng. Vui lòng nhập lại!");
                        System.out.print(" ➱ ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue == AppUtils.isRetry(InputOption.FIND));
    }

    private void findById() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.print("Nhập Id người dùng cần tìm: ");
        long id = Long.parseLong(scanner.nextLine());
        User user = userService.findById(id);
        if (user != null) {
            List<User> usersFind = new ArrayList<>();
            usersFind.add(user);
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("❌ Chúng tôi không thấy tài khoản của bạn! Vui lòng nhập lại");
            System.out.print(" ➱ ");
        }
    }

    private void findByUserName() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.print("Nhập tên tài khoản cần tìm: ");
        String username = scanner.nextLine();
        List<User> usersFind = userService.findByUserName(username);
        if (usersFind != null) {
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("❌ Chúng tôi không thấy tài khoản của bạn! Vui lòng nhập lại");
            System.out.print(" ➱ ");
        }
    }

    public void updateFullName(User newUser) {
        String name = inputFullName(InputOption.UPDATE);
        newUser.setFullName(name);
        userService.update(newUser);
        System.out.print("✅ Đã thay đổi tên từ thành công");
        AppUtils.pressEnterToContinue();
    }

    public void updateEmail(User newUser) {
        String email = inputEmail(InputOption.UPDATE);
        newUser.setEmail(email);
        userService.update(newUser);
        System.out.print(" ✅ Đã đổi email từ  thành công");
        AppUtils.pressEnterToContinue();
    }

    public void updatePhone(User newUser) {
        String phone = inputPhone(InputOption.UPDATE);
        newUser.setPhone(phone);
        userService.update(newUser);
        System.out.print("✅ Đã đổi số điện thoại thành công!");
        AppUtils.pressEnterToContinue();
    }

    public void updateAddress(User newUser) {
        String address = inputAddress(InputOption.UPDATE);
        newUser.setAddress(address);
        userService.update(newUser);
        System.out.print("✅ Đã đổi địa chỉ thành công");
        AppUtils.pressEnterToContinue();
    }


    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case UPDATE:
                System.out.println("Nhập Id tài khoản muốn chỉnh sửa: ");
                System.out.print(" ➱ ");
                break;
            case DELETE:
                System.out.println("Nhập Id tài khoản muốn chỉnh xóa: ");
                System.out.print(" ➱ ");
                break;
        }
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = userService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("❌ Chúng tôi không thấy tài khoản của bạn ! Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
        return id;
    }

    private String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số điện thoại của bạn theo đúng định dạng sau: =>{ Số điện thoại bao gồm  10 chữ số, số đầu tiên là (0 hoăc +84) số thứ 2 là 1 trong các số sau (3,7,8,9)} , Ví Du: 0 323 456 789 hoặc +84 956 943 000)");
                break;
            case UPDATE:
                System.out.println("Nhập số điện thoại của bạn theo đúng định dạng sau: =>{ Số điện thoại bao gồm  10 chữ số, số đầu tiên là (0 hoăc +84) số thứ 2 là 1 trong các số sau (3,7,8,9)} , Ví Du: 0 323 456 789 hoặc +84 956 943 000)");
                System.out.println("Nhập số điện thoại bạn muốn thay đổi: ");
                System.out.print(" ➱ ");
                break;
        }
        System.out.print(" ➱ ");
        String phone;
        do {
            if (!ValidateUtils.isPhoneValid(phone = scanner.nextLine())) {
                System.out.println("Nhập số điện thoại của bạn theo đúng định dạng sau: =>{ Số điện thoại bao gồm 10 chữ số, số đầu tiên là (0 hoăc +84) số thứ 2 là 1 trong các số sau (3,7,8,9)} , Ví Du: 0 323 456 789 hoặc +84 956 943 000)");
                System.out.print(" ➱ ");
                continue;
            }
            if (userService.existsByPhone(phone)) {
                System.out.println("Số điện thoại " + phone + "đã tồn tại. Vui lòng nhập lại !");
                System.out.print(" ➱ ");
                continue;
            }
            break;
        } while (true);
        return phone;
    }

    private String inputEmail(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập email của bạn (VD: vana123@gmail.com)");
                break;
            case UPDATE:
                System.out.println("Nhập email mới: ");
                System.out.print(" ➱ ");
                break;
        }
        System.out.print(" ➱ ");
        String email;
        do {
            if (!ValidateUtils.isEmailValid(email = scanner.nextLine())) {
                System.out.println("❌ Email không đúng định dạng, vui lòng nhập lại");
                System.out.print(" ➱ ");
                continue;
            }
            if (userService.existsByEmail(email)) {
                System.out.println("❌ Email " + email + " đã tồn tại. Vui lòng nhập lại");
                System.out.print(" ➱ ");
                continue;
            }
            break;
        } while (true);
        return email;
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập địa chỉ (Ký tự đầu của từng từ phải viết hoa, VD: Huế)");
                System.out.print(" ➱ ");
                break;
            case UPDATE:
                System.out.println("Nhập địa chỉ mới: ");
                System.out.print(" ➱ ");
                break;
        }
        System.out.print(" ➱ ");
        String address;
        while (!ValidateUtils.isAddressValid(address = scanner.nextLine())) {
            System.out.println("❌ Địa chỉ không đúng định dạng, vui lòng nhập lại!");
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
                System.out.println("Nhập họ và tên mà bạn muốn đổi: ");
                System.out.print(" ➱ ");
                break;
        }
        String fullName;
        while (!ValidateUtils.isFullNameValid(fullName = scanner.nextLine())) {
            System.out.println("Nhập tên người dùng (Ghi hoa chữ cái đầu, VD: Trần Văn A)");
            System.out.print("➱ ");
        }
        return fullName;
    }

    public String inputPassWord() {
        System.out.println("Nhập mật khẩu (mật khẩu phải >= 8 kí tự, Ví Dụ: Hvanfsdfw1.)");
        System.out.print(" ➱ ");
        String password;
        while (!ValidateUtils.isPassWordValid(password = scanner.nextLine())) {
            System.out.println("Mật khẩu phải lớn hơn hoặc bằng 8 kí tự trong đó có ít nhất 1 ký tự viết hoa, viết thường, chữ số và kí tự đặt biệt như(!@#$%^&*_) Ví Dụ: Hvanfsdfw1.");
            System.out.print(" ➱ ");
        }
        return password;
    }
    private String inputUserName() {
        System.out.println("Nhập tài khoản (Có từ 6-32 kí tự bao gồm chữ hoặc số,có thể sử dụng 2 kí tự đặt biệt(_ .). VD: nguenvan_a hoặc nguyenvana hoặc nguyen1243.");
        System.out.print(" ➱ ");
        String username;
        do {
            if (!ValidateUtils.isUsernameValid(username = AppUtils.retryString())) {
                System.out.println("❌ " + username + " của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại!");
                continue;
            }
            if (userService.existsByUserName(username)) {
                System.out.println("❌" + username + " đã tồn tại. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
                continue;
            }
            break;
        } while (true);
        return username;
    }
    public String findNameById(long id) {
        return userService.findNameById(id);
    }
    public void showUser(List<User> users, InputOption option) {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒---Danh sách Người Dùng---☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-5s%-12s | %-5s%-15s | %-6s%-15s | %-16s%-28s | %-6s%-10s | %-2s%-22s | %-4s%-18s | %-2s%-20s |\n",
                "", "Id",
                "", "Tên tài khoản",
                "", "Tên người dùng",
                "", "Email",
                "", "Số điện thoại",
                "", "Địa chỉ",
                "", "Ngày tạo",
                "", "Ngày cập nhật"
        );
        System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        for (User user : users) {
            System.out.printf("| %-5s%-12s | %-5s%-15s | %-6s%-15s | %-16s%-28s | %-6s%-13s | %-2s%-22s | %-4s%-18s | %-2s%-20s |\n",
                    "", user.getId(),
                    "", user.getUserName(),
                    "", user.getFullName(),
                    "", user.getEmail(),
                    "", user.getPhone(),
                    "", user.getAddress(),
                    "", InstantUtils.instantToString(user.getCreatedAt()),
                    "", user.getUpdatedAt() == null ? "" : InstantUtils.instantToString(user.getUpdatedAt())
            );
        }
        System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        if (option != InputOption.UPDATE && option != InputOption.DELETE && option != InputOption.FIND) {
            AppUtils.pressEnterToContinue();
        }
    }

    private static void menuFindUser() {
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒ Tìm Tài Khoản ☒☒☒☒☒☒☒☒☒☒");
        System.out.println("☒    1. Tìm kiếm theo Id.           ☒");
        System.out.println("☒    2. Tìm kiếm theo tên.          ☒");
        System.out.println("☒    3. Quay lại                    ☒");
        System.out.println("☒    0. Thoát.                      ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    private static void menuUpdateUser() {
        System.out.println("☒☒☒☒☒--Thay đổi thông tin tài khoản--☒☒☒☒☒");
        System.out.println("☒    1. Thay đổi tên.                    ☒");
        System.out.println("☒    2. Thay đổi email.                  ☒");
        System.out.println("☒    3. Thay đổi số điện thoại.          ☒");
        System.out.println("☒    4. Thay đổi địa chỉ.                ☒");
        System.out.println("☒    5. Quay lại                         ☒");
        System.out.println("☒    0. Thoát.                           ☒");
        System.out.println("☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒☒");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

    private static void menuDeleteUser() {
        System.out.println("----------------Xóa tài khoản ---------------");
        System.out.println("        1. Có.   2. Không.     0. Thoát.    ");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" ➱ ");
    }

}