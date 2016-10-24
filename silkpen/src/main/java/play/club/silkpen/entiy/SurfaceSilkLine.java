package play.club.silkpen.entiy;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.Random;

/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/20 9:26
 * 修改人：fuzh2
 * 修改时间：2016/6/20 9:26
 * 修改备注：
 */
public class SurfaceSilkLine {
    private int initX;
    private int initY;

    private static final float BLUR_SIZE = 1.0F;
    private Point startPoint;
    private Point midPoint;
    private Point endPoint;

    private Path path;

    private Random random;
    private Paint paint;

    public SurfaceSilkLine(Point point) {
        iniPan();
        startPoint = new Point();
        startPoint.set(point.x, point.y);
        midPoint = new Point();
        endPoint = new Point();
        path = new Path();
        random = new Random();
        randomPoints();
    }

    private void iniPan() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.08f);
        paint.setAlpha(128);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));
    }

    private void randomPoints() {
        initX = Math.round(startPoint.x) <= 0 ? 100 : Math.round(startPoint.x);
        initY = Math.round(startPoint.y) <= 0 ? 300 : Math.round(startPoint.y);
        midPoint.set(random.nextInt(initX), random.nextInt(initY));
        endPoint.set(random.nextInt(initX), random.nextInt(initY));

        path.reset();
        for (int i = 0; i < 100; i++) {
            float clips = 3.6f * i;
            path.moveTo(initX + clips, initY + clips);
            path.cubicTo(startPoint.x + clips, startPoint.y + clips, midPoint.x + clips, midPoint.y, endPoint.x, endPoint.y + clips);
        }
    }

    public void setStartPoint(Point point) {
        this.startPoint = point;
        randomPoints();
    }

    public void newSilkLine() {
        int newX = (int) (startPoint.x * Math.cos(10));
        int newY = (int) (startPoint.y * Math.sin(10));
        Log.d("Postion", "x= " + newX + "  y= " + newY);
        if (newX == 0)
            newX = random.nextInt(400);
        if (newY == 0)
            newY = random.nextInt(600);
        startPoint.set(newX, newY);
        randomPoints();
    }

    public void newSilkLine(float x, float y) {
//        int newX= (int) (x*Math.cos(10));
//        int newY= (int) (y*Math.sin(10));
        int newX = Math.round(x);
        int newY = Math.round(y);
        Log.d("Postion", "x= " + newX + "  y= " + newY);
        if (newX == 0)
            newX = random.nextInt(400);
        if (newY == 0)
            newY = random.nextInt(600);
        startPoint.set(newX, newY);
        randomPoints();
    }

    public void drawPath(Canvas canvas) {
//        canvas.drawRect(new Rect(3,3,400,500),paint);
        canvas.drawPath(path, paint);
//        newSilkLine();
    }

}
