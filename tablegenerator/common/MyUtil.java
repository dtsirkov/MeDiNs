package common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.vaadin.shared.ui.colorpicker.Color;

public final class MyUtil {
	
	/** The Constant RADIX. */
	private static final int RADIX = 16;
	
	/**
	 * Instantiates a new MyUtil object. As this class is a Utility class the <br>
	 * default constructor shall not have the <i>default</i> or <i>public</i> visibility.
	 */
	private MyUtil() {
	}
	
	/**
	 * Generates the current date.
	 * 
	 * @return The current date. ("DD.MM.YYYY")
	 */
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String date = cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR);
		return date; 
	}
	
	/**
	 * Generates the SHA security code for the given input.
	 * 
	 * @param password The string which is supposed to be digested with SHA security.
	 * @return Returns the digested SHA security as a string value.
	 */
	public static String getSHACode(final String password) {
    	try {
			byte[] bytesOfPassword = null;
			MessageDigest md = null;
			bytesOfPassword = password.getBytes("UTF-8");
			md = MessageDigest.getInstance("SHA");
			byte[] digest = md.digest(bytesOfPassword);
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(RADIX);
			return hashtext;
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		return "";
	}
	
	
	
	/**
	 * This method generated a new Color value with the hexadecimal color as
	 * input.
	 * 
	 * @param colorStr
	 *            The hexadecimal value of a color.
	 * @return The color based on the values given.
	 */
	public static Color hex2Rgb(final String colorStr) {
		Color c;
		try {
			c = new Color(
					Integer.valueOf(colorStr.substring(1, 3), 16), 
					Integer.valueOf(colorStr.substring(3, 5), 16), 
					Integer.valueOf(colorStr.substring(5, 7), 16));
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the int.
	 * 
	 * @param value
	 *            The value
	 * @param nullAllowed
	 *            The null allowed
	 * @return The int
	 */
	public static Integer getInt(final String value, final boolean nullAllowed) {
		if ("".equals(value) || !value.matches("\\d")) {
			return null;
		}
		return Integer.parseInt(value);
	}

	
	
	
	
	
	
	public static String base64encode(String text, String key) {
		try {
			
			if (text == null || key == null) {
				return null;
			}

			char[] keys = key.toCharArray();
			char[] mesg = text.toCharArray();

			int ml = mesg.length;
			int kl = keys.length;
			char[] newmsg = new char[ml];

			for (int i = 0; i < ml; i++) {
				newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
			}
			mesg = null;
			keys = null;
			
			String rez = new BASE64Encoder().encode(new String(newmsg).getBytes("UTF-8"));
			return rez;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String base64decode(String text, String key) {

		try {
			String str = new String(new BASE64Decoder().decodeBuffer(text), "UTF-8");
			
			if (str == null || key == null) {
				return null;
			}

			char[] keys = key.toCharArray();
			char[] mesg = str.toCharArray();

			int ml = mesg.length;
			int kl = keys.length;
			char[] newmsg = new char[ml];

			for (int i = 0; i < ml; i++) {
				newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
			}
			mesg = null;
			keys = null;

			return new String(newmsg);

			
			
		} catch (IOException e) {
			return null;
		}

	}

	
	
	
	
	
	
	
	public static void main(String[] args) {
		hex2Rgb("#a22ed445");
	}

	public static String getCaption(String str) {
		System.out.println(str);
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	
	
	
}