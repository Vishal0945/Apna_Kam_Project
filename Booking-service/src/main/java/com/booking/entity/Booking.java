package com.booking.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", unique = true)
    private String bookingId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_mobile")
    private String customerMobile;

    @Column(name = "worker_id")
    private String workerId;

    @Column(name = "service_type")
    private String serviceType;
    
    @Column
    private String serviceSubType;

    @Column(name = "booking_date")
    private String bookingDate;

    @Column(name = "slot")
    private String slot;

    @Column(name = "address")
    private String address;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Timestamp createdAt;

}