package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.JsonReaderService;

@RestController
@RequestMapping("/jsonReader")
public class JsonReaderController {

	@Autowired
	private JsonReaderService jsonReaderService;
	
	@GetMapping("/postTheDatasetDetails")
	public String letsExecuteThePostTest() throws Exception {
		return jsonReaderService.postTheResult();
	}
}
