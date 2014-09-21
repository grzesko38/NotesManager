package pl.arczynskiadam.web.form.note;

import java.io.Serializable;
import java.util.Arrays;

public class NotesSelectionForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int[] ids;

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "NotesSelection [ids=" + Arrays.toString(ids) + "]";
	}
}
