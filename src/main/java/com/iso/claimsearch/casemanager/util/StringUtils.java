package com.iso.claimsearch.casemanager.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains some helper methods for dealing with strings.
 * 
 * @author i90845
 * 
 */

public class StringUtils {

  /**
   * Splits a string into an array based on the chunk size.
   * 
   * @param text - The text to split into an array.
   * @param chunkSize - The chunk size.
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static final String[] split(String text, int chunkSize) {
    if (text == null) {
      return new String[0];
    }

    List lines = new ArrayList(10);
    StringBuffer buffer = new StringBuffer(text);

    while (buffer.length() >= chunkSize) {
      lines.add(buffer.substring(0, chunkSize));
      buffer.delete(0, chunkSize);
    }

    if (buffer.length() != 0)
      lines.add(buffer.toString());

    return (String[]) lines.toArray(new String[lines.size()]);
  }


}
