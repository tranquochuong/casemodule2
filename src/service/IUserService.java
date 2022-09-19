package service;

import model.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User login(String userName, String passWord);

    void add(User newUser);

    void update(User newUser);

    void removeById(long id);

    User findById(long id);

    boolean existsByUserName(String userName);

    boolean existById(long id);

    List<User> findByUserName(String value);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    String findNameById(long id);
}