package org.example.challengestationspaths.dto.request;

import java.io.Serial;
import java.io.Serializable;

public class StationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3351619821506116379L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
