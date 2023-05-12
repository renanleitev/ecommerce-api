package com.amenity_reservation_system;

import com.amenity_reservation_system.model.AmenityType;
import com.amenity_reservation_system.model.Product;
import com.amenity_reservation_system.model.Reservation;
import com.amenity_reservation_system.model.User;
import com.amenity_reservation_system.repos.ProductRepository;
import com.amenity_reservation_system.repos.ReservationRepository;
import com.amenity_reservation_system.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;


@SpringBootApplication
public class AmenityReservationSystemApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AmenityReservationSystemApplication.class, args);
    }


    private void insertDummyProducts(int count, ProductRepository productRepository) {
        Random random = new Random();

        for (int i = 1; i <= count; i++) {
            String description = "Product " + i + " description";
            String additionalFeatures = "Feature " + i + ", Feature " + (i + 1);
            String imageUrl = getRandomImageUrl(random);

            Product product = Product.builder()
                    .images(imageUrl)
                    .name("Dodge")
                    .price("$9.99")
                    .quantity(5)
                    .totalPrice(49.95)
                    .os("Windows")
                    .description(description)
                    .additionalFeatures(additionalFeatures)
                    .build();

            productRepository.save(product);
        }
    }

    private String getRandomImageUrl(Random random) {

         String[] imageUrls = {
                "https://i0.wp.com/garagem.nekarelogios.com.br/wp-content/uploads/2018/06/01-2.jpg?resize=640%2C445&ssl=1",
                "https://www.pastorecc.com.br/site/photos/cars/385/bg_14367957352ea2a70d2c4d.JPG",
                 "https://i.pinimg.com/originals/99/2d/9c/992d9c804ca308f56f96fa4fcf5d1870.jpg"
                // Add more image URLs as needed
        };

        int index = random.nextInt(imageUrls.length);
        return imageUrls[index];
    }

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository, UserRepository userRepository) {
        return (args) -> {

            User user = User.builder()
                    .name("John")
                    .surname("Doe")
                    .email("test@example.com")
                    .address("123 Main Street")
                    .password("123")
                    .build();

            userRepository.save(user);

            insertDummyProducts(20, productRepository);
        };
    }
}
