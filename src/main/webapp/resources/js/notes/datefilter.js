DateFilterScripts = {
	bindClearDateFilter: function() {
		var clearFilter = function() {
			window.location.href = $(this).data('clraction');
		}
		$(".cancelDateFilter").click(clearFilter);
	},
	
	bindApplyDateFilter: function() {
		$("#submitDateFilterButton").click(function(event) {
			event.preventDefault();
			DateFilterScripts.applyDateFilter();
		});
	},
	bindSubmitWithEnter: function() {
		$("#dateFilterForm").each(function() {
	        $(this).find("input").keypress(function(e) {
	            if(e.which == 10 || e.which == 13) {
	            	DateFilterScripts.applyDateFilter();
	            }
	        });
	    });
	},
	applyDateFilter: function() {
		var form = $('#dateFilterForm');
		window.location.href = form.attr('action') + "?from=" + form.find('input#from').val() + "&to=" + form.find('input#to').val();
	},
	bindAll: function() {
		with(DateFilterScripts) {
			bindApplyDateFilter();
			bindClearDateFilter();
			bindSubmitWithEnter();
		}
	}
}

$(document).ready(function() {
	with(DateFilterScripts) {
		bindAll();
	}
});