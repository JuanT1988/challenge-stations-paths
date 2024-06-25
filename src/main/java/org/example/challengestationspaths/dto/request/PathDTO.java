package org.example.challengestationspaths.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"cost", "source_id", "destination_id"})
public class PathDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5859452354387153769L;

    @JsonProperty("cost")
    private double cost;
    @JsonProperty("source_id")
    private long sourceId;
    @JsonProperty("destination_id")
    private long destinationId;

    public PathDTO() {
    }

    public PathDTO(long sourceId, long destinationId, double cost) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }
}
