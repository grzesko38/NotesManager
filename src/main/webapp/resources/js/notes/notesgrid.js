NotesGridScripts = {
	// === checkboxes === 
	bindSelectAll: function() {
		$("#selectAll").click(function() {
			$("input[id^='selections']").each(function() {
				this.checked = $("#selectAll").is(':checked');
			});
			NotesGridScripts.postAjaxSelectedIds($('#notesGridForm').data('checkboxajaxaction'));
		});
	},
	bindNoteCheckboxes: function() {
		$("input[id^='selections']").click(function() {
			NotesGridScripts.updateSelectAllCheckbox();
			NotesGridScripts.postAjaxSelectedIds($('#notesGridForm').data('checkboxajaxaction'));
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
	postAjaxSelectedIds: function(action) {
		var params = {
			    type: 'POST',
			    url: action,
			    data: JSON.stringify({"selections": NotesGridScripts.selectedCheckboxesToArray()}),
			    contentType:'application/json'
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
	
	// === entries per page ===
	bindEntriesPerPage: function() {
		$(".notesPageSize").each(
			function() {
				$(this).change(function() {
					window.location.href = $(this).data('action') + '?size=' + $(this).val();
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
                    		  $('#deleteAllNotes').unbind('click').click();
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
                    		  $('#deleteSelectedNotes').unbind('click').click();
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
			if (NotesGridScripts.selectedCheckboxesToArray().length > 0) {
				event.preventDefault();
				$( "#dialog-deleteSelected" ).dialog( "open" );
			}
		});
		
		$("#deleteAllNotes").click(function(event) {
			event.preventDefault();
			$( "#dialog-deleteAll" ).dialog( "open" );
		});
	},
	
	// === all ===
	bindAll: function() {
		with (NotesGridScripts) {
			bindSelectAll();
			bindNoteCheckboxes();
			bindEntriesPerPage();
			bindDeleteNotes();
		}
	}
}

$(document).ready(function() {
	with (NotesGridScripts) {
		updateSelectAllCheckbox();
		bindAll();
	}
});