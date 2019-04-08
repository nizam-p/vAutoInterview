package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DealerDTO;
import com.example.demo.model.DealersDTO;
import com.example.demo.model.VehicleInfoDTO;

public interface JsonReaderService {
	
	String getDatasetId() throws Exception;
	
	List<Integer> getVehicles(String datasetId) throws Exception;
	
	VehicleInfoDTO getTheVehicleInfo(String datasetId, long vehicleId) throws Exception;
	
	DealerDTO getTheDealerInfo(String datasetId, long dealerId) throws Exception;
	
	DealersDTO getAllDealersWithTheirCarsInfo() throws Exception;
	
	String postTheResult() throws Exception;

}
