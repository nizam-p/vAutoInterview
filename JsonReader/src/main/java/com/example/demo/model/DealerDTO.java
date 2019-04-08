package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DealerDTO implements Serializable, Comparable<DealerDTO>{

	private static final long serialVersionUID = 2851346638076799895L;

	
	private long dealerId;
	private String name;
	private List<VehicleInfoDTO> vehicles = new ArrayList<VehicleInfoDTO>();


	public DealerDTO() {
		super();
	}
	

	public DealerDTO(long dealerId) {
		super();
		this.dealerId = dealerId;
	}


	public long getDealerId() {
		return dealerId;
	}


	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<VehicleInfoDTO> getVehicles() {
		return vehicles;
	}


	public void setVehicles(List<VehicleInfoDTO> vehicles) {
		this.vehicles = vehicles;
	}


	public int compareTo(DealerDTO otherDealer) {
		return (int) (this.getDealerId() - otherDealer.getDealerId());
	}

	
}
