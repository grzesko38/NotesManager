NotesDateFilterScripts = {
	bindClearDateFilter: function() {
		$("#cancelDateFilter").click(function() {
			$("#dateForm").find("input[name='date']" ).val("");
			$("#dateForm").submit();
		});
	},
	
	bindApplyDateFilter: function() {
		$("#dateFilterFormSubmitButton").click(function() {
			if ($("#dateForm").find( "input[name='date']" ).val().trim()) {
				$("#dateForm").submit();
			}
		});
	},
	bindAll: function() {
		NotesDateFilterScripts.bindApplyDateFilter();
		NotesDateFilterScripts.bindClearDateFilter();
	}
}

$(document).ready(function() {
	NotesDateFilterScripts.bindAll();
});