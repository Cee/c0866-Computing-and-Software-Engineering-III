package excalibur.game.presentation.tools;

/**
 * Created by Xc on 2014/5/21.
 */

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class FontLoader {

    private static Map<String,Font> fontMap = new HashMap<>();



    public static Font loadFont(String fontFileName, float fontSize)  //第一个参数是外部字体名，第二个是字体大小
    {
        if (fontMap.containsKey(fontFileName)){
            return fontMap.get(fontFileName).deriveFont((float) fontSize);
        }

        try {
            File file = new File("font/"+fontFileName);
            FileInputStream aixing = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(Font.PLAIN,fontSize);


            aixing.close();

            fontMap.put(fontFileName,dynamicFontPt);

            return dynamicFontPt;
        } catch (Exception e)//异常处理
        {
            e.printStackTrace();
            return new java.awt.Font("宋体", Font.PLAIN, 14);
        }
    }

}
