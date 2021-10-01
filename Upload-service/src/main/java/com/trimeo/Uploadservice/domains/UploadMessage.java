package com.trimeo.Uploadservice.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "upload_message")
public class UploadMessage {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;*/

    @Id
    @Column(unique = true)
    private String id;

    @NotNull(message = "destination cannot be empty")
    private String destination;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String messageType;

    private LocalDateTime sendTime;

    @NotNull(message = "Source cannot be empty")
    private String sourceAddress;

    private Integer noOfSends;

}
