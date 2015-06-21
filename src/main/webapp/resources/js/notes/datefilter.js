DateFilterScripts = {
	bindClearDateFilter: function() {
		var clearFilter = function() {
			window.location.href = $(this).data('clraction');
		}
		$(".cancelDateFilter").click(clearFilter);
	},
	
	bindApplyDateFilter: function() {
		$("#submitDateFilterButton").click(function() {
			$("#dateFilterForm").submit();
		});
	},
	bindSubmitWithEnter: function() {
		$("#dateFilterForm").each(function() {
	        $(this).find("input").keypress(function(e) {
	            if(e.which == 10 || e.which == 13) {
	                this.form.submit();
	            }
	        });
	    });
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