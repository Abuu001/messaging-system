package com.trimeo.Uploadservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "upload_files")
public class InboundUploadMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Please upload a file")
    private String filename;

    @NotNull(message = "Source cannot be empty")
    private String sourceAddress;

    private Integer status;

}
