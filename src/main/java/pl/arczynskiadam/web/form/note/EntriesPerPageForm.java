package pl.arczynskiadam.web.form.note;

import java.util.ArrayList;
import java.util.List;

public class EntriesPerPageForm {
	
	public EntriesPerPageForm(Integer selected, List<Integer> sizes) {
		pageSizes = new ArrayList<EntriesPerPageForm.ComboListItem>();
		for (int size : sizes) {
			pageSizes.add(new ComboListItem(Integer.toString(size), Integer.toString(size)));
		}
		
		if (!containsPageSize(selected)) {
			throw new IllegalArgumentException("chosen selection must be in set of given page sizes."
					+ "given sizes: " + printSizes() + " selected size: " + selected);
		}
		
		entries = Integer.toString(selected);
	}
	
	private List<ComboListItem> pageSizes;
	private String entries; //selected item
	
	public boolean containsPageSize(int pageSize) {
		for (ComboListItem item : pageSizes) {
			if (item.value.equals(Integer.toString(pageSize))) {
				return true;
			}
		}
		return false;
	}
	
	public String printSizes() {
		StringBuilder sb = new StringBuilder("[");
		for (ComboListItem item : pageSizes) {
			sb.append(item.name).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public List<ComboListItem> getPageSizes() {
		return pageSizes;
	}

	public void setPageSizes(List<ComboListItem> pageSizes) {
		this.pageSizes = pageSizes;
	}

	public String getEntries() {
		return entries;
	}

	public void setEntries(String entries) {
		this.entries = entries;
	}

	private class ComboListItem {
		private String name;
		private String value;
		
		ComboListItem(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
