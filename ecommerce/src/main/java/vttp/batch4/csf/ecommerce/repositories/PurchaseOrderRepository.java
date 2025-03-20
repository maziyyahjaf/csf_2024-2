package vttp.batch4.csf.ecommerce.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.ecommerce.exceptions.ItemNotSavedException;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderRepository.class);

  public static final String ADD_ORDER_TO_DB = 
      """
        INSERT INTO orders(order_id, order_date, customer_name, ship_address, priority, comments)
        VALUES (?, ?, ?, ?, ?, ?)
      
      """;
  
  public static final String ADD_ITEMS_TO_DB = 
      """
        INSERT INTO line_items(product_id, product_name, quantity, price, order_id)
        VALUES (?, ?, ?, ?, ?)
      """;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // You may only add Exception to the method's signature
  public void create(Order order) {
    // TODO Task 3

    try {
      template.update(ADD_ORDER_TO_DB, 
                        order.getOrderId(), 
                        order.getDate(), 
                        order.getName(),
                        order.getAddress(),
                        order.getPriority(),
                        order.getComments());

      addItemsToDB(order.getCart(), order.getOrderId());

    } catch (DataAccessException ex) {
      logger.error("SQL Error: {} - {}", ex.getMessage(), ex.getCause());
      throw new ItemNotSavedException(String.format("Error occured when saving order with order id %s", order.getOrderId()));
      
    }

  }

  public void addItemsToDB(Cart cartItems, String orderId) {
    List<LineItem> lineItems = cartItems.getLineItems();
    for (LineItem item : lineItems) {
      try {
        template.update(ADD_ITEMS_TO_DB,
                        item.getProductId(),
                        item.getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        orderId);           

      } catch (DataAccessException ex) {
        logger.error("SQL Error: {} - {}", ex.getMessage(), ex.getCause());
        throw new ItemNotSavedException(String.format("Error saving item (name: %s product id: %s) in Order Id %s into the database.", item.getName(), item.getProductId(), orderId));
      }
    }
  }
}
