package com.gwizd.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class FirebaseMessageService {
    private final FirebaseMessaging firebaseMessaging;
    public void sendMessage(Map<String, String> details) {
        Message msg = Message.builder()
                .setTopic("allUsers")
                .putAllData(details)
                .build();

        try {
            firebaseMessaging.send(msg);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
