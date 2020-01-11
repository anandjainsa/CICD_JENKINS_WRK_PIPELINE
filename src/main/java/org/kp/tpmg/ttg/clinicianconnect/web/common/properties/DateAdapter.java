package org.kp.tpmg.ttg.clinicianconnect.web.common.properties;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





public class DateAdapter extends XmlAdapter<String, Date> {
	
	private static Logger log = LogManager.getLogger(DateAdapter.class);
	
	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);

	public static Date convertToDate(String time) {
		String[] formats = {"yyyy-MM-dd'T'hh:mm:ss",YYYY_MM_DD_HH_MM_SS, "MM/dd/yy hh:mm a", "MM/dd/yy  hh:mm a",
				"MM/dd/yy", "yyyy-MM-dd hh:mm a", "yyyy-MM-dd hh a", "yyyy-MM-dd" };
		for (String formatString : formats) {
			try {
				return new SimpleDateFormat(formatString).parse(time);
			} catch (ParseException e) {
				// intentionally left empty 
				//because if it fails, it will try another format from an array
			}
		}
		return null;
	}

	public static Date addOneDayToDate(Date currentdate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currentdate);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	public static String isTimeAMorPM(Date datetime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (datetime != null) {
			String date = df.format(datetime);

			String checkstartdate1 = date + " " + "12:00:00";
			String checkstartdate2 = date + " " + "24:00:00";

			Date checkstarttime1 = null;
			Date checkstarttime2 = null;
			try {
				checkstarttime1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
						.parse(checkstartdate1);
				checkstarttime2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
						.parse(checkstartdate2);

			} catch (ParseException e) {
				log.error("ERROR: ClinicianConnect: isTimeAMorPM: " , e);
				return "";
			}
			return findAMorPM(datetime, checkstarttime1, checkstarttime2);
		} else {
			return "";
		}
	}

	private static String findAMorPM(Date datetime, Date checkstarttime1, Date checkstarttime2) {
		Calendar afterDate = Calendar.getInstance();
		afterDate.setTimeZone(TimeZone.getTimeZone("PST"));
		afterDate.setTime(checkstarttime1);
		Calendar beforeDate = Calendar.getInstance();
		beforeDate.setTimeZone(TimeZone.getTimeZone("PST"));
		beforeDate.setTime(checkstarttime2);
		if (datetime.after(afterDate.getTime())
				&& datetime.before(beforeDate.getTime())) {
			return "PM";
		}
		if(datetime.equals(afterDate.getTime())){
			return "PM";
		}
		return "AM";
	}

	public static Date checkEndTime(Date startDateTime, Date endDateTime) {
		String startampm = isTimeAMorPM(startDateTime);
		String endampm = isTimeAMorPM(endDateTime);
		// if starttime is PM and endTime is AM then add one day in endtime
		if ("PM".equalsIgnoreCase(startampm) && "AM".equalsIgnoreCase(endampm)) {
			// then check end time end time is between 12am to 12pm
			return DateAdapter.addOneDayToDate(endDateTime);
		}

		return endDateTime;
	}

	@Override
	public String marshal(Date v) throws Exception {
		return dateFormat.format(v);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		return dateFormat.parse(v);
	}

	public static synchronized Long convertToMilliSeconds(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(
		YYYY_MM_DD_HH_MM_SS);

		Date date3 = null;
		try {
			date3 = formatter.parse(date);
			if(date3 != null){
				return date3.getTime();
			}
		} catch (ParseException e) {
			log.error("ERROR: ClinicianConnect: convertToMilliSeconds: " +date+":", e);
		}
		return null;
	}
	
	public static Long getCurrentDateInMilliSeconds(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		try {
			date = format.parse(dateFormat.format(date));
		} catch (ParseException e) {
		
			 log.error("ERROR in getCurrentDateInMilliSeconds in DateAdapter is: " ,e);
		}
		return date.getTime();
	}
	public static String getTodaysDateLBFormat(){
	    Calendar c = Calendar.getInstance(); 
	    c.setTime(new Date()); 
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(c.getTime());
	}
	public static String getTomorrowsDateLBFormat(){
	    Calendar c = Calendar.getInstance(); 
	    c.setTime(new Date()); 
	    c.add(Calendar.DATE, 1);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(c.getTime());
	}
	
	public static Date getDateformat(Date date){
		return convertToDate(new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(date));
	}
}
