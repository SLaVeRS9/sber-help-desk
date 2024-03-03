package ru.sberbank.edu.common.error;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ServiceError {

    private int status;
    private String details;
    private long timeStamp;
}
