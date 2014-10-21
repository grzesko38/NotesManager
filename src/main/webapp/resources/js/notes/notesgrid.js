NotesGridScripts = {
	toggleAll: function() {
		$("input[id^='selectedNotesIds']").each(function() {
			this.checked = $("#selectAll").is(':checked');
		});
	},
	updateCheckboxes: function() {
		var allChecked  = true;
		$("input[name='selectedNotesIds']").each(function() {
			if( this.checked ) {
				allUnchecked = false;
			} else {
				allChecked = false;
			}
		});
		if (allChecked) {
			$("#selectAll").prop('checked', true);
		} else {
			$("#selectAll").prop('checked', false);
		}
	},
	submitEntriesPerPageForm: function() {
		$("#entriesPerPageForm").submit();
	}
}

$(document).ready(function() {
	$("#selectAll").change(NotesGridScripts.toggleAll);
	$("input[id^='selectedNotesIds']").change(NotesGridScripts.updateCheckboxes);
	$("#entriesPerPage").change(NotesGridScripts.submitEntriesPerPageForm);
});