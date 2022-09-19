package service;

import model.User;
import utils.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    public final static String PATH_USER = "D:\\CodeGym\\CaseStudy_Java\\untitled\\data\\users.csv";
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        List<String> lines = CSVUtils.read(PATH_USER);
        for (String line : lines) {
            users.add(User.parseUser(line));
        }
        return users;
    }

    @Override
    public User login(String userName, String passWord) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUserName().equals(userName)
                    && user.getPassWord().equals(passWord)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User newUser) {
        newUser.setCreatedAt(Instant.now());
        List<User> users = findAll();
        users.add(newUser);
        CSVUtils.write(PATH_USER, users);
    }

    @Override
    public void update(User newUser) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId() == newUser.getId()) {
                String passWord = newUser.getPassWord();
                String fullName = newUser.getFullName();
                String email = newUser.getEmail();
                String phone = newUser.getPhone();
                String address = newUser.getAddress();
                user.setPassWord(passWord);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                user.setUpdatedAt(Instant.now());
                CSVUtils.write(PATH_USER, users);
                break;
            }
        }
    }

    @Override
    public void removeById(long id) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i)).getId() == id) {
                users.remove(users.get(i));
            }
        }
        CSVUtils.write(PATH_USER, users);
    }

    @Override
    public boolean existsByUserName(String userName) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUserName().equals(userName))
                return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getEmail().equals(email))
                return true;
        }
        return false;
    }

    @Override
    public boolean existsByPhone(String phone) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getPhone().equals(phone))
                return true;
        }
        return false;
    }

    @Override
    public String findNameById(long id) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId() == id) {
                return user.getFullName();
            }
        }
        return null;
    }

    @Override
    public User findById(long id) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }

    @Override
    public List<User> findByUserName(String value) {
        List<User> users = findAll();
        List<User> usersFind = new ArrayList<>();
        for (User item : users) {
            if ((item.getUserName().toUpperCase()).contains(value.toUpperCase())) {
                usersFind.add(item);
            }
        }
        if (usersFind.isEmpty()) {
            return null;
        }
        return usersFind;
    }

}