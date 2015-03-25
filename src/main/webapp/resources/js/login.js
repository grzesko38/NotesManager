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
	bindAll: function() {
		LoginScripts.bindLogin();
		LoginScripts.bindLogout();
	}
}

$( document ).ready(function() {
	LoginScripts.bindAll();
});