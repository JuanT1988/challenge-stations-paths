package org.example.challengestationspaths.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"path", "cost"})
public class PathResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8562049279695622138L;

    private List<Long> path;
    private double cost;

    public PathResponseDTO() {
        path = new ArrayList<Long>();
        cost = Double.POSITIVE_INFINITY;
    }

    public List<Long> getPath() {
        return path;
    }

    public void setPath(List<Long> path) {
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
