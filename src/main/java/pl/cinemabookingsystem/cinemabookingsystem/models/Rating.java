
package pl.cinemabookingsystem.cinemabookingsystem.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Source",
        "Value"
})
@Entity
public class Rating {
    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("Source")
    private String source;
    @JsonProperty("Value")
    private String value;

    @JsonProperty("Source")
    public String getSource() {
        return source;
    }

    @JsonProperty("Source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("Value")
    public String getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(String value) {
        this.value = value;
    }


}
