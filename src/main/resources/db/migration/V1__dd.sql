-- 1. parent tables
CREATE TABLE driver (
   id BIGINT NOT NULL  AUTO_INCREMENT,
   created_at DATETIME(6),
   updated_at DATETIME(6),
   license_number VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE passenger (
   id BIGINT NOT NULL  AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   created_at DATETIME(6),
   updated_at DATETIME(6),
   PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 2. child table
CREATE TABLE booking (
   id BIGINT NOT NULL  AUTO_INCREMENT,
   created_at DATETIME(6),
   updated_at DATETIME(6),
   distance BIGINT,
   start_time DATETIME(6),
   booking_status ENUM(
     'ASSIGNING_DRIVER','CAB_ARRIVED','CANCELLED',
     'COMPLETED','IN_DRIVE','SCHEDULED'
   ),
   end_time DATETIME(6),
   driver_id BIGINT,
   passenger_id BIGINT,
   PRIMARY KEY (id),
   CONSTRAINT fk_booking_driver
     FOREIGN KEY (driver_id) REFERENCES driver(id),
   CONSTRAINT fk_booking_passenger
     FOREIGN KEY (passenger_id) REFERENCES passenger(id)
) ENGINE=InnoDB;

CREATE TABLE `booking_review` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `created_at` datetime(6) DEFAULT NULL,
   `updated_at` datetime(6) DEFAULT NULL,
   `content` varchar(255) NOT NULL,
   `rating` double NOT NULL,
   `booking_id` bigint NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_p4ps590fybk1a6a07kydp51h` (`booking_id`),
   CONSTRAINT `FKjkq7o59og4g3162nfq8kehrep` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`)
 ) ENGINE=InnoDB;

CREATE TABLE `passenger_rating` (
   `passenger_rating` double NOT NULL,
   `passenger_review_content` varchar(255) NOT NULL,
   `id` bigint NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `FKlvh2cv5fhaq83gyy4f516ijlo` FOREIGN KEY (`id`) REFERENCES `booking_review` (`id`)
 ) ENGINE=InnoDB;


