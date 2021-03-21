package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.amqp.Publisher;
import com.trimeo.Broadcastservice.domains.ConsumptionRates;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ProcessorService;
import com.trimeo.Broadcastservice.repositories.ConsumptionRateRepository;
import com.trimeo.Broadcastservice.repositories.ContactlistRepository;
import com.trimeo.Broadcastservice.utils.DateUtils;
import com.trimeo.Broadcastservice.utils.SMSUtils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {

    @NonNull
    private final ValidationServiceImpl validationService;

    @NonNull
    private final ContactlistRepository contactlistRepository;

    @NonNull
    private final ConsumptionRateRepository ratesRepository;

    @NonNull
    private final SMSUtils smsUtils;

    @NonNull
    private final DateUtils dateUtils;

    @NonNull
    private final Publisher publisher;

    @Override
    public void incomingBroadcastPayload(BroadcastDTO broadcastDTO) {

        if(validationService.validateBroadcastPayload(broadcastDTO) &&
        validationService.shortCodeActiveAndExist(broadcastDTO.getSourceAddress())){

            if(broadcastDTO.isSend()){
                //TODO: Call service incharge of messaging sending
                log.info(">>>>>>> Sending this message asap <<<<<<<<<<<<<");
            }else{
                chargeBroadcast(broadcastDTO);
            }
        }
    }

    @Override
    public int fetchNumberContactsInBroadcast(BroadcastDTO broadcastDTO) {
        return fetchContactsForBroadcast(broadcastDTO).size();
    }

    @Override
    public Set<String> fetchContactsForBroadcast(BroadcastDTO broadcastDTO) {

        ArrayList<Integer> listIds = new ArrayList<>();

        for(int i = 0; i < broadcastDTO.getListIDs().split(",").length; i++){
            listIds.add(Integer.valueOf(broadcastDTO.getListIDs().split(",")[i]));
        }

        return contactlistRepository.findContactId(listIds);
    }

    @Override
    public void chargeBroadcast(BroadcastDTO broadcastDTO) {

        log.info("Fetching consumption rate for client ::: " + broadcastDTO.getClientCode());
        ConsumptionRates rates = ratesRepository.findByClientId(broadcastDTO.getClientID());

        log.info("::::::::  Calculating sms cost ::::::::::::");
        double messageLength = SMSUtils.getPartCount(broadcastDTO.getMessage());
        double numberOfRecipients = fetchNumberContactsInBroadcast(broadcastDTO);
        double consumptionRate = rates.getRate();

        double smsCost = messageLength * numberOfRecipients * consumptionRate;
        log.info("Total sms cost ::::: " + smsCost + " units :::::");

        // TODO: Send to creditService for charging
        Boolean charged = true;

        if(charged){
            // todo: add db field for num of contacts, credits consumed & update on consume
            scheduleChargedBroadcast(broadcastDTO);
        }else {
            log.info("Failed to charge the broadcast message");
        }

    }

    @Override
    public void scheduleChargedBroadcast(BroadcastDTO broadcastDTO) {
        // TODO: Call publisher to publish message to delay exchange ttl(time to send message)
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
