package com.example.demo.model;

import java.io.Serializable;

public class DatasetDTO implements Serializable{

	private static final long serialVersionUID = 5537940982614511735L;
	
	private String datasetId;

	public DatasetDTO() {
		super();
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

}
