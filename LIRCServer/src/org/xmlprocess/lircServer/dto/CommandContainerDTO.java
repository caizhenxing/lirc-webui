package org.xmlprocess.lircServer.dto;

import java.util.ArrayList;

public class CommandContainerDTO {
	
	int numTimes = 1;
	String[] cmds;
	

	public int getNumTimes() {
		return numTimes;
	}

	public void setNumTimes(int numTimes) {
		this.numTimes = numTimes;
	}

	public String[] getCmds() {
		return cmds;
	}

	public void setCmds(String[] cmds) {
		this.cmds = cmds;
	}

	
	

}
