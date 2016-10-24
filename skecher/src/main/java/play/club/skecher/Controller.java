package play.club.skecher;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import play.club.skecher.style.StylesFactory;


/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
public class Controller implements View.OnTouchListener {
    private Style style;
    private final Canvas mCanvas;
    private boolean toDraw = false;
    private Paint mColor = new Paint();

    public Controller(Canvas canvas) {
        clear();
        mCanvas = canvas;
    }

    public void draw() {
        if (toDraw) {
            style.draw(mCanvas);
        }
    }

    public void setStyle(Style style) {
        toDraw = false;
        style.setColor(mColor.getColor());
        this.style = style;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                toDraw = true;
                style.strokeStart(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                style.stroke(mCanvas, event.getX(), event.getY());
                break;
        }
        return true;
    }

    public void clear() {
        toDraw = false;
        StylesFactory.clearCache();
        setStyle(StylesFactory.getCurrentStyle());
    }

    public void setPaintColor(Paint color) {
        mColor = color;
        style.setColor(color.getColor());
    }

    public Paint getPaintColor() {
        return mColor;
    }

    public void setColorBrige(String startColor, String endColor) {
        style.setColorBrige(startColor, endColor);
    }

}
