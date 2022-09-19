package service;


import model.OrderItem;
import utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderItemService implements IOrderItemService {
    public final static String PATH_ORDERITEM = "D:\\CodeGym\\CaseStudy_Java\\untitled\\data\\order_items.csv";
    private static OrderItemService instance;

    private OrderItemService() {
    }

    public static OrderItemService getInstance() {
        if (instance == null) instance = new OrderItemService();
        return instance;
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH_ORDERITEM);
        for (String record : records) {
            orderItems.add(OrderItem.parseOrderItem(record));
        }
        return orderItems;
    }

    @Override
    public void add(OrderItem newOrderItem) {
        List<OrderItem> orderItems = findAll();
        orderItems.add(newOrderItem);
        CSVUtils.write(PATH_ORDERITEM, orderItems);
    }

    @Override
    public void update(OrderItem newOrderItem) {
        List<OrderItem> orderItems = findAll();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == newOrderItem.getId()) {
                orderItem.setPrice(newOrderItem.getPrice());
                orderItem.setQuantity(newOrderItem.getQuantity());
                orderItem.setProductId(newOrderItem.getProductId());
                orderItem.setOrderId(newOrderItem.getOrderId());
                CSVUtils.write(PATH_ORDERITEM, orderItems);
                break;
            }
        }
    }
    @Override
    public void removeById(long id) {
        List<OrderItem> orderItems = findAll();
        for (int i = 0; i < orderItems.size(); i++) {
            if ((orderItems.get(i)).getId() == id) {
                orderItems.remove(orderItems.get(i));
            }
        }
        CSVUtils.write(PATH_ORDERITEM, orderItems);
    }
    @Override
    public OrderItem findById(long id) {
        List<OrderItem> orderItems = findAll();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == id) {
                return orderItem;
            }
        }
        return null;
    }
    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        List<OrderItem> orderItems = findAll();
        List<OrderItem> orderItemsFind = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderId() == orderId) {
                orderItemsFind.add(orderItem);
            }
        }
        if (orderItemsFind.isEmpty()) {
            return null;
        }
        return orderItemsFind;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }
}
