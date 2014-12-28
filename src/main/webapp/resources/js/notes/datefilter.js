NotesDateFilterScripts = {
	clearDateFilter: function() {
		$("#dateForm").find("input[name='date']" ).val("");
		$("#dateForm").submit();
	},
	
	submitDateFilterForm: function() {
		if ($("#dateForm").find( "input[name='date']" ).val().trim()) {
			$("#dateForm").submit();
		}
	}
}

$(document).ready(function() {
	$("#cancelDateFilter").click(NotesDateFilterScripts.clearDateFilter)
	$("#dateFilterFormSubmitButton").click(NotesDateFilterScripts.submitDateFilterForm);
});