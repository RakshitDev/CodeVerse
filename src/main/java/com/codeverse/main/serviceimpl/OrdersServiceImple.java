package com.codeverse.main.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeverse.main.entities.Orders;
import com.codeverse.main.repository.OrdersRepository;
import com.codeverse.main.service.OrdersService;
@Service
public class OrdersServiceImple implements OrdersService {
	@Autowired
	OrdersRepository ordersRepository;

	@Override
	public void storeOderDetails(Orders orders) {
		ordersRepository.save(orders);
		
	}

}
