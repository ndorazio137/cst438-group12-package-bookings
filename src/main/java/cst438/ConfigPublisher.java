package cst438;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

@Configuration
public class ConfigPublisher {
   
   @Bean
   Queue carReservationQueue() {
      return new Queue("carReservationQueue", false);
   }

   @Bean
   Queue hotelReservationQueue() {
      return new Queue("hotelReservationQueue", false);
   }

   @Bean
   Queue flightReservationQueue() {
      return new Queue("flightReservationQueue", false);
   }

   @Bean
   public FanoutExchange fanout() {
      return new FanoutExchange("travelSite3");
   }
   
   @Bean
   Binding marketingBinding(Queue carReservationQueue, FanoutExchange exchange) {
      return BindingBuilder.bind(carReservationQueue).to(exchange);
   }

   @Bean
   Binding financeBinding(Queue hotelReservationQueue, FanoutExchange exchange) {
      return BindingBuilder.bind(hotelReservationQueue).to(exchange);
   }

   @Bean
   Binding adminBinding(Queue flightReservationQueue, FanoutExchange exchange) {
      return BindingBuilder.bind(flightReservationQueue).to(exchange);
   }
}