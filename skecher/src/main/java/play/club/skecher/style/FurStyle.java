package play.club.skecher.style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;


import java.util.ArrayList;
import java.util.HashMap;

import play.club.skecher.Style;


/**
 * 项目名称：HackArt
 * 类描述：毛皮效果
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
class FurStyle implements Style {

    private final float DISTANCE_SQRT=2000;
    private float prevX;
    private float prevY;

    private ArrayList<PointF> points = new ArrayList<PointF>();

    private Paint paint = new Paint();

    {
        paint.setColor(Color.BLACK);
        paint.setAlpha(25);
        paint.setAntiAlias(true);
    }

    @Override
    public void stroke(Canvas c, float x, float y) {
        PointF current = new PointF(x, y);
        points.add(current);

        c.drawLine(prevX, prevY, x, y, paint);

        float dx = 0;
        float dy = 0;
        float length = 0;

        for (int i = 0, max = points.size(); i < max; i++) {
            PointF point = points.get(i);
            dx = point.x - current.x;
            dy = point.y - current.y;

            length = dx * dx + dy * dy;

            if (length < DISTANCE_SQRT && Math.random() > length / DISTANCE_SQRT) {
                float ddx = dx * 0.5F;
                float ddy = dy * 0.5F;
                c.drawLine(point.x + ddx, point.y + ddy, point.x - ddx, point.y
                        - ddy, paint);
            }
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
        paint.setAlpha(25);
    }

    @Override
    public void saveState(HashMap<Integer, Object> state) {
        ArrayList<PointF> points = new ArrayList<PointF>();
        points.addAll(this.points);
        state.put(StylesFactory.FUR, points);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void restoreState(HashMap<Integer, Object> state) {
        this.points.clear();
        ArrayList<PointF> points = (ArrayList<PointF>) state
                .get(StylesFactory.FUR);
        this.points.addAll(points);
    }

    @Override
    public void setColorBrige(String startColor, String endColor) {

    }
}
