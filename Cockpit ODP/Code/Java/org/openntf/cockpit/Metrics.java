package org.openntf.cockpit;

import java.io.Serializable;

import lotus.domino.NotesException;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class Metrics implements Serializable {

	private static final long serialVersionUID = 1473132006122124544L;
	private int ticketCount = 0;
	private int urgentCount = 0;
	private int solvedCount = 0;
	private int unassignedCount = 0;
	private int myCount = 0;
	private float duration = 0;
	private double quota = 0;

	public Metrics() {
		// calc all the values
		try {
			this.ticketCount = ExtLibUtil.getCurrentDatabase().search("SELECT form=\"ticket\" & !(ticketStatus=\"90\" | ticketStatus=\"99\")").getCount();
			this.urgentCount = ExtLibUtil.getCurrentDatabase().search("SELECT form=\"ticket\" & ticketPriority=\"1\" & (ticketStatus!=\"90\" | ticketStatus!=\"99\")").getCount();
			this.solvedCount = ExtLibUtil.getCurrentDatabase().getView("ticketsSolved").getAllEntries().getCount();
			this.unassignedCount = ExtLibUtil.getCurrentDatabase().search("SELECT form=\"ticket\" & (ticketResponsible=\"\") & !(ticketStatus=\"90\" | ticketStatus=\"99\")").getCount();
			this.myCount = ExtLibUtil.getCurrentDatabase().getView("ticketsByResponsible").getAllEntriesByKey(ExtLibUtil.getCurrentSession().getEffectiveUserName()).getCount();
			calcDuration();
			calcQuota();
		} catch (NotesException e) {
			e.printStackTrace();
		}
	}

	private void calcDuration() {

	}

	private void calcQuota() {
		try {
			this.quota = (this.solvedCount * 100.0f) / this.ticketCount;
		} catch (Exception e) {

		}
	}

	public int getMyCount() {
		return myCount;
	}

	public void setMyCount(int myCount) {
		this.myCount = myCount;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public int getUrgentCount() {
		return urgentCount;
	}

	public void setUrgentCount(int urgentCount) {
		this.urgentCount = urgentCount;
	}

	public int getSolvedCount() {
		return solvedCount;
	}

	public void setSolvedCount(int solvedCount) {
		this.solvedCount = solvedCount;
	}

	public int getUnassignedCount() {
		return unassignedCount;
	}

	public void setUnassignedCount(int unassignedCount) {
		this.unassignedCount = unassignedCount;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public double getQuota() {
		return quota;
	}

	public void setQuota(float quota) {
		this.quota = quota;
	}

}
