package kz.kaps.resort.dataproviders.sms;

import kz.kaps.resort.core.communication.SMSService;
import kz.kaps.resort.dataproviders.sms.soap.wsdl.Send;
import kz.kaps.resort.dataproviders.sms.soap.wsdl.SendResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SMSServiceImpl extends WebServiceGatewaySupport implements SMSService {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String SMS_PROVIDER_SOAP_URL = "url";

    @Override
    public void sendSMS(String recipientPhone, String textMessage) {
        Send sendRequest = new Send();

        sendRequest.setLogin(LOGIN);
        sendRequest.setPsw(PASSWORD);

        sendRequest.setPhones(recipientPhone);
        sendRequest.setMes(textMessage);

        SendResponse response = (SendResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SMS_PROVIDER_SOAP_URL, sendRequest);
        return;
    }
}
