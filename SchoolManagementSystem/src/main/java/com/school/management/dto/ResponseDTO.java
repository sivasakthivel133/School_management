package com.school.management.dto;

import com.school.management.entity.School;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private String message;
    private Object data;
    private String statusValue;

    public ResponseDTO(String message, Object data, String statusValue) {
        this.message = message;
        this.data = data;
        this.statusValue = statusValue;
    }

    public ResponseDTO() {

    }

    public ResponseDTO(String success, School school) {
    }

}


