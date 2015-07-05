package pl.arczynskiadam.web.propertyEditors;

import java.beans.PropertyEditorSupport;

import pl.arczynskiadam.web.data.DateFilterData;

public class DateFilterEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
    	DateFilterData filter = new DateFilterData();
        this.setValue(filter);
    }

    // Converts a Category to a String (when displaying form)
    @Override
    public String getAsText() {
        DateFilterData filter = (DateFilterData) this.getValue();
        return null;
    }

}