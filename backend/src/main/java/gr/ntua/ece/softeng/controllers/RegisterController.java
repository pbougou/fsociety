package gr.ntua.ece.softeng.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gr.ntua.ece.softeng.entities.Parent;
import gr.ntua.ece.softeng.entities.Providers;
import gr.ntua.ece.softeng.entities.Role;
import gr.ntua.ece.softeng.entities.User;
import gr.ntua.ece.softeng.error.CustomResponse;
import gr.ntua.ece.softeng.repositories.ParentRepository;
import gr.ntua.ece.softeng.repositories.ProvidersRepository;
import gr.ntua.ece.softeng.repositories.UserRepository;

@RestController
@RequestMapping(path="/register")
public class RegisterController {

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private ProvidersRepository providersRepository;

	@Autowired
	private UserRepository userRepository;

	private final static String POST_PARENT_URL = "/parent";
	@PostMapping(POST_PARENT_URL)
	public @ResponseBody CustomResponse createParent(@RequestBody Parent parent) throws IOException, JSONException {

		String username = parent.getUsername();
		String password = parent.getPassword();
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

		synchronized(this) {
			if(parentRepository.findByUsername(parent.getUsername()) != null) {
				CustomResponse res = new CustomResponse();
				res.setMessage("Username already exists"); 
				return res;
			}


			userRepository.save(new User(username, sha256hex, Arrays.asList(new Role("PARENT"))));

			parent.setPassword(sha256hex);
			parent.setFpoints(0);
			parent.setWallet(0);
			String Area = parent.getArea();
			String StreetName = parent.getStreetName().replaceAll("\\s+","");
			String StreetNumber = parent.getStreetNumber().toString();
			final String TARGET_URL =
					"https://maps.googleapis.com/maps/api/geocode/json?address=";
			final String help1= "+";
			final String help2=",";
			final String API_KEY =
					"&key=AIzaSyCi-UTmdLdEpurrr8A5Ou5I17cihpelPcI";
			URL serverUrl = new URL(TARGET_URL+StreetNumber+help1+StreetName+help2+Area+API_KEY);
			URLConnection urlConnection = serverUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setDoOutput(true);

			if (httpConnection.getInputStream() == null) {
				System.out.println("No stream");
			}

			Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());
			String resp = "";
			while (httpResponseScanner.hasNext()) {
				String line = httpResponseScanner.nextLine();
				resp += line;
			}
			httpResponseScanner.close();

			JSONObject json = new JSONObject(resp);

			JSONArray results =  json.getJSONArray("results");
			JSONObject sessionobj=results.getJSONObject(0);
			JSONObject geometry=sessionobj.getJSONObject("geometry");
			JSONObject location=geometry.getJSONObject("location");
			Double latitude=location.getDouble("lat");
			Double longitude=location.getDouble("lng");
			parent.setLatitude(latitude);
			parent.setLongitude(longitude);
			parentRepository.save(parent);

			parentRepository.save(parent);
			
			CustomResponse res = new CustomResponse();
			res.setMessage("ok with post from parent"); 
			return res;

		}


	}

	private final static String POST_PROVIDER_URL = "/provider";
	@PostMapping(POST_PROVIDER_URL)
	@Transactional
	public @ResponseBody CustomResponse createParent(@RequestBody Providers provider) {
		String username = provider.getUserName();
		String password = provider.getPassword();
		String companyName = provider.getcompanyName();
		
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);


		provider.setPassword(sha256hex);
		synchronized(this) {
			if(providersRepository.findByCompanyName(provider.getcompanyName()) != null) {
				CustomResponse res = new CustomResponse();
				res.setMessage("username already exists"); 
				return res;
			}
				
			List<Role> list = new ArrayList<Role>();
			list.add(new Role("PROVIDER"));
			list.add(new Role(companyName));
			
			userRepository.save(new User(username, sha256hex, list));
			
			providersRepository.save(provider);
			CustomResponse res = new CustomResponse();
			res.setMessage("ok with post from provider"); 
			return res;
			
		}

	}
	
	




}
