package club.zhcs.jxls;

import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class Formats
{
  public static WritableCellFormat createFormat(int fontSize, boolean isBold, boolean isItalic, Alignment align, VerticalAlignment valign, boolean isWrap)
  {
    WritableFont wf = null;
    if (isBold)
      wf = new WritableFont(WritableFont.TIMES, fontSize, 
        WritableFont.BOLD, isItalic);
    else {
      wf = new WritableFont(WritableFont.TIMES, fontSize, 
        WritableFont.NO_BOLD, isItalic);
    }
    WritableCellFormat wcf = new WritableCellFormat(wf);
    try {
      wcf.setAlignment(align);
      wcf.setVerticalAlignment(valign);
      wcf.setWrap(isWrap);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    return wcf;
  }
}