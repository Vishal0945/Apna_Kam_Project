package com.booking.service;

import com.booking.model.BookingModel;
import com.booking.model.ResponseModel;

public interface BookingService {

	 ResponseModel createBooking(BookingModel model);

	    ResponseModel getBookingById(String bookingId);
	    

	    ResponseModel getBookingsByCustomer(String mobile);

	    ResponseModel getBookingsByWorker(String workerId);

	    ResponseModel cancelBooking(String bookingId);

	    ResponseModel startBooking(String bookingId);

	    ResponseModel completeBooking(String bookingId);
	    
	    ResponseModel autoAssignWorker(BookingModel model);

}
