package service;

import model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();

    void add(Order newOrder);

    void update(Order newOrder);

    void delete(long id);

    Order findById(long id);

    List<Order> findByUserId(long id);

    List<Order> findByFullName(String value);

    List<Order> findByFullName(String value, long userId);

    List<Order> findByPhone(String value);

    List<Order> findByPhone(String value, long userId);

    List<Order> findByAddress(String value);

    List<Order> findByAddress(String value, long userId);;

    boolean existById(long id);

    List<Order> findOrderByUserId(long userId);
}