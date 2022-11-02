
package com.pinesphere.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
 
@Entity
@Table(name = "work_permit")
@Data
public class WorkPermit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private Integer id; 				// this is the primary key
	
	@Column(name="work_permit_name") 
	private String work_permit_name; 
	
	@Column(name="mail_sent_ind") 
	private String mail_sent_ind; 
	 
	@Column(name="time")
	private Timestamp time;
		
}