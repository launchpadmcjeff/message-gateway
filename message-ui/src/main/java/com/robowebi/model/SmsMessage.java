package com.robowebi.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

@Entity
public class SmsMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String toPhoneCountryCode;

	@Column
	private String toPhoneAreaCode;

	@Column
	private String toPhonePrefix;

	@Column
	private String toPhoneLine;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SmsMessage)) {
			return false;
		}
		SmsMessage other = (SmsMessage) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getToPhoneCountryCode() {
		return toPhoneCountryCode;
	}

	public void setToPhoneCountryCode(String toPhoneCountryCode) {
		this.toPhoneCountryCode = toPhoneCountryCode;
	}

	public String getToPhoneAreaCode() {
		return toPhoneAreaCode;
	}

	public void setToPhoneAreaCode(String toPhoneAreaCode) {
		this.toPhoneAreaCode = toPhoneAreaCode;
	}

	public String getToPhonePrefix() {
		return toPhonePrefix;
	}

	public void setToPhonePrefix(String toPhonePrefix) {
		this.toPhonePrefix = toPhonePrefix;
	}

	public String getToPhoneLine() {
		return toPhoneLine;
	}

	public void setToPhoneLine(String toPhoneLine) {
		this.toPhoneLine = toPhoneLine;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (toPhoneCountryCode != null && !toPhoneCountryCode.trim().isEmpty())
			result += "toPhoneCountryCode: " + toPhoneCountryCode;
		if (toPhoneAreaCode != null && !toPhoneAreaCode.trim().isEmpty())
			result += ", toPhoneAreaCode: " + toPhoneAreaCode;
		if (toPhonePrefix != null && !toPhonePrefix.trim().isEmpty())
			result += ", toPhonePrefix: " + toPhonePrefix;
		if (toPhoneLine != null && !toPhoneLine.trim().isEmpty())
			result += ", toPhoneLine: " + toPhoneLine;
		return result;
	}
}