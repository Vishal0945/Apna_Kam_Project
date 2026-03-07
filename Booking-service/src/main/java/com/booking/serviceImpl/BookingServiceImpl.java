package com.booking.serviceImpl;




import java.sql.Timestamp;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.booking.entity.Booking;
import com.booking.entity.WorkerDetails;
import com.booking.model.BookingModel;
import com.booking.model.ResponseModel;
import com.booking.service.BookingService;

@Service
public class BookingServiceImpl extends AbstractMasterRepository implements BookingService {

   

    @Override
    public ResponseModel createBooking(BookingModel model) {

        ResponseModel res = new ResponseModel();

        Booking booking = new Booking();

        booking.setBookingId(generateBookingId());
        booking.setCustomerName(model.getCustomerName());
        booking.setCustomerMobile(model.getCustomerMobile());
        booking.setWorkerId(model.getWorkerId());
        booking.setServiceType(model.getServiceType());
        booking.setServiceSubType(model.getServiceSubType());
        booking.setBookingDate(model.getBookingDate());
        booking.setSlot(model.getSlot());
        booking.setAddress(model.getAddress());
        booking.setAmount(model.getAmount());
        booking.setStatus("CREATED");
        booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        bookingRepository.save(booking);

        res.setMessage("Booking created successfully");
        res.setData(booking);
        res.setHttpStatus(HttpStatus.OK);

        return res;
    }

    @Override
    public ResponseModel getBookingById(String bookingId) {

        ResponseModel res = new ResponseModel();

        Optional<Booking> booking = bookingRepository.findByBookingId(bookingId);

        if (booking.isPresent()) {

            res.setMessage("Booking found");
            res.setData(booking.get());
            res.setHttpStatus(HttpStatus.OK);

        } else {

            res.setMessage("Booking not found");
            res.setHttpStatus(HttpStatus.NOT_FOUND);

        }

        return res;
    }

    private String generateBookingId() {

        String prefix = "BK";
        String year = String.valueOf(Year.now().getValue());

        String lastBookingId = bookingRepository.findTopByOrderByBookingIdDesc();

        int nextSequence = 1;

        if (lastBookingId != null && lastBookingId.matches("BK/\\d{4}/\\d+")) {

            String seq = lastBookingId.substring(lastBookingId.lastIndexOf("/") + 1);
            nextSequence = Integer.parseInt(seq) + 1;
        }

        return String.format("%s/%s/%04d", prefix, year, nextSequence);
    }
    
    
    @Override
    public ResponseModel getBookingsByCustomer(String mobile) {

        ResponseModel res = new ResponseModel();

        List<Booking> bookings = bookingRepository.findByCustomerMobile(mobile);

        if (bookings.isEmpty()) {
            res.setMessage("No bookings found for this customer");
            res.setHttpStatus(HttpStatus.NOT_FOUND);
        } else {
            res.setMessage("Customer bookings fetched successfully");
            res.setData(bookings);
            res.setHttpStatus(HttpStatus.OK);
        }

        return res;
    }

    // GET bookings by worker
    @Override
    public ResponseModel getBookingsByWorker(String workerId) {

        ResponseModel res = new ResponseModel();

        List<Booking> bookings = bookingRepository.findByWorkerId(workerId);

        if (bookings.isEmpty()) {
            res.setMessage("No bookings found for this worker");
            res.setHttpStatus(HttpStatus.NOT_FOUND);
        } else {
            res.setMessage("Worker bookings fetched successfully");
            res.setData(bookings);
            res.setHttpStatus(HttpStatus.OK);
        }

        return res;
    }

    // CANCEL BOOKING
    @Override
    public ResponseModel cancelBooking(String bookingId) {

        ResponseModel res = new ResponseModel();

        Optional<Booking> bookingOpt = bookingRepository.findByBookingId(bookingId);

        if (bookingOpt.isEmpty()) {

            res.setMessage("Booking not found");
            res.setHttpStatus(HttpStatus.NOT_FOUND);
            return res;
        }

        Booking booking = bookingOpt.get();
        booking.setStatus("CANCELLED");

        bookingRepository.save(booking);

        res.setMessage("Booking cancelled successfully");
        res.setData(booking);
        res.setHttpStatus(HttpStatus.OK);

        return res;
    }

    // START BOOKING
    @Override
    public ResponseModel startBooking(String bookingId) {

        ResponseModel res = new ResponseModel();

        Optional<Booking> bookingOpt = bookingRepository.findByBookingId(bookingId);

        if (bookingOpt.isEmpty()) {

            res.setMessage("Booking not found");
            res.setHttpStatus(HttpStatus.NOT_FOUND);
            return res;
        }

        Booking booking = bookingOpt.get();
        booking.setStatus("IN_PROGRESS");

        bookingRepository.save(booking);

        res.setMessage("Service started");
        res.setData(booking);
        res.setHttpStatus(HttpStatus.OK);

        return res;
    }

    // COMPLETE BOOKING
    @Override
    public ResponseModel completeBooking(String bookingId) {

        ResponseModel res = new ResponseModel();

        Optional<Booking> bookingOpt = bookingRepository.findByBookingId(bookingId);

        if (bookingOpt.isEmpty()) {

            res.setMessage("Booking not found");
            res.setHttpStatus(HttpStatus.NOT_FOUND);
            return res;
        }

        Booking booking = bookingOpt.get();
        booking.setStatus("COMPLETED");

        bookingRepository.save(booking);

        res.setMessage("Service completed successfully");
        res.setData(booking);
        res.setHttpStatus(HttpStatus.OK);

        return res;
    }

	@Override
	public ResponseModel autoAssignWorker(BookingModel model) {
		// TODO Auto-generated method stub
		return null;
	}
    
 

 //   @Override
//    public ResponseModel autoAssignWorker(BookingModel model) {
//
//        ResponseModel res = new ResponseModel();
//
//        // find available workers
//        List<WorkerDetails> workers =
//        		workerDetailsRepo.findByServiceTypeAndAvailability(
//                        model.getServiceType(), true);
//
//        if (workers.isEmpty()) {
//
//            res.setMessage("No worker available for this service");
//            res.setHttpStatus(HttpStatus.NOT_FOUND);
//            return res;
//        }
//
//        // pick first available worker
//        WorkerDetails worker = workers.get(0);
//
//        Booking booking = new Booking();
//
//        booking.setBookingId(generateBookingId());
//        booking.setCustomerName(model.getCustomerName());
//        booking.setCustomerMobile(model.getCustomerMobile());
//        booking.setWorkerId(worker.getWorkerId());
//        booking.setServiceType(model.getServiceType());
//        booking.setBookingDate(model.getBookingDate());
//        booking.setSlot(model.getSlot());
//        booking.setAddress(model.getAddress());
//        booking.setAmount(model.getAmount());
//        booking.setStatus("ASSIGNED");
//        booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//
//        bookingRepository.save(booking);
//
//        res.setMessage("Worker assigned successfully");
//        res.setData(booking);
//        res.setHttpStatus(HttpStatus.OK);
//
//        return res;
//    }

}