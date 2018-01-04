package com.robowebi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "!prod" })
public class LoggingSmsService implements SmsService {
	private final Logger log = LoggerFactory.getLogger(LoggingSmsService.class);

	@Override
	public String send(SmsMessage message) {
		log.info(message.toString());
		return "42";
	}

}
