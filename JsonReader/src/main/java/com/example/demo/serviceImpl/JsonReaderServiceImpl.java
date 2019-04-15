package com.example.demo.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import com.demo.example.exceptionhandler.BadRequestException;
import com.example.demo.model.DatasetDTO;
import com.example.demo.model.DealerDTO;
import com.example.demo.model.DealersDTO;
import com.example.demo.model.GroupOfVehiclesDTO;
import com.example.demo.model.VehicleInfoDTO;
import com.example.demo.service.JsonReaderService;
import com.example.demo.utils.HttpProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class JsonReaderServiceImpl implements JsonReaderService {

	private String datasetId = null;
	private HttpURLConnection con = null;
	
	private BufferedReader br = null;
	private StringBuffer sb = null;
	private String inputLine = null;
	private ObjectMapper mapper = new ObjectMapper();
	
	
	
	public String getDatasetId() {
		DatasetDTO result = null;
		String uri = "http://vautointerview.azurewebsites.net/api/datasetId";

		try {
			con = HttpProtocol.getInstance(uri);
			if (con.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				sb = new StringBuffer();
	
				while ((inputLine = br.readLine()) != null)
					sb.append(inputLine);
				br.close();
	
				result = mapper.readValue(sb.toString(), DatasetDTO.class);
			}
			con.disconnect();
		}
		catch(BadRequestException | IOException e) {
			throw new BadRequestException("ERR CODE : 1028");
		}
		
		this.datasetId = result.getDatasetId();
		return datasetId;
	}
	
	
	
	
	public List<Integer> getVehicles(String datasetId) {
		GroupOfVehiclesDTO result = null;
		String uri = "http://vautointerview.azurewebsites.net/api/" + datasetId + "/vehicles";
		
		try {
			con = HttpProtocol.getInstance(uri);
			if (con.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				sb = new StringBuffer();
	
				while ((inputLine = br.readLine()) != null)
					sb.append(inputLine);
				br.close();
	
				result = mapper.readValue(sb.toString(), GroupOfVehiclesDTO.class);
			}
			con.disconnect();
		}
		catch(BadRequestException | IOException e) {
			throw new BadRequestException("ERR CODE : 1038");
		}
		
		return result.getVehicleIds();
	}
	

	
	
	public VehicleInfoDTO getTheVehicleInfo(String datasetId, long vehicleId) {
		VehicleInfoDTO result = null;
		String uri = "http://vautointerview.azurewebsites.net/api/"+datasetId+"/vehicles/"+vehicleId;
		
		try {
			con = HttpProtocol.getInstance(uri);
			if(con.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				sb = new StringBuffer();
		
				while ((inputLine = br.readLine()) != null)
					sb.append(inputLine);
				br.close();
				
				result = mapper.readValue(sb.toString(), VehicleInfoDTO.class);
			}
			con.disconnect();
		}
		catch(BadRequestException | IOException e) {
			throw new BadRequestException("ERR CODE : 1048");
		}
		
		return result;
	}
	
	
	
	public DealerDTO getTheDealerInfo(String datasetId, long dealerId) {
		DealerDTO result = null;
		String uri = "http://vautointerview.azurewebsites.net/api/"+datasetId+"/dealers/"+dealerId;
		
		try {
			con = HttpProtocol.getInstance(uri);
			if(con.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				sb = new StringBuffer();
		
				while ((inputLine = br.readLine()) != null)
					sb.append(inputLine);
				br.close();
				
				result = mapper.readValue(sb.toString(), DealerDTO.class);
			}
			con.disconnect();
		}
		catch(BadRequestException | IOException e) {
			throw new BadRequestException("ERR CODE : 1058");
		}
		
		return result;
	}
	
	

	public DealersDTO getAllDealersWithTheirCarsInfo() {
		DealersDTO result = new DealersDTO();
		int index = -1;

		DealerDTO dealer = null;
		VehicleInfoDTO newVehicle = null;

		datasetId = getDatasetId();
		
		for(Integer vehicleId : getVehicles(datasetId)) {
			newVehicle = getTheVehicleInfo(datasetId, vehicleId);
			index = result.contains(newVehicle.getDealerId());
			
			if(index >= 0)
				result.getDealers().get(index).getVehicles().add(newVehicle);
			else {
				dealer = new DealerDTO(newVehicle.getDealerId());
				dealer.getVehicles().add(newVehicle);
				dealer.setVehicles(dealer.getVehicles());
				result.getDealers().add(dealer);
				result.setDealers(result.getDealers());
			}
		}
		
		for(DealerDTO dealerDTO : result.getDealers())
			dealerDTO.setName(getTheDealerInfo(datasetId, dealerDTO.getDealerId()).getName());
		
		Collections.sort(result.getDealers());
		return result;
	}



	public String postTheResult() {
		String status = null;
		DealersDTO result = getAllDealersWithTheirCarsInfo();
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try { 
			HttpPost postRequest = new HttpPost("http://vautointerview.azurewebsites.net/api/"+datasetId+ "/answer");
			String json = mapper.writeValueAsString(result);
			StringEntity testEntity = new StringEntity(json);
			
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.setEntity(testEntity);

			HttpResponse response = httpClient.execute(postRequest);
			
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			sb = new StringBuffer();

			while ((inputLine = br.readLine()) != null)
				sb.append(inputLine);
			br.close();
			
			status = sb.toString();
		}
		catch(BadRequestException | IOException e) {
			throw new BadRequestException("ERR CODE : 1068");
		}
		finally {
			httpClient.getConnectionManager().shutdown();
			HttpProtocol.shutdown();
			mapper = null;
		}
		
		return status;
	}
	

}
