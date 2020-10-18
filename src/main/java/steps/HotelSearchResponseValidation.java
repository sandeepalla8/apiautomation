package steps;

import com.vimalselvam.cucumber.listener.Reporter;
import io.restassured.response.ResponseOptions;
import org.junit.Assert;
import pojos.geoSuggest.response.GeoSuggestResponse;
import pojos.geoSuggest.response.Hotel;
import pojos.geoSuggest.response.Location;

public class HotelSearchResponseValidation {
    private ResponseOptions responseOptions;
    private int statusCode;
    private boolean validationStatus = true;

    public ResponseOptions getResponseOptions() {
        return responseOptions;
    }

    public void setResponseOptions(ResponseOptions responseOptions) {
        this.responseOptions = responseOptions;
    }
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public boolean isValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(boolean validationStatus) {
        this.validationStatus = validationStatus;
    }


    public void responseValidation() {
        assertEqualCheck("Response status code validation failed ", String.valueOf(statusCode), String.valueOf(responseOptions.getStatusCode()), "Status Code");
        GeoSuggestResponse geoSuggestResponse = responseOptions.getBody().as(GeoSuggestResponse.class);
        assertTrueCheck("Hotels", geoSuggestResponse.getHotels() != null,geoSuggestResponse.getHotels().toString());
        geoSuggestResponse.getHotels().stream().filter(hotel -> (hotel != null)).forEach(hotel -> hotelValidation(hotel));
        assertTrueCheck("locations", geoSuggestResponse.getLocations()!= null,geoSuggestResponse.getLocations().toString());
        geoSuggestResponse.getLocations().stream().filter(location -> (location != null)).forEach(location -> locationValidation(location));
    }


    public void hotelValidation(Hotel hotel) {
        assertTrueCheck("hotelId", hotel.getHotelId() != null,hotel.getHotelId().toString());
        assertTrueCheck("name", hotel.getName() != null,hotel.getName());
        assertTrueCheck("city", hotel.getCity() != null,hotel.getCity());
        assertTrueCheck("country", hotel.getCountry() != null,hotel.getCountry());
        assertTrueCheck("thumbnail_url", hotel.getThumbnailUrl() != null,hotel.getThumbnailUrl());
        assertTrueCheck("displayType", hotel.getDisplayType() != null,hotel.getDisplayType());
        assertTrueCheck("latitude", hotel.getLatitude()!= null,hotel.getLatitude().toString());
        assertTrueCheck("longitude", hotel.getLongitude() != null,hotel.getLongitude().toString());
        assertTrueCheck("isActive", hotel.getIsActive() != null,hotel.getIsActive().toString());
    }
    public void locationValidation(Location location) {
        assertTrueCheck("name", location.getName() != null,location.getName());
        assertTrueCheck("placeId", location.getPlaceId()!= null,location.getPlaceId());
        assertTrueCheck("source", location.getSource()!= null,location.getSource());
        assertTrueCheck("country", location.getCountry() != null,location.getCity());
        assertTrueCheck("city", location.getCity() != null,location.getDisplayType());
        assertTrueCheck("displayType", location.getDisplayType() != null,location.getDisplayType());
        assertTrueCheck("TypeNameEN", location.getGoogleType().getTypeNameEN()!= null,location.getGoogleType().getTypeNameEN());
        assertTrueCheck("TypeNameAR", location.getGoogleType().getTypeNameAR() != null,location.getGoogleType().getTypeNameAR());
    }

    public void assertTrueCheck(String field, boolean status,String val) {
        try {
            Assert.assertEquals(field + " field is not present in response", true, status);
            Reporter.addStepLog(field + " field is present in response with value :"+val);
        } catch (AssertionError e) {
            setValidationStatus(false);
            Reporter.addStepLog("<b style=\"background-color:rgb(255, 255, 0);\">" + field + " field is not present in response</b>");
        }
    }

    public void assertEqualCheck(String errorMessage, String expected, String actual, String field) {
        try {
            Assert.assertEquals(errorMessage, expected, actual);
            Reporter.addStepLog("<div style='padding: 3px; width: 680px; white-space: pre-wrap; word-break: break-all; word-wrap: break-word;'><b>" + field + "</b> value is matched Expected value: <b>" + expected + "</b> Actual Value: <b>" + actual + "</b></div>");
        } catch (AssertionError e) {
            setValidationStatus(false);
            Reporter.addStepLog("<b style=\"background-color:rgb(255, 255, 0);\">" + field + " value is mismatched Expected value: " + expected + " Actual Value: " + actual + "</b>");
        }
    }
}
