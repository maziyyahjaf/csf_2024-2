package vttp.batch4.csf.ecommerce.controllers;


import org.springframework.http.MediaType;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch4.csf.ecommerce.Utils;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@Controller
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  @PostMapping(path="/order")
  public ResponseEntity<String> postOrder(@RequestBody String rawJsonString) {
    // TODO Task 3
    JsonReader jsonReader = Json.createReader(new StringReader(rawJsonString));
    JsonObject root = jsonReader.readObject();
    Order order = Utils.toOrder(root);
    poSvc.createNewPurchaseOrder(order);
    // create the response entity
    JsonObject payload = Json.createObjectBuilder()
                            .add("orderId", order.getOrderId())
                            .build();

	 
	 return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(payload.toString());
  }
}
