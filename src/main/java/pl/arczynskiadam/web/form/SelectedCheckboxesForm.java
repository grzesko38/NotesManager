package pl.arczynskiadam.web.form;

import java.util.HashSet;
import java.util.Set;

public class SelectedCheckboxesForm {
	{
		selections = new HashSet<String>();
	}
	
	private Set<String> selections;

	public Set<String> getSelections() {
		return selections;
	}

	public void setSelections(Set<String> selections) {
		this.selections = selections;
	}

	@Override
	public String toString() {
		return "selected checkboxes=" + selections.toString();
	}
}
