package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.domains.Contactlist;
import com.trimeo.Broadcastservice.domains.Contacts;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ContactService;
import com.trimeo.Broadcastservice.repositories.ContactlistRepository;
import com.trimeo.Broadcastservice.repositories.ContactsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    @NonNull
    private final ContactlistRepository contactlistRepository;

    @NonNull
    private final ContactsRepository contactsRepository;

    @Override
    public int fetchNumberContactsInBroadcast(BroadcastDTO broadcastDTO) {
        return fetchContactsForBroadcast(broadcastDTO).size();
    }

    @Override
    public Set<String> fetchContactsForBroadcast(BroadcastDTO broadcastDTO) {

        ArrayList<Integer> listIds = new ArrayList<>();
        Set<Integer> contactIds = new HashSet<>();
        Set<String> contacts = new HashSet<>();

        for(int i = 0; i < broadcastDTO.getListIDs().split(",").length; i++){
            listIds.add(Integer.valueOf(broadcastDTO.getListIDs().split(",")[i]));
        }

        for(Contactlist var : contactlistRepository.findContactId(listIds)){
            contactIds.add(var.getContactID());
        }

        for(Contacts msisdn : contactsRepository.findContactsBy(contactIds)){
            contacts.add(msisdn.getMsisdn());
        }

        return contacts;
    }
}
