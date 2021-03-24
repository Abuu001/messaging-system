package com.trimeo.Broadcastservice.mongo_sequences;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "outbound_sequence")
public class OutboundSequence {

    @Id
    @Field("_id")
    private String id;

    private Integer seq;
}
