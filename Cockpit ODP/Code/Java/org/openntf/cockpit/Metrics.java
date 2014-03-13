package org.openntf.cockpit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import lotus.domino.DateTime;
import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.NotesException;

import com.ibm.icu.text.SimpleDateFormat;
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
	private int dayOverdue = 2;
	private ArrayList<Ticket> overdue = null;

	public Metrics() {
		// calc all the values
		try {
			Document profile = ExtLibUtil.getCurrentDatabase().getView("profiles").getDocumentByKey("profile");
			if (profile != null) {
				this.dayOverdue = profile.getItemValueInteger("profileOverdue");
				profile.recycle();
			}

			this.ticketCount = ExtLibUtil.getCurrentDatabase().search("SELECT form=\"ticket\" & !(ticketStatus=\"90\" | ticketStatus=\"99\")").getCount();
			this.urgentCount = ExtLibUtil.getCurrentDatabase().search("SELECT form=\"ticket\" & ticketPriority=\"1\" & (ticketStatus!=\"90\" | ticketStatus!=\"99\")").getCount();
			this.solvedCount = ExtLibUtil.getCurrentDatabase().getView("ticketsSolved").getAllEntries().getCount();
			this.unassignedCount = ExtLibUtil.getCurrentDatabase().search("SELECT form=\"ticket\" & (ticketResponsible=\"\") & !(ticketStatus=\"90\" | ticketStatus=\"99\")").getCount();
			this.myCount = ExtLibUtil.getCurrentDatabase().getView("ticketsByResponsible").getAllEntriesByKey(ExtLibUtil.getCurrentSession().getEffectiveUserName()).getCount();
			this.overdue = new ArrayList<Ticket>();
			calcOverdue();
			calcDuration();
			calcQuota();
		} catch (NotesException e) {
			e.printStackTrace();
		}
	}

	private void calcOverdue() {
		// build a list of overdue tickets as an array as a document collection
		// doesn't work in a serializable object

		String search = "SELECT form=\"ticket\" & ticketUpdated < @Adjust(@Now;0;0;-" + this.dayOverdue + ";0;0;0) & (ticketStatus!=\"90\" & ticketStatus!=\"99\")";

		try {
			DocumentCollection col = ExtLibUtil.getCurrentDatabase().search(search);
			DateTime dt = null;

			Document doc = col.getFirstDocument();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
			while (doc != null) {
				dt = (DateTime) doc.getItemValueDateTimeArray("ticketUpdated").elementAt(0);
				this.overdue.add(new Ticket(doc.getUniversalID(), formatter.format(dt.toJavaDate()), doc.getItemValueString("ticketSubject"), ExtLibUtil.getCurrentSession().createName(
						doc.getItemValueString("ticketResponsible")).getCommon(), ExtLibUtil.getCurrentSession().createName(doc.getItemValueString("ticketResponsible")).getAbbreviated(), doc.getItemValueString("ticketStatus")));
				doc = col.getNextDocument(doc);
			}
			if (doc != null)
				doc.recycle();
			if (dt != null)
				dt.recycle();
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

	public ArrayList<Ticket> getOverdue() {
		return overdue;
	}

	public void setOverdue(ArrayList<Ticket> overdue) {
		this.overdue = overdue;
	}

}
