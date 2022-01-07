package com.hotelbooking.controller;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hotelbooking.service.HotelBookingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/hotel")
public class HotelBookingController {
	private static final Logger log = LoggerFactory.getLogger(HotelBookingController.class);
	@Autowired
	HotelBookingService hotelBookingService;


	@SuppressWarnings("unused")
	@GetMapping(path = "/searchByCountry", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveBookingAdmin(@RequestParam(value = "location") String location,
			@RequestParam(value = "checkin") String checkin, @RequestParam(value = "checkout") String checkout,
			@RequestParam(value = "rooms") String rooms, @RequestParam(value = "adults") String adults,
			@RequestParam(value = "children") String children) {
		JSONObject jsonObject = new JSONObject();
		try {
			HttpURLConnection conn = hotelBookingService.getConn(location, checkin, checkout, rooms, adults, children);
			log.info("Message......" + conn.getResponseMessage());
			InputStream content = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			String s = in.lines().collect(Collectors.joining());
			log.info("Response......" + s);
			jsonObject = new JSONObject(s);
			return ResponseEntity.ok(jsonObject.toMap());
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", 400);
			jsonObject.put("status", "Error reading data");
			return ResponseEntity.ok(jsonObject.toMap());
		}
	}

}
