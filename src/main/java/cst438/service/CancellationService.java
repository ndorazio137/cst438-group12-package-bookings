package cst438.service;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class CancellationService {
 //Dependent on:
   @Autowired
   private CarService carService;
   @Autowired
   private HotelService hotelService;
   @Autowired
   private HotelService flightService;
   
   @Autowired
   private RabbitTemplate rabbitTemplate;
   @Autowired
   private FanoutExchange fanout;
   
   //TODO: finish CancellationService
}
