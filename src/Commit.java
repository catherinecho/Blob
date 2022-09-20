import java.util.*;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  
import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Commit {
	private static String parent = null;
	private static String child = null;
	private static String pTree; 
	private static String author;
	private static String summary;
	private static String date;
	private static String filename;
	public Commit(String pt, String summ, String a, String pointer) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		pTree = pt;
		summary = summ;
		author = a;
		if(pointer == null)
			parent= null;
		else
			parent = pointer;
		child = null;
		date = getDate();
		String temp = summary + date+author+parent;
		temp += sha1Code(temp);
		filename = temp;
		writeFile(); 
		
		
	}
	public static String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException, FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        while (digestInputStream.read(bytes) > 0);
        byte[] resultByteArry = digest.digest();
        return bytesToHexString(resultByteArry);
    }
    
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(value).toUpperCase());
        }
        return sb.toString();
    }
	public static String getDate() {
		// from javatpoint
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String strDate = dateFormat.format(date);  
		return strDate; 
	}
	public static void writeFile() throws FileNotFoundException {
		File file = new File(filename);
		PrintWriter out = new PrintWriter("objects/" + file);
		out.append(filename + "\n" + parent  + "\n" + child  + "\n" + author  + "\n" + date  + "\n" + summary + "\n");
		out.close();
		
		
	}
	
}
