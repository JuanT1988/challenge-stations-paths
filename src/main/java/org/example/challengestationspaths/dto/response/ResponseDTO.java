package org.example.challengestationspaths.dto.response;

import java.io.Serial;
import java.io.Serializable;

public class ResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2483506303246364006L;

    private String status;

    public ResponseDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
