package org.emailverification.entityF;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="document")
public class Document {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=512, nullable=false, unique=true)
	private String name;
	
	private Long size;
	
	@Column(name="upload_Time")
	private Date uploadTime;
	
	byte[] content;
	
	

	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	


	public Document(Long id, String name, Long size, byte[] content) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.content = content;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}


	
	
	

}
