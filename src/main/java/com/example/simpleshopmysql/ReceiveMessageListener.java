package com.example.simpleshopmysql;

import com.example.simpleshopmysql.models.Product;
import com.example.simpleshopmysql.models.SaleOrder;
import com.example.simpleshopmysql.services.OrderService;
import com.example.simpleshopmysql.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessageListener {

	@Autowired
	OrderService orderService;

	/**
	 * 監聽Queue中是否有資料，若有資料則進行消費。
	 * @param order
	 */
	@RabbitListener(queues={"put_order"})
	public SaleOrder receive_put_order(SaleOrder order) {
		try {
			SaleOrder newOrder = orderService.createOrders(order);
			return newOrder;
		} catch (Exception e) {
			SaleOrder newOrder = new SaleOrder();
			newOrder.setState("null");
			return newOrder;
		}
	}

}