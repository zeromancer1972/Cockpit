package org.openntf.cockpit;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import lotus.domino.Document;
import lotus.domino.Item;
import lotus.domino.Name;

import org.openntf.domino.utils.XSPUtil;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class History implements Serializable {

	private static final long serialVersionUID = 1809269965845338393L;

	public History() {

	}

	public void addHistory(Document doc, boolean isNew) {
		try {
			if (isNew) {
				this.addHistory(doc, "QuickTicket");
			} else {
				this.addHistory(doc, "Ticket updated");
			}
		} catch (Exception e) {

		}
	}

	public void addHistory(Document doc, String msg) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH);
		String now = formater.format(new Date());
		Name user = null;
		Item it = null;
		try {
			user = XSPUtil.getCurrentSession().createName(ExtLibUtil.getCurrentSession().getEffectiveUserName());
			msg = user.getAbbreviated() + "::" + now + "::" + msg;
			if (doc.hasItem("ticketHistory")) {
				it = doc.getFirstItem("ticketHistory");
				it.appendToTextList(msg);
			} else {
				doc.replaceItemValue("ticketHistory", user.getAbbreviated() + "::" + now + "::Ticket created");
			}
			doc.save();
			it.recycle();
		} catch (Exception e) {

		}
	}
}
