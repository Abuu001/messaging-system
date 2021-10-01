package com.trimeo.Uploadservice.repository;

import com.trimeo.Uploadservice.domains.UploadMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadMessageRepository extends JpaRepository<UploadMessage,Integer> {

}
