package com.hotelbooking.service;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotelbooking.controller.HotelBookingController;
@Service
public class HotelBookingService {
	private static final Logger log = LoggerFactory.getLogger(HotelBookingController.class);
	public static final String usernameAndPassword = "tripfactory23623:920A445CBBD0F1506960B55C2C3861B3EF8CEA80";
	public static final String METHOD_URL = "http://rest.reserve-online.net/availability";

	public HttpURLConnection getConn(String location, String checkin, String checkout, String rooms, String adults, String children) throws URISyntaxException, IOException {
		URIBuilder ub = new URIBuilder(METHOD_URL);
		ub.addParameter("location", location);
		ub.addParameter("checkin", checkin);
		ub.addParameter("checkout", checkout);
		ub.addParameter("rooms", rooms);
		ub.addParameter("adults", adults);
		ub.addParameter("children", children);
		String urls = ub.toString();
		URL url = new URL(urls);

		HttpURLConnection conns = (HttpURLConnection) url.openConnection();
		String encoding3 = Base64.getEncoder().encodeToString(
				usernameAndPassword.getBytes(StandardCharsets.UTF_8));
		log.info("username and password encode datas++====>"+usernameAndPassword);
		System.out.println("encoding3" + encoding3);
		
		try {
			conns.setRequestMethod("GET");
		
		conns.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
		conns.setRequestProperty("Accept", "application/json");
		conns.setRequestProperty("Authorization", "Basic " + encoding3);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conns;

	}

}
