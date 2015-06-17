AddNoteScripts = {
	bindCharCounters: function() {
		$('textarea[data-maxchars]').each(function() {
			$(this).bind("input", function() {
				AddNoteScripts.updateCharCount(this);
			});
		});
	},
	
	updateAllCharCounts: function() {
		$('textarea[data-maxchars]').each(function() {
			AddNoteScripts.updateCharCount(this);
		});
	},
	
	updateCharCount: function(textArea) {
		var text_length = $(textArea).val().length;
        var text_remaining = $(textArea).data('maxchars') - text_length;
        $(textArea).next().find('span').empty().text(text_remaining);
	},
	
	bindAll: function() {
		with (AddNoteScripts) {
			bindCharCounters();
		}
	}
}

$(document).ready(function() {
	with (AddNoteScripts) {
		updateAllCharCounts();
		bindAll();
	}
});