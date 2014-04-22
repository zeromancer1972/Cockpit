package org.openntf.cockpit;

import java.io.Serializable;

import lotus.domino.Database;
import lotus.domino.NotesException;

import org.openntf.domino.utils.XSPUtil;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class Acl implements Serializable {

	private static final long serialVersionUID = -1078384153186411254L;
	private boolean createDocuments = false;

	public Acl() {
		try {
			this.createDocuments = (XSPUtil.getCurrentDatabase().queryAccessPrivileges(ExtLibUtil.getCurrentSession().getEffectiveUserName()) & Database.DBACL_CREATE_DOCS) > 0;
		} catch (NotesException e) {

			e.printStackTrace();
		}
	}

	public boolean isCreateDocuments() {
		return createDocuments;
	}

	public void setCreateDocuments(boolean createDocuments) {
		this.createDocuments = createDocuments;
	}
}
