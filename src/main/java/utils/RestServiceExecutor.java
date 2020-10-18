package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestServiceExecutor {
    private RequestSpecBuilder builder = new RequestSpecBuilder();
    private String method;
    private String url;
    private String currentRequest;
    public String getMethod(){
        return this.method;
    }
    public void setMethod(String method){
        this.method=method;
    }

    public String getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(String currentRequest) {
        this.currentRequest = currentRequest;
    }

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    private ResponseOptions<Response> executeService() {
        RequestSpecification requestSpecification = builder.build();
        RequestSpecification request = RestAssured.given();
        //request.contentType(ContentType.JSON);
        request.spec(requestSpecification);
        if (this.method.equalsIgnoreCase("GET")) {
            return request.get(this.url);
        } else if (this.method.equalsIgnoreCase("POST")) {
            return request.post(this.url);
        }
        return null;
    }

    public ResponseOptions<Response> executeWithQueryParams(Map<String, String> queryPath) {
        builder.addQueryParams(queryPath);
        return executeService();
    }
    public ResponseOptions<Response> executeWithHeaderAndQueryParams(Map<String, String> queryPath,Map<String, String> headers) {
        builder.addQueryParams(queryPath);
        builder.addHeaders(headers);
        return executeService();
    }
    public ResponseOptions<Response> executeWithPathParams(Map<String, String> pathParams) {
        builder.addPathParams(pathParams);
        return executeService();
    }

    public ResponseOptions<Response> executeWithHeaderParamsAndBody(Map<String, String> headerParams, String body) {
        setCurrentRequest(body);
        builder.setBody(body);
        builder.addHeaders(headerParams);
        return executeService();
    }
}
