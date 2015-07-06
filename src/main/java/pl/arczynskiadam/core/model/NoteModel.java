package pl.arczynskiadam.core.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.arczynskiadam.core.model.converters.LocalDateConverter;
import pl.arczynskiadam.core.model.converters.LocalDateTimeConverter;

@Entity
@Table(name="NOTES")
public class NoteModel implements Serializable {
	
	private static final long serialVersionUID = -3163891392532378041L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="CONTENT")
	private String content;
	
	@Column(name="DEADLINE")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate deadline;
	
	@Column(name="LONGITUDE")
	private Double longitude;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="DATE_CREATED")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dateCreated;

	@Column(name="LAST_MODIFIED")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime lastModified;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "USER_FK")
	private UserModel author;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
		author.addNote(this);
	}
	
	public boolean isAvailableOnMap()
	{
		return latitude != null && longitude != null;
	}

	@Override
	public String toString() {
		return "NoteVO [id=" + id + ", content=" + content + ", dateCreated="
				+ dateCreated + ", lastModified=" + lastModified + "]";
	}
}
