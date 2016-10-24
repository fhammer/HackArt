package play.club.skecher.style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import java.util.HashMap;

import play.club.skecher.Style;


/**
 * 项目名称：HackArt
 * 类描述：按照一定的算法画圆
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
class CirclesStyle implements Style {
    private float prevX;
    private float prevY;

    private Paint paint = new Paint();

    {
        paint.setColor(Color.BLACK);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
    }

    @Override
    public void stroke(Canvas c, float x, float y) {
        float dx = x - prevX;
        float dy = y - prevY;

        int dxy = (int) (Math.sqrt(dx * dx + dy * dy) * 2);

        int gridx = (int) (Math.floor(x / 50) * 50 + 25);
        int gridy = (int) (Math.floor(y / 50) * 50 + 25);

        int rand = (int) (Math.floor(Math.random() * 9) + 1);
        int radius = dxy / rand;

        for (int i = 0; i < rand; i++) {
            c.drawCircle(gridx, gridy, (rand - i) * radius, paint);
        }

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
        paint.setColor(color);
        paint.setAlpha(50);
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
