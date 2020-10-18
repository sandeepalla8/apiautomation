package steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.java8.En;
import io.restassured.response.ResponseOptions;

import org.junit.Assert;
import pojos.hotelSearch.request.*;
import utils.RestServiceExecutor;

import java.util.*;

public class HotelSearchCucumberSteps implements En {
    RestServiceExecutor restServiceExecutor = new RestServiceExecutor();
    HotelSearchResponseValidation hotelSearchResponseValidation = new HotelSearchResponseValidation();
    HotelResponseValidation hotelResponseValidation=new HotelResponseValidation();
    ResponseOptions responseOptions;

    public HotelSearchCucumberSteps() {
        When("^I get a list of hotels and locations using Geo key \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", (String url, String method, String geoKey,String header) -> iGetAListOfHotelsUsingTheAPIEndpoint(url, method, geoKey,header));
        Then("^I validate the geo based hotel search response \"([^\"]*)\",\"([^\"]*)\"$", (Integer statusCode, String geoKey) -> validateResponse(statusCode, geoKey));
        When("^I post a hotel search request with header params and request body using API parameters \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", (String url, String header, String method, String destination, String checkin, String checkout, String guestCombo, String placeId) -> iPostHotelSearchUsingTheAPIEndpoint(url, header, method, destination, checkin, checkout, guestCombo, placeId));
        Then("^I validate the date based hotel search response \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", (Integer statusCode, String destination,String checkin, String checkout, String guestCombo,String placeId) -> validateHotelResponse(statusCode, destination, checkin, checkout, guestCombo, placeId));
        Then("^I validate the hotel search error response \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", (Integer statusCode, String destination,String checkin, String checkout, String guestCombo,String placeId) -> validateHotelErrorResponse(statusCode, destination, checkin, checkout, guestCombo, placeId));
    }

    public void iGetAListOfHotelsUsingTheAPIEndpoint(String url, String method, String geoKey,String header) {
        Map<String, String> queryParams = new HashMap<>();
        restServiceExecutor.setUrl(url);
        restServiceExecutor.setMethod(method);
        queryParams.put("query", geoKey);
        responseOptions = restServiceExecutor.executeWithHeaderAndQueryParams(queryParams,getHeaders(header));
        Reporter.addStepLog("<b>API Request:</b> \n<div style='padding: 3px; width: 680px; white-space: pre-wrap; word-break: break-all; word-wrap: break-word;'>" + restServiceExecutor.getUrl() + "?query=" + geoKey + "</div>");
        Reporter.addStepLog("<b>API Response:</b> \n<div style='padding: 3px; width: 680px; white-space: pre-wrap; word-break: break-all; word-wrap: break-word;'>" + responseOptions.getBody().asString() + "</div>");
    }

    public void iPostHotelSearchUsingTheAPIEndpoint(String url, String header, String method, String destination, String checkin, String checkout, String guestCombo, String placeId) {
        restServiceExecutor.setUrl(url);
        restServiceExecutor.setMethod(method);
        String requestBody = getPostHotelSearchRequest(checkin, checkout, destination, guestCombo, placeId);
        responseOptions = restServiceExecutor.executeWithHeaderParamsAndBody(getHeaders(header), requestBody);
        Reporter.addStepLog("<b>API Request:</b> \n<div style='padding: 3px; width: 680px; white-space: pre-wrap; word-break: break-all; word-wrap: break-word;'>" + restServiceExecutor.getUrl() + "</div>");
        Reporter.addStepLog("<b>API Response:</b> \n<div style='padding: 3px; width: 680px; white-space: pre-wrap; word-break: break-all; word-wrap: break-word;'>" + responseOptions.getBody().asString() + "</div>");
    }

    public void validateResponse(Integer statusCode, String geoKey) {
        hotelSearchResponseValidation.setResponseOptions(responseOptions);
        hotelSearchResponseValidation.setStatusCode(statusCode);
        hotelSearchResponseValidation.responseValidation();
        Assert.assertEquals("Response Validation is failed", true, hotelSearchResponseValidation.isValidationStatus());
    }

    public void validateHotelResponse(Integer statusCode, String destination,String checkin, String checkout, String guestCombo,String placeId) {
        hotelResponseValidation.setResponseOptions(responseOptions);
        hotelResponseValidation.setStatusCode(statusCode);
        hotelResponseValidation.responseValidation();
        Assert.assertEquals("Response Validation is failed", true, hotelResponseValidation.isValidationStatus());
    }

    public void validateHotelErrorResponse(Integer statusCode, String destination,String checkin, String checkout, String guestCombo,String placeId) {
        hotelResponseValidation.setResponseOptions(responseOptions);
        hotelResponseValidation.setStatusCode(statusCode);
        hotelResponseValidation.responseValidation();
        Assert.assertEquals("Response Validation is failed", true, hotelResponseValidation.isValidationStatus());
    }

    public Map<String, String> getHeaders(String header) {
        Map<String, String> headerParams = new HashMap<>();
        String[] headers = header.split(";");
        Arrays.stream(headers).filter(head->head!=null).forEach(head -> {
            System.out.println(head);
            String[] headerValues = head.split(":");
            headerParams.put(headerValues[0], headerValues[1]);
        });
        return headerParams;
    }

    public String getPostHotelSearchRequest(String checkin, String checkout, String destination, String guestCombo, String placeId) {
        String request = "";
        try {
            HotelSearchRequest hotelSearchRequest = new HotelSearchRequest();
            hotelSearchRequest.setDates(getDates(checkin, checkout));
            hotelSearchRequest.setDestination(destination);
            hotelSearchRequest.setRoom(getRooms(guestCombo));
            hotelSearchRequest.setPlaceId(placeId);
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(hotelSearchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public Dates getDates(String checkin, String checkout) {
        Dates dates = new Dates();
        dates.setCheckin(checkin);
        dates.setCheckout(checkout);
        return dates;
    }

    public List<Room> getRooms(String guestCombo) {
        List<Room> rooms = new ArrayList<>();
        String[] guestCombos = guestCombo.split(",");
        Arrays.stream(guestCombos).forEach(guestInfo -> {
            Room room = new Room();
            room.setGuest(getGuests(guestInfo));
            rooms.add(room);
        });
        return rooms;
    }

    public List<Guest> getGuests(String guestInfo) {
        List<Guest> guests = new ArrayList<>();
        String[] guestInfoArr = guestInfo.split("-");
        Arrays.stream(guestInfoArr).forEach(guestDetails -> {
            try {
                String[] guestArray = guestDetails.split(":");
                Guest guest = new Guest();
                switch (guestArray.length) {
                    case 1:
                        guest.setType(guestArray[0]);
                        break;
                    case 2:
                        guest.setType(guestArray[0]);
                        guest.setAge(Integer.parseInt(guestArray[1]));
                        break;
                    default:

                }
                guests.add(guest);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return guests;
    }
}
