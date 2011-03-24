package org.xmlprocess.lircServer.io;

//No copyright, no warranty; use as you will.
//Written by Adam Flinton


import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
* Implements GetFile for URIs
* 
* @author Adam Flinton
* @version 1.1
*/
public class GetFileURL {

//	private static final Log log = LogFactory.getLog(GetFileURL.class);

	private String fqn = "";
	private String name = "";
	private String file_filterS;

	private long lastMod = 0;
	
	private Date lastModDate;

	private boolean[] fileOK;
	public File file;
	
	public boolean debug = false;

	public String fullqual(String filename) throws Exception {
		// System.out.println("Entering GFU.fullqual");
		String fname = filename;
		// String fpath = "file:///";
		java.net.URL fins = getClass().getResource(filename);
		File f1;
		f1 = new File(fname);
		// System.out.println("Filename passed in = " + filename);
		// System.out.println("fname now = " + fname);
		if (f1.exists()) {
			try {
				fname = f1.toURL().toString();
				name = f1.getName();
			} catch (java.net.MalformedURLException mfu) {
				System.out.println("java.Net.MalfromedURLException caught in GetFileURL.fullqual Error = "
								+ mfu);
			}
			// fname = fpath + f1.getAbsolutePath();
			// System.out.println("File found thus fname now = " + fname);
			return fname;
		} // end checking if local file or URL
		else if (fins != null) {
			// System.out.println("Fins = " + fins.toString());
			return fins.toString();
		} else {
			// System.out.println("File not in a jar nor a local file thus fname
			// now = " + fname);

			fqn = fname;

			return fqn;
		}
	}

	/**
	 * Get a file as an InputStream.
	 * 
	 * @param args
	 *            args[0] is the filename.
	 * @return The InputStream
	 */
	public InputStream getFile(String[] args) throws Exception {
		String fname = args[0];
		boolean fileExists;
		/* fileOK[0] = exists fileOK[1] = canRead fileOK[2] = canWrite fileOK[3] = is
		 * directory fileOK[4] = isURL  
		 */
		
		fileOK = new boolean[5];
		fileOK[0] = false;
		fileOK[1] = false;
		fileOK[2] = false;
		fileOK[3] = false;
		fileOK[4] = false;

		// System.out.println("GetFile = fname = "+fname);
		String fpath = "";
		//File f1;
		file = new File(fname);
		InputStream fins = null;
		InputStream fins1 = getClass().getResourceAsStream(fname);
		if (fins1 != null) {
			fins = fins1;
		} else if (file.exists()) {
			fileExists = true;
			fileOK[0] = true;
			// System.out.println("the file exists");
			if (file.canRead()) {
				fileOK[1] = true;
			}
			if (file.canWrite()) {
				fileOK[2] = true;
			}
			if (file.isDirectory()) {
				fileOK[3] = true;
			}
	
		if (fileOK[1]) {
			/*try {
				fpath = file.toURL().toString();
			} catch (java.net.MalformedURLException mfu) {
				System.out.println("java.Net.MalformedURLException caught in GetFileURL.fullqual. NOT A URL. Error = "+ mfu);
			}*/
			
				fpath = file.getAbsolutePath();
				name = file.getName();
				fqn = fpath;
				lastMod = file.lastModified();
				// System.out.println("the file is " + fpath);
				try {
					fins = new FileInputStream(fname);
				} catch (IOException e) {
					throw new Exception("FileName = " + fname
							+ " IOException " + e.toString());
				} // end of checking for exception
			} 
			else {
				throw new Exception("the file exists but is is not readable " + fname);
			}
		} // end of if for file.exists
		else {
			try {
				URLConnection hpCon = null;
				try {
					URL fileobj = new URL(fname);
					hpCon = fileobj.openConnection();
				} catch (MalformedURLException e) {
					// possibly it's a local file
					String fname2 = "file:///" + fname;
					URL fileobj = new URL(fname);
					hpCon = fileobj.openConnection();
				}
				int urlfilelen = hpCon.getContentLength();
				if (urlfilelen > 0) {
					InputStream f1s = hpCon.getInputStream();
					fins = f1s;
					fileOK[1] = true;
					fileOK[4] = true;
				} else
					throw new Exception("the file DOES NOT EXIST"
							+ fname);
			} catch (MalformedURLException e) {
				throw new Exception(" FileName = " + fname
						+ "\nurl is invalid " + e.toString());

			} // end of checking for exception
			catch (IOException e) {
				throw new Exception("FileName = " + fname
						+ " IOException " + e.toString());
			} // end of checking for exception
		} // end of else for fileexist			

		return fins;
	} // // end of if for fileurl method

	public static void main(String args[]) {
		GetFileURL fileobj = new GetFileURL();
		String fabspath = "";
		// String[] args1 = new String[1];
		// args1[0]="D:/Data/Hl7/XML/XForm/Model_PRSC_MT040101UK09.mif";
		// args=args1;
		try {
			// fabspath = fileobj.getFileURL(args[0]);
			// System.out.println("the absolute path of the file"+fabspath);
			InputStream temp = fileobj.getFile(args);
			/*
			 * this is done for testing whether the stream has been returned or
			 * not
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(temp));
			int c;
			while ((c = br.read()) != -1) {
				System.out.print((char) c);
			}
		} catch (Exception e) {
			System.err.println(e);
		} // end of checking for exception
	
	} // end of main method
	
	/**
	 * Tests a file - returns a boolean array 
	 * test[0] = exists 
	 * test[1] = canRead 
	 * test[2] = canWrite 
	 * test[3] = is directory 
	 * test[4] = isURL 
	 * @param filename
	 * @return
	 */

	public boolean[] testfile(String filename) {

		// String[] Arg = new String[2];
		/*
		 * test[0] = exists test[1] = canRead test[2] = canWrite test[3] = is
		 * directory test[4] = isURL 
		 * 
		 */

		boolean[] test = new boolean[5];
		test[0] = false;
		test[1] = false;
		test[2] = false;
		test[3] = false;
		// test[4] = false;

		File f1;
		f1 = new File(filename);
		lastMod = f1.lastModified();
		if (f1.exists()) {
			test[0] = true;
			// System.out.println("the file exists");
		}

		if (f1.canRead()) {
			test[1] = true;
			// System.out.println("the file is readable");
		}

		if (f1.canWrite()) {
			test[2] = true;
			// System.out.println("the file is writable");
		}
		if (f1.isDirectory()) {
			test[3] = true;
			// System.out.println("the file is a directory");
		}

		/*
		 * No need to test URL so don't if(!test[0]){
		 * 
		 * 
		 * URL url = new URL(filename); HttpURLConnection conn =
		 * (HttpURLConnection) url.openConnection();
		 * conn.setRequestMethod("HEAD"); if (HttpURLConnection.HTTP_OK ==
		 * conn.getResponseCode()) { // its there } else { // its not } }
		 */

		return test;

	}

	/**
	 * Given a directory path it returns a vector of the files in that directory
	 * based on a filter which is currently .xml TODO: see if app needs to be
	 * able to handle multiple file endings
	 * 
	 * @param DirPath
	 * @return A Vector containing the list of files in that directory.
	 */
	public Vector dirList(String DirPath) {

		Vector dirlist = new Vector();

		// Test File
		boolean[] filetest = testfile(DirPath);

		// First check to see file/dir exists.

		// First check to see path resolves to a directory & not a file

		// then see if the dir is readable

		if (filetest[0] && filetest[1] && filetest[3]) {
			// System.out.println("We can list this dir");
			File dir;
			dir = new File(DirPath);

			File[] files;
			if(file_filterS != null && file_filterS.length() > 0 ){
				files = dir.listFiles(getfileFilter(file_filterS));
			}
			else
			{
				files = dir.listFiles();
			}
			
			if (files == null) {
				// Either dir does not exist or is not a directory
			} else {
				for (int i = 0; i < files.length; i++) {
					// Get filename of file or directory
					File fi = files[i];
					try {
						// String URLPath = fi.toURL().toExternalForm();
						String URLPath = fi.getPath();
						// String UP1 = fi.toURL().toString();
						// System.out.println("URLPath= "+URLPath + " UP1 =
						// "+UP1);
						dirlist.add(URLPath);
					} catch (Exception E) {

						System.out.println("Except E = " + E);
					}
				}
			}

		}

		return dirlist;
	}

	/**
	 * A little test method just to work out what dir you are in.
	 * 
	 * @param DirPath
	 * @return
	 */

	public String pwd(String DirPath) {

		String whereAmI = "";
		try {
			// Test File
			boolean[] filetest = testfile(DirPath);

			// First check to see file/dir exists.

			// First check to see path resolves to a directory & not a file

			// then see if the dir is readable

			if (filetest[0] && filetest[1] && filetest[3]) {
				// System.out.println("We can list this dir");
				File dir;
				dir = new File(DirPath);
				String URLPath = dir.toURL().toExternalForm();
				System.out.println("Dir = " + URLPath);
				whereAmI = URLPath;

			}
		} catch (Exception E) {

			System.out.println("GFU.pwd Except = " + E);
		}
		// Print children - Dir filefilter?

		return whereAmI;
	}

	public String getFilesize(String filename) {

		String filesize = "";
		try {
			boolean[] test = testfile(filename);
			if (test[0] && test[1]) {

				File f1 = new File(filename);
				Long length = new Long(f1.length());
				filesize = length.toString();
			}

		} catch (Exception E) {
			System.out.println("Ex in getFilesize E =" + E);
		}

		return filesize;
	}

	public static String filenameToURL(String filename) {
		File f = new File(filename);
		String tmp = f.getAbsolutePath();
		if (File.separatorChar == '\\') {
			tmp = tmp.replace('\\', '/');
		}
		// Note: gives incorrect results when filename already begins with
		file: // /
		return "file:///" + tmp;
	}

	public boolean deletefile(String filename) {
		boolean success = true;

		File f1 = new File(filename);
		success = f1.delete();

		return success;
	}

	public FilenameFilter getfilenameFilter(final String fileending) {

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				// return !name.startsWith(".");
				return name.endsWith(fileending);
			}
		};

		return filter;

	}

	public FileFilter getfileFilter(final String fileending) {

		FileFilter filter = new FileFilter() {
			public boolean accept(File f1) {
				String name = f1.getName();
				// return !name.startsWith(".");
				return name.endsWith(fileending);
			}
		};

		return filter;

	}
	
	public FileFilter getDirFilter() {

		FileFilter filter = new FileFilter() {
			public boolean accept(File f1) {	
				// return !name.startsWith(".");
				return f1.isDirectory();
			}
		};
	return filter;
	}
	
	

	public String getFileName() {

		return fqn;
	}

	public String getFileName(String filename) throws Exception {
		// try{
		String[] arg1 = new String[1];
		arg1[0] = filename;
		// filename = filenameToURL(filename);
		// fullqual(filename);
		try{
			getFile(arg1);
			}
			catch(Exception e){
				if(debug){
				System.out.println("getFile threw an error : "+e);
				}
			}
		/*
		 * } catch (Exception E) { System.out.println("Ex in getFileName E =" +
		 * E); }
		 */
		return fqn;
	}

public FileDetailsDTO getFileDetails(String filename) throws Exception {
		// try{
		// System.out.println("getFileDetails called filename = "+filename);
		String[] arg1 = new String[1];
		arg1[0] = filename;
		// filename = filenameToURL(filename);
		// fullqual(filename);
		
		FileDetailsDTO fdto = new FileDetailsDTO();
		try{
		getFile(arg1);
		}
		catch(Exception e){
			if(debug){
			System.out.println("Debug = "+debug +" getFile threw an error : "+e);
			}
		}
				
		
		fdto.setFilePath(file.getPath());
		fdto.setLastMod(file.lastModified());
		fdto.setJustPath(file.getParent());
		String fn = file.getName();
		fdto.setFileName(fn);
		
		
		try{
		if(file_filterS != null && file_filterS.length() > 0 && fn.length() > 0){
			int index = fn.indexOf(file_filterS);
			if(index > -1){
			String bfn = fn.substring(0, index);
			fdto.setBasicFN(bfn);
			}
		}
		//If no file filter assume dot
		if(file_filterS == null || file_filterS.length() == 0 && fn.length() > 0){
			int index = fn.indexOf(".");
			if(index > -1){
			String bfn = fn.substring(0, index);
			fdto.setBasicFN(bfn);
			}
		}
		} catch (Exception ex){
			System.out.println("getFileDetails getFile threw an error : "+ex);
		}
		fdto.setDetails(testfile(filename));

		return fdto;
	} 

	public void store(String sfilepath, String sfileContent) {
		String scontent = sfileContent;
		String spath = sfilepath;
		char[] buf = new char[scontent.length()];
		scontent.getChars(0, scontent.length(), buf, 0);
		try {
			// if append to be done just add true to the consructor say
			// FileWriter f1 = new FileWriter(spath,true);
			FileWriter f1 = new FileWriter(spath);
			f1.write(buf);
			f1.close();
		} catch (Exception e) {
			System.out.println("String Writing Error Occured Filepath = "
					+ spath + "Error = " + e);
		}
	}

	public long checklastMod() {

		long lastMod = 0;

		return lastMod;
	}

	/**
	 * Returns a file as a string. Used to load text files. Creation date:
	 * (17/05/04 13:24:31)
	 * 
	 * @return The file as a string
	 * @param Filename
	 *            The filename/path of the file to be loaded
	 * @throws Exception
	 */
	public String fileToString(String Filename) throws Exception {

		// System.out.println("fileToString called Filename = "+Filename);

		// GetFileURL gfu = new GetFileURL();
		String[] S = new String[1];
		S[0] = Filename;
		InputStream is = getFile(S);	
		//BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		BufferedReader reader = new BufferedReader(new FileReader(Filename));
		
		
		StringBuffer fileData = new StringBuffer(1000);
	      
	     char[] buf = new char[1024];
	          int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	           String readData = String.valueOf(buf, 0, numRead);
	            fileData.append(readData);
	            buf = new char[1024];
	         }
	        reader.close();
		        return fileData.toString();
	}
	
	

	
public  byte[] getBytesFromFile(String fn) throws Exception  {
		String[] S = new String[1];
		S[0] = fn;
		InputStream is = getFile(S); 
     // Get the size of the file
     long length = file.length();
 
     if (length > Integer.MAX_VALUE) {
         throw new Exception("File length is greater than Integer.MAX_VALUE length = "+length);
     }
     byte[] bytes = new byte[(int)length];
 
     // Read in the bytes
     int offset = 0;
     int numRead = 0;
     while (offset < bytes.length
            && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
         offset += numRead;
     }
 
     // Ensure all the bytes have been read in
     if (offset < bytes.length) {
         throw new IOException("Could not completely read file "+file.getName());
     }
     // Close the input stream and return bytes
     is.close();
     return bytes;
 }

	
	

	public String getDirPath(String filepath) {
		String DirPath = "";
		File f1;
		f1 = new File(filepath);

		if (f1.isDirectory()) {
			DirPath = filepath;
			// System.out.println("the file is a directory");
		} else {
			// get the parent file
			DirPath = f1.getParent();

		}

		return DirPath;
	}

	public String getFile_filterS() {
		return file_filterS;
	}

	public void setFile_filterS(String file_filterS) {
		this.file_filterS = file_filterS;
	}

	public long getLastMod() {
		return lastMod;
	}

	public void setLastMod(long lastMod) {
		this.lastMod = lastMod;
	}

	public Date getLastModDate() {
		
		if(lastMod == 0){
			lastModDate = null;
		}
		else{
			lastModDate = new Date(lastMod);
		}
		
		
		return lastModDate;
	}

	public File[] listRoots(){
		
		File[] roots = File.listRoots(); 
		
		return roots;
	}

	/**
	 * Given a directory path it returns a vector of the files in that directory
	 * based on a filter which is currently .xml TODO: see if app needs to be
	 * able to handle multiple file endings
	 * 
	 * @param DirPath
	 * @return A Vector containing the list of files in that directory.
	 */
	
	public Vector recursiveInitialdirList(String DirPath) {
		File dir;
		Vector retV = null;
		dir = new File(DirPath);
		if(dir.isDirectory()){
			retV = recursivedirList(dir);
		}
		return retV;
	}
	
	
	public Vector recursivedirList(File dir) {
	
		Vector dirlist = new Vector();
	
		// then see if the dir is readable
	
		if (dir.canRead()) {
			// System.out.println("We can list this dir");
			File[] files;
			if(file_filterS != null && file_filterS.length() > 0 ){
				files = dir.listFiles(getfileFilter(file_filterS));
			}
			else
			{
				file_filterS = ".xml";
				files = dir.listFiles(getfileFilter(file_filterS));
			}
			
			if (files == null) {
				// Either dir does not exist or is not a directory
			} else {
				for (int i = 0; i < files.length; i++) {
					// Get filename of file or directory
					File fi = files[i];
					try {
						// String URLPath = fi.toURL().toExternalForm();
						//System.out.println("fi name1 = "+fi.getName());
						
						if(!fi.isDirectory()){
						//System.out.println("fi name = "+fi.getName());	
						FileDetailsDTO fdto = new FileDetailsDTO();
						fdto.setFilePath(fi.getPath());
						fdto.setLastMod(fi.lastModified());
						fdto.setJustPath(fi.getParent());
						String fn = fi.getName();
						fdto.setFileName(fn);
						
						
						if(file_filterS != null && file_filterS.length() > 0){
							int index = fn.indexOf(file_filterS);
							String bfn = fn.substring(0, index);
							fdto.setBasicFN(bfn);
						}
						
						dirlist.add(fdto);
					}
						
					} catch (Exception E) {
	
						System.out.println("Except E = " + E);
					}
				}
			}
			
			File[] dirs = dir.listFiles(getDirFilter());
			if(dirs.length > 0){
				for (int i = 0; i < dirs.length; i++) {
					// Get filename of file or directory
					File diri = dirs[i];
					//System.out.println("diri name = "+diri.getName());
					Vector v1 = recursivedirList(diri);
					for (Enumeration e = v1.elements(); e.hasMoreElements();) {
						FileDetailsDTO fdto1 = (FileDetailsDTO) e.nextElement();
						dirlist.add(fdto1);
					}
				}
			}
		}
		return dirlist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFqn() {
		return fqn;
	}

	public void setFqn(String fqn) {
		this.fqn = fqn;
	}

	public boolean[] getFileOK() {
		return fileOK;
	}

	public void setFileOK(boolean[] fileOK) {
		this.fileOK = fileOK;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	

	
	
}