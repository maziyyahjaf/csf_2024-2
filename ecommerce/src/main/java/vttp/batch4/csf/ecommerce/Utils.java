package vttp.batch4.csf.ecommerce;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.models.Product;

public class Utils {

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  public static Product toProduct(Document doc) {
    Product product = new Product();
    product.setId(doc.getObjectId("_id").toHexString());
    product.setName(doc.getString("ProductName"));
    product.setBrand(doc.getString("Brand"));
    product.setPrice(doc.getDouble("Price").floatValue());
    product.setDiscountPrice(doc.getDouble("DiscountPrice").floatValue());
    product.setImage(doc.getString("Image_Url"));
    product.setQuantity(doc.getString("Quantity"));
    return product;
  }

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  public static JsonObject toJson(Product product) {
    return Json.createObjectBuilder()
      .add("prodId", product.getId())
      .add("name", product.getName())
      .add("brand", product.getBrand())
      .add("price", product.getPrice())
      .add("discountPrice", product.getDiscountPrice())
      .add("image", product.getImage())
      .add("quantity", product.getQuantity())
      .build();
  }

  public static Order toOrder(JsonObject jsonObject) {
    Order order = new Order();
    String name = jsonObject.getString("name");
    String address = jsonObject.getString("address");
    boolean priority = jsonObject.getBoolean("priority");
    String comments = jsonObject.getString("comments");

    // Retrieve the existing cart from the Order instance
    Cart cart = order.getCart();
    List<LineItem> lineItems = cart.getLineItems();

    JsonObject cartjson = jsonObject.getJsonObject("cart");
    JsonArray lineItemsArray = cartjson.getJsonArray("lineItems");
    for (JsonObject lineItem : lineItemsArray.getValuesAs(JsonObject.class)) {
      LineItem item = toLineItem(lineItem);
      lineItems.add(item);
    }

    cart.setLineItems(lineItems);
    order.setName(name);
    order.setAddress(address);
    order.setPriority(priority);
    order.setComments(comments);
    order.setCart(cart);

    return order;
    
  }

  public static LineItem toLineItem(JsonObject jsonObject) {
    LineItem lineItem = new LineItem();

    String productId = jsonObject.getString("prodId");
    String name = jsonObject.getString("name");
    int quantity = jsonObject.getJsonNumber("quantity").intValue();
    float price = jsonObject.getJsonNumber("price").bigDecimalValue().floatValue();

    lineItem.setProductId(productId);
    lineItem.setName(name);
    lineItem.setQuantity(quantity);
    lineItem.setPrice(price);

    return lineItem;
  }
}
