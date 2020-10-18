package pojos.hotelSearch.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dates.checkin",
        "dates.checkout",
        "destination"
})
public class Detail {

    @JsonProperty("dates.checkin")
    private List<String> datesCheckin = null;
    @JsonProperty("dates.checkout")
    private List<String> datesCheckout = null;
    @JsonProperty("destination")
    private List<String> destination = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("dates.checkin")
    public List<String> getDatesCheckin() {
        return datesCheckin;
    }

    @JsonProperty("dates.checkin")
    public void setDatesCheckin(List<String> datesCheckin) {
        this.datesCheckin = datesCheckin;
    }
    @JsonProperty("dates.checkout")
    public List<String> getDatesCheckout() {
        return datesCheckout;
    }

    @JsonProperty("dates.checkout")
    public void setDatesCheckout(List<String> datesCheckout) {
        this.datesCheckout = datesCheckout;
    }
    @JsonProperty("destination")
    public List<String> getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(List<String> destination) {
        this.destination = destination;
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

