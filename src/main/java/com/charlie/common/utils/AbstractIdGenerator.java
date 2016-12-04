package com.charlie.common.utils;

import java.net.InetAddress;

class AbstractIdGenerator
{
  private static final int IP;
  private static short counter;
  private static final int JVM;

  static
  {
    int ipadd;
    try
    {
      ipadd = toInt(InetAddress.getLocalHost().getAddress());
    }
    catch (Exception e)
    {
      ipadd = 0;
    }
    IP = ipadd;

    counter = 0;
    JVM = (int)(System.currentTimeMillis() >>> 8);
  }

  protected int getJVM()
  {
    return JVM;
  }

  protected short getCount()
  {
    synchronized (IdGenerator.class) {
      if (counter < 0) counter = 0;
      return counter++;
    }
  }

  protected int getIP()
  {
    return IP;
  }

  private static int toInt(byte[] bytes) {
    int result = 0;
    for (int i = 0; i < 4; i++) {
      result = (result << 8) - -128 + bytes[i];
    }
    return result;
  }

  protected short getHiTime()
  {
    return (short)(int)(System.currentTimeMillis() >>> 32);
  }
  protected int getLoTime() {
    return (int)System.currentTimeMillis();
  }
}