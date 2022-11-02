package com.pinesphere.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinesphere.domain.WorkPermit;
import com.pinesphere.repository.WorkPermitRepository;

@Service
public class WorkPermitService {
private static final Logger logger = LoggerFactory.getLogger(WorkPermitScheduler.class);
	@Autowired
	WorkPermitRepository workPermitRepository;
	
	public static LocalDateTime NEXT_RUN_TIME = LocalDateTime.now();
	public static Integer WORK_PERMIT_ID = null;
	
	public void addWorkPermit() {
		try {
			Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		    
			WorkPermit wp = new WorkPermit();
			wp.setWork_permit_name("Testing");
			wp.setMail_sent_ind("N");
			wp.setTime(ts);
			workPermitRepository.save(wp);
			logger.error("---------------------------- ");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void sendMail() {
		Optional<WorkPermit> wp = workPermitRepository.findById(WORK_PERMIT_ID);
		System.out.println("Mail is being sent for "+wp.toString());
		
		WorkPermit wp1 = wp.get();
		wp1.setMail_sent_ind("Y");
		workPermitRepository.save(wp1);
		this.pollDB();
	}
	
	public void pollDB() {
		try {
			List<Object[]> listObjWP = workPermitRepository.findNextWorkPermit();
			Object[] objWP = null;
			if(listObjWP != null && (!listObjWP.isEmpty() )) 
				objWP = listObjWP.get(0);
		
			WorkPermit wp1;
			if(objWP != null) {
				WORK_PERMIT_ID = Integer.parseInt(objWP[0].toString());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			    Date parsedDate = sdf.parse(objWP[3].toString());
			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				
				LocalDateTime t = timestamp.toInstant().atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
				t = t.plusMinutes(30);
	
				if(t.isBefore(LocalDateTime.now())) 
					t = LocalDateTime.now();
				NEXT_RUN_TIME = t;
			}else {
				WORK_PERMIT_ID = null;
				NEXT_RUN_TIME = LocalDateTime.now().plusMinutes(15); // Default polling for every 15 mins
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}