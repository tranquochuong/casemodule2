package service;

import model.OrderItem;

import java.util.List;

public interface IOrderItemService {

    List<OrderItem> findAll();

    void add(OrderItem orderItem);

    void update(OrderItem newOrderItem);

    void removeById(long id);

    OrderItem findById(long id);

    List<OrderItem> findByOrderId(long orderId);

    boolean existById(long id);
}