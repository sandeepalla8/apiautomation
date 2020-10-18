package pojos.geoSuggest.response;

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
        "TypeNameEN",
        "TypeNameAR"
})
public class GoogleType {

    @JsonProperty("TypeNameEN")
    private String typeNameEN;
    @JsonProperty("TypeNameAR")
    private String typeNameAR;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("TypeNameEN")
    public String getTypeNameEN() {
        return typeNameEN;
    }

    @JsonProperty("TypeNameEN")
    public void setTypeNameEN(String typeNameEN) {
        this.typeNameEN = typeNameEN;
    }

    @JsonProperty("TypeNameAR")
    public String getTypeNameAR() {
        return typeNameAR;
    }

    @JsonProperty("TypeNameAR")
    public void setTypeNameAR(String typeNameAR) {
        this.typeNameAR = typeNameAR;
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
