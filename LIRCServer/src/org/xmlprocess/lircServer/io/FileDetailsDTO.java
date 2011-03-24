package org.xmlprocess.lircServer.io;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDetailsDTO {
	String fileName;
	String filePath;
	long lastMod = 0;
	Date lastModDate;
	String lastModDateS;
	String DATE_FORMAT_NOW = "yyyy_MM_dd_HH_mm_ss";
	DateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
	String justPath;
	String basicFN;
	/*
	 * test[0] = exists test[1] = canRead test[2] = canWrite test[3] = is
	 * directory test[4] = isURL 
	 * 
	 */
	boolean[] details;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getLastMod() {
		return lastMod;
	}
	public void setLastMod(long lastMod) {
		this.lastMod = lastMod;
		setLastModDate(new Date(this.lastMod));
	}
	public Date getLastModDate() {
		return lastModDate;
	}
	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
		setLastModDateS(df.format(lastModDate));
		//df.format(lastModDate);
	}
	public String getLastModDateS() {
		return lastModDateS;
	}
	public void setLastModDateS(String lastModDateS) {
		this.lastModDateS = lastModDateS;
	}
	
	public String getJustPath() {
		return justPath;
	}
	public void setJustPath(String justPath) {
		this.justPath = justPath;
	}
	public String getBasicFN() {
		return basicFN;
	}
	public void setBasicFN(String basicFN) {
		this.basicFN = basicFN;
	}
	public boolean[] getDetails() {
		return details;
	}
	public void setDetails(boolean[] details) {
		this.details = details;
	}
}
