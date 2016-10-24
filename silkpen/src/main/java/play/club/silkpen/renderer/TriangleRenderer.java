package play.club.silkpen.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Collection;

import play.club.silkpen.entiy.Triangle;
import play.club.silkpen.hatcheries.color.ColorGenerator;
import play.club.silkpen.hatcheries.color.RandomColorGenerator;

/**
 * Triangle renderer
 *
 * @author fuzh2
 */
public class TriangleRenderer {

    private Paint trianglePaint;
    private ColorGenerator colorGenerator;

    private Path path;

    public TriangleRenderer(ColorGenerator colorGenerator) {
        this.colorGenerator = colorGenerator;

        initPaint();
    }

    private void initPaint() {
        trianglePaint = new Paint();
        trianglePaint.setStyle(Paint.Style.FILL);
        trianglePaint.setAntiAlias(true);
        trianglePaint.setStrokeWidth(2);
        trianglePaint.setStrokeCap(Paint.Cap.SQUARE);
        trianglePaint.setStrokeJoin(Paint.Join.BEVEL);

        if (colorGenerator == null) {
            colorGenerator = new RandomColorGenerator();
        }

        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
    }

    public void render(Collection<Triangle> triangles, Canvas canvas) {
        colorGenerator.setCount(triangles.size());
        for (Triangle triangle : triangles) {
            path.reset();

            path.moveTo(triangle.a.x, triangle.a.y);
            path.lineTo(triangle.b.x, triangle.b.y);
            path.lineTo(triangle.c.x, triangle.c.y);
            path.lineTo(triangle.a.x, triangle.a.y);

            path.close();

            int color = colorGenerator.nextColor();

            trianglePaint.setColor(color);
            canvas.drawPath(path, trianglePaint);
        }
    }
}
