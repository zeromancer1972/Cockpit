package org.openntf.cockpit;

import java.io.Serializable;

public class Ticket implements Serializable {

	private static final long serialVersionUID = -5758181686762996842L;
	private String unid;
	private String created;
	private String subject;
	private String assigned;
	private String abbreviated;

	public Ticket(String unid, String created, String subject, String assigned, String abbreviated) {
		this.unid = unid;
		this.created = created;
		this.subject = subject;
		this.assigned = assigned;
		this.abbreviated = abbreviated;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}

}
