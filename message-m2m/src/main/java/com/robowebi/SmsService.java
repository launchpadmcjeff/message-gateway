package com.robowebi;

/**
 * 
 * This interface was factored out to allow for AwsSmsService in prod environments and LoggingSmsService in all other.
 * That way we can use a prod profile for the AWS bean and avoid running up service charges and spamming.
 */
public interface SmsService {
	public abstract String send(SmsMessage message);
}
