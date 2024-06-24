package org.example.challengestationspaths.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"station_id", "name"})
public class Station implements Serializable {

    @Serial
    private static final long serialVersionUID = 3279430681336302921L;

    @JsonProperty("station_id")
    private long stationId;
    @JsonProperty("name")
    private String name;

    public Station() {}

    public Station(long stationId, String name) {
        this.stationId = stationId;
        this.name = name;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
