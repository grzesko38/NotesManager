NotesGridScripts = {
	// === checkboxes === 
	bindSelectAll: function() {
		$("#selectAll").click(function() {
			$("input[id^='selections']").each(function() {
				this.checked = $("#selectAll").is(':checked');
			});
			NotesGridScripts.postAjaxSelectedIds();
		});
	},
	bindNoteCheckbox: function() {
		$("input[id^='selections']").click(function() {
			NotesGridScripts.updateSelectAllCheckbox();
			NotesGridScripts.postAjaxSelectedIds();
		});
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
			    url: '/NotesManager/notesmanager/updateSelections.json',
			    data: JSON.stringify({"selections": NotesGridScripts.selectedCheckboxesToArray()}),
			    dataType: 'json',
		};
		$.ajax(params);
		$("#dialog-deleteSelected > span").html(NotesGridScripts.selectedCheckboxesToArray().length);
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
	bindEntriesPerPage: function() {
		$(".entriesPerPage").each(
			function() {
				$(this).change(function() {
					$(this).parents('form:first').submit();
				})
			}
		);
	},
	
	// === delete buttons ===
	bindDeleteNotes: function() {
		$('#dialog-deleteAll').dialog({
            autoResize: false,
//            show: "puff",
//            hide: "puff",
            height: 'auto',
            width: 'auto',
            autoOpen: false,
            modal: true,
            position: { my: "center", at: "center", of: window },
            draggable: true,
            closeText: $("#popupI18NData").data("close"),
            buttons: [
                      {
                    	  text: $("#popupI18NData").data("yes"),
                    	  click : function() {
                    		  NotesGridScripts.submitDeleteAllNotes();
                    	  }
                      },
                      {
	                      text: $("#popupI18NData").data("no"),
	                	  click: function() {
	                		  $(this).dialog("close");
	                	  }
                      }
                     ]
        });
		
		$('#dialog-deleteSelected').dialog({
            autoResize: false,
//            show: "puff",
//            hide: "puff",
            height: 'auto',
            width: 'auto',
            autoOpen: false,
            modal: true,
            position: { my: "center", at: "center", of: window },
            draggable: true,
            closeText: $("#popupI18NData").data("close"),
            buttons: [
                      {
                    	  text: $("#popupI18NData").data("yes"),
                    	  click : function() {
                    		  NotesGridScripts.submitDeleteSelectedNotes();
                    	  }
                      },
                      {
	                      text: $("#popupI18NData").data("no"),
	                	  click: function() {
	                		  $(this).dialog("close");
	                	  }
                      }
                     ]
        });
		
		$("#deleteSelectedNotes").click(function(event) {
			if (NotesGridScripts.selectedCheckboxesToArray().length === 0) {
				NotesGridScripts.submitDeleteSelectedNotes();
			} else {
				$( "#dialog-deleteSelected" ).dialog( "open" );
			}
		});
		
		$("#deleteAllNotes").click(function(event) {
			$( "#dialog-deleteAll" ).dialog( "open" );
		});
	},
	
	submitDeleteAllNotes: function() {
		var input = $("<input>").attr("type", "hidden").attr("name", "delete").val("all");
		$('#notesGridForm').append($(input));
		$("#notesGridForm").submit();
	},
	
	submitDeleteSelectedNotes: function() {
		var input = $("<input>").attr("type", "hidden").attr("name", "delete").val("selected");
		$('#notesGridForm').append($(input));
		$("#notesGridForm").submit();
	},
	
	// === all ===
	bindAll: function() {
		NotesGridScripts.bindSelectAll();
		NotesGridScripts.bindNoteCheckbox();
		NotesGridScripts.bindEntriesPerPage();
		NotesGridScripts.bindDeleteNotes();
	}
}

$(document).ready(function() {
	NotesGridScripts.updateSelectAllCheckbox();
	NotesGridScripts.bindAll();
});