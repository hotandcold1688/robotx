package com.macyou.robot.common;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StringUtils
{
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static final Pattern KVP_PATTERN = Pattern.compile("([_a-zA-Z]\\w*)[=](.*)"); //key value pair pattern.

	private static final Pattern PATTERN = Pattern.compile("\\{([0-9]+)\\}");

	private static final boolean[] WHITE_LIST = new boolean[]{
		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,
		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,
		false,false,false,false,false,true,false,false,false,false,true,false,true,true,false,false, //[ ][!]["][#][$][%][&]['][(][)][*][+][,][-][.][/]
		true,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true, //[0][1][2][3][4][5][6][7][8][9][:][;][<][=][>][?]
		false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true, //[@][A][B][C][D][E][F][G][H][I][J][K][L][M][N][O]
		true,true,true,true,true,true,true,true,true,true,true,false,false,false,true,true, //[P][Q][R][S][T][U][V][W][X][Y][Z][[][\][]][^][_]
		true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true, //[`][a][b][c][d][e][f][g][h][i][j][k][l][m][n][o]
		true,true,true,true,true,true,true,true,true,true,true,false,true,false,true,false //[p][q][r][s][t][u][v][w][x][y][z][{][|][}][~][ ]
	};

	private static final int[]  ISO8859_1_ENTITIES = {
		160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,
		176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,
		192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,
		208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,
		224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,
		240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255
	};

	private static final int[] ISO10646_ENTITIES = {
		// symbols, mathematical symbols, and Greek letters
		402,
		913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,
		931,932,933,934,935,936,937,
		945,946,947,948,949,950,951,952,953,954,955,956,957,958,959,960,961,962,963,964,965,966,967,968,969,
		977,978,982,
		8226,8230,8242,8243,8254,8260,8472,8465,8476,8482,8501,8592,8593,8594,8595,8596,8629,
		8656,8657,8658,8659,8660,
		8704,8706,8707,8709,8711,8712,8713,8715,8719,8721,8722,8727,8730,8733,8734,8736,8743,8744,8745,8746,8747,8756,8764,8773,8776,
		8800,8801,8804,8805,8834,8835,8836,8838,8839,8853,8855,8869,8901,8968,8969,8970,8971,
		9001,9002,9674,9824,9827,9829,9830,
		// markup-significant and internationalization characters
		338,339,352,353,376,710,732,
		8194,8195,
		8201,8204,8205,8206,8207,8211,8212,8216,8217,8218,8220,8221,8222,8224,8225,8240,8249,8250,8364
    };

	private static final String[] CONTROL_CHAR_MAP = new String[]{
		"\\u0000","\\u0001","\\u0002","\\u0003","\\u0004","\\u0005","\\u0006","\\u0007",
		"\\b","\\t","\\n","\\u000b","\\f","\\r","\\u000e","\\u000f",
		"\\u0010","\\u0011","\\u0012","\\u0013","\\u0014","\\u0015","\\u0016","\\u0017",
		"\\u0018","\\u0019","\\u001a","\\u001b","\\u001c","\\u001d","\\u001e","\\u001f"
	};

	/**
	 * is empty string.
	 *
	 * @param str source string.
	 * @return is empty.
	 */
	public static boolean isEmpty(String str)
	{
		if( str == null || str.length() == 0 )
			return true;
		return false;
	}

	/**
	 * translat.
	 *
	 * @param src source string.
	 * @param from src char table.
	 * @param to target char table.
	 * @return String.
	 */
	public static String translat(String src,String from,String to)
	{
		if( isEmpty(src) ) return src;
		StringBuilder sb = null;
		int ix;
		char c;
		for(int i=0,len=src.length();i<len;i++)
		{
			c = src.charAt(i);
			ix = from.indexOf(c);
			if( ix == -1 )
			{
				if( sb != null )
					sb.append(c);
			}
			else
			{
				if( sb == null )
				{
					sb = new StringBuilder(len);
					sb.append(src,0,i);
				}
				if( ix < to.length() )
					sb.append(to.charAt(ix));
			}
		}
		return sb == null ? src : sb.toString();
	}

	/**
	 * split.
	 *
	 * @param ch char.
	 * @return string array.
	 */
	public static String[] split(String str,char ch)
	{
		List<String> list = null;
        char c;
        int ix = 0,len=str.length();
		for(int i=0;i<len;i++)
		{
			c = str.charAt(i);
			if( c == ch )
			{
				if( list == null )
					list = new ArrayList<String>();
				list.add(str.substring(ix,i));
				ix = i + 1;
			}
		}
		if( ix > 0 )
			list.add(str.substring(ix));
		return list == null ? EMPTY_STRING_ARRAY : (String[])list.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * join string.
	 *
	 * @param array String array.
	 * @return String.
	 */
	public static String join(String[] array)
	{
		if( array.length == 0 ) return "";
		StringBuilder sb = new StringBuilder();
		for( String s : array )
			sb.append(s);
		return sb.toString();
	}

	/**
	 * join string like javascript.
	 *
	 * @param array String array.
	 * @param split split
	 * @return String.
	 */
	public static String join(String[] array,char split)
	{
		if( array.length == 0 ) return "";
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<array.length;i++)
		{
			if( i > 0 )
				sb.append(split);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * join string like javascript.
	 *
	 * @param array String array.
	 * @param split split
	 * @return String.
	 */
	public static String join(String[] array,String split)
	{
		if( array.length == 0 ) return "";
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<array.length;i++)
		{
			if( i > 0 )
				sb.append(split);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * parse key-value pair.
	 *
	 * @param str string.
	 * @return key-value map;
	 */
	public static Map<String,String> parseKeyValuePair(String str)
	{
		return parseKeyValuePair(str,"[,;]+");
	}

	/**
	 * parse key-value pair.
	 *
	 * @param str string.
	 * @param itemSeparator item separator.
	 * @return key-value map;
	 */
	public static Map<String,String> parseKeyValuePair(String str,String itemSeparator)
	{
		return parseKeyValuePair(str,itemSeparator,"[=]");
	}

	/**
	 * parse key-value pair.
	 *
	 * @param str string.
	 * @param itemSeparator item separator.
	 * @param separator key-value separator.
	 * @return key-value map;
	 */
	public static Map<String,String> parseKeyValuePair(String str,String itemSeparator,String separator)
	{
		String[] tmp = str.split(itemSeparator);
		Map<String,String> map = new HashMap<String,String>(tmp.length);
		Pattern pattern = ( "[=]".equals(separator) ? KVP_PATTERN : Pattern.compile("([_a-zA-Z]\\w*)"+separator+"(.*)") );
		for(int i=0;i<tmp.length;i++)
		{
			Matcher matcher = pattern.matcher(tmp[i]);
			if( matcher.matches() == false )
				continue;
			map.put(matcher.group(1),matcher.group(2));
		}
		return map;
	}


	/**
	 * format.
	 *
	 * @param source string.
	 * @param args {0}...{x}.
	 * @return string.
	 */
	public static String format(String source,Object... args)
	{
		if( isEmpty(source) ) return source;
		Object tmp;
		int end = 0;
		StringBuilder sb = null;
		Matcher matcher = PATTERN.matcher(source);
		while( matcher.find() )
		{
			if( sb == null )
				sb = new StringBuilder(source.length()<<1);
			sb.append(source.substring(end,matcher.start()));
			String str = matcher.group(1);
			if( str.length() > 0 )
			{
				int ix = Integer.parseInt(str);
				if( ix < args.length )
				{
					tmp = args[ix];
					if( tmp == null || tmp instanceof String )
						sb.append(tmp);
					else
						sb.append(tmp.toString());
				}
			}
			end = matcher.end();
		}
		if( sb == null )
			return source;
		sb.append(source.substring(end));
		return sb.toString();
	}

	/**
	 * convert to wildcard regex.
	 *
	 * @param str
	 * @return
	 */
	public static String toWildcardRegex(final String str)
	{
		int x = 0;
		char[] cs = str.toCharArray();
        StringBuilder sb = new StringBuilder(cs.length<<1);
        sb.append('^');
		for( char c : cs )
		{
			if( c == '*' )
			{
				x++;
			}
			else
			{
				if( x > 0 )
				{
					sb.append( x == 1 ? "\\w*" : ".*" );
					x = 0;
				}
				switch( c )
				{
					case '\\': case '.':
						sb.append('\\').append(c);
	        			break;
					case '?':
						sb.append("\\w");
	        			break;
					default:
	        			if( sb != null )
	        				sb.append(c);
				}
			}
		}
		if( x > 0 )
			sb.append( x == 1 ? "\\w*" : ".*" );
		sb.append('$');
		return sb.toString();
	}

	public static interface Coder{ String escape(String input); }

	public static Coder Javascript = new Coder(){
		public String escape(String str)
		{
			if( str == null )
				return str;
			int len = str.length();
			if( len == 0 )
				return str;
	        char c;
	        StringBuilder sb = null;
	        for(int i=0;i<len;i++)
	        {
	        	c = str.charAt(i);
	        	if( c < ' ' ) // control char.
	        	{
	        		if( sb == null )
	        		{
	        			sb = new StringBuilder(len<<1);
						sb.append(str,0,i);
	        		}
	            	sb.append(CONTROL_CHAR_MAP[c]);
	        	}
	        	else
	        	{
	            	switch( c )
	            	{
	            		case '\\': case '/': case '"':
	    	        		if( sb == null )
	    	        		{
	    	        			sb = new StringBuilder(len<<1);
	    						sb.append(str,0,i);
	    	        		}
	            			sb.append('\\').append(c);
	            			break;
	            		default:
	            			if( sb != null )
	            				sb.append(c);
	            	}
	        	}
	        }
	        return sb == null ? str : sb.toString();
		}
	};

	public static Coder Html4 = new Coder(){

	    private Set<Integer> EntitySet = new HashSet<Integer>();

        {
            for( int entity : ISO8859_1_ENTITIES )
                EntitySet.add(entity);
            for( int entity : ISO10646_ENTITIES )
                EntitySet.add(entity);
        }

		public String escape(String str)
		{
			if( str == null )
				return str;
			int len = str.length();
			if( len == 0 )
				return str;
			char c;
			StringBuilder sb = null;
			for(int i=0;i<len;i++)
			{
				c = str.charAt(i);
				if( c < WHITE_LIST.length ? WHITE_LIST[c] == false : EntitySet.contains((int)c) )
				{
					if( sb == null )
					{
						sb = new StringBuilder(len<<1);
						sb.append(str,0,i);
					}
					sb.append("&#"+Integer.toString(c)+";");
				}
				else
				{
					if( sb != null )
						sb.append(c);
				}
			}
			return sb == null ? str : sb.toString();
		}
	};

	public static Coder Xml = new Coder(){
		public String escape(String str)
		{
			if( str == null )
				return str;
			int len = str.length();
			if( len == 0 )
				return str;
			char c;
			String sub;
			StringBuilder sb = null;
	        for(int i=0;i<len;i++)
	        {
	        	c = str.charAt(i);
	        	switch( c )
	        	{
	    			case '&':
	    				sub = "&amp;";
	    				if( sb == null )
                        {
                            sb = new StringBuilder(len<<1);
                            sb.append(str,0,i);
                        }
                        sb.append(sub);
	    				break;
	    			case '<':
	    				sub = "&lt;";
	    				if( sb == null )
                        {
                            sb = new StringBuilder(len<<1);
                            sb.append(str,0,i);
                        }
                        sb.append(sub);
	    				break;
	    			case '>':
                        sub = "&gt;";
                        if( sb == null )
                        {
                            sb = new StringBuilder(len<<1);
                            sb.append(str,0,i);
                        }
                        sb.append(sub);
                        break;
	    			case '"':
	    				sub = "&quot;";
		        		if( sb == null )
		        		{
		        			sb = new StringBuilder(len<<1);
							sb.append(str,0,i);
		        		}
		            	sb.append(sub);
		            	break;
	    			case '\'':
                        sub = "&apos;";
                        if( sb == null )
                        {
                            sb = new StringBuilder(len<<1);
                            sb.append(str,0,i);
                        }
                        sb.append(sub);
                        break;
		            default:
	        			if( sb != null )
	        				sb.append(c);
	        	}
	        }
	        return sb == null ? str : sb.toString();
		}
	};

	private StringUtils(){}
}

