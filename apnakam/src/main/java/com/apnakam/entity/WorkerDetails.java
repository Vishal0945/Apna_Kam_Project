package com.apnakam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class WorkerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
    private String workerId;


	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "mobile_no", nullable = false, unique = true)
	private String mobileNo;

	@Column
	private String email;

	@Column(name = "service_type", nullable = false)
	private String serviceType; // plumber, electrician

	@Column(name = "experience_years")
	private int experienceYears;

	@Column(name = "base_price")
	private Double basePrice;

	@Column
	private Boolean availability/* = true */;

	@Column
	private Double rating /* = 0.0 */;

	@Column
	private String city;

	@Column
	private String status /* = "ACTIVE" */;
	@Column
	public String loginPassword;


	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
