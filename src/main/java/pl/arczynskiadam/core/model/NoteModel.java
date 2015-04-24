package pl.arczynskiadam.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="NOTES")
public class NoteModel implements Serializable {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="CONTENT")
	private String content;
	
	@Column(name="DATE_CREATED")
	private Date dateCreated;

	@Column(name="LAST_MODIFIED")
	private Date lastModified;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "USER_FK")
	private UserModel author;
	
	@Transient
	private String formattedDateCreated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}


	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
		author.addNote(this);
	}

	public String getFormattedDateCreated() {
		return formattedDateCreated;
	}

	public void setFormattedDateCreated(String formattedDateCreated) {
		this.formattedDateCreated = formattedDateCreated;
	}

	@Override
	public String toString() {
		return "NoteVO [id=" + id + ", content=" + content + ", dateCreated="
				+ dateCreated + ", lastModified=" + lastModified + "]";
	}
}
