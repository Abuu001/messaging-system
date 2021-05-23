package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.amqp.Publisher;
import com.trimeo.Broadcastservice.domains.ConsumptionRates;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ContactService;
import com.trimeo.Broadcastservice.interfaces.OutboundService;
import com.trimeo.Broadcastservice.interfaces.ProcessorService;
import com.trimeo.Broadcastservice.interfaces.ValidationService;
import com.trimeo.Broadcastservice.repositories.ConsumptionRateRepository;
import com.trimeo.Broadcastservice.utils.DateUtils;
import com.trimeo.Broadcastservice.utils.SMSUtils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {

    @NonNull
    private final ValidationService validationService;

    @NonNull
    private final ContactService contactService;

    @NonNull
    private final ConsumptionRateRepository ratesRepository;

    @NonNull
    private final OutboundService outboundService;

    @NonNull
    private final SMSUtils smsUtils;

    @NonNull
    private final DateUtils dateUtils;

    @NonNull
    private final Publisher publisher;

    @Override
    public void incomingBroadcastPayload(BroadcastDTO broadcastDTO) {

        if(validationService.validateBroadcastPayload(broadcastDTO) &&
                validationService.messageNotPastExpiryDateTime(broadcastDTO.getExpiryTime()) &&
                validationService.shortCodeActiveAndExist(broadcastDTO.getSourceAddress())){

            if(broadcastDTO.isSend()){
                outboundService.createOutboundPayload(broadcastDTO);
            }else{
                chargeBroadcast(broadcastDTO);
            }
        }else {
            log.error(":::: Message Failed validation ::::");
        }
    }

    @Override
    public void chargeBroadcast(BroadcastDTO broadcastDTO) {

        log.info("Fetching consumption rate for client ::: " + broadcastDTO.getClientCode());
        ConsumptionRates rates = ratesRepository.findByClientId(broadcastDTO.getClientID());

        log.info("::::::::  Calculating sms cost ::::::::::::");
        double messageLength = SMSUtils.getPartCount(broadcastDTO.getMessage());
        double numberOfRecipients = contactService.fetchNumberContactsInBroadcast(broadcastDTO);
        double consumptionRate = rates.getRate();

        double smsCost = messageLength * numberOfRecipients * consumptionRate;
        log.info("Total sms cost ::::: " + smsCost + " units :::::");

        // TODO: Send to creditService for charging
        Boolean charged = true;

        if(charged){
            // todo: add db field for num of contacts, credits consumed & update on consume
            scheduleChargedBroadcast(broadcastDTO);
        }else {
            log.error("Failed to charge the broadcast message");
        }

    }

    @Override
    public void scheduleChargedBroadcast(BroadcastDTO broadcastDTO) {
        // set send to true already charged
        broadcastDTO.setSend(true);
        // since we will publish to delay queue we calculate delay as sendTime - now
        long delay = Timestamp.valueOf(broadcastDTO.getSendTime()).getTime() -
                Timestamp.valueOf(dateUtils.getZonedTimeNow()).getTime();

        log.info("delay :::::::: " + delay);
        // delay -ve means send time is past so send now ie. delay = 0;
        if(delay < 0){
            delay = 0;
            log.info(":::: Send date time is in past so send message now ::::");
        }
        // schedule broadcastSMS
        publisher.sendToDelayExchange(broadcastDTO, delay);
    }

}
