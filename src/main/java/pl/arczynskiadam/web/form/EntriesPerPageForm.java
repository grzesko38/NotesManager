package pl.arczynskiadam.web.form;

import java.util.ArrayList;
import java.util.List;

import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;

public class EntriesPerPageForm {
	{
		size = Integer.toString(NoteControllerConstants.Defaults.Pagination.ENTRIES_PER_PAGE);
	}
	
	private List<ComboListItem> pageSizes;
	private String size; //selected item
	
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public static ArrayList<EntriesPerPageForm.ComboListItem> convertToPageSizesItemsList(List<Integer> sizes) {
		ArrayList<EntriesPerPageForm.ComboListItem> pageSizes = new ArrayList<EntriesPerPageForm.ComboListItem>();
		for (int size : sizes) {
			pageSizes.add(new ComboListItem(Integer.toString(size), Integer.toString(size)));
		}
		return pageSizes;
	}
	
	@SuppressWarnings("unused")
	static class ComboListItem {
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
