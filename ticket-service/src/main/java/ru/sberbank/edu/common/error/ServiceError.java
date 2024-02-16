package ru.sberbank.edu.common.error;

import lombok.Data;

@Data
public class ServiceError {

    private int status;
    private String details;
    private long timeStamp;
}
