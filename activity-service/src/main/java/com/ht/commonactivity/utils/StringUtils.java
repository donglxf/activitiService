package com.ht.commonactivity.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * <p>String工具类</p> 
 *
 * @author: dyb
 * @since: 2018年1月4日上午11:00:00
 * @version: 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final Logger log = LoggerFactory.getLogger(StringUtils.class);

	private static final NumberFormat nf = NumberFormat.getInstance();

	private static final NumberFormat pnf = NumberFormat.getPercentInstance();

	public static final String SYSTEM_DEFAULT_ENCODING = "ISO-8859-1";

	/**
	 * 字節流通向字符流或者字符流通向字節流需要的字符集,默認使用平臺的字符集,如平臺字符集和web app沖突,需要指定字符集.默認為UTF-8
	 */
	public static final String ENCODING = "UTF-8";

	public static final boolean USE_URI_ENCODING = false;

	public static final String URI_ENCODING = ENCODING;


	// 校验email的正则表达式
	static final String REGEXMAIL = "^([a-zA-Z0-9_-])+(.[a-zA-Z0-9]+)*@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-]+[a-zA-Z0-9]+)*$";

	static final String ZERO = "0";

	static final String REGEX_ESCAPE_HTML = "(<[^>]*>)|[\\t|\\r|\\n]|(&nbsp;)";
	
	static final String PREFIX_UNICODE = "\\u";

	private StringUtils() {
	}

	/**
	 * number数字按length长度补零，如 number=1 , length=4 ，则返回 0001 字符串
	 * 
	 * @param number
	 * @param length
	 * @return
	 */
	public static final String fillZero(Number number, int length) {
		return fillZero(number.toString(), length);
	}

	/**
	 * number数字按length长度补零，如 number=1 , length=4 ，则返回 0001 字符串
	 * 
	 * @param number
	 * @param length
	 * @return
	 */
	public static final String fillZero(String number, int length) {
		if (isEmpty(number) || length < 1)
			return number;
		if (number.equalsIgnoreCase("-1"))
			number = RandomStringUtils.randomNumeric(length);
		StringBuilder zero = new StringBuilder(ZERO);
		for (int i = 0; i < length; i++)
			zero.append(ZERO);
		zero.append(number);
		String ret = zero.toString();
		return ret.substring(ret.length() - length);
	}
	
	/**
	 * number数字按length长度补满，如 number=9 , length=4 ，则返回 9999 字符串
	 * @param number
	 * @param length
	 * @return
	 */
	public static final String fillNumber(String number, int length) {
		if (isEmpty(number) || length < 1){
			return number;
		}
		if(Integer.parseInt(number)<0){
			return number;
		}		
		StringBuilder sb = new StringBuilder(number);
		for (int i = 0; i < length; i++){
			sb.append(number);
		}
		sb.append(number);
		String ret = sb.toString();
		return ret.substring(ret.length() - length);
	}
	

	/**
	 * 将string的首字母小写
	 * 
	 * @param string
	 * @return
	 */
	public static final String getStartLower(String string) {
		String regex = "^(\\w+?)(?=[A-Z])";
		Matcher m = Pattern.compile(regex).matcher(string);
		if (m.find()) {
			return m.group();
		} else {
			return string;
		}
	}

	/**
	 * upper the first char in string
	 * 
	 * @param string
	 * @return
	 */
	public static final String upperFirst(String string) {
		return string.substring(0, 1).toUpperCase().concat(string.substring(1));
	}

	/**
	 * lower the first char in string
	 * 
	 * @param string
	 * @return
	 */
	public static final String lowerFirst(String string) {
		return string.substring(0, 1).toLowerCase().concat(string.substring(1));
	}

	/**
	 * 
	 * 與 String getNotNullString(String str) 相反 if str is null or empty (e.g: "" ,
	 * "  " ) then return null <br />
	 * else str <br />
	 * 方便處理 pojo 時，設定 null, 而不是 ""
	 * 
	 * @param str
	 * @return
	 */
	public static String getNullStringIfEmpty(String str) {
		if (isEmpty(str)) {
			return null;
		} else {
			return str;
		}
	}

	/**
	 * 與 String getNotNullString(String str, boolean isTrim) 相反 if str is null or
	 * empty (e.g: "" , "  " ) then return null <br />
	 * else if isTrim then trim str <br />
	 * 方便處理 pojo 時，設定 null, 而不是 ""
	 * 
	 * @param str
	 * @param isTrim
	 * @return
	 */
	public static String getNullStringIfEmpty(String str, boolean isTrim) {
		if (isEmpty(str)) {
			return null;
		} else {
			return isTrim ? str.trim() : str;
		}
	}

	/**
	 * if str is null then return "" else return trimed str
	 * 
	 * @param str
	 *          String
	 * @return String
	 */
	public static String getNotNullString(String str) {
		return getNotNullString(str, true);
	}

	/**
	 * if str is null then return "" else if isTrim then trim str
	 * 
	 * @param str
	 *          String
	 * @param isTrim
	 *          boolean
	 * @return String
	 */
	public static String getNotNullString(String str, boolean isTrim) {
		return str == null ? "" : (isTrim ? str.trim() : str);
	}

	/**
	 * if str is null then return special string else if isTrim then trim str
	 * 
	 * @param str
	 *          String
	 * @return String
	 */
	public static String getNotNullString(String str, String defaultString) {
		return str == null ? defaultString : (str.trim().equals("") ? defaultString
				: str.trim());
	}

	/**
	 * 固定字串長度,不足長度者以空格補
	 * 
	 * 
	 * @param str
	 *          String
	 * @param intLen
	 *          int
	 * @return String
	 */
	public static String alignedString(String str, int intLen) {
		StringBuffer sb = new StringBuffer(str);
		for (int i = 0; i < (intLen - str.length()); i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}
	
	/**
	 * 用StringTokenizer分割字串得到字串數組
	 * 
	 * @param str
	 *          String
	 * @return List
	 */
	public static List getStringTokenizerList(String str) {
		return getStringTokenizerList(str, null);
	}

	/**
	 * 用StringTokenizer分割字串得到字串數組
	 *
	 * @param str
	 *          String
	 * @param delim
	 *          String
	 * @return List
	 */
	public static List getStringTokenizerList(String str, String delim) {
		if (str == null)
			return null;
		List retList = new ArrayList();
		StringTokenizer st = new StringTokenizer(str);
		if (delim != null) {
			st = new StringTokenizer(str, delim);
		}
		while (st.hasMoreTokens()) {
			retList.add(st.nextToken());
		}
		return retList;
	}


	/**
	 * 得到Throwable里的訊息字串
	 * 
	 * @param throwable
	 *          Throwable
	 * @return String
	 */
	public static String getStackTraceString(Throwable throwable) {
		StringBuffer sb = new StringBuffer(throwable.getMessage());
		StackTraceElement ste[] = throwable.getStackTrace();
		for (int i = 0; i < ste.length; i++) {
			sb.append(ste[i].toString()).append("\n");
		}
		return sb.toString();
	}

	public static String getPackageFolder(Class clazz) {
		StringBuilder sb = new StringBuilder("/").append(clazz.getPackage()
				.getName());
		return sb.toString().replaceAll("\\.", "/");
	}

	public static String getFilePath(Class clazz, String fileName) {
		return getPackageFolder(clazz).concat("/").concat(fileName);
	}

	/**
	 * 得到filePath中的fileName, Example filePath="c:/test/test.txt" return test.txt
	 * 
	 * @param filePath
	 *          String
	 * @return String
	 */
	public static String getFileName(String filePath) {
		int i = Math.max(filePath.lastIndexOf("\\"), filePath.lastIndexOf("/"));
		return filePath.substring(i + 1);
	}
	

	/**
	 * return the path of parent folder from specifid file by URI. Example : if
	 * URI is "test/public/index.jsp",then return "test/public".
	 * 
	 * @param URI
	 *          String
	 * @return String
	 */
	public static String getParentPath(String URI) {
		String ret = "";
		int last = URI.lastIndexOf("/");
		if (last != -1) {
			ret = URI.substring(0, last);
		}
		return ret;
	}

	/**
	 * Specialization of format,format against specified two aguments.It is
	 * synchronized method.
	 * 
	 * @param number
	 *          double
	 * @param newMaximumFractionDigits
	 *          int the maximum number of digits allowed in the fraction portion
	 *          of a number.
	 * @param newMinimumFractionDigits
	 *          int the minimum number of digits allowed in the fraction portion
	 *          of a number.
	 * @return String
	 */
	public static final synchronized String getFormatNumber(double number,
			int newMaximumFractionDigits, int newMinimumFractionDigits) {
		nf.setMaximumFractionDigits(newMaximumFractionDigits);
		nf.setMinimumFractionDigits(newMinimumFractionDigits);
		return nf.format(number);
	}

	/**
	 * Specialization of format,returned getFormatNumber(number,2,0).
	 * 
	 * @param number
	 *          double
	 * @return String
	 */
	public static final String getFormatNumber(double number) {
		return getFormatNumber(number, 2, 0);
	}

	/**
	 * Specialization of format,force number to double type,and format new double
	 * number.
	 * 
	 * @param number
	 *          int
	 * @param newMaximumFractionDigits
	 *          int the maximum number of digits allowed in the fraction portion
	 *          of a number.
	 * @param newMinimumFractionDigits
	 *          int the minimum number of digits allowed in the fraction portion
	 *          of a number.
	 * @return String
	 */
	public static final String getFormatNumber(int number,
			int newMaximumFractionDigits, int newMinimumFractionDigits) {
		return getFormatNumber((double) number, newMaximumFractionDigits,
				newMinimumFractionDigits);
	}

	/**
	 * Specialization of format,force number to double type,and format new double
	 * number. fact : returned getFormatNumber((double)number).
	 * 
	 * @param number
	 *          int
	 * @return String
	 */
	public static final String getFormatNumber(int number) {
		return getFormatNumber((double) number);
	}

	/**
	 * return format numberstring,numberstring must contain a parsable double,
	 * format new double number after numberstring been parsed .
	 * 
	 * @param numberstring
	 *          String
	 * @param newMaximumFractionDigits
	 *          int the maximum number of digits allowed in the fraction portion
	 *          of a number.
	 * @param newMinimumFractionDigits
	 *          int the minimum number of digits allowed in the fraction portion
	 *          of a number.
	 * @return String
	 */
	public static final String getFormatNumber(String numberstring,
			int newMaximumFractionDigits, int newMinimumFractionDigits) {
		double number = Double.parseDouble(numberstring.trim());
		return getFormatNumber(number, newMaximumFractionDigits,
				newMinimumFractionDigits);
	}

	/**
	 * fact : returned getFormatNumber(numberstring,2,0).
	 * 
	 * @param numberstring
	 *          String
	 * @return String
	 */
	public static final String getFormatNumber(String numberstring) {
		return getFormatNumber(numberstring, 2, 0);
	}

	/**
	 * Specialization of format,force number to double type,and format new double
	 * number.
	 * 
	 * @param number
	 *          long
	 * @param newMaximumFractionDigits
	 *          int the maximum number of digits allowed in the fraction portion
	 *          of a number.
	 * @param newMinimumFractionDigits
	 *          int the minimum number of digits allowed in the fraction portion
	 *          of a number.
	 * @return String
	 */
	public static final String getFormatNumber(long number,
			int newMaximumFractionDigits, int newMinimumFractionDigits) {
		return getFormatNumber((double) number, newMaximumFractionDigits,
				newMinimumFractionDigits);
	}

	/**
	 * Specialization of format,force number to double type,and format new double
	 * number. fact : returned getFormatNumber((double)number).
	 * 
	 * @param number
	 *          long
	 * @return String
	 */
	public static final String getFormatNumber(long number) {
		return getFormatNumber((double) number);
	}

	/**
	 * Return a percent string from formatting number by percentage format, format
	 * against specified two aguments.It is synchronized method.
	 * 
	 * @param number
	 *          double
	 * @param newMaximumFractionDigits
	 *          int the maximum number of digits allowed in the fraction portion
	 *          of a number.
	 * @param newMinimumFractionDigits
	 *          int the minimum number of digits allowed in the fraction portion
	 *          of a number.
	 * @return String a percent string
	 */
	public static final synchronized String getPercentage(double number,
			int newMaximumFractionDigits, int newMinimumFractionDigits) {
		pnf.setMaximumFractionDigits(newMaximumFractionDigits);
		pnf.setMinimumFractionDigits(newMinimumFractionDigits);
		return pnf.format(number);
	}

	/**
	 * Return a percent string from formatting number by percentage format,
	 * returned getPercentage(number,2,0) is fact.
	 * 
	 * @param number
	 *          double
	 * @return String a percent string
	 */
	public static final String getPercentage(double number) {
		return getPercentage(number, 2, 0);
	}

	/**
	 * Return a percent string,numberstring must contain a parsable double, format
	 * new double number after numberstring been parsed . format against specified
	 * two aguments.
	 * 
	 * @param numberstring
	 *          String
	 * @param newMaximumFractionDigits
	 *          int the maximum number of digits allowed in the fraction portion
	 *          of a number.
	 * @param newMinimumFractionDigits
	 *          int the minimum number of digits allowed in the fraction portion
	 *          of a number.
	 * @return String a percent string
	 */
	public static final String getPercentage(String numberstring,
			int newMaximumFractionDigits, int newMinimumFractionDigits) {
		double number = Double.parseDouble(numberstring.trim());
		return getPercentage(number, newMaximumFractionDigits,
				newMinimumFractionDigits);
	}

	/**
	 * Return a percent string from formatting numberstring by percentage format,
	 * returned getPercentage(numberstring,2,0) is fact.
	 * 
	 * @param numberstring
	 *          String
	 * @return String a percent string
	 */
	public static final String getPercentage(String numberstring) {
		return getPercentage(numberstring, 2, 0);
	}

	/**
	 * Constructs a new String by decoding the specified array of bytes using the
	 * specified charset.
	 * 
	 * @param bytes
	 *          byte[]
	 * @param charsetName
	 *          String
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static final String getString(byte[] bytes, String charsetName)
			throws UnsupportedEncodingException {
		return new String(bytes, charsetName);
	}

	/**
	 * Encodes this String into a sequence of bytes using the named charset,
	 * storing the result into a new byte array.
	 * 
	 * @param source
	 *          String
	 * @param charsetName
	 *          String
	 * @return byte[]
	 * @throws UnsupportedEncodingException
	 */
	public static final byte[] getBytes(String source, String charsetName)
			throws UnsupportedEncodingException {
		return source.getBytes(charsetName);
	}

	/**
	 * Replaces each substring of this string that matches the given regular
	 * expression with the given replacement.
	 * 
	 * @param input
	 *          String
	 * @param regex
	 *          String
	 * @param replacement
	 *          String
	 * @return String
	 */
	public static final String replace(String input, String regex, String replacement) {
		return input.replaceAll(regex, replacement);
	}

	/**
	 * 将input中的target字符串移除
	 * @param input
	 * @param target
	 * @return
	 */
	public static final String replaceString(String input, final String target) {
		return replaceString(input, target, null);
	}

	/**
	 * replace target string to replacement string
	 * 
	 * @param input
	 * @param target
	 * @param replacement
	 * @return
	 */
	public static final String replaceString(String input, final String target,
			String replacement) {
		if (null == input || null == target)
			return input;
		if (null == replacement)
			replacement = "";
		int index = input.indexOf(target);
		while (index != -1) {
			input = input.substring(0, index).concat(replacement)
					.concat(input.substring(index + target.length()));
			index = input.indexOf(target, index + target.length());
		}
		return input;
	}
	
	/**
	 * 
	 * <p>指定字符串开始位置及长度的替换成*</p> 
	 * @param input
	 * @param startIndex
	 * @param length
	 * @return 
	 * @throws
	 */
	public static final String replaceStar(final String input, int startIndex,
			int length) {
		return replaceStar(input, null, startIndex, length);
	}

	/**
	 * 
	 * <p>指定字符串开始位置及长度的替换成*，但受限于截至字符endString</p> 
	 * @param input
	 * @param endString
	 * @param startIndex
	 * @param length
	 * @return 
	 * @throws
	 */
	public static final String replaceStar(final String input, final String endString,
			int startIndex, int length) {
		if (isBlank(input) || startIndex >= input.length()) {
			return input;
		}
		if (length < 0) {
			length = 0;
		}

		int end = -1;
		if (!isBlank(endString)) {
			end = input.indexOf(endString);
		}

		int endIndex = startIndex + length;

		if (end != -1) {
			if (end < startIndex) {
				return input;
			} else if (end < endIndex) {
				endIndex = end;
			}
		}

		if (endIndex >= input.length()) {
			endIndex = input.length();
		}
		StringBuilder sb = new StringBuilder(input.substring(0, startIndex));
		for (int i = 0; i < endIndex - startIndex; i++) {
			sb.append("*");
		}
		sb.append(input.substring(endIndex));
		return sb.toString();
	}

	/**
	 * 
	 * <p>指定字符串开始位置及长度的替换成*，但受限于截至字符@</p> 
	 * @param input
	 * @param startIndex
	 * @param length
	 * @return 
	 * @throws
	 */
	public static final String replaceStarForMail(final String input, int startIndex,
			int length) {
		return replaceStar(input, "@", startIndex, length);
	}
	
	/**
	 * 移除字符串string的首尾大括号{}
	 * 
	 * @param string
	 * @return
	 */
	public static final String removeStartEndBigBracket(String string) {
		return removeStartEnd(string, CharConstants.LEFT_BIG_BRACKET,
				CharConstants.RIGHT_BIG_BRACKET);
	}

	/**
	 * 移除字符串string的首尾中括号[]
	 * 
	 * @param string
	 * @return
	 */
	public static final String removeStartEndSquareBracket(String string) {
		return removeStartEnd(string, CharConstants.LEFT_SQUARE_BRACKET,
				CharConstants.RIGHT_SQUARE_BRACKET);
	}

	/**
	 * 移除字符串string的首尾小括号()
	 * 
	 * @param string
	 * @return
	 */
	public static final String removeStartEndBracket(String string) {
		return removeStartEnd(string, CharConstants.LEFT_BRACKET,
				CharConstants.RIGHT_BRACKET);
	}

	/**
	 * 移除字符串string的首尾双引号 ""
	 * 
	 * @param string
	 * @return
	 */
	public static final String removeStartEndDoubleQuote(String string) {
		return removeStartEnd(string, CharConstants.DOUBLE_QUOTATION_MARK,
				CharConstants.DOUBLE_QUOTATION_MARK);
	}

	/**
	 * 判断字符串是否被大括号包围，如{aaa}返回true
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean startEndWithBigBracket(String string) {
		return startEndWith(string, CharConstants.LEFT_BIG_BRACKET,
				CharConstants.RIGHT_BIG_BRACKET);
	}

	/**
	 * 判断字符串是否被中括号包围，如[aaa]返回true
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean startEndWithSquareBracket(String string) {
		return startEndWith(string, CharConstants.LEFT_SQUARE_BRACKET,
				CharConstants.RIGHT_SQUARE_BRACKET);
	}

	/**
	 * 判断字符串是否被小括号包围，如(aaa)返回true
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean startEndWithBracket(String string) {
		return startEndWith(string, CharConstants.LEFT_BRACKET,
				CharConstants.RIGHT_BRACKET);
	}

	/**
	 * 判断字符串string 是否以 startChar字符开始，且以endChar字符结束
	 * 
	 * @param string
	 * @param startChar
	 * @param endChar
	 * @return
	 */
	public static final boolean startEndWith(String string, String startChar,
			String endChar) {
		if (isNotBlank(string)) {
			string = string.trim();
			return string.startsWith(startChar) && string.endsWith(endChar);
		}
		return false;
	}

	/**
	 * 移除字符串开始符号是 startChar的字符，并移除字符串结束符号是 endChar 的字符
	 * 
	 * @param string
	 * @param startChar
	 * @param endChar
	 * @return
	 */
	public static final String removeStartEnd(String string, String startChar,
			String endChar) {
		if (null == string)
			return string;
		string = string.trim();
		if (isNotBlank(startChar) && string.startsWith(startChar))
			string = string.substring(startChar.length());
		if (isNotBlank(endChar) && string.endsWith(endChar))
			string = string.substring(0, string.length() - endChar.length());
		return string.trim();
	}

	/**
	 * 移除src字符串中 在 startEndChar 字符之间的字符串，startEndChar也一并移除
	 * 
	 * @param src
	 * @param startEndChar
	 * @return
	 */
	public static final String removeBetween(String src, String startEndChar) {
		return removeBetween(src, startEndChar, false);
	}

	/**
	 *  移除src字符串中 在 startEndChar 字符之间的字符串，contain为true时 startEndChar并不移除，否则startEndChar也一并移除
	 *  
	 * @param src
	 * @param startEndChar
	 * @param contain
	 * @return
	 */
	public static final String removeBetween(String src, String startEndChar,
			boolean contain) {
		return removeBetween(src, startEndChar, startEndChar, contain);
	}

	/**
	 *  移除src字符串中 在 startChar 与 endChar 字符之间的字符串，startChar与endChar也一并移除
	 *  
	 * @param src
	 * @param startChar
	 * @param endChar
	 * @return
	 */
	public static final String removeBetween(String src, String startChar,
			String endChar) {
		return removeBetween(src, startChar, endChar, false);
	}

	/**
	 *  移除src字符串中 在 startChar 与 endChar 字符之间的字符串，contain为true时startChar与endChar并不移除，否则一并移除
	 * @param src
	 * @param startChar
	 * @param endChar
	 * @param contain
	 * @return
	 */
	public static final String removeBetween(String src, String startChar,
			String endChar, boolean contain) {
		if (isBlank(src))
			return null;
		if (isBlank(startChar))
			throw new IllegalArgumentException("argument[startChar] cannot be empty!");
		if (isBlank(startChar) && isBlank(endChar))
			return src;
		int eIndex = src.length();
		int sIndex = src.indexOf(startChar);
		if (isNotBlank(endChar) && -1 != sIndex)
			eIndex = src.indexOf(endChar, sIndex + startChar.length());
		if (-1 != sIndex && -1 != eIndex && eIndex > sIndex) {
			src = src.substring(0, sIndex) + (contain ? startChar + endChar : "")
					+ src.substring(eIndex + endChar.length());
		}
		return src;
	}

//	public static String getRandomFileName(String fileName) {
//		return (System.currentTimeMillis() + (long) (Math.random() * 10000000000d))
//				+ FileUtils.getFileAuxiliaryName(fileName);
//	}

	/**
	 * 判断str是否是字母数字符号的组合
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isCheckPwd(String str) {
		String numStr = "0123456789";
		String letterStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String symbolStr = "`~!@#$%^&*()-_+=|<,>.?/:;'\"\\";
		boolean numFlag = false;
		boolean letterFlag = false;
		boolean symbolFlag = false;
		for (int i = 0; i < str.length(); i++) {
			if (numStr.indexOf(str.charAt(i)) != -1) {
				numFlag = true;
			} else if (letterStr.indexOf(str.charAt(i)) != -1) {
				letterFlag = true;
			} else if (symbolStr.indexOf(str.charAt(i)) != -1) {
				symbolFlag = true;
			}
		}
		return (numFlag && letterFlag && symbolFlag);
	}

	/**
	 * 随机产生一个含字母数字符号组合的字符串
	 * 
	 * @return
	 */
	public static String randomPwdString() {
		String ret = "";
		String[] strArray = { "0123456789",
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
				"`~!@#$%^&*()-_+=|,>.?/:;'\"\\" };
		Random random = new Random();
		String tmpRet = "";
		for (int i = 0; i < strArray.length; i++) {
			String str = strArray[i];
			for (int j = 0; j < 2; j++) {
				int start = random.nextInt(str.length());
				tmpRet += str.substring(start, start + 1);
			}
		}

		int[] indexs = { 0, 1, 2, 3, 4, 5 };
		for (int i = 0; i < indexs.length; i++) {
			int position = (int) (Math.random() * 6);
			int tmp = indexs[position];
			indexs[position] = indexs[i];
			indexs[i] = tmp;
		}
		for (int i = 0; i < indexs.length; i++) {
			ret += tmpRet.substring(indexs[i], indexs[i] + 1);
		}

		return ret;
	}

	/**
	 * 将oracle的blob数据读入string
	 * 
	 * @param blob
	 * @return
	 */
	public static final String getBlobValue(Blob blob) {
		StringBuffer sbString = new StringBuffer("");
		InputStream is = null;
		BufferedReader br = null;
		try {
			if (blob != null) {
				is = blob.getBinaryStream();
				br = new BufferedReader(new InputStreamReader(is, ENCODING));
				String line = null;
				while ((line = br.readLine()) != null) {
					sbString.append(line);
				}
			}
		} catch (IOException ex) {
			log.error("", ex);
		} catch (SQLException e) {
			log.error("", e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException ex) {
					log.error("", ex);
				}
			if (is != null)
				try {
					is.close();
				} catch (IOException ex) {
					log.error("", ex);
				}
		}
		return sbString.toString();
	}

	/**
	 * 将oracle的clob读入string
	 * 
	 * @param clob
	 * @return
	 */
	public static final String getClobValue(Clob clob) {
		StringBuffer sbString = new StringBuffer("");
		BufferedReader br = null;
		try {
			if (clob != null) {
				br = new BufferedReader(clob.getCharacterStream());
				String line = null;
				while ((line = br.readLine()) != null) {
					sbString.append(line);
				}
			}
		} catch (IOException ex) {
			log.error("", ex);
		} catch (SQLException e) {
			log.error("", e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException ex) {
					log.error("", ex);
				}
		}
		return sbString.toString();

	}

	/*
	 * 增加clob類型
	 */
	public static final String getClobString(Object clob) {
		if (null == clob)
			return "";
		else if (Clob.class.isAssignableFrom(clob.getClass()))
			return getClobValue((Clob) clob);
		else
			return clob.toString();
	}

	/**
	 * <p>繁体转简体</p>
	 * 
	 * @param message
	 * @return
	 */
	public static final String transferTranditionalToSimplified(String message) {		
		String taiWan = "啊阿埃挨哎唉哀皚癌藹矮艾礙愛隘鞍氨安俺按暗岸胺案肮昂盎凹敖熬翺襖傲奧懊澳芭捌扒叭吧笆八疤巴拔跋靶把耙壩霸罷爸白柏百擺佰敗拜稗斑班搬扳般頒板版扮拌伴瓣半辦絆邦幫梆榜膀綁棒磅蚌鎊傍謗苞胞包褒剝薄雹保堡飽寶抱報暴豹鮑爆杯碑悲卑北輩背貝鋇倍狽備憊焙被奔苯本笨崩繃甭泵蹦迸逼鼻比鄙筆彼碧蓖蔽畢斃毖幣庇痹閉敝弊必辟壁臂避陛鞭邊編貶扁便變卞辨辯辮遍標彪膘表鼈憋別癟彬斌瀕濱賓擯兵冰柄丙秉餅炳病並玻菠播撥缽波博勃搏鉑箔伯帛舶脖膊渤泊駁捕蔔哺補埠不布步簿部怖擦猜裁材才財睬踩采彩菜蔡餐參蠶殘慚慘燦蒼艙倉滄藏操糙槽曹草廁策側冊測層蹭插叉茬茶查碴搽察岔差詫拆柴豺攙摻蟬饞讒纏鏟産闡顫昌猖場嘗常長償腸廠敞暢唱倡超抄鈔朝嘲潮巢吵炒車扯撤掣徹澈郴臣辰塵晨忱沈陳趁襯撐稱城橙成呈乘程懲澄誠承逞騁秤吃癡持匙池遲弛馳恥齒侈尺赤翅斥熾充衝沖蟲崇寵抽酬疇躊稠愁籌仇綢瞅醜臭初出櫥廚躇鋤雛滁除楚礎儲矗搐觸處揣川穿椽傳船喘串瘡窗幢床闖創吹炊捶錘垂春椿醇唇淳純蠢戳綽疵茨磁雌辭慈瓷詞此刺賜次聰蔥囪匆從叢湊粗醋簇促躥篡竄摧崔催脆瘁粹淬翠村存寸磋撮搓措挫錯搭達答瘩打大呆歹傣戴帶殆代貸袋待逮怠耽擔丹單鄲撣膽旦氮但憚淡誕彈蛋當擋黨蕩檔刀搗蹈倒島禱導到稻悼道盜德得的蹬燈登等瞪凳鄧堤低滴迪敵笛狄滌翟嫡抵底地蒂第帝弟遞締顛掂滇碘點典靛墊電佃甸店惦奠澱殿碉叼雕凋刁掉吊釣調跌爹碟蝶叠諜疊丁盯叮釘頂鼎錠定訂丟東冬董懂動棟侗恫凍洞兜抖鬥陡豆逗痘都督毒犢獨讀堵睹賭杜鍍肚度渡妒端短鍛段斷緞堆兌隊對墩噸蹲敦頓囤鈍盾遁掇哆多奪垛躲朵跺舵剁惰墮蛾峨鵝俄額訛娥惡厄扼遏鄂餓恩而兒耳爾餌洱二貳發罰筏伐乏閥法琺藩帆番翻樊礬釩繁凡煩反返範販犯飯泛坊芳方肪房防妨仿訪紡放菲非啡飛肥匪誹吠肺廢沸費芬酚吩氛分紛墳焚汾粉奮份忿憤糞豐封楓蜂峰鋒風瘋烽逢馮縫諷奉鳳佛否夫敷膚孵扶拂輻幅氟符伏俘服浮涪福袱弗甫撫輔俯釜斧脯腑府腐赴副覆賦複傅付阜父腹負富訃附婦縛咐噶嘎該改概鈣蓋溉幹甘杆柑竿肝趕感稈敢贛岡剛鋼缸肛綱崗港杠篙臯高膏羔糕搞鎬稿告哥歌擱戈鴿胳疙割革葛格蛤閣隔鉻個各給根跟耕更庚羹埂耿梗工攻功恭龔供躬公宮弓鞏汞拱貢共鈎勾溝苟狗垢構購夠辜菇咕箍估沽孤姑鼓古蠱骨谷股故顧固雇刮瓜剮寡挂褂乖拐怪棺關官冠觀管館罐慣灌貫光廣逛瑰規圭矽歸龜閨軌鬼詭癸桂櫃跪貴劊輥滾棍鍋郭國果裹過哈骸孩海氦亥害駭酣憨邯韓含涵寒函喊罕翰撼捍旱憾悍焊汗漢夯杭航壕嚎豪毫郝好耗號浩呵喝荷菏核禾和何合盒貉閡河涸赫褐鶴賀嘿黑痕很狠恨哼亨橫衡恒轟哄烘虹鴻洪宏弘紅喉侯猴吼厚候後呼乎忽瑚壺葫胡蝴狐糊湖弧虎唬護互滬戶花嘩華猾滑畫劃化話槐徊懷淮壞歡環桓還緩換患喚瘓豢煥渙宦幻荒慌黃磺蝗簧皇凰惶煌晃幌恍謊灰揮輝徽恢蛔回毀悔慧卉惠晦賄穢會燴彙諱誨繪葷昏婚魂渾混豁活夥火獲或惑霍貨禍擊圾基機畸稽積箕肌饑迹激譏雞姬績緝吉極棘輯籍集及急疾汲即嫉級擠幾脊己薊技冀季伎祭劑悸濟寄寂計記既忌際妓繼紀嘉枷夾佳家加莢頰賈甲鉀假稼價架駕嫁殲監堅尖箋間煎兼肩艱奸緘繭檢柬堿鹼揀撿簡儉剪減薦檻鑒踐賤見鍵箭件健艦劍餞漸濺澗建僵姜將漿江疆蔣槳獎講匠醬降蕉椒礁焦膠交郊澆驕嬌嚼攪鉸矯僥腳狡角餃繳絞剿教酵轎較叫窖揭接皆稭街階截劫節莖睛晶鯨京驚精粳經井警景頸靜境敬鏡徑痙靖竟競淨炯窘揪究糾玖韭久灸九酒廄救舊臼舅咎就疚鞠拘狙疽居駒菊局咀矩舉沮聚拒據巨具距踞鋸俱句懼炬劇捐鵑娟倦眷卷絹撅攫抉掘倔爵桔傑捷睫竭潔結解姐戒藉芥界借介疥誡屆巾筋斤金今津襟緊錦僅謹進靳晉禁近燼浸盡勁荊兢覺決訣絕均菌鈞軍君峻俊竣浚郡駿喀咖卡咯開揩楷凱慨刊堪勘坎砍看康慷糠扛抗亢炕考拷烤靠坷苛柯棵磕顆科殼咳可渴克刻客課肯啃墾懇坑吭空恐孔控摳口扣寇枯哭窟苦酷庫褲誇垮挎跨胯塊筷儈快寬款匡筐狂框礦眶曠況虧盔巋窺葵奎魁傀饋愧潰坤昆捆困括擴廓闊垃拉喇蠟臘辣啦萊來賴藍婪欄攔籃闌蘭瀾讕攬覽懶纜爛濫琅榔狼廊郎朗浪撈勞牢老佬姥酪烙澇勒樂雷鐳蕾磊累儡壘擂肋類淚棱楞冷厘梨犁黎籬狸離漓理李裏鯉禮莉荔吏栗麗厲勵礫曆利傈例俐痢立粒瀝隸力璃哩倆聯蓮連鐮廉憐漣簾斂臉鏈戀煉練糧涼梁粱良兩輛量晾亮諒撩聊僚療燎寥遼潦了撂鐐廖料列裂烈劣獵琳林磷霖臨鄰鱗淋凜賃吝拎玲菱零齡鈴伶羚淩靈陵嶺領另令溜琉榴硫餾留劉瘤流柳六龍聾嚨籠窿隆壟攏隴樓婁摟簍漏陋蘆盧顱廬爐擄鹵虜魯麓碌露路賂鹿潞祿錄陸戮驢呂鋁侶旅履屢縷慮氯律率濾綠巒攣孿灤卵亂掠略掄輪倫侖淪綸論蘿螺羅邏鑼籮騾裸落洛駱絡媽麻瑪碼螞馬罵嘛嗎埋買麥賣邁脈瞞饅蠻滿蔓曼慢漫謾芒茫盲氓忙莽貓茅錨毛矛鉚卯茂冒帽貌貿麽玫枚梅酶黴煤沒眉媒鎂每美昧寐妹媚門悶們萌蒙檬盟錳猛夢孟眯醚靡糜迷謎彌米秘覓泌蜜密冪棉眠綿冕免勉娩緬面苗描瞄藐秒渺廟妙蔑滅民抿皿敏憫閩明螟鳴銘名命謬摸摹蘑模膜磨摩魔抹末莫墨默沫漠寞陌謀牟某拇牡畝姆母墓暮幕募慕木目睦牧穆拿哪呐鈉那娜納氖乃奶耐奈南男難囊撓腦惱鬧淖呢餒內嫩能妮霓倪泥尼擬你匿膩逆溺蔫拈年碾攆撚念娘釀鳥尿捏聶孽齧鑷鎳涅您檸獰凝甯擰濘牛扭鈕紐膿濃農弄奴努怒女暖虐瘧挪懦糯諾哦歐鷗毆藕嘔偶漚啪趴爬帕怕琶拍排牌徘湃派攀潘盤磐盼畔判叛乓龐旁耪胖抛咆刨炮袍跑泡呸胚培裴賠陪配佩沛噴盆砰抨烹澎彭蓬棚硼篷膨朋鵬捧碰坯砒霹批披劈琵毗啤脾疲皮匹痞僻屁譬篇偏片騙飄漂瓢票撇瞥拼頻貧品聘乒坪蘋萍平憑瓶評屏坡潑頗婆破魄迫粕剖撲鋪仆莆葡菩蒲埔樸圃普浦譜曝瀑期欺棲戚妻七淒漆柒沏其棋奇歧畦崎臍齊旗祈祁騎起豈乞企啓契砌器氣迄棄汽泣訖掐洽牽扡釺鉛千遷簽仟謙乾黔錢鉗前潛遣淺譴塹嵌欠歉槍嗆腔羌牆薔強搶橇鍬敲悄橋瞧喬僑巧鞘撬翹峭俏竅切茄且怯竊欽侵親秦琴勤芹擒禽寢沁青輕氫傾卿清擎晴氰情頃請慶瓊窮秋丘邱球求囚酋泅趨區蛆曲軀屈驅渠取娶齲趣去圈顴權醛泉全痊拳犬券勸缺炔瘸卻鵲榷確雀裙群然燃冉染瓤壤攘嚷讓饒擾繞惹熱壬仁人忍韌任認刃妊紉扔仍日戎茸蓉榮融熔溶容絨冗揉柔肉茹蠕儒孺如辱乳汝入褥軟阮蕊瑞銳閏潤若弱撒灑薩腮鰓塞賽三三傘散桑嗓喪搔騷掃嫂瑟色澀森僧莎砂殺刹沙紗傻啥煞篩曬珊苫杉山刪煽衫閃陝擅贍膳善汕扇繕墒傷商賞晌上尚裳梢捎稍燒芍勺韶少哨邵紹奢賒蛇舌舍赦攝射懾涉社設砷申呻伸身深娠紳神沈審嬸甚腎慎滲聲生甥牲升繩省盛剩勝聖師失獅施濕詩屍虱十石拾時什食蝕實識史矢使屎駛始式示士世柿事拭誓逝勢是嗜噬適仕侍釋飾氏市恃室視試收手首守壽授售受瘦獸蔬樞梳殊抒輸叔舒淑疏書贖孰熟薯暑曙署蜀黍鼠屬術述樹束戍豎墅庶數漱恕刷耍摔衰甩帥栓拴霜雙爽誰水睡稅吮瞬順舜說碩朔爍斯撕嘶思私司絲死肆寺嗣四伺似飼巳松聳慫頌送宋訟誦搜艘擻嗽蘇酥俗素速粟僳塑溯宿訴肅酸蒜算雖隋隨綏髓碎歲穗遂隧祟孫損筍蓑梭唆縮瑣索鎖所塌他它她塔獺撻蹋踏胎苔擡台泰酞太態汰坍攤貪癱灘壇檀痰潭譚談坦毯袒碳探歎炭湯塘搪堂棠膛唐糖倘躺淌趟燙掏濤滔縧萄桃逃淘陶討套特藤騰疼謄梯剔踢銻提題蹄啼體替嚏惕涕剃屜天添填田甜恬舔腆挑條迢眺跳貼鐵帖廳聽烴汀廷停亭庭挺艇通桐酮瞳同銅彤童桶捅筒統痛偷投頭透凸禿突圖徒途塗屠土吐兔湍團推頹腿蛻褪退吞屯臀拖托脫鴕陀馱駝橢妥拓唾挖哇蛙窪娃瓦襪歪外豌彎灣玩頑丸烷完碗挽晚皖惋宛婉萬腕汪王亡枉網往旺望忘妄威巍微危韋違桅圍唯惟為濰維葦萎委偉僞尾緯未蔚味畏胃餵魏位渭謂尉慰衛瘟溫蚊文聞紋吻穩紊問嗡翁甕撾蝸渦窩我斡臥握沃巫嗚鎢烏汙誣屋無蕪梧吾吳毋武五捂午舞伍侮塢戊霧晤物勿務悟誤昔熙析西硒矽晰嘻吸錫犧稀息希悉膝夕惜熄烯溪汐犀檄襲席習媳喜銑洗系隙戲細瞎蝦匣霞轄暇峽俠狹下廈夏嚇掀鍁先仙鮮纖鹹賢銜舷閑涎弦嫌顯險現獻縣腺餡羨憲陷限線相廂鑲香箱襄湘鄉翔祥詳想響享項巷橡像向象蕭硝霄削哮囂銷消宵淆曉小孝校肖嘯笑效楔些歇蠍鞋協挾攜邪斜脅諧寫械卸蟹懈泄瀉謝屑薪芯鋅欣辛新忻心信釁星腥猩惺興刑型形邢行醒幸杏性姓兄凶胸匈洶雄熊休修羞朽嗅鏽秀袖繡墟戌需虛噓須徐許蓄酗敘旭序畜恤絮婿緒續軒喧宣懸旋玄選癬眩絢靴薛學穴雪血勳熏循旬詢尋馴巡殉汛訓訊遜迅壓押鴉鴨呀丫芽牙蚜崖衙涯雅啞亞訝焉咽閹煙淹鹽嚴研蜒岩延言顔閻炎沿奄掩眼衍演豔堰燕厭硯雁唁彥焰宴諺驗殃央鴦秧楊揚佯瘍羊洋陽氧仰癢養樣漾邀腰妖瑤搖堯遙窯謠姚咬舀藥要耀椰噎耶爺野冶也頁掖業葉曳腋夜液一壹醫揖銥依伊衣頤夷遺移儀胰疑沂宜姨彜椅蟻倚已乙矣以藝抑易邑屹億役臆逸肄疫亦裔意毅憶義益溢詣議誼譯異翼翌繹茵蔭因殷音陰姻吟銀淫寅飲尹引隱印英櫻嬰鷹應纓瑩螢營熒蠅迎贏盈影穎硬映喲擁傭臃癰庸雍踴蛹詠泳湧永恿勇用幽優悠憂尤由郵鈾猶油遊酉有友右佑釉誘又幼迂淤于盂榆虞愚輿余俞逾魚愉渝漁隅予娛雨與嶼禹宇語羽玉域芋郁籲遇喻峪禦愈欲獄育譽浴寓裕預豫馭鴛淵冤元垣袁原援轅園員圓猿源緣遠苑願怨院曰約越躍鑰嶽粵月悅閱耘雲鄖勻隕允運蘊醞暈韻孕匝砸雜栽哉災宰載再在咱攢暫贊贓髒葬遭糟鑿藻棗早澡蚤躁噪造皂竈燥責擇則澤賊怎增憎曾贈紮喳渣劄軋鍘閘眨柵榨咋乍炸詐摘齋宅窄債寨瞻氈詹粘沾盞斬輾嶄展蘸棧占戰站湛綻樟章彰漳張掌漲杖丈帳賬仗脹瘴障招昭找沼趙照罩兆肇召遮折哲蟄轍者鍺蔗這浙珍斟真甄砧臻貞針偵枕疹診震振鎮陣蒸掙睜征猙爭怔整拯正政幀症鄭證芝枝支吱蜘知肢脂汁之織職直植殖執值侄址指止趾只旨紙志摯擲至致置幟峙制智秩稚質炙痔滯治窒中盅忠鍾衷終種腫重仲衆舟周州洲謅粥軸肘帚咒皺宙晝驟珠株蛛朱豬諸誅逐竹燭煮拄矚囑主著柱助蛀貯鑄築住注祝駐抓爪拽專磚轉撰賺篆樁莊裝妝撞壯狀椎錐追贅墜綴諄准捉拙卓桌琢茁酌啄著灼濁茲咨資姿滋淄孜紫仔籽滓子自漬字鬃棕蹤宗綜總縱鄒走奏揍租足卒族祖詛阻組鑽纂嘴醉最罪尊遵昨左佐柞做作坐座";
		String profChina = "啊阿埃挨哎唉哀皑癌蔼矮艾碍爱隘鞍氨安俺按暗岸胺案肮昂盎凹敖熬翱袄傲奥懊澳芭捌扒叭吧笆八疤巴拔跋靶把耙坝霸罢爸白柏百摆佰败拜稗斑班搬扳般颁板版扮拌伴瓣半办绊邦帮梆榜膀绑棒磅蚌镑傍谤苞胞包褒剥薄雹保堡饱宝抱报暴豹鲍爆杯碑悲卑北辈背贝钡倍狈备惫焙被奔苯本笨崩绷甭泵蹦迸逼鼻比鄙笔彼碧蓖蔽毕毙毖币庇痹闭敝弊必辟壁臂避陛鞭边编贬扁便变卞辨辩辫遍标彪膘表鳖憋别瘪彬斌濒滨宾摈兵冰柄丙秉饼炳病并玻菠播拨钵波博勃搏铂箔伯帛舶脖膊渤泊驳捕卜哺补埠不布步簿部怖擦猜裁材才财睬踩采彩菜蔡餐参蚕残惭惨灿苍舱仓沧藏操糙槽曹草厕策侧册测层蹭插叉茬茶查碴搽察岔差诧拆柴豺搀掺蝉馋谗缠铲产阐颤昌猖场尝常长偿肠厂敞畅唱倡超抄钞朝嘲潮巢吵炒车扯撤掣彻澈郴臣辰尘晨忱沉陈趁衬撑称城橙成呈乘程惩澄诚承逞骋秤吃痴持匙池迟弛驰耻齿侈尺赤翅斥炽充冲冲虫崇宠抽酬畴踌稠愁筹仇绸瞅丑臭初出橱厨躇锄雏滁除楚础储矗搐触处揣川穿椽传船喘串疮窗幢床闯创吹炊捶锤垂春椿醇唇淳纯蠢戳绰疵茨磁雌辞慈瓷词此刺赐次聪葱囱匆从丛凑粗醋簇促蹿篡窜摧崔催脆瘁粹淬翠村存寸磋撮搓措挫错搭达答瘩打大呆歹傣戴带殆代贷袋待逮怠耽担丹单郸掸胆旦氮但惮淡诞弹蛋当挡党荡档刀捣蹈倒岛祷导到稻悼道盗德得的蹬灯登等瞪凳邓堤低滴迪敌笛狄涤翟嫡抵底地蒂第帝弟递缔颠掂滇碘点典靛垫电佃甸店惦奠淀殿碉叼雕凋刁掉吊钓调跌爹碟蝶迭谍叠丁盯叮钉顶鼎锭定订丢东冬董懂动栋侗恫冻洞兜抖斗陡豆逗痘都督毒犊独读堵睹赌杜镀肚度渡妒端短锻段断缎堆兑队对墩吨蹲敦顿囤钝盾遁掇哆多夺垛躲朵跺舵剁惰堕蛾峨鹅俄额讹娥恶厄扼遏鄂饿恩而儿耳尔饵洱二贰发罚筏伐乏阀法珐藩帆番翻樊矾钒繁凡烦反返范贩犯饭泛坊芳方肪房防妨仿访纺放菲非啡飞肥匪诽吠肺废沸费芬酚吩氛分纷坟焚汾粉奋份忿愤粪丰封枫蜂峰锋风疯烽逢冯缝讽奉凤佛否夫敷肤孵扶拂辐幅氟符伏俘服浮涪福袱弗甫抚辅俯釜斧脯腑府腐赴副覆赋复傅付阜父腹负富讣附妇缚咐噶嘎该改概钙盖溉干甘杆柑竿肝赶感秆敢赣冈刚钢缸肛纲岗港杠篙皋高膏羔糕搞镐稿告哥歌搁戈鸽胳疙割革葛格蛤阁隔铬个各给根跟耕更庚羹埂耿梗工攻功恭龚供躬公宫弓巩汞拱贡共钩勾沟苟狗垢构购够辜菇咕箍估沽孤姑鼓古蛊骨谷股故顾固雇刮瓜剐寡挂褂乖拐怪棺关官冠观管馆罐惯灌贯光广逛瑰规圭硅归龟闺轨鬼诡癸桂柜跪贵刽辊滚棍锅郭国果裹过哈骸孩海氦亥害骇酣憨邯韩含涵寒函喊罕翰撼捍旱憾悍焊汗汉夯杭航壕嚎豪毫郝好耗号浩呵喝荷菏核禾和何合盒貉阂河涸赫褐鹤贺嘿黑痕很狠恨哼亨横衡恒轰哄烘虹鸿洪宏弘红喉侯猴吼厚候后呼乎忽瑚壶葫胡蝴狐糊湖弧虎唬护互沪户花哗华猾滑画划化话槐徊怀淮坏欢环桓还缓换患唤痪豢焕涣宦幻荒慌黄磺蝗簧皇凰惶煌晃幌恍谎灰挥辉徽恢蛔回毁悔慧卉惠晦贿秽会烩汇讳诲绘荤昏婚魂浑混豁活伙火获或惑霍货祸击圾基机畸稽积箕肌饥迹激讥鸡姬绩缉吉极棘辑籍集及急疾汲即嫉级挤几脊己蓟技冀季伎祭剂悸济寄寂计记既忌际妓继纪嘉枷夹佳家加荚颊贾甲钾假稼价架驾嫁歼监坚尖笺间煎兼肩艰奸缄茧检柬碱硷拣捡简俭剪减荐槛鉴践贱见键箭件健舰剑饯渐溅涧建僵姜将浆江疆蒋桨奖讲匠酱降蕉椒礁焦胶交郊浇骄娇嚼搅铰矫侥脚狡角饺缴绞剿教酵轿较叫窖揭接皆秸街阶截劫节茎睛晶鲸京惊精粳经井警景颈静境敬镜径痉靖竟竞净炯窘揪究纠玖韭久灸九酒厩救旧臼舅咎就疚鞠拘狙疽居驹菊局咀矩举沮聚拒据巨具距踞锯俱句惧炬剧捐鹃娟倦眷卷绢撅攫抉掘倔爵桔杰捷睫竭洁结解姐戒藉芥界借介疥诫届巾筋斤金今津襟紧锦仅谨进靳晋禁近烬浸尽劲荆兢觉决诀绝均菌钧军君峻俊竣浚郡骏喀咖卡咯开揩楷凯慨刊堪勘坎砍看康慷糠扛抗亢炕考拷烤靠坷苛柯棵磕颗科壳咳可渴克刻客课肯啃垦恳坑吭空恐孔控抠口扣寇枯哭窟苦酷库裤夸垮挎跨胯块筷侩快宽款匡筐狂框矿眶旷况亏盔岿窥葵奎魁傀馈愧溃坤昆捆困括扩廓阔垃拉喇蜡腊辣啦莱来赖蓝婪栏拦篮阑兰澜谰揽览懒缆烂滥琅榔狼廊郎朗浪捞劳牢老佬姥酪烙涝勒乐雷镭蕾磊累儡垒擂肋类泪棱楞冷厘梨犁黎篱狸离漓理李里鲤礼莉荔吏栗丽厉励砾历利傈例俐痢立粒沥隶力璃哩俩联莲连镰廉怜涟帘敛脸链恋炼练粮凉梁粱良两辆量晾亮谅撩聊僚疗燎寥辽潦了撂镣廖料列裂烈劣猎琳林磷霖临邻鳞淋凛赁吝拎玲菱零龄铃伶羚凌灵陵岭领另令溜琉榴硫馏留刘瘤流柳六龙聋咙笼窿隆垄拢陇楼娄搂篓漏陋芦卢颅庐炉掳卤虏鲁麓碌露路赂鹿潞禄录陆戮驴吕铝侣旅履屡缕虑氯律率滤绿峦挛孪滦卵乱掠略抡轮伦仑沦纶论萝螺罗逻锣箩骡裸落洛骆络妈麻玛码蚂马骂嘛吗埋买麦卖迈脉瞒馒蛮满蔓曼慢漫谩芒茫盲氓忙莽猫茅锚毛矛铆卯茂冒帽貌贸么玫枚梅酶霉煤没眉媒镁每美昧寐妹媚门闷们萌蒙檬盟锰猛梦孟眯醚靡糜迷谜弥米秘觅泌蜜密幂棉眠绵冕免勉娩缅面苗描瞄藐秒渺庙妙蔑灭民抿皿敏悯闽明螟鸣铭名命谬摸摹蘑模膜磨摩魔抹末莫墨默沫漠寞陌谋牟某拇牡亩姆母墓暮幕募慕木目睦牧穆拿哪呐钠那娜纳氖乃奶耐奈南男难囊挠脑恼闹淖呢馁内嫩能妮霓倪泥尼拟你匿腻逆溺蔫拈年碾撵捻念娘酿鸟尿捏聂孽啮镊镍涅您柠狞凝宁拧泞牛扭钮纽脓浓农弄奴努怒女暖虐疟挪懦糯诺哦欧鸥殴藕呕偶沤啪趴爬帕怕琶拍排牌徘湃派攀潘盘磐盼畔判叛乓庞旁耪胖抛咆刨炮袍跑泡呸胚培裴赔陪配佩沛喷盆砰抨烹澎彭蓬棚硼篷膨朋鹏捧碰坯砒霹批披劈琵毗啤脾疲皮匹痞僻屁譬篇偏片骗飘漂瓢票撇瞥拼频贫品聘乒坪苹萍平凭瓶评屏坡泼颇婆破魄迫粕剖扑铺仆莆葡菩蒲埔朴圃普浦谱曝瀑期欺栖戚妻七凄漆柒沏其棋奇歧畦崎脐齐旗祈祁骑起岂乞企启契砌器气迄弃汽泣讫掐洽牵扦钎铅千迁签仟谦乾黔钱钳前潜遣浅谴堑嵌欠歉枪呛腔羌墙蔷强抢橇锹敲悄桥瞧乔侨巧鞘撬翘峭俏窍切茄且怯窃钦侵亲秦琴勤芹擒禽寝沁青轻氢倾卿清擎晴氰情顷请庆琼穷秋丘邱球求囚酋泅趋区蛆曲躯屈驱渠取娶龋趣去圈颧权醛泉全痊拳犬券劝缺炔瘸却鹊榷确雀裙群然燃冉染瓤壤攘嚷让饶扰绕惹热壬仁人忍韧任认刃妊纫扔仍日戎茸蓉荣融熔溶容绒冗揉柔肉茹蠕儒孺如辱乳汝入褥软阮蕊瑞锐闰润若弱撒洒萨腮鳃塞赛三叁伞散桑嗓丧搔骚扫嫂瑟色涩森僧莎砂杀刹沙纱傻啥煞筛晒珊苫杉山删煽衫闪陕擅赡膳善汕扇缮墒伤商赏晌上尚裳梢捎稍烧芍勺韶少哨邵绍奢赊蛇舌舍赦摄射慑涉社设砷申呻伸身深娠绅神沈审婶甚肾慎渗声生甥牲升绳省盛剩胜圣师失狮施湿诗尸虱十石拾时什食蚀实识史矢使屎驶始式示士世柿事拭誓逝势是嗜噬适仕侍释饰氏市恃室视试收手首守寿授售受瘦兽蔬枢梳殊抒输叔舒淑疏书赎孰熟薯暑曙署蜀黍鼠属术述树束戍竖墅庶数漱恕刷耍摔衰甩帅栓拴霜双爽谁水睡税吮瞬顺舜说硕朔烁斯撕嘶思私司丝死肆寺嗣四伺似饲巳松耸怂颂送宋讼诵搜艘擞嗽苏酥俗素速粟僳塑溯宿诉肃酸蒜算虽隋随绥髓碎岁穗遂隧祟孙损笋蓑梭唆缩琐索锁所塌他它她塔獭挞蹋踏胎苔抬台泰酞太态汰坍摊贪瘫滩坛檀痰潭谭谈坦毯袒碳探叹炭汤塘搪堂棠膛唐糖倘躺淌趟烫掏涛滔绦萄桃逃淘陶讨套特藤腾疼誊梯剔踢锑提题蹄啼体替嚏惕涕剃屉天添填田甜恬舔腆挑条迢眺跳贴铁帖厅听烃汀廷停亭庭挺艇通桐酮瞳同铜彤童桶捅筒统痛偷投头透凸秃突图徒途涂屠土吐兔湍团推颓腿蜕褪退吞屯臀拖托脱鸵陀驮驼椭妥拓唾挖哇蛙洼娃瓦袜歪外豌弯湾玩顽丸烷完碗挽晚皖惋宛婉万腕汪王亡枉网往旺望忘妄威巍微危韦违桅围唯惟为潍维苇萎委伟伪尾纬未蔚味畏胃喂魏位渭谓尉慰卫瘟温蚊文闻纹吻稳紊问嗡翁瓮挝蜗涡窝我斡卧握沃巫呜钨乌污诬屋无芜梧吾吴毋武五捂午舞伍侮坞戊雾晤物勿务悟误昔熙析西硒矽晰嘻吸锡牺稀息希悉膝夕惜熄烯溪汐犀檄袭席习媳喜铣洗系隙戏细瞎虾匣霞辖暇峡侠狭下厦夏吓掀锨先仙鲜纤咸贤衔舷闲涎弦嫌显险现献县腺馅羡宪陷限线相厢镶香箱襄湘乡翔祥详想响享项巷橡像向象萧硝霄削哮嚣销消宵淆晓小孝校肖啸笑效楔些歇蝎鞋协挟携邪斜胁谐写械卸蟹懈泄泻谢屑薪芯锌欣辛新忻心信衅星腥猩惺兴刑型形邢行醒幸杏性姓兄凶胸匈汹雄熊休修羞朽嗅锈秀袖绣墟戌需虚嘘须徐许蓄酗叙旭序畜恤絮婿绪续轩喧宣悬旋玄选癣眩绚靴薛学穴雪血勋熏循旬询寻驯巡殉汛训讯逊迅压押鸦鸭呀丫芽牙蚜崖衙涯雅哑亚讶焉咽阉烟淹盐严研蜒岩延言颜阎炎沿奄掩眼衍演艳堰燕厌砚雁唁彦焰宴谚验殃央鸯秧杨扬佯疡羊洋阳氧仰痒养样漾邀腰妖瑶摇尧遥窑谣姚咬舀药要耀椰噎耶爷野冶也页掖业叶曳腋夜液一壹医揖铱依伊衣颐夷遗移仪胰疑沂宜姨彝椅蚁倚已乙矣以艺抑易邑屹亿役臆逸肄疫亦裔意毅忆义益溢诣议谊译异翼翌绎茵荫因殷音阴姻吟银淫寅饮尹引隐印英樱婴鹰应缨莹萤营荧蝇迎赢盈影颖硬映哟拥佣臃痈庸雍踊蛹咏泳涌永恿勇用幽优悠忧尤由邮铀犹油游酉有友右佑釉诱又幼迂淤于盂榆虞愚舆余俞逾鱼愉渝渔隅予娱雨与屿禹宇语羽玉域芋郁吁遇喻峪御愈欲狱育誉浴寓裕预豫驭鸳渊冤元垣袁原援辕园员圆猿源缘远苑愿怨院曰约越跃钥岳粤月悦阅耘云郧匀陨允运蕴酝晕韵孕匝砸杂栽哉灾宰载再在咱攒暂赞赃脏葬遭糟凿藻枣早澡蚤躁噪造皂灶燥责择则泽贼怎增憎曾赠扎喳渣札轧铡闸眨栅榨咋乍炸诈摘斋宅窄债寨瞻毡詹粘沾盏斩辗崭展蘸栈占战站湛绽樟章彰漳张掌涨杖丈帐账仗胀瘴障招昭找沼赵照罩兆肇召遮折哲蛰辙者锗蔗这浙珍斟真甄砧臻贞针侦枕疹诊震振镇阵蒸挣睁征狰争怔整拯正政帧症郑证芝枝支吱蜘知肢脂汁之织职直植殖执值侄址指止趾只旨纸志挚掷至致置帜峙制智秩稚质炙痔滞治窒中盅忠钟衷终种肿重仲众舟周州洲诌粥轴肘帚咒皱宙昼骤珠株蛛朱猪诸诛逐竹烛煮拄瞩嘱主著柱助蛀贮铸筑住注祝驻抓爪拽专砖转撰赚篆桩庄装妆撞壮状椎锥追赘坠缀谆准捉拙卓桌琢茁酌啄着灼浊兹咨资姿滋淄孜紫仔籽滓子自渍字鬃棕踪宗综总纵邹走奏揍租足卒族祖诅阻组钻纂嘴醉最罪尊遵昨左佐柞做作坐座";
		String ret = "";
		for (int i = 0; i < message.length(); i++) {
			if (taiWan.indexOf(message.charAt(i)) != -1)
				ret += profChina.charAt(taiWan.indexOf(message.charAt(i)));
			else
				ret += message.charAt(i);
		}
		return ret;
	}

	/**
	 * <p>简体转繁体</p>
	 * 
	 * @param message
	 * @return
	 */
	public static final String transferSimplifiedToTranditional(String message) {		
		String profChina = "啊阿埃挨哎唉哀皚癌藹矮艾礙愛隘鞍氨安俺按暗岸胺案肮昂盎凹敖熬翺襖傲奧懊澳芭捌扒叭吧笆八疤巴拔跋靶把耙壩霸罷爸白柏百擺佰敗拜稗斑班搬扳般頒板版扮拌伴瓣半辦絆邦幫梆榜膀綁棒磅蚌鎊傍謗苞胞包褒剝薄雹保堡飽寶抱報暴豹鮑爆杯碑悲卑北輩背貝鋇倍狽備憊焙被奔苯本笨崩繃甭泵蹦迸逼鼻比鄙筆彼碧蓖蔽畢斃毖幣庇痹閉敝弊必辟壁臂避陛鞭邊編貶扁便變卞辨辯辮遍標彪膘表鼈憋別癟彬斌瀕濱賓擯兵冰柄丙秉餅炳病並玻菠播撥缽波博勃搏鉑箔伯帛舶脖膊渤泊駁捕蔔哺補埠不布步簿部怖擦猜裁材才財睬踩采彩菜蔡餐參蠶殘慚慘燦蒼艙倉滄藏操糙槽曹草廁策側冊測層蹭插叉茬茶查碴搽察岔差詫拆柴豺攙摻蟬饞讒纏鏟産闡顫昌猖場嘗常長償腸廠敞暢唱倡超抄鈔朝嘲潮巢吵炒車扯撤掣徹澈郴臣辰塵晨忱沈陳趁襯撐稱城橙成呈乘程懲澄誠承逞騁秤吃癡持匙池遲弛馳恥齒侈尺赤翅斥熾充衝沖蟲崇寵抽酬疇躊稠愁籌仇綢瞅醜臭初出櫥廚躇鋤雛滁除楚礎儲矗搐觸處揣川穿椽傳船喘串瘡窗幢床闖創吹炊捶錘垂春椿醇唇淳純蠢戳綽疵茨磁雌辭慈瓷詞此刺賜次聰蔥囪匆從叢湊粗醋簇促躥篡竄摧崔催脆瘁粹淬翠村存寸磋撮搓措挫錯搭達答瘩打大呆歹傣戴帶殆代貸袋待逮怠耽擔丹單鄲撣膽旦氮但憚淡誕彈蛋當擋黨蕩檔刀搗蹈倒島禱導到稻悼道盜德得的蹬燈登等瞪凳鄧堤低滴迪敵笛狄滌翟嫡抵底地蒂第帝弟遞締顛掂滇碘點典靛墊電佃甸店惦奠澱殿碉叼雕凋刁掉吊釣調跌爹碟蝶叠諜疊丁盯叮釘頂鼎錠定訂丟東冬董懂動棟侗恫凍洞兜抖鬥陡豆逗痘都督毒犢獨讀堵睹賭杜鍍肚度渡妒端短鍛段斷緞堆兌隊對墩噸蹲敦頓囤鈍盾遁掇哆多奪垛躲朵跺舵剁惰墮蛾峨鵝俄額訛娥惡厄扼遏鄂餓恩而兒耳爾餌洱二貳發罰筏伐乏閥法琺藩帆番翻樊礬釩繁凡煩反返範販犯飯泛坊芳方肪房防妨仿訪紡放菲非啡飛肥匪誹吠肺廢沸費芬酚吩氛分紛墳焚汾粉奮份忿憤糞豐封楓蜂峰鋒風瘋烽逢馮縫諷奉鳳佛否夫敷膚孵扶拂輻幅氟符伏俘服浮涪福袱弗甫撫輔俯釜斧脯腑府腐赴副覆賦複傅付阜父腹負富訃附婦縛咐噶嘎該改概鈣蓋溉幹甘杆柑竿肝趕感稈敢贛岡剛鋼缸肛綱崗港杠篙臯高膏羔糕搞鎬稿告哥歌擱戈鴿胳疙割革葛格蛤閣隔鉻個各給根跟耕更庚羹埂耿梗工攻功恭龔供躬公宮弓鞏汞拱貢共鈎勾溝苟狗垢構購夠辜菇咕箍估沽孤姑鼓古蠱骨谷股故顧固雇刮瓜剮寡挂褂乖拐怪棺關官冠觀管館罐慣灌貫光廣逛瑰規圭矽歸龜閨軌鬼詭癸桂櫃跪貴劊輥滾棍鍋郭國果裹過哈骸孩海氦亥害駭酣憨邯韓含涵寒函喊罕翰撼捍旱憾悍焊汗漢夯杭航壕嚎豪毫郝好耗號浩呵喝荷菏核禾和何合盒貉閡河涸赫褐鶴賀嘿黑痕很狠恨哼亨橫衡恒轟哄烘虹鴻洪宏弘紅喉侯猴吼厚候後呼乎忽瑚壺葫胡蝴狐糊湖弧虎唬護互滬戶花嘩華猾滑畫劃化話槐徊懷淮壞歡環桓還緩換患喚瘓豢煥渙宦幻荒慌黃磺蝗簧皇凰惶煌晃幌恍謊灰揮輝徽恢蛔回毀悔慧卉惠晦賄穢會燴彙諱誨繪葷昏婚魂渾混豁活夥火獲或惑霍貨禍擊圾基機畸稽積箕肌饑迹激譏雞姬績緝吉極棘輯籍集及急疾汲即嫉級擠幾脊己薊技冀季伎祭劑悸濟寄寂計記既忌際妓繼紀嘉枷夾佳家加莢頰賈甲鉀假稼價架駕嫁殲監堅尖箋間煎兼肩艱奸緘繭檢柬堿鹼揀撿簡儉剪減薦檻鑒踐賤見鍵箭件健艦劍餞漸濺澗建僵姜將漿江疆蔣槳獎講匠醬降蕉椒礁焦膠交郊澆驕嬌嚼攪鉸矯僥腳狡角餃繳絞剿教酵轎較叫窖揭接皆稭街階截劫節莖睛晶鯨京驚精粳經井警景頸靜境敬鏡徑痙靖竟競淨炯窘揪究糾玖韭久灸九酒廄救舊臼舅咎就疚鞠拘狙疽居駒菊局咀矩舉沮聚拒據巨具距踞鋸俱句懼炬劇捐鵑娟倦眷卷絹撅攫抉掘倔爵桔傑捷睫竭潔結解姐戒藉芥界借介疥誡屆巾筋斤金今津襟緊錦僅謹進靳晉禁近燼浸盡勁荊兢覺決訣絕均菌鈞軍君峻俊竣浚郡駿喀咖卡咯開揩楷凱慨刊堪勘坎砍看康慷糠扛抗亢炕考拷烤靠坷苛柯棵磕顆科殼咳可渴克刻客課肯啃墾懇坑吭空恐孔控摳口扣寇枯哭窟苦酷庫褲誇垮挎跨胯塊筷儈快寬款匡筐狂框礦眶曠況虧盔巋窺葵奎魁傀饋愧潰坤昆捆困括擴廓闊垃拉喇蠟臘辣啦萊來賴藍婪欄攔籃闌蘭瀾讕攬覽懶纜爛濫琅榔狼廊郎朗浪撈勞牢老佬姥酪烙澇勒樂雷鐳蕾磊累儡壘擂肋類淚棱楞冷厘梨犁黎籬狸離漓理李裡鯉禮莉荔吏栗麗厲勵礫曆利傈例俐痢立粒瀝隸力璃哩倆聯蓮連鐮廉憐漣簾斂臉鏈戀煉練糧涼梁粱良兩輛量晾亮諒撩聊僚療燎寥遼潦了撂鐐廖料列裂烈劣獵琳林磷霖臨鄰鱗淋凜賃吝拎玲菱零齡鈴伶羚淩靈陵嶺領另令溜琉榴硫餾留劉瘤流柳六龍聾嚨籠窿隆壟攏隴樓婁摟簍漏陋蘆盧顱廬爐擄鹵虜魯麓碌露路賂鹿潞祿錄陸戮驢呂鋁侶旅履屢縷慮氯律率濾綠巒攣孿灤卵亂掠略掄輪倫侖淪綸論蘿螺羅邏鑼籮騾裸落洛駱絡媽麻瑪碼螞馬罵嘛嗎埋買麥賣邁脈瞞饅蠻滿蔓曼慢漫謾芒茫盲氓忙莽貓茅錨毛矛鉚卯茂冒帽貌貿麽玫枚梅酶黴煤沒眉媒鎂每美昧寐妹媚門悶們萌蒙檬盟錳猛夢孟眯醚靡糜迷謎彌米秘覓泌蜜密冪棉眠綿冕免勉娩緬面苗描瞄藐秒渺廟妙蔑滅民抿皿敏憫閩明螟鳴銘名命謬摸摹蘑模膜磨摩魔抹末莫墨默沫漠寞陌謀牟某拇牡畝姆母墓暮幕募慕木目睦牧穆拿哪呐鈉那娜納氖乃奶耐奈南男難囊撓腦惱鬧淖呢餒內嫩能妮霓倪泥尼擬你匿膩逆溺蔫拈年碾攆撚念娘釀鳥尿捏聶孽齧鑷鎳涅您檸獰凝甯擰濘牛扭鈕紐膿濃農弄奴努怒女暖虐瘧挪懦糯諾哦歐鷗毆藕嘔偶漚啪趴爬帕怕琶拍排牌徘湃派攀潘盤磐盼畔判叛乓龐旁耪胖抛咆刨炮袍跑泡呸胚培裴賠陪配佩沛噴盆砰抨烹澎彭蓬棚硼篷膨朋鵬捧碰坯砒霹批披劈琵毗啤脾疲皮匹痞僻屁譬篇偏片騙飄漂瓢票撇瞥拼頻貧品聘乒坪蘋萍平憑瓶評屏坡潑頗婆破魄迫粕剖撲鋪仆莆葡菩蒲埔樸圃普浦譜曝瀑期欺棲戚妻七淒漆柒沏其棋奇歧畦崎臍齊旗祈祁騎起豈乞企啓契砌器氣迄棄汽泣訖掐洽牽扡釺鉛千遷簽仟謙乾黔錢鉗前潛遣淺譴塹嵌欠歉槍嗆腔羌牆薔強搶橇鍬敲悄橋瞧喬僑巧鞘撬翹峭俏竅切茄且怯竊欽侵親秦琴勤芹擒禽寢沁青輕氫傾卿清擎晴氰情頃請慶瓊窮秋丘邱球求囚酋泅趨區蛆曲軀屈驅渠取娶齲趣去圈顴權醛泉全痊拳犬券勸缺炔瘸卻鵲榷確雀裙群然燃冉染瓤壤攘嚷讓饒擾繞惹熱壬仁人忍韌任認刃妊紉扔仍日戎茸蓉榮融熔溶容絨冗揉柔肉茹蠕儒孺如辱乳汝入褥軟阮蕊瑞銳閏潤若弱撒灑薩腮鰓塞賽三三傘散桑嗓喪搔騷掃嫂瑟色澀森僧莎砂殺刹沙紗傻啥煞篩曬珊苫杉山刪煽衫閃陝擅贍膳善汕扇繕墒傷商賞晌上尚裳梢捎稍燒芍勺韶少哨邵紹奢賒蛇舌舍赦攝射懾涉社設砷申呻伸身深娠紳神沈審嬸甚腎慎滲聲生甥牲升繩省盛剩勝聖師失獅施濕詩屍虱十石拾時什食蝕實識史矢使屎駛始式示士世柿事拭誓逝勢是嗜噬適仕侍釋飾氏市恃室視試收手首守壽授售受瘦獸蔬樞梳殊抒輸叔舒淑疏書贖孰熟薯暑曙署蜀黍鼠屬術述樹束戍豎墅庶數漱恕刷耍摔衰甩帥栓拴霜雙爽誰水睡稅吮瞬順舜說碩朔爍斯撕嘶思私司絲死肆寺嗣四伺似飼巳松聳慫頌送宋訟誦搜艘擻嗽蘇酥俗素速粟僳塑溯宿訴肅酸蒜算雖隋隨綏髓碎歲穗遂隧祟孫損筍蓑梭唆縮瑣索鎖所塌他它她塔獺撻蹋踏胎苔擡台泰酞太態汰坍攤貪癱灘壇檀痰潭譚談坦毯袒碳探歎炭湯塘搪堂棠膛唐糖倘躺淌趟燙掏濤滔縧萄桃逃淘陶討套特藤騰疼謄梯剔踢銻提題蹄啼體替嚏惕涕剃屜天添填田甜恬舔腆挑條迢眺跳貼鐵帖廳聽烴汀廷停亭庭挺艇通桐酮瞳同銅彤童桶捅筒統痛偷投頭透凸禿突圖徒途塗屠土吐兔湍團推頹腿蛻褪退吞屯臀拖托脫鴕陀馱駝橢妥拓唾挖哇蛙窪娃瓦襪歪外豌彎灣玩頑丸烷完碗挽晚皖惋宛婉萬腕汪王亡枉網往旺望忘妄威巍微危韋違桅圍唯惟為濰維葦萎委偉僞尾緯未蔚味畏胃餵魏位渭謂尉慰衛瘟溫蚊文聞紋吻穩紊問嗡翁甕撾蝸渦窩我斡臥握沃巫嗚鎢烏汙誣屋無蕪梧吾吳毋武五捂午舞伍侮塢戊霧晤物勿務悟誤昔熙析西硒矽晰嘻吸錫犧稀息希悉膝夕惜熄烯溪汐犀檄襲席習媳喜銑洗系隙戲細瞎蝦匣霞轄暇峽俠狹下廈夏嚇掀鍁先仙鮮纖鹹賢銜舷閑涎弦嫌顯險現獻縣腺餡羨憲陷限線相廂鑲香箱襄湘鄉翔祥詳想響享項巷橡像向象蕭硝霄削哮囂銷消宵淆曉小孝校肖嘯笑效楔些歇蠍鞋協挾攜邪斜脅諧寫械卸蟹懈泄瀉謝屑薪芯鋅欣辛新忻心信釁星腥猩惺興刑型形邢行醒幸杏性姓兄凶胸匈洶雄熊休修羞朽嗅鏽秀袖繡墟戌需虛噓須徐許蓄酗敘旭序畜恤絮婿緒續軒喧宣懸旋玄選癬眩絢靴薛學穴雪血勳熏循旬詢尋馴巡殉汛訓訊遜迅壓押鴉鴨呀丫芽牙蚜崖衙涯雅啞亞訝焉咽閹煙淹鹽嚴研蜒岩延言顔閻炎沿奄掩眼衍演豔堰燕厭硯雁唁彥焰宴諺驗殃央鴦秧楊揚佯瘍羊洋陽氧仰癢養樣漾邀腰妖瑤搖堯遙窯謠姚咬舀藥要耀椰噎耶爺野冶也頁掖業葉曳腋夜液一壹醫揖銥依伊衣頤夷遺移儀胰疑沂宜姨彜椅蟻倚已乙矣以藝抑易邑屹億役臆逸肄疫亦裔意毅憶義益溢詣議誼譯異翼翌繹茵蔭因殷音陰姻吟銀淫寅飲尹引隱印英櫻嬰鷹應纓瑩螢營熒蠅迎贏盈影穎硬映喲擁傭臃癰庸雍踴蛹詠泳湧永恿勇用幽優悠憂尤由郵鈾猶油遊酉有友右佑釉誘又幼迂淤于盂榆虞愚輿余俞逾魚愉渝漁隅予娛雨與嶼禹宇語羽玉域芋郁籲遇喻峪禦愈欲獄育譽浴寓裕預豫馭鴛淵冤元垣袁原援轅園員圓猿源緣遠苑願怨院曰約越躍鑰嶽粵月悅閱耘雲鄖勻隕允運蘊醞暈韻孕匝砸雜栽哉災宰載再在咱攢暫贊贓髒葬遭糟鑿藻棗早澡蚤躁噪造皂竈燥責擇則澤賊怎增憎曾贈紮喳渣劄軋鍘閘眨柵榨咋乍炸詐摘齋宅窄債寨瞻氈詹粘沾盞斬輾嶄展蘸棧占戰站湛綻樟章彰漳張掌漲杖丈帳賬仗脹瘴障招昭找沼趙照罩兆肇召遮折哲蟄轍者鍺蔗這浙珍斟真甄砧臻貞針偵枕疹診震振鎮陣蒸掙睜征猙爭怔整拯正政幀症鄭證芝枝支吱蜘知肢脂汁之織職直植殖執值侄址指止趾只旨紙志摯擲至致置幟峙制智秩稚質炙痔滯治窒中盅忠鍾衷終種腫重仲衆舟周州洲謅粥軸肘帚咒皺宙晝驟珠株蛛朱豬諸誅逐竹燭煮拄矚囑主著柱助蛀貯鑄築住注祝駐抓爪拽專磚轉撰賺篆樁莊裝妝撞壯狀椎錐追贅墜綴諄准捉拙卓桌琢茁酌啄著灼濁茲咨資姿滋淄孜紫仔籽滓子自漬字鬃棕蹤宗綜總縱鄒走奏揍租足卒族祖詛阻組鑽纂嘴醉最罪尊遵昨左佐柞做作坐座";
		String taiWan = "啊阿埃挨哎唉哀皑癌蔼矮艾碍爱隘鞍氨安俺按暗岸胺案肮昂盎凹敖熬翱袄傲奥懊澳芭捌扒叭吧笆八疤巴拔跋靶把耙坝霸罢爸白柏百摆佰败拜稗斑班搬扳般颁板版扮拌伴瓣半办绊邦帮梆榜膀绑棒磅蚌镑傍谤苞胞包褒剥薄雹保堡饱宝抱报暴豹鲍爆杯碑悲卑北辈背贝钡倍狈备惫焙被奔苯本笨崩绷甭泵蹦迸逼鼻比鄙笔彼碧蓖蔽毕毙毖币庇痹闭敝弊必辟壁臂避陛鞭边编贬扁便变卞辨辩辫遍标彪膘表鳖憋别瘪彬斌濒滨宾摈兵冰柄丙秉饼炳病并玻菠播拨钵波博勃搏铂箔伯帛舶脖膊渤泊驳捕卜哺补埠不布步簿部怖擦猜裁材才财睬踩采彩菜蔡餐参蚕残惭惨灿苍舱仓沧藏操糙槽曹草厕策侧册测层蹭插叉茬茶查碴搽察岔差诧拆柴豺搀掺蝉馋谗缠铲产阐颤昌猖场尝常长偿肠厂敞畅唱倡超抄钞朝嘲潮巢吵炒车扯撤掣彻澈郴臣辰尘晨忱沉陈趁衬撑称城橙成呈乘程惩澄诚承逞骋秤吃痴持匙池迟弛驰耻齿侈尺赤翅斥炽充冲冲虫崇宠抽酬畴踌稠愁筹仇绸瞅丑臭初出橱厨躇锄雏滁除楚础储矗搐触处揣川穿椽传船喘串疮窗幢床闯创吹炊捶锤垂春椿醇唇淳纯蠢戳绰疵茨磁雌辞慈瓷词此刺赐次聪葱囱匆从丛凑粗醋簇促蹿篡窜摧崔催脆瘁粹淬翠村存寸磋撮搓措挫错搭达答瘩打大呆歹傣戴带殆代贷袋待逮怠耽担丹单郸掸胆旦氮但惮淡诞弹蛋当挡党荡档刀捣蹈倒岛祷导到稻悼道盗德得的蹬灯登等瞪凳邓堤低滴迪敌笛狄涤翟嫡抵底地蒂第帝弟递缔颠掂滇碘点典靛垫电佃甸店惦奠淀殿碉叼雕凋刁掉吊钓调跌爹碟蝶迭谍叠丁盯叮钉顶鼎锭定订丢东冬董懂动栋侗恫冻洞兜抖斗陡豆逗痘都督毒犊独读堵睹赌杜镀肚度渡妒端短锻段断缎堆兑队对墩吨蹲敦顿囤钝盾遁掇哆多夺垛躲朵跺舵剁惰堕蛾峨鹅俄额讹娥恶厄扼遏鄂饿恩而儿耳尔饵洱二贰发罚筏伐乏阀法珐藩帆番翻樊矾钒繁凡烦反返范贩犯饭泛坊芳方肪房防妨仿访纺放菲非啡飞肥匪诽吠肺废沸费芬酚吩氛分纷坟焚汾粉奋份忿愤粪丰封枫蜂峰锋风疯烽逢冯缝讽奉凤佛否夫敷肤孵扶拂辐幅氟符伏俘服浮涪福袱弗甫抚辅俯釜斧脯腑府腐赴副覆赋复傅付阜父腹负富讣附妇缚咐噶嘎该改概钙盖溉干甘杆柑竿肝赶感秆敢赣冈刚钢缸肛纲岗港杠篙皋高膏羔糕搞镐稿告哥歌搁戈鸽胳疙割革葛格蛤阁隔铬个各给根跟耕更庚羹埂耿梗工攻功恭龚供躬公宫弓巩汞拱贡共钩勾沟苟狗垢构购够辜菇咕箍估沽孤姑鼓古蛊骨谷股故顾固雇刮瓜剐寡挂褂乖拐怪棺关官冠观管馆罐惯灌贯光广逛瑰规圭硅归龟闺轨鬼诡癸桂柜跪贵刽辊滚棍锅郭国果裹过哈骸孩海氦亥害骇酣憨邯韩含涵寒函喊罕翰撼捍旱憾悍焊汗汉夯杭航壕嚎豪毫郝好耗号浩呵喝荷菏核禾和何合盒貉阂河涸赫褐鹤贺嘿黑痕很狠恨哼亨横衡恒轰哄烘虹鸿洪宏弘红喉侯猴吼厚候后呼乎忽瑚壶葫胡蝴狐糊湖弧虎唬护互沪户花哗华猾滑画划化话槐徊怀淮坏欢环桓还缓换患唤痪豢焕涣宦幻荒慌黄磺蝗簧皇凰惶煌晃幌恍谎灰挥辉徽恢蛔回毁悔慧卉惠晦贿秽会烩汇讳诲绘荤昏婚魂浑混豁活伙火获或惑霍货祸击圾基机畸稽积箕肌饥迹激讥鸡姬绩缉吉极棘辑籍集及急疾汲即嫉级挤几脊己蓟技冀季伎祭剂悸济寄寂计记既忌际妓继纪嘉枷夹佳家加荚颊贾甲钾假稼价架驾嫁歼监坚尖笺间煎兼肩艰奸缄茧检柬碱硷拣捡简俭剪减荐槛鉴践贱见键箭件健舰剑饯渐溅涧建僵姜将浆江疆蒋桨奖讲匠酱降蕉椒礁焦胶交郊浇骄娇嚼搅铰矫侥脚狡角饺缴绞剿教酵轿较叫窖揭接皆秸街阶截劫节茎睛晶鲸京惊精粳经井警景颈静境敬镜径痉靖竟竞净炯窘揪究纠玖韭久灸九酒厩救旧臼舅咎就疚鞠拘狙疽居驹菊局咀矩举沮聚拒据巨具距踞锯俱句惧炬剧捐鹃娟倦眷卷绢撅攫抉掘倔爵桔杰捷睫竭洁结解姐戒藉芥界借介疥诫届巾筋斤金今津襟紧锦仅谨进靳晋禁近烬浸尽劲荆兢觉决诀绝均菌钧军君峻俊竣浚郡骏喀咖卡咯开揩楷凯慨刊堪勘坎砍看康慷糠扛抗亢炕考拷烤靠坷苛柯棵磕颗科壳咳可渴克刻客课肯啃垦恳坑吭空恐孔控抠口扣寇枯哭窟苦酷库裤夸垮挎跨胯块筷侩快宽款匡筐狂框矿眶旷况亏盔岿窥葵奎魁傀馈愧溃坤昆捆困括扩廓阔垃拉喇蜡腊辣啦莱来赖蓝婪栏拦篮阑兰澜谰揽览懒缆烂滥琅榔狼廊郎朗浪捞劳牢老佬姥酪烙涝勒乐雷镭蕾磊累儡垒擂肋类泪棱楞冷厘梨犁黎篱狸离漓理李里鲤礼莉荔吏栗丽厉励砾历利傈例俐痢立粒沥隶力璃哩俩联莲连镰廉怜涟帘敛脸链恋炼练粮凉梁粱良两辆量晾亮谅撩聊僚疗燎寥辽潦了撂镣廖料列裂烈劣猎琳林磷霖临邻鳞淋凛赁吝拎玲菱零龄铃伶羚凌灵陵岭领另令溜琉榴硫馏留刘瘤流柳六龙聋咙笼窿隆垄拢陇楼娄搂篓漏陋芦卢颅庐炉掳卤虏鲁麓碌露路赂鹿潞禄录陆戮驴吕铝侣旅履屡缕虑氯律率滤绿峦挛孪滦卵乱掠略抡轮伦仑沦纶论萝螺罗逻锣箩骡裸落洛骆络妈麻玛码蚂马骂嘛吗埋买麦卖迈脉瞒馒蛮满蔓曼慢漫谩芒茫盲氓忙莽猫茅锚毛矛铆卯茂冒帽貌贸么玫枚梅酶霉煤没眉媒镁每美昧寐妹媚门闷们萌蒙檬盟锰猛梦孟眯醚靡糜迷谜弥米秘觅泌蜜密幂棉眠绵冕免勉娩缅面苗描瞄藐秒渺庙妙蔑灭民抿皿敏悯闽明螟鸣铭名命谬摸摹蘑模膜磨摩魔抹末莫墨默沫漠寞陌谋牟某拇牡亩姆母墓暮幕募慕木目睦牧穆拿哪呐钠那娜纳氖乃奶耐奈南男难囊挠脑恼闹淖呢馁内嫩能妮霓倪泥尼拟你匿腻逆溺蔫拈年碾撵捻念娘酿鸟尿捏聂孽啮镊镍涅您柠狞凝宁拧泞牛扭钮纽脓浓农弄奴努怒女暖虐疟挪懦糯诺哦欧鸥殴藕呕偶沤啪趴爬帕怕琶拍排牌徘湃派攀潘盘磐盼畔判叛乓庞旁耪胖抛咆刨炮袍跑泡呸胚培裴赔陪配佩沛喷盆砰抨烹澎彭蓬棚硼篷膨朋鹏捧碰坯砒霹批披劈琵毗啤脾疲皮匹痞僻屁譬篇偏片骗飘漂瓢票撇瞥拼频贫品聘乒坪苹萍平凭瓶评屏坡泼颇婆破魄迫粕剖扑铺仆莆葡菩蒲埔朴圃普浦谱曝瀑期欺栖戚妻七凄漆柒沏其棋奇歧畦崎脐齐旗祈祁骑起岂乞企启契砌器气迄弃汽泣讫掐洽牵扦钎铅千迁签仟谦乾黔钱钳前潜遣浅谴堑嵌欠歉枪呛腔羌墙蔷强抢橇锹敲悄桥瞧乔侨巧鞘撬翘峭俏窍切茄且怯窃钦侵亲秦琴勤芹擒禽寝沁青轻氢倾卿清擎晴氰情顷请庆琼穷秋丘邱球求囚酋泅趋区蛆曲躯屈驱渠取娶龋趣去圈颧权醛泉全痊拳犬券劝缺炔瘸却鹊榷确雀裙群然燃冉染瓤壤攘嚷让饶扰绕惹热壬仁人忍韧任认刃妊纫扔仍日戎茸蓉荣融熔溶容绒冗揉柔肉茹蠕儒孺如辱乳汝入褥软阮蕊瑞锐闰润若弱撒洒萨腮鳃塞赛三叁伞散桑嗓丧搔骚扫嫂瑟色涩森僧莎砂杀刹沙纱傻啥煞筛晒珊苫杉山删煽衫闪陕擅赡膳善汕扇缮墒伤商赏晌上尚裳梢捎稍烧芍勺韶少哨邵绍奢赊蛇舌舍赦摄射慑涉社设砷申呻伸身深娠绅神沈审婶甚肾慎渗声生甥牲升绳省盛剩胜圣师失狮施湿诗尸虱十石拾时什食蚀实识史矢使屎驶始式示士世柿事拭誓逝势是嗜噬适仕侍释饰氏市恃室视试收手首守寿授售受瘦兽蔬枢梳殊抒输叔舒淑疏书赎孰熟薯暑曙署蜀黍鼠属术述树束戍竖墅庶数漱恕刷耍摔衰甩帅栓拴霜双爽谁水睡税吮瞬顺舜说硕朔烁斯撕嘶思私司丝死肆寺嗣四伺似饲巳松耸怂颂送宋讼诵搜艘擞嗽苏酥俗素速粟僳塑溯宿诉肃酸蒜算虽隋随绥髓碎岁穗遂隧祟孙损笋蓑梭唆缩琐索锁所塌他它她塔獭挞蹋踏胎苔抬台泰酞太态汰坍摊贪瘫滩坛檀痰潭谭谈坦毯袒碳探叹炭汤塘搪堂棠膛唐糖倘躺淌趟烫掏涛滔绦萄桃逃淘陶讨套特藤腾疼誊梯剔踢锑提题蹄啼体替嚏惕涕剃屉天添填田甜恬舔腆挑条迢眺跳贴铁帖厅听烃汀廷停亭庭挺艇通桐酮瞳同铜彤童桶捅筒统痛偷投头透凸秃突图徒途涂屠土吐兔湍团推颓腿蜕褪退吞屯臀拖托脱鸵陀驮驼椭妥拓唾挖哇蛙洼娃瓦袜歪外豌弯湾玩顽丸烷完碗挽晚皖惋宛婉万腕汪王亡枉网往旺望忘妄威巍微危韦违桅围唯惟为潍维苇萎委伟伪尾纬未蔚味畏胃喂魏位渭谓尉慰卫瘟温蚊文闻纹吻稳紊问嗡翁瓮挝蜗涡窝我斡卧握沃巫呜钨乌污诬屋无芜梧吾吴毋武五捂午舞伍侮坞戊雾晤物勿务悟误昔熙析西硒矽晰嘻吸锡牺稀息希悉膝夕惜熄烯溪汐犀檄袭席习媳喜铣洗系隙戏细瞎虾匣霞辖暇峡侠狭下厦夏吓掀锨先仙鲜纤咸贤衔舷闲涎弦嫌显险现献县腺馅羡宪陷限线相厢镶香箱襄湘乡翔祥详想响享项巷橡像向象萧硝霄削哮嚣销消宵淆晓小孝校肖啸笑效楔些歇蝎鞋协挟携邪斜胁谐写械卸蟹懈泄泻谢屑薪芯锌欣辛新忻心信衅星腥猩惺兴刑型形邢行醒幸杏性姓兄凶胸匈汹雄熊休修羞朽嗅锈秀袖绣墟戌需虚嘘须徐许蓄酗叙旭序畜恤絮婿绪续轩喧宣悬旋玄选癣眩绚靴薛学穴雪血勋熏循旬询寻驯巡殉汛训讯逊迅压押鸦鸭呀丫芽牙蚜崖衙涯雅哑亚讶焉咽阉烟淹盐严研蜒岩延言颜阎炎沿奄掩眼衍演艳堰燕厌砚雁唁彦焰宴谚验殃央鸯秧杨扬佯疡羊洋阳氧仰痒养样漾邀腰妖瑶摇尧遥窑谣姚咬舀药要耀椰噎耶爷野冶也页掖业叶曳腋夜液一壹医揖铱依伊衣颐夷遗移仪胰疑沂宜姨彝椅蚁倚已乙矣以艺抑易邑屹亿役臆逸肄疫亦裔意毅忆义益溢诣议谊译异翼翌绎茵荫因殷音阴姻吟银淫寅饮尹引隐印英樱婴鹰应缨莹萤营荧蝇迎赢盈影颖硬映哟拥佣臃痈庸雍踊蛹咏泳涌永恿勇用幽优悠忧尤由邮铀犹油游酉有友右佑釉诱又幼迂淤于盂榆虞愚舆余俞逾鱼愉渝渔隅予娱雨与屿禹宇语羽玉域芋郁吁遇喻峪御愈欲狱育誉浴寓裕预豫驭鸳渊冤元垣袁原援辕园员圆猿源缘远苑愿怨院曰约越跃钥岳粤月悦阅耘云郧匀陨允运蕴酝晕韵孕匝砸杂栽哉灾宰载再在咱攒暂赞赃脏葬遭糟凿藻枣早澡蚤躁噪造皂灶燥责择则泽贼怎增憎曾赠扎喳渣札轧铡闸眨栅榨咋乍炸诈摘斋宅窄债寨瞻毡詹粘沾盏斩辗崭展蘸栈占战站湛绽樟章彰漳张掌涨杖丈帐账仗胀瘴障招昭找沼赵照罩兆肇召遮折哲蛰辙者锗蔗这浙珍斟真甄砧臻贞针侦枕疹诊震振镇阵蒸挣睁征狰争怔整拯正政帧症郑证芝枝支吱蜘知肢脂汁之织职直植殖执值侄址指止趾只旨纸志挚掷至致置帜峙制智秩稚质炙痔滞治窒中盅忠钟衷终种肿重仲众舟周州洲诌粥轴肘帚咒皱宙昼骤珠株蛛朱猪诸诛逐竹烛煮拄瞩嘱主著柱助蛀贮铸筑住注祝驻抓爪拽专砖转撰赚篆桩庄装妆撞壮状椎锥追赘坠缀谆准捉拙卓桌琢茁酌啄着灼浊兹咨资姿滋淄孜紫仔籽滓子自渍字鬃棕踪宗综总纵邹走奏揍租足卒族祖诅阻组钻纂嘴醉最罪尊遵昨左佐柞做作坐座";
		String ret = "";
		for (int i = 0; i < message.length(); i++) {
			if (taiWan.indexOf(message.charAt(i)) != -1)
				ret += profChina.charAt(taiWan.indexOf(message.charAt(i)));
			else
				ret += message.charAt(i);
		}
		return ret;
	}

	/**
	 * 按月份的阿拉伯数字返回中文数字
	 * 
	 * @param m
	 * @return
	 */
	public static final String monthLowerCaseToUpperCase(int m) {
		String str = "";
		switch (m) {
			case 1:
				str = "一";
				break;
			case 2:
				str = "二";
				break;
			case 3:
				str = "三";
				break;
			case 4:
				str = "四";
				break;
			case 5:
				str = "五";
				break;
			case 6:
				str = "六";
				break;
			case 7:
				str = "七";
				break;
			case 8:
				str = "八";
				break;
			case 9:
				str = "九";
				break;
			case 10:
				str = "十";
				break;
			case 11:
				str = "十一";
				break;
			case 12:
				str = "十二";
				break;
			default:
				str = "";
		}
		return str;
	}

	/**
	 * 判斷是否是中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断strName中是否含有中文字符
	 * 
	 * @param strName
	 * @return
	 */
	public static final boolean isChinese(String strName) {
		boolean isChinese = false;
		if (strName != null) {
			char[] ch = strName.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				if (isChinese(c) == true) {
					isChinese = true;
					break;
				} else {
				}
			}
		}
		return isChinese;
	}

	/**
	 * last ip addree str replace ***
	 * 
	 * @param ip
	 * @return
	 */
	public static final String getNewEncryptIP(String ip) {
		String[] ipArr = ip.replace(".", ",").split(",");
		if (ipArr.length != 0) {
			String lastIp = ipArr[ipArr.length - 1];
			ip = ip.replace(ip.substring(ip.length() - lastIp.length(), ip.length()),
					"***");
		}
		return ip;
	}

	/**
	 * 判断字符串内容是否是数字
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean isNumeric(String string) {
		return org.apache.commons.lang3.StringUtils.isNumeric(string);
	}

	/**
	 * 判断字符串是否是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * 判断字符串是否是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 判断字符串是否有内容
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否有内容
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * 正則表達式檢查mail是否合法
	 * 
	 * @param email
	 * @return
	 */
	public static final boolean isMailAddress(String email) {
		if (isBlank(email))
			return false;
		email = substring(email, CharConstants.LESS_THAN, CharConstants.GREAT_THAN);
		return Pattern.compile(REGEXMAIL).matcher(email).matches();
	}

	/**
	 * 截取src字符串中在startEndChar字符之间的字符串
	 * 
	 * @param src
	 * @param startEndChar
	 * @return
	 */
	public static final String substring(String src, String startEndChar) {
		return substring(src, startEndChar, startEndChar);
	}

	/**
	 * 截取src字符串中在startChar与endChar字符之间的字符串
	 * 
	 * @param src
	 * @param startChar
	 * @param endChar
	 * @return
	 */
	public static final String substring(String src, String startChar, String endChar) {
		if (isBlank(src))
			return null;
		if (isBlank(startChar))
			throw new IllegalArgumentException("argument[startChar] cannot be empty!");
		if (isBlank(startChar) && isBlank(endChar))
			return src;
		int eIndex = src.length();
		int sIndex = src.indexOf(startChar);
		if (isNotBlank(endChar) && -1 != sIndex)
			eIndex = src.indexOf(endChar, sIndex + startChar.length());
		if (-1 != sIndex && -1 != eIndex && eIndex > sIndex) {
			src = src.substring(sIndex + 1, eIndex);
		}
		return src;
	}

	/**
	 * 从str字符串中截取charSize长度的字符串，并加上suffix返回，如 wordLimit("12345678",3,"...") 返回 "123..."
	 * 
	 * @param str
	 * @param charSize
	 * @param suffix
	 * @return
	 */
	public static final String wordLimit(String str, int charSize, String suffix) {
		if (str == null) {
			return "";
		}
		if (str.length() > charSize) {
			str = str.substring(0, charSize) + suffix;
		}
		return str;
	}

	/**
	 * 从str字符串中截取charSize长度的字符串，并加上"..."返回，如 wordLimit("12345678",3) 返回 "123..."
	 * 
	 * @param str
	 * @param charSize
	 * @return
	 */
	public static final String wordLimit(String str, int charSize) {
		return wordLimit(str, charSize, "...");
	}

	/**
	 * 移除content字符串中的所有html标签
	 * 
	 * @param content
	 * @return
	 */
	public static final String escapeHtml(final String content) {
		return content.replaceAll(REGEX_ESCAPE_HTML, "");
	}

	/**
	 * 按照 USE_URI_ENCODING ， URI_ENCODING 的值重新编码 valueFromURI 字符串返回,
	 * USE_URI_ENCODING 默认值为false，URI_ENCODING默认值为UTF-8,
	 * 默认不做任何处理，如果USE_URI_ENCODING 为true则会使用 ISO-8859-1 获取byte后再用utf-8编码字符串
	 * 
	 * @param valueFromURI
	 * @return
	 */
	public static final String getStringFromURI(String valueFromURI) {
		if (USE_URI_ENCODING){
			return valueFromURI;
		}else{
			try {
				return getString(valueFromURI);
			} catch (UnsupportedEncodingException ex) {
				log.error("error occurs while encode string value is \"" + valueFromURI + "\"", ex);
				return valueFromURI;
			}
		}
	}

	/**
	 * 使用 ISO-8859-1 获取byte后再用utf-8编码字符串
	 * 
	 * @param dist
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static final String getString(String dist)
			throws UnsupportedEncodingException {
		return getString(dist, SYSTEM_DEFAULT_ENCODING, URI_ENCODING);
	}

	public static final String getString(String dist, String distCharset,
			String targetCharset) throws UnsupportedEncodingException {
		return new String(dist.getBytes(distCharset), targetCharset);
	}

	/**
	 * 判斷字符串是否是全為半角字符組成的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isSingleByteString(final String str) {
		if (null == str) {
			return false;
		}
		byte[] bytes;
		for (int i = 0; i < str.length(); i++) {
			bytes = (new Character(str.charAt(i)).toString()).getBytes();
			if (bytes.length > 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 截取指定按byte計算字符串長度的字串
	 * 
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static final String substringByte(final String str, final int maxLength) {
		if (isBlank(str)) {
			return "";
		}
		int strLen = str.length();
		strLen = maxLength > strLen ? strLen : maxLength;
		int doubleLen = "中".getBytes().length;
		if (strLen * doubleLen <= maxLength)
			return str;
		if (isSingleByteString(str))
			return str.substring(0, strLen);
		int length = 0;
		int finalLen = strLen;
		for (int i = 0; i < str.length(); i++) {
			length += (new Character(str.charAt(i)).toString()).getBytes().length;
			if (length > maxLength) {
				finalLen = i;
				break;
			}
		}
		return str.substring(0, finalLen);
	}
	
	/**
	 * 使用正则表达式验证。
	 * 
	 * @param regex
	 * @param input
	 * @return
	 */
	public static final boolean validByRegex(String regex, String input) {
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher = p.matcher(input);
		return regexMatcher.find();
	}
	
	/**
	 * native string to ascii 
	 * @param text
	 * @return
	 */
	public static final String native2Unicode(String text) {
		StringBuilder sb = new StringBuilder();  
        for (int i=0; i<text.length(); i++){  
            char ch = text.charAt(i);  
            String ascii = char2Ascii(ch);  
            sb.append(ascii);  
        }  
        return sb.toString();  
	}
	
	/** 
     * char -> ascii 
     * @param ch 
     * @return 
     */  
    private static String char2Ascii(char ch) {  
        if (ch < 256){  
            return Character.toString(ch);  
        }  
        String hex = Integer.toHexString(ch).toUpperCase();  
        if (hex.length() < 4){  
            hex = "0" + hex;  
        }  
        return PREFIX_UNICODE + hex;  
    }  

    /**
     * ascii to native string
     * @param text
     * @return
     */
	public static final String unicode2Native(String text) {
		StringBuilder sb = new StringBuilder();  
        int start = 0;  
        int idx = text.indexOf(PREFIX_UNICODE);  
        while (idx != -1){  
            // 上一个 Unicode 码与当前 Unicode 码之间的有效字符  
            // eg: \u0101ABC\u0102 之间的ABC  
            sb.append(text.substring(start, idx));  
            // 转换当前 Unicode 码  
            String ascii = text.substring(idx + 2,idx + 6);  
            char ch = (char)Integer.parseInt(ascii,16);  
            sb.append(ch);  
            // 查找下一个 Unicode  
            start = idx + 6;  
            idx = text.indexOf(PREFIX_UNICODE,start);  
        }  
        // 结尾的有效字符  
        sb.append(text.substring(start));  
        return sb.toString();  
	}
	
	/**
	 * 根据一串逗号分隔的字符串取得字符串形数组
	 * 
	 * @param value
	 * @return
	 */
	public static String[] getStringAryByStr(String value) {
		if (ObjectUtils.isEmpty(value))
			return null;
		String[] aryId = value.split(",");
		String[] lAryId = new String[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = (aryId[i]);
		}
		return lAryId;
	}
	
	/**
	 * 去掉字符串中的空白
	 * @param src:要去空白的字符串
	 * @return if src is null then return null
	 */
	public static String removeBlank(String src) {
		String dest = null;
		if (src != null) {
			//先把全角空白换成半角空白,再去两端空白(因为两端有可能是全角空格,而用trim是不能去掉的)
			src=src.replaceAll("[\\s\\p{Zs}]+"," ").trim();
			Pattern p = Pattern.compile("\\s{1,}|\t|\r|\n|[\\s\\p{Zs}]+");
			Matcher m = p.matcher(src);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * 把字符串中的空白用其它字符串替代
	 * @param src:要去空白的字符串
	 * @param operater:需要替换的字符串,如时为Null,则用""代替
	 * @return if src is null then return null
	 */
	public static String replaceBlank(String src,String operater) {
		String dest = null;
		if (src != null) {
			//先把全角空白换成半角空白,再去两端空白(因为两端有可能是全角空格,而用trim是不能去掉的)
			src=src.replaceAll("[\\s\\p{Zs}]+"," ").trim();
			Pattern p = Pattern.compile("\\s{1,}|\t|\r|\n|[\\s\\p{Zs}]+");
			Matcher m = p.matcher(src);
			if(operater==null){
				operater="";
			}
			dest = m.replaceAll(operater);
		}
		return dest;
	}
	
	/**
	 * 将Emoji符号移除到
	 * @param source
	 * @return
	 */
	public static final String removeEmoji(String source) {
		return replaceEmoji(source, "");
	}
	
	/**
	 * 将Emoji符号替换成指定字符*
	 * @param source
	 * @return
	 */
	public static final String replaceEmojiToStar(String source) {
		return replaceEmoji(source, "*");
	}
	
	/**
	 * 将Emoji符号替换成指定字符replacement
	 * @param source
	 * @param replacement
	 * @return
	 */
	public static final String replaceEmoji(String source, String replacement) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", replacement);
		} else {
			return source;
		}
	}
	
	/**
	 * obj to string phh
	 * @param obj 要转的对象
	 * @param def 如果obj为null时可指定一个值
	 */
	public static final String valueOf(Object obj, String def) {
		return (obj == null) ? def : obj.toString();
	}
	
	/**
	 * obj to string phh
	 * @param obj
	 * @return if obj is null then null
	 */
	public static final String valueOf(Object obj) {
		return valueOf(obj, "");
	}
	
}
