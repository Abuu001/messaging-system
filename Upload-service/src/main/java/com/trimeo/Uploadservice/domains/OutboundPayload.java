package com.trimeo.Uploadservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundPayload {

   private String destination;
   private String message;
   private String messageType;
   private String sendTime;
   private String sourceAddress;
   private Integer status;

}
