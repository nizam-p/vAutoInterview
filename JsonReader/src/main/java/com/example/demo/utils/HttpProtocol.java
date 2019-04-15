package com.example.demo.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpProtocol {
	
	private static HttpURLConnection con = null;
	
	private HttpProtocol() {
	}
	
	
	public static HttpURLConnection getInstance(String uri) {
		URL url = null;
		
		try {
			url = new URL(uri);
			
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();
		}
		catch(IOException e) {
			
		}
		
		return con;
	}
	
	public static void shutdown() {
		if(con != null)
			con = null;
	}

}
