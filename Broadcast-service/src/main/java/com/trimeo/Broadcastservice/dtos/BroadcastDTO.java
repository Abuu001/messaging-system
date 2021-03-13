package com.trimeo.Broadcastservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime sendTime;

    @NotNull(message = "expiryTime cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime expiryTime;

    @Positive
    @NotNull(message = "Payload must contain userID")
    private Integer userID;

    @NotNull(message = "clientCode cannot be null")
    @NotBlank(message = "clientCode cannot be blank")
    private String clientCode;

    @NotBlank
    @NotNull
    private boolean isRecurring;

}
