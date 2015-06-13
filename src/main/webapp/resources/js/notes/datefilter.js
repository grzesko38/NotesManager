NotesDateFilterScripts = {
	bindClearDateFilter: function() {
		$("#cancelDateFilter").click(function() {
			window.location.href = $(this).data('clraction');
		});
	},
	
	bindApplyDateFilter: function() {
		$("#dateFilterFormSubmitButton").click(function() {
			$("#dateForm").submit();
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