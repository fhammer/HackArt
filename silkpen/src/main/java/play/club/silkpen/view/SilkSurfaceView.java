package play.club.silkpen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import play.club.silkpen.entiy.SurfaceSilkLine;


/**
 * Created by Administrator on 2016/6/19.
 */
public class SilkSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private boolean istag;

    private SurfaceHolder holder;
    private Canvas canvas;
    private SurfaceSilkLine silkLine;
    private Paint paint;

    public SilkSurfaceView(Context context) {
        super(context);
        init();
    }

    public SilkSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);

        silkLine=new SurfaceSilkLine(new Point(100,300));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        istag=true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        drawback();
    }

    private void drawback() {
        canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        istag=false;
    }

    @Override
    public void run() {
        long timeMillis=0;
        while (istag){
            timeMillis=System.currentTimeMillis();
            draw();
            long durTime = 30 - System.currentTimeMillis() + timeMillis;
            durTime=durTime>0?durTime:1;
            try {
                Thread.sleep(durTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try {
            canvas=holder.lockCanvas();
            silkLine.drawPath(canvas);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas!=null&&holder!=null)
                holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getPointerCount()){
//            case 1: {
//                Log.d("Event",event.getAction()+"");

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        silkLine.newSilkLine(event.getX(),event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
//            }
//                break;
//        }
        return true;
    }
}
