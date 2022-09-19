package service;

import model.Order;
import utils.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    public final static String PATH_ORDER = "D:\\CodeGym\\CaseStudy_Java\\untitled\\data\\orders.csv";
    private static OrderService instance;

    private OrderService() {
    }

    public static OrderService getInstance() {
        if (instance == null) instance = new OrderService();
        return instance;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        List<String> lines = CSVUtils.read(PATH_ORDER);
        for (String line : lines) {
            orders.add(Order.parseOrder(line));
        }
        return orders;
    }

    @Override
    public void add(Order newOrder) {
        List<Order> orders = findAll();
        newOrder.setCreatedAt(Instant.now());
        orders.add(newOrder);
        CSVUtils.write(PATH_ORDER, orders);
    }

    @Override
    public void update(Order newOrder) {
        List<Order> orders = findAll();
        for (Order order : orders) {
            if (order.getId() == newOrder.getId()) {
                order.setFullName(newOrder.getFullName());
                order.setPhone(newOrder.getPhone());
                order.setAddress(newOrder.getAddress());
                order.setGrandTotal(newOrder.getGrandTotal());
                order.setUserId(newOrder.getUserId());
                order.setUpdatedAt(Instant.now());
                CSVUtils.write(PATH_ORDER, orders);
                break;
            }
        }
    }

    @Override
    public void delete(long id) {
        List<Order> orders = findAll();
        for (int i = 0; i < orders.size(); i++) {
            if ((orders.get(i)).getId() == id) {
                orders.remove(orders.get(i));
            }
        }
        CSVUtils.write(PATH_ORDER, orders);
    }

    @Override
    public Order findById(long id) {
        List<Order> orders = findAll();
        for (Order order : orders) {
            if (order.getId() == id)
                return order;
        }
        return null;
    }

    @Override
    public List<Order> findByAddress(String value, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getAddress().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByPhone(String value, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getPhone().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByAddress(String value) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getAddress().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByFullName(String value, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getFullName().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByPhone(String value) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getPhone().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByUserId(long userId) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                ordersFind.add(order);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByFullName(String value) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getFullName().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }

    @Override
    public List<Order> findOrderByUserId(long userId) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                ordersFind.add(order);
            }
        }
        return ordersFind;
    }
}