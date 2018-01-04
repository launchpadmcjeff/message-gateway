package com.robowebi;

import java.util.Map;

public class SmsMessage {

	public String smsNumber;
	
	public String templateId;
	
	private Map<String, String> templateParameters;

	public SmsMessage() {
	}

	public SmsMessage(String smsNumber, String templateId, Map<String, String> templateParameters) {
		this.smsNumber = smsNumber;
		this.templateId = templateId;
		this.templateParameters = templateParameters;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public String getTemplateId() {
		return templateId;
	}

	@Override
	public String toString() {
		return "SmsMessage [smsNumber=" + smsNumber + ", templateId=" + templateId + ", templateParameters="
				+ templateParameters + "]";
	}


	
}
