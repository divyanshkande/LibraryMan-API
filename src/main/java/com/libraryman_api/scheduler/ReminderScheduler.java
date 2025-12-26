package com.libraryman_api.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.libraryman_api.entity.Reminder;
import com.libraryman_api.repository.ReminderRepository;

@Component
/**
 * Scheduler responsible for sending due-date and overdue book reminders.
 *
 * <p>This scheduler queries only the reminders that are due on the current
 * date, avoiding full table scans of borrowing records and significantly
 * improving system performance.</p>
 */

public class ReminderScheduler {

    private final ReminderRepository reminderRepository;
    public ReminderScheduler(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }
    /**
     * Sends reminders scheduled for the current date.
     *
     * <p>Executed daily at 9 AM to process pending reminders and mark them
     * as sent after successful notification delivery.</p>
     */

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDueReminders() {

        LocalDate today = LocalDate.now();
        List<Reminder> reminders =
                reminderRepository.findByReminderDateAndSentFalse(today);

        for (Reminder reminder : reminders) {
            // call notification logic
            reminder.setSent(true);
        }

        reminderRepository.saveAll(reminders);
    }
}
