package play.club.skecher.colorpicker;

import android.graphics.Color;


/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
class Utils {
    static float[] color2HSV(int color) {
        float[] hsv = new float[3];

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        Color.RGBToHSV(red, green, blue, hsv);

        return hsv;
    }
}
