package view;

import model.User;
import service.IUserService;
import service.UserService;
import utils.AppUtils;

import java.util.Scanner;

public class LoginView {
    Scanner   scanner = new Scanner(System.in);
    private final IUserService userService;
    User user = new User();

    public LoginView() {
        userService = UserService.getInstance();
    }

    public void login() {
        boolean isTrue = true;
        do {
            try {
                System.out.println("                                                        ");
                System.out.println("==[:::::::::::::> Đăng Nhập Hệ Thống <:::::::::::::]==");
                System.out.println("                                                        ");
                System.out.print("Username: ");
                String userName = AppUtils.retryString();
                System.out.println("                                                        ");
                System.out.print("Password: ");
                String passWord = AppUtils.retryString();
                user = userService.login(userName, passWord);
                if (user == null) {
                    System.err.println(" ❌-----Tài khoản hoặc mật khẩu không đúng----------❌");
                        isTrue=true;
                } else {
                    System.out.println(" ❤️-----------Đăng Nhập Thành Công-------   ❤️ ");
                    AdminView.launch(user.getId());
                    isTrue=false;
                }
            } catch (Exception e) {
                System.out.println("❌ Bạn đã nhập sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" ➱ ");
            }
        } while (isTrue);
    }
}