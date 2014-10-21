package pl.arczynskiadam.web.form;

import java.io.Serializable;
import java.util.Arrays;

public class SelectedCheckboxesForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String[] selections;

	public String[] getSelections() {
		return selections;
	}

	public void setSelections(String[] selections) {
		this.selections = selections;
	}

	@Override
	public String toString() {
		return "selected checkboxes=" + Arrays.toString(selections) + "]";
	}
}
