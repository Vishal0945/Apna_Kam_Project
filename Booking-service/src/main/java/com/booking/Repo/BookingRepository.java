package com.booking.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.entity.Booking;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingId(String bookingId);
    
    
    @Query(value = """
            SELECT booking_id 
            FROM bookings
            WHERE booking_id LIKE 'BK/%'
            ORDER BY booking_id DESC
            LIMIT 1
            """, nativeQuery = true)
    String findTopByOrderByBookingIdDesc();
    
    List<Booking> findByCustomerMobile(String mobile);

    List<Booking> findByWorkerId(String workerId);
    
    @Query("SELECT b FROM Booking b WHERE b.serviceType = :serviceType")
    List<Booking> getBooking(@Param("serviceType") String serviceType);
//    List<WorkerDetails> findByServiceTypeAndAvailability(String serviceType, Boolean availability);

}
