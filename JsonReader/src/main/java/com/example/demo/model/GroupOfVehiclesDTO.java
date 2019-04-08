package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

public class GroupOfVehiclesDTO implements Serializable{

	private static final long serialVersionUID = 1485115699965662751L;

	private List<Integer> vehicleIds;

	
	public GroupOfVehiclesDTO() {
		super();
	}

	public List<Integer> getVehicleIds() {
		return vehicleIds;
	}

	public void setVehicleIds(List<Integer> vehicleIds) {
		this.vehicleIds = vehicleIds;
	}

	@Override
	public String toString() {
		return "Vehicles [vehicleIds=" + vehicleIds + "]";
	}
	
}
