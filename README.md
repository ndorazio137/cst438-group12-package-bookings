# cst438-group12-package-bookings

Production site: https://cst438-package-bookings.herokuapp.com/
Developer Demo: https://cst438-package-bookings.herokuapp.com/demo

Test login credentials:

   ---Username/email: koakes@csumb.edu
   
   ---Password: password1
   
   
Test Cities:

   ---Los Angeles
   
   ---New York
   
   
Note, travel group 3's services only work with dates in the following range: 

   ---2021/07/01
   
   ---2021/07/07
   
   
=========== Database design ==========
MySQL relational database

2 Relations: users, reservations

  "users"
  
     Column Name | Type
     
     ------------+---------------
     
     user_id     | int AI PK
     
     username    | varchar(254)
     
     password    | varchar(254)
     
     email       | varchar(254)
     
     firstname   | varchar(254)
     
     lastname    | varchar(254)
     
    
  "reservations"
  
     Column Name             | Type
     
     ------------------------+-----------------
     
     reservation_id          | varchar(254) PK 
     
     user_id                 | int PK 
     
     car_reservation_id      | varchar(254) 
     
     hotel_reservation_id    | varchar(254) 
     
     flight_reservation_id   | varchar(254) 
     
     json_car_reservation    | mediumtext 
     
     json_hotel_reservation  | mediumtext 
     
     json_flight_reservation | mediumtext
     

The json_car_reservation, json_hotel_reservation, and json_flight_reservation 
fields are used to store the reservation details for each respective booking. 

