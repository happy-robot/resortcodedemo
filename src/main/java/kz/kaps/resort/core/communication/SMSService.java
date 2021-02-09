package kz.kaps.resort.core.communication;

public interface SMSService {

    void sendSMS(String recipientPhone, String textMessage);

}
