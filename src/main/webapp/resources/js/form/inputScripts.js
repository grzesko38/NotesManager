InputScripts = {
	CharCounter: function(input) {
		this.init = function() {
			with(InputScripts.CharCounter.prototype)
			{
				updateCharCount(input)
				bindCharCounter(input)
			}
		};
		
		this.bindCharCounter = function(input) {
			input.bind("input", function() {
				InputScripts.CharCounter.prototype.updateCharCount(input)
			});
		};
	}
}

InputScripts.CharCounter.prototype = {
		updateCharCount: function(input) {
			var text_length = input.val().length;
		    var text_remaining = input.data('maxchars') - text_length;
		    input.next().find('span').empty().text(text_remaining);
		},
		
		bindCharCounter: function(input) {
			input.bind("input", function() {
				InputScripts.CharCounter.prototype.updateCharCount(input)
			});
		}
		
	};