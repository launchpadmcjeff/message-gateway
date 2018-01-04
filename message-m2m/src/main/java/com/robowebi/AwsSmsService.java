package com.robowebi;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Service
@Profile({ "prod" })
public class AwsSmsService implements SmsService {
	Logger LOG = LoggerFactory.getLogger(SmsService.class);
	
	@Override
	public String send(SmsMessage message) {
		AmazonSNSClient snsClient = new AmazonSNSClient();
		String phoneNumber = "+14422242359";
		Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
		// <set SMS attributes>
		sendSMSMessage(snsClient, message.templateId, phoneNumber, smsAttributes);
		return "42";
	}

	public void sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber,
			Map<String, MessageAttributeValue> smsAttributes) {
		PublishResult result = snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber)
				.withMessageAttributes(smsAttributes));
		LOG.info(result.toString()); // Prints the message ID.
	}
}
