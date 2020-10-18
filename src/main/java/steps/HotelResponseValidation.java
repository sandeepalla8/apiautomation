package steps;

import com.vimalselvam.cucumber.listener.Reporter;
import io.restassured.response.ResponseOptions;
import org.junit.Assert;
import pojos.hotelSearch.response.HotelSearchErrorResponse;
import pojos.hotelSearch.response.HotelSearchResponse;

public class HotelResponseValidation {
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
        if(statusCode==200) {
            HotelSearchResponse hotelSearchResponse = responseOptions.getBody().as(HotelSearchResponse.class);
            assertTrueCheck("type", hotelSearchResponse.getType() != null,hotelSearchResponse.getType());
            assertTrueCheck("query", hotelSearchResponse.getQuery() != null,hotelSearchResponse.getQuery());
            assertTrueCheck("queryParameters", hotelSearchResponse.getQueryParameters() != null,hotelSearchResponse.getQueryParameters());
            assertTrueCheck("queryParametersObj.sortBy", hotelSearchResponse.getQueryParametersObj().getSortBy() != null,hotelSearchResponse.getQueryParametersObj().getSortBy());
            assertTrueCheck("queryParametersObj.isGeo", hotelSearchResponse.getQueryParametersObj().getIsGeo() != null,hotelSearchResponse.getQueryParametersObj().getIsGeo().toString());
            assertTrueCheck("queryParametersObj.hId", hotelSearchResponse.getQueryParametersObj().getHId() != null,hotelSearchResponse.getQueryParametersObj().getHId());
            assertTrueCheck("queryParametersObj.isCountry", hotelSearchResponse.getQueryParametersObj().getIsCountry() != null,hotelSearchResponse.getQueryParametersObj().getIsCountry());
            assertTrueCheck("queryParametersObj.placeId", hotelSearchResponse.getQueryParametersObj().getPlaceId() != null,hotelSearchResponse.getQueryParametersObj().getPlaceId());
            assertTrueCheck("queryParametersObj.types", hotelSearchResponse.getQueryParametersObj().getTypes() != null,hotelSearchResponse.getQueryParametersObj().getTypes().toString());
            assertTrueCheck("isCountry", hotelSearchResponse.getIsCountry() != null,hotelSearchResponse.getIsCountry().toString());

        }else{
            HotelSearchErrorResponse hotelSearchErrorResponse = responseOptions.getBody().as(HotelSearchErrorResponse.class);
            assertTrueCheck("status", hotelSearchErrorResponse.getStatus() != null,hotelSearchErrorResponse.getStatus().toString());
            assertTrueCheck("title", hotelSearchErrorResponse.getTitle() != null,hotelSearchErrorResponse.getTitle());
            assertTrueCheck("detail", hotelSearchErrorResponse.getDetail() != null,hotelSearchErrorResponse.getDetail().toString());
            if(hotelSearchErrorResponse.getDetail().getDatesCheckin() != null){
                assertTrueCheck("detail.dates.checkin", hotelSearchErrorResponse.getDetail().getDatesCheckin() != null,hotelSearchErrorResponse.getDetail().getDatesCheckin().toString());
            }
            if(hotelSearchErrorResponse.getDetail().getDatesCheckout() != null){
                assertTrueCheck("detail.dates.checkout", hotelSearchErrorResponse.getDetail().getDatesCheckout() != null,hotelSearchErrorResponse.getDetail().getDatesCheckout().toString());
            }
            if(hotelSearchErrorResponse.getDetail().getDestination() != null){
                assertTrueCheck("detail.destination", hotelSearchErrorResponse.getDetail().getDestination() != null,hotelSearchErrorResponse.getDetail().getDestination().toString());
            }
            assertTrueCheck("type", hotelSearchErrorResponse.getType() != null,hotelSearchErrorResponse.getType());
        }
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
