package com.codeverse.main.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeverse.main.entities.Orders;
import com.codeverse.main.service.OrdersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class OrdersApi {
	
	@Autowired
	public OrdersService ordersService;
	
	
	@PostMapping("/storeOrderDetails")
	ResponseEntity<String> storeUserOrdersDetails(@RequestBody Orders orders) throws RazorpayException{
		
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_ZhnbQNgZdvF9YS", "pt5cOAWufAJyKTwCrZrbiv55");

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",orders.getCourseAmount()); // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
		orderRequest.put("currency","INR");
		orderRequest.put("receipt", "rcpt_Id_"+System.currentTimeMillis());
		
		
		Order  order = razorpayClient.orders.create(orderRequest);
		System.out.println(order);
		
		String orderId = order.get("id");
		orders.setOrderId(orderId);
		
		ordersService.storeOderDetails(orders);
		return ResponseEntity.ok("Order details Stored Successfully");
	}

}
