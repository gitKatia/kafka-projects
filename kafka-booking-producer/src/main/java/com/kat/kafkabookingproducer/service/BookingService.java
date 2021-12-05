package com.kat.kafkabookingproducer.service;

import com.kat.kafkabookingproducer.model.Booking;
import com.kat.kafkabookingproducer.producer.KafkaBookingProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final KafkaBookingProducer kafkaBookingProducer;

    private List<Booking> getBookings() {
        Booking booking1 = Booking.builder()
                .bookingId(UUID.randomUUID().toString())
                .adults(2)
                .rooms(2)
                .checkIn(LocalDate.now().plusDays(10))
                .checkout(LocalDate.now().plusDays(12))
                .build();
        Booking booking2 = Booking.builder()
                .bookingId(UUID.randomUUID().toString())
                .adults(2)
                .rooms(3)
                .checkIn(LocalDate.now().plusDays(10))
                .checkout(LocalDate.now().plusDays(12))
                .build();
        return Arrays.asList(booking1, booking2);
    }

    @Scheduled(fixedRate = 4000)
    public void sendBookings() {
        getBookings().forEach(kafkaBookingProducer::publishBooking);
    }
}
