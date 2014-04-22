package org.openntf.cockpit;

import java.io.Serializable;
import java.util.Vector;

import lotus.domino.Document;
import lotus.domino.Session;

import org.openntf.domino.utils.XSPUtil;

public class RightsManagement implements Serializable {
	private static final long serialVersionUID = 8157811105680678241L;

	public RightsManagement() {
	}

	/**
	 * Calculate all name fields to canonical form
	 * 
	 * @param doc
	 */
	@SuppressWarnings("unchecked")
	public void setNames(Document doc) {
		Session session = XSPUtil.getCurrentSession();
		Vector names = null;
		// item names as array
		String items[] = { "ticketResponsible" };
		try {
			for (String item : items) {
				if (doc.hasItem(item)) {
					names = session.evaluate("@Name([Canonicalize]; " + item + ")", doc);
					doc.replaceItemValue(item, names);
				}
			}
			doc.save();
		} catch (Exception e) {
		}
	}
}