package com.trimeo.Uploadservice.repository;
import com.trimeo.Uploadservice.domains.InboundUploadMessage;
import com.trimeo.Uploadservice.domains.UploadMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadRepository extends JpaRepository<InboundUploadMessage,Integer> {

   /* @Query(value = "SELECT m.id,m.destination,m.message,m.messageType, m.sendTime,m.sourceAddress FROM OutboundPayload m",nativeQuery = true)
    List<UploadMessage> fetchOutBoundPayload();*/
}
