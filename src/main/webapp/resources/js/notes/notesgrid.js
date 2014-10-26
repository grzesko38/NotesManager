NotesGridScripts = {
	toggleAll: function() {
		$("input[id^='selections']").each(function() {
			this.checked = $("#selectAll").is(':checked');
		});
	},
	updateCheckboxes: function() {
		var allChecked  = true;
		$("input[name='selections']").each(function() {
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
	handleEntriesPerPageForms: function() {
		$(".entriesPerPage").each(
			function() {
				$(this).change(function() {
					$(this).parents('form:first').submit();
				})
			}
		);
	}
}

$(document).ready(function() {
	$("#selectAll").change(NotesGridScripts.toggleAll);
	$("input[id^='selections']").change(NotesGridScripts.updateCheckboxes);
	NotesGridScripts.handleEntriesPerPageForms();
});