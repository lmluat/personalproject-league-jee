package com.lmluat.league.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ResponseBody {
    private final String errorMessage;
    private final String errorKey;

    private final Response.Status status;
    private final UUID uuid;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;

    public ResponseBody(String errorMessage, String errorKey, Response.Status status) {
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.uuid = UUID.randomUUID();
    }
}
