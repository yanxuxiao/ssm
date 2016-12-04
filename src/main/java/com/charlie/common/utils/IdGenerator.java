package com.charlie.common.utils;


public class IdGenerator extends AbstractIdGenerator
{

  protected String format(int intval) {
    String formatted = Integer.toHexString(intval);
    StringBuffer buf = new StringBuffer("00000000");
    buf.replace(8 - formatted.length(), 8, formatted);
    return buf.toString();
  }

  protected String format(short shortval) {
    String formatted = Integer.toHexString(shortval);
    StringBuffer buf = new StringBuffer("0000");
    buf.replace(4 - formatted.length(), 4, formatted);
    return buf.toString();
  }

  public String generate() {
    return  new StringBuffer(32)
    		.append(format(getIP()))
    		.append(format(getJVM()))
    		.append(format(getHiTime()))
    		.append(format(getLoTime()))
    		.append(format(getCount())).toString() ;
  }
  
  public static String getNewId() {
	  IdGenerator generator = new IdGenerator() ;
	 return  generator.generate() ;
  }
  
}