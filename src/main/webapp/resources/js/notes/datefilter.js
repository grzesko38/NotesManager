NotesDateFilterScripts = {
	clearDateFilter: function() {
//		$("#dateForm").find("input[name='date']" ).val("");
//		$("#dateForm").submit();
		
		var params = {
			url: $("#dateForm").attr("action"),
			data: {
				date: ""
			},
			dataType: "html",
			success: function(data) {$("html").html(data);},
			type: "get"
		};
		$.ajax(params);
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