package play.club.skecher.colorpicker;

import android.graphics.Paint;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
public interface Picker {
    interface OnColorChangedListener {
        void colorChanged(Paint paint);
    }

    void setOnColorChangedListener(OnColorChangedListener listener);

    void setColor(int color);
}
