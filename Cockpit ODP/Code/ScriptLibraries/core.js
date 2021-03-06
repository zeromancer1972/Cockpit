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

$(document).on(
		'click',
		'.panel-heading span.clickable',
		function(e) {
			var $this = $(this);
			if (!$this.hasClass('panel-collapsed')) {
				$this.parents('.panel').find('.panel-body').hide();
				$this.addClass('panel-collapsed');
				$this.find('i').removeClass('glyphicon-chevron-up').addClass(
						'glyphicon-chevron-down');
			} else {
				$this.parents('.panel').find('.panel-body').show();
				$this.removeClass('panel-collapsed');
				$this.find('i').removeClass('glyphicon-chevron-down').addClass(
						'glyphicon-chevron-up');
			}
		});
dojo._xhr = dojo.xhr;
var loadOld;
function hijacked(response, ioArgs) {
	// your code or function here (pre-processed)
	loadOld(response, ioArgs);
	// your code or function here (post-processed)
	$("[data-toggle=tooltip]").tooltip( {

	});
}
dojo.xhr = function(mode, args, bool) {
	loadOld = args["load"];
	args["load"] = hijacked;
	dojo._xhr(mode, args, bool);
}