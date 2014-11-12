NotesGridScripts = {
	// === checkboxes === 
	toggleAll: function() {
		$("input[id^='selections']").each(function() {
			this.checked = $("#selectAll").is(':checked');
		});
		NotesGridScripts.postAjaxSelectedIds();
	},
	updateSelectAllCheckbox: function() {
		var allChecked  = true;
		$("input[name='selections']").each(function() {
			if( !this.checked ) {
				allChecked = false;
			}
		});
		$("#selectAll").prop('checked', allChecked);
	},
	postAjaxSelectedIds: function() {
		var params = {
				headers: { 
			        'Accept': 'application/json',
			        'Content-Type': 'application/json' 
			    },
			    type: 'POST',
			    url: '/NotesManager/notesmanager/updateSelections.ajax',
			    data: JSON.stringify({"selections": NotesGridScripts.selectedCheckboxesToArray()}),
			    dataType: 'json',
		};
		$.ajax(params);
	},
	selectedCheckboxesToArray: function() {
		var inputs = $("input[id^='selections']:checked");
	    var arr = [];
	    for (var i = 0; i < inputs.length; i++) {
	        if (inputs[i].checked)
	            arr.push(inputs[i].value);
	    }
	    return arr;
	},
	
	// === entries per page form ===
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
	$("#selectAll").click(NotesGridScripts.toggleAll);
	$("input[id^='selections']").click(function() {
		NotesGridScripts.updateSelectAllCheckbox();
		NotesGridScripts.postAjaxSelectedIds();
	});
	NotesGridScripts.updateSelectAllCheckbox();
	NotesGridScripts.handleEntriesPerPageForms();
	
	
});