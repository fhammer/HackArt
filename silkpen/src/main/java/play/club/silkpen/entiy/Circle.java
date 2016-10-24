package play.club.silkpen.entiy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * @author fuzh2
 *         MVC设计模式：
 *         V:显示view
 *         M:模型mode
 *         C:控制controller
 */

public class Circle {
    private float cx;//圆心坐标
    private float cy;
    private int radius;//半径
    private Paint paint;//画笔
    private int alpha = 100;//透明度


    public Circle() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Circle(float cx, float cy) {
        super();
        this.cx = cx;
        this.cy = cy;
        paint = new Paint();
        paint.setColor(Color.RED);
        // 给画笔设置样式
        paint.setStyle(Style.STROKE);// 笔触
        // 设置线条的宽度
        paint.setStrokeWidth(5);
    }

    public void drawCircle(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
        change();
    }

    //变大
    public void change() {
        radius += 3;
        paint.setAlpha(alpha);
        if (alpha > 20) {
            alpha -= 20;
        }
    }

}
