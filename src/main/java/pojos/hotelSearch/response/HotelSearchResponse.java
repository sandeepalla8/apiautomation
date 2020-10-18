package pojos.hotelSearch.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "query",
        "queryParameters",
        "queryParametersObj",
        "isCountry"
})
public class HotelSearchResponse {

    @JsonProperty("type")
    private String type;
    @JsonProperty("query")
    private String query;
    @JsonProperty("queryParameters")
    private String queryParameters;
    @JsonProperty("queryParametersObj")
    private QueryParametersObj queryParametersObj;
    @JsonProperty("isCountry")
    private Boolean isCountry;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("query")
    public String getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(String query) {
        this.query = query;
    }

    @JsonProperty("queryParameters")
    public String getQueryParameters() {
        return queryParameters;
    }

    @JsonProperty("queryParameters")
    public void setQueryParameters(String queryParameters) {
        this.queryParameters = queryParameters;
    }

    @JsonProperty("queryParametersObj")
    public QueryParametersObj getQueryParametersObj() {
        return queryParametersObj;
    }

    @JsonProperty("queryParametersObj")
    public void setQueryParametersObj(QueryParametersObj queryParametersObj) {
        this.queryParametersObj = queryParametersObj;
    }

    @JsonProperty("isCountry")
    public Boolean getIsCountry() {
        return isCountry;
    }

    @JsonProperty("isCountry")
    public void setIsCountry(Boolean isCountry) {
        this.isCountry = isCountry;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}