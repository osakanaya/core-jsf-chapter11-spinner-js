package com.corejsf;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named("cardExpirationDate")
@SessionScoped
public class CreditCardExpiration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int month = 1;
	private int year = 2010;
	private int changes = 0;
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}

	public int getChanges() {
		return changes;
	}
	
	public void changeListener(ValueChangeEvent e) {
		if (!e.getNewValue().toString().equals(e.getOldValue())) {
			changes++;
		}
	}
}
