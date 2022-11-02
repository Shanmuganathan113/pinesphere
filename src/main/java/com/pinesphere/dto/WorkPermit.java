package com.pinesphere.dto;

import lombok.Data;

@Data
public class WorkPermit {
	private String logId; 	// log_id from log table
	
	public String id; 		// Intended segment ids
	
	private String logDescription;
	
	public Integer taskStatusId;
	
	public String taskStatus;
	
	public Integer segmentId;
	
}
