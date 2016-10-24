package play.club.skecher.style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import play.club.skecher.ColorRender;
import play.club.skecher.Style;

import java.util.HashMap;
import java.util.List;


/**
 * 项目名称：HackArt
 * 类描述：画丝带的效果
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
class RibbonStyle implements Style {

    private final int MAX_SIZE = 100;

    private final float EASE_MULTIPLE = 0.1f;
    private final float EASE_EXTRA = 0.5f;
    private final float DIV = 0.1f;

    private final int ALPHA = 25;

    private Paint paint = new Paint();
    private Painter[] painters = new Painter[MAX_SIZE];

    private float x;
    private float y;
    private List<String> mColors;

    private class Painter {
        private static final int SCREEN_WIDTH = 1080;
        private static final int SCREEN_HEIGHT = 1920;

        float dx = SCREEN_WIDTH / 2;
        float dy = SCREEN_HEIGHT / 2;
        float ax = 0;
        float ay = 0;
        float div = DIV;
        float ease = (float) (Math.random() * EASE_MULTIPLE + EASE_EXTRA);
    }

    {
        paint.setColor(Color.BLACK);
        paint.setAlpha(ALPHA);
        paint.setAntiAlias(true);

        for (int i = 0; i < MAX_SIZE; i++) {
            painters[i] = new Painter();
        }

        mColors = ColorRender.gradientColor("#ff5722", "#ffccbc", MAX_SIZE);
    }

    @Override
    public void draw(Canvas c) {
        float startX = 0;
        float startY = 0;
        for (int i = 0; i < painters.length; i++) {
            startX = painters[i].dx;
            startY = painters[i].dy;
            painters[i].dx -= painters[i].ax = (painters[i].ax + (painters[i].dx - x)
                    * painters[i].div)
                    * painters[i].ease;
            painters[i].dy -= painters[i].ay = (painters[i].ay + (painters[i].dy - y)
                    * painters[i].div)
                    * painters[i].ease;
            paint.setColor(Color.parseColor(mColors.get(i)));
            c.drawLine(startX, startY, painters[i].dx, painters[i].dy, paint);
        }
    }

    @Override
    public void stroke(Canvas c, float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void strokeStart(float x, float y) {
        this.x = x;
        this.y = y;

        for (int i = 0, max = painters.length; i < max; i++) {
            Painter painter = painters[i];
            painter.dx = x;
            painter.dy = y;
        }
    }

    @Override
    public void setColor(int color) {
        paint.setColor(color);
        paint.setAlpha(25);
    }

    @Override
    public void saveState(HashMap<Integer, Object> state) {
    }

    @Override
    public void restoreState(HashMap<Integer, Object> state) {
    }

    @Override
    public void setColorBrige(String startColor, String endColor) {
        mColors = ColorRender.gradientColor(startColor, endColor, MAX_SIZE);
    }
}
