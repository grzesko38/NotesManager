NotesDateFilterScripts = {
	clearDateFilter: function() {
		$("#dateForm").find("input[name='date']" ).val("");
//		$("#dateForm").submit();
		
		$.ajax({
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			method: "GET",
		    url: "/NotesManager/notesmanager/resetDateFilter.ajax",
		    data: JSON.stringify({"selections":["521"]}),
		    dataType: 'json',
		});
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