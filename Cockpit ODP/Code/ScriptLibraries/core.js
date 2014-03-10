doLogoutDialog = function() {
	$("#confirmlogout").modal( {
		backdrop : "static"
	});
}

doLogout = function() {
	require( [ "dojo/cookie" ], function(cookie) {
		if (cookie("DomAuthSessId")) {
			cookie("DomAuthSessId", null, {
				path : "/",
				expires : "Thu, 01 Jan 1970 00:00:00 GMT"
			});
		}
		if (cookie("LtpaToken")) {
			cookie("LtpaToken", null, {
				path : "/",
				expires : "Thu, 01 Jan 1970 00:00:00 GMT"
			});
		}
		if (cookie("LtpaToken2")) {
			cookie("LtpaToken2", null, {
				path : "/",
				expires : "Thu, 01 Jan 1970 00:00:00 GMT"
			});
		}

		location.reload();
	})
}

require( [ "dojo/_base/lang", "ibm/xsp/widget/layout/DateTextBox",
		"ibm/xsp/widget/layout/TimeTextBox",
		"ibm/xsp/widget/layout/DateTimeTextBox" ], function(lang, DateTextBox,
		TimeTextBox, DateTimeTextBox) {
	var a = {};
	lang.mixin(a, {
		postCreate : function() {
			this.inherited(arguments);
		}
	});
	DateTextBox.extend(a);
	TimeTextBox.extend(a);
	DateTimeTextBox.extend(a);
});

dojo.addOnLoad( function() {
	dojo.query(".customerKey a").forEach( function(node, index, nodelist) {
		dojo.connect(node, "click", function(evt) {
			showKey(evt.target.innerHTML)
		})
	})
	$('.disabled').focus( function() {
		$(this).blur();
	});
	$('.nav-disabled').click( function(e) {
		e.preventDefault();
	});
	$(".datepicker").datepicker( {

	});
	$(".select-all").focus( function(e) {
		$(this).select();
	}).mouseup( function() {
		return false;
	});	
})