package pl.arczynskiadam.web.messages;

import java.util.Collection;

public class GlobalMessage {
	private String code;
	private Collection<String> params;
	
	@SuppressWarnings("unused")
	private GlobalMessage() {
		//block usage
	}
	
	public GlobalMessage(String code, Collection<String> attributes) {
		this.code = code;
		this.params = attributes;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Collection<String> getParams() {
		return params;
	}
	public void setParams(Collection<String> attributes) {
		this.params = attributes;
	}
}
