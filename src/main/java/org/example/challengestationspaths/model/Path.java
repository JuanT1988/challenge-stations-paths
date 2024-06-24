package org.example.challengestationspaths.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.challengestationspaths.dto.request.PathDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Path implements Serializable, Comparable<Path> {
    @Serial
    private static final long serialVersionUID = 1798134163427358590L;

    @JsonProperty("path_id")
    private long pathId;
    @JsonProperty("source_id")
    private long sourceId;
    @JsonProperty("destination_id")
    private long destinationId;
    @JsonProperty("cost")
    private double cost;

    public Path() {}

    public Path(long sourceId, long destinationId) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }

    public Path(long pathId, PathDTO path) {
        this.pathId = pathId;
        this.sourceId = path.getSourceId();
        this.destinationId = path.getDestinationId();
        this.cost = path.getCost();
    }

    public long getPathId() {
        return pathId;
    }

    public void setPathId(long pathId) {
        this.pathId = pathId;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return (sourceId == path.sourceId && destinationId == path.destinationId)
                || (sourceId == path.destinationId && destinationId == path.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, destinationId);
    }

    @Override
    public int compareTo(Path o) {

        if (this.cost > o.cost) {
            return 1;
        }
        return 0;
    }
}
