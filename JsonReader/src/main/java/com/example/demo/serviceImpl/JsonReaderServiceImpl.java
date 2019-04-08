package com.example.demo.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import com.example.demo.model.DatasetDTO;
import com.example.demo.model.DealerDTO;
import com.example.demo.model.DealersDTO;
import com.example.demo.model.GroupOfVehiclesDTO;
import com.example.demo.model.VehicleInfoDTO;
import com.example.demo.service.JsonReaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class JsonReaderServiceImpl implements JsonReaderService {

	private String datasetId = null;
	
	
	public String getDatasetId() throws Exception {
		DatasetDTO result = new DatasetDTO();
		
		String url = "http://vautointerview.azurewebsites.net/api/datasetId";
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.connect();

		if (con.getResponseCode() == 200) {

			ObjectMapper mapper = new ObjectMapper();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String inputline;

			while ((inputline = br.readLine()) != null)
				sb.append(inputline);
			br.close();

			result = mapper.readValue(sb.toString(), DatasetDTO.class);
		}
		con.disconnect();
		this.datasetId = result.getDatasetId();
		return datasetId;
	}
	
	
	
	
	public List<Integer> getVehicles(String datasetId) throws Exception {
		GroupOfVehiclesDTO result = new GroupOfVehiclesDTO();

		String url = "http://vautointerview.azurewebsites.net/api/" + datasetId + "/vehicles";
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.connect();

		if (con.getResponseCode() == 200) {

			ObjectMapper mapper = new ObjectMapper();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String inputline;

			while ((inputline = br.readLine()) != null)
				sb.append(inputline);
			br.close();

			result = mapper.readValue(sb.toString(), GroupOfVehiclesDTO.class);
		}
		con.disconnect();
		return result.getVehicleIds();
	}
	

	
	
	public VehicleInfoDTO getTheVehicleInfo(String datasetId, long vehicleId) throws Exception{
		VehicleInfoDTO result = null;
		
		String url = "http://vautointerview.azurewebsites.net/api/"+datasetId+"/vehicles/"+vehicleId;
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.connect();
		
		if(con.getResponseCode() == 200) {
	
			ObjectMapper mapper = new ObjectMapper();
	
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String inputline;
	
			while ((inputline = br.readLine()) != null)
				sb.append(inputline);
			br.close();
			
			result = mapper.readValue(sb.toString(), VehicleInfoDTO.class);
		}
		con.disconnect();
		return result;
	}
	
	
	
	public DealerDTO getTheDealerInfo(String datasetId, long dealerId) throws Exception{
		DealerDTO result = null;
		
		String url = "http://vautointerview.azurewebsites.net/api/"+datasetId+"/dealers/"+dealerId;
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.connect();
		
		if(con.getResponseCode() == 200) {
	
			ObjectMapper mapper = new ObjectMapper();
	
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String inputline;
	
			while ((inputline = br.readLine()) != null)
				sb.append(inputline);
			br.close();
			
			result = mapper.readValue(sb.toString(), DealerDTO.class);
		}
		con.disconnect();
		return result;
	}
	
	

	public DealersDTO getAllDealersWithTheirCarsInfo() throws Exception {
		DealersDTO result = new DealersDTO();
		int index = -1;

		DealerDTO dealer = null;
		VehicleInfoDTO newVehicle = null;

		String datasetId = getDatasetId();
		
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



	public String postTheResult() throws Exception {
		String status = null;
		DealersDTO result = getAllDealersWithTheirCarsInfo();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		String json = mapper.writeValueAsString(result);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try { 
			HttpPost postRequest = new HttpPost("http://vautointerview.azurewebsites.net/api/"+datasetId+ "/answer");
			
			StringEntity testEntity = new StringEntity(json);
			
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.setEntity(testEntity);

			HttpResponse response = httpClient.execute(postRequest);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer();
			String inputline;

			while ((inputline = br.readLine()) != null)
				sb.append(inputline);
			br.close();
			
			status = sb.toString();
		}
		finally {
			httpClient.getConnectionManager().shutdown(); 
		}
		return status;
	}
	

}
