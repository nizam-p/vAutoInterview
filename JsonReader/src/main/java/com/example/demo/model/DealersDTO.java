package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DealersDTO implements Serializable{

	private static final long serialVersionUID = 1211514743469254422L;
	
	private List<DealerDTO> dealers = new ArrayList<DealerDTO>();


	public DealersDTO() {
		super();
	}

	public List<DealerDTO> getDealers() {
		return dealers;
	}

	public void setDealers(List<DealerDTO> dealers) {
		this.dealers = dealers;
	}

	public int contains(long dealerId) {
		int index = -1;
		for(int i= 0; i<dealers.size(); i++) {
			if(dealers.get(i).getDealerId() == dealerId) {
				index = i;
				break;
			}
		}
		return index;
	}



}
