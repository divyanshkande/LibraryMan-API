package com.libraryman_api.service;

import org.springframework.stereotype.Service;

import com.libraryman_api.entity.Reminder;

@Service
public class NotificationService {

    public void sendReminder(Reminder reminder) {
       
        System.out.println(
            "Reminder sent for borrowingId: " + reminder.getBorrowingId() +
            " Type: " + reminder.getType()
        );
    }
}
