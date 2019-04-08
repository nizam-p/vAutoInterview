package com.example.demo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

//@JsonIgnoreProperties(value = "dealerId")
public class VehicleInfoDTO implements Serializable{

	private static final long serialVersionUID = -994706235542646512L;
	
	private long vehicleId;
	private int year;
	private String make;
	private String model;
	
//	@Expose(Serialize = false, deserialize = true)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long dealerId;
	
	
	public VehicleInfoDTO() {
		super();
	}
	
	
	public long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public long getDealerId() {
		return dealerId;
	}
	
	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

}
