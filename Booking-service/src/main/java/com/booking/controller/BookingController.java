package com.booking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.model.BookingModel;
import com.booking.model.ResponseModel;
import com.booking.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseModel createBooking(@RequestBody BookingModel model) {

        return bookingService.createBooking(model);
    }

    @GetMapping("/bookingDetails")
    public ResponseModel getBooking(@RequestParam String bookingId) {

        return bookingService.getBookingById(bookingId);
    }
    
    @GetMapping("/customer")
    public ResponseModel getCustomerBookings(@RequestParam String mobile) {

        return bookingService.getBookingsByCustomer(mobile);
    }

    // Get bookings by worker
    @GetMapping("/worker/getBookingsByWorker")
    public ResponseModel getWorkerBookings(@RequestParam String workerId) {

        return bookingService.getBookingsByWorker(workerId);
    }

    // Cancel booking
    @PutMapping("/cancel")
    public ResponseModel cancelBooking(@RequestParam String bookingId) {

        return bookingService.cancelBooking(bookingId);
    }

    // Start service
    @PutMapping("/start")
    public ResponseModel startBooking(@RequestParam String bookingId) {

        return bookingService.startBooking(bookingId);
    }

    // Complete service
    @PutMapping("/complete")
    public ResponseModel completeBooking(@RequestParam String bookingId) {

        return bookingService.completeBooking(bookingId);
    }
    
    @PostMapping("/auto-assign")
    public ResponseModel autoAssignWorker(@RequestBody BookingModel model){

        return bookingService.autoAssignWorker(model);

    }

}
