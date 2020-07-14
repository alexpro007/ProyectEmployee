package com.employee.api.exception;

import java.util.Date;

public class DetailException {
	private Date dateException;
	private String messageException;
	private String detailsExcption;

	public DetailException(Date dateException, String messageException, String detailsException) {
		this.dateException = dateException;
		this.messageException = messageException;
		this.detailsExcption = detailsException;
	}

	public Date getDateException() {
		return dateException;
	}

	public void setDateException(Date dateException) {
		this.dateException = dateException;
	}

	public String getMessageException() {
		return messageException;
	}

	public void setMessageException(String messageException) {
		this.messageException = messageException;
	}

	public String getDetailsExcption() {
		return detailsExcption;
	}

	public void setDetailsExcption(String detailsExcption) {
		this.detailsExcption = detailsExcption;
	}

}
