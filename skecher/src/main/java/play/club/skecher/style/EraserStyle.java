package play.club.skecher.style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import java.util.HashMap;

import play.club.skecher.Style;


/**
 * 项目名称：HackArt
 * 类描述：橡皮擦
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
class EraserStyle implements Style {
    private float prevX;
    private float prevY;

    private Paint paint = new Paint();

    {
        paint.setColor(Color.WHITE);
        paint.setAlpha(255);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
    }

    @Override
    public void stroke(Canvas c, float x, float y) {
        c.drawLine(prevX, prevY, x, y, paint);
        prevX = x;
        prevY = y;
    }

    @Override
    public void strokeStart(float x, float y) {
        prevX = x;
        prevY = y;
    }

    @Override
    public void draw(Canvas c) {
    }

    @Override
    public void setColor(int color) {
    }

    @Override
    public void saveState(HashMap<Integer, Object> state) {
    }

    @Override
    public void restoreState(HashMap<Integer, Object> state) {
    }

    @Override
    public void setColorBrige(String startColor, String endColor) {

    }
}
