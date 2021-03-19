package com.trimeo.Broadcastservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BroadcastDTO {

    @Positive
    @NotNull(message = "Payload must contain broadcastID")
    private Integer broadcastID;

    @NotBlank(message = "broadcast must have message")
    @NotNull(message = "message cannot be null")
    private String message;

    @NotEmpty(message = "sourceAddress must be provided")
    @NotNull(message = "sourceAddress cannot be null")
    private String sourceAddress;

    @NotBlank(message = "listIDs should be provided")
    @NotNull(message = "listIDs cannot be null")
    private String listIDs;

    @NotNull(message = "expiryTime cannot be null")
    @Pattern(regexp = "\\d{4}-[01]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d(?:\\.\\d+)?Z?", message = "SendTime must be of the format yyyy-MM-dd HH:mm:ss.SSS")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private String sendTime;

    @NotNull(message = "expiryTime cannot be null")
    @Pattern(regexp = "\\d{4}-[01]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d(?:\\.\\d+)?Z?", message = "ExpiryTime must be of the format yyyy-MM-dd HH:mm:ss.SSS")
    private String expiryTime;

    @Positive
    @NotNull(message = "Payload must contain userID")
    private Integer userID;

    @NotNull(message = "clientCode cannot be null")
    @NotBlank(message = "clientCode cannot be blank")
    private String clientCode;

    @NotNull
    private boolean isRecurring;

    private boolean send = false;
}
