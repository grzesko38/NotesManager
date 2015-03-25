LoginScripts = {
	bindLogin: function() {
		$("#loginAction").click(function() {
			$("#topBarLoginForm").submit();
		});
	},
	bindLogout: function() {
		$("#logoutAction").click(function() {
			$("#topBarLogoutForm").submit();
		});
	},
	bindSubmitWithEnter: function() {
		$("#topBarLoginForm").each(function() {
	        $(this).find("input").keypress(function(e) {
	            if(e.which == 10 || e.which == 13) {
	                this.form.submit();
	            }
	        });

	        $(this).find('input[type=submit]').hide();
	    });
	},
	bindAll: function() {
		LoginScripts.bindLogin();
		LoginScripts.bindLogout();
		LoginScripts.bindSubmitWithEnter();
	}
}

$( document ).ready(function() {
	LoginScripts.bindAll();
});