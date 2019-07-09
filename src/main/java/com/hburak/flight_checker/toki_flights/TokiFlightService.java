package com.hburak.flight_checker.toki_flights;

import com.hburak.flight_checker.config.ApplicationProperties;
import com.hburak.flight_checker.entity.Flight;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokiFlightService {
    private final ApplicationProperties applicationProperties;

    public List<Flight> getFlights() throws IOException, JSONException {
        ResponseEntity<Object> response = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String cheapResponse = getFlightsResponse(applicationProperties.getCheapFlightsUrl());
        String businessResponse = getFlightsResponse(applicationProperties.getBusinessFlightsUrl());
        return mergeResponses(cheapResponse, businessResponse);
    }

    private List<Flight> mergeResponses(String cheapResponse, String businessResponse) throws JSONException {
        List<TokiBusinessFlightResponse> businessFlights = new ArrayList<>();
        List<TokiCheapFlightResponse> cheapFlights = new ArrayList<>();
        List<Flight> flights = new ArrayList<>();
        JSONObject cheapJson = new JSONObject(cheapResponse);
        JSONObject businessJson = new JSONObject(businessResponse);
        JSONArray cheapJsonArray = (JSONArray) cheapJson.get("data");
        JSONArray businessArray = (JSONArray) businessJson.get("data");

        for(int i = 0; i < cheapJsonArray.length(); i++) {
            Flight oneFlight = new Flight();
            TokiCheapFlightResponse cheapFlight = new TokiCheapFlightResponse();
            cheapFlight.setRoute(cheapJsonArray.getJSONObject(i).getString("route"));
            int x = cheapFlight.getRoute().indexOf("-");
            log.info("dash found in route at index #" + x);
            log.info("route: " + cheapFlight.getRoute());
            oneFlight.setDeparture(cheapFlight.getRoute().substring(0, x));
            oneFlight.setArrival(cheapFlight.getRoute().substring(x + 1, cheapFlight.getRoute().length()));
            oneFlight.setDepartureTime(cheapJsonArray.getJSONObject(i).getLong("departure"));
            oneFlight.setArrivalTime(cheapJsonArray.getJSONObject(i).getLong("arrival"));
            flights.add(oneFlight);

            cheapFlight.setArrivalTime(cheapJsonArray.getJSONObject(i).getLong("arrival"));
            cheapFlight.setDepartureTime(cheapJsonArray.getJSONObject(i).getLong("departure"));
            log.info("cheap flights " + cheapFlight.toString());
        }

        for (int i = 0; i < businessArray.length(); i++) {
            Flight oneFlight = new Flight();
            TokiBusinessFlightResponse businessFlight = new TokiBusinessFlightResponse();
            businessFlight.setDeparture(businessArray.getJSONObject(i).getString("departure"));
            businessFlight.setArrival(businessArray.getJSONObject(i).getString("arrival"));
            businessFlight.setDepartureTime(businessArray.getJSONObject(i).getLong("departureTime"));
            businessFlight.setArrivalTime(businessArray.getJSONObject(i).getLong("arrivalTime"));
            log.info("business flights " + businessFlight.toString());

            oneFlight.setArrivalTime(businessArray.getJSONObject(i).getLong("arrivalTime"));
            oneFlight.setArrival(businessArray.getJSONObject(i).getString("arrival"));
            oneFlight.setDeparture(businessArray.getJSONObject(i).getString("departure"));
            oneFlight.setDepartureTime(businessArray.getJSONObject(i).getLong("departureTime"));
            oneFlight.setBusiness(true);
            flights.add(oneFlight);
        }

        return flights;
    }

    private String getFlightsResponse(String flightUrl) throws IOException {
        URL url = new URL(flightUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        StringBuffer content = new StringBuffer();
        con.setRequestMethod("GET");
        con.setConnectTimeout(60000);
        if (con.getResponseCode() < 300) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        }
        return content.toString();
    }
}
