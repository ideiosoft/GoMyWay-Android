package ssc.snow.app.gomyways.data.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;


@SuppressLint("AppCompatCustomView")
public class SquareRoundCornerImageView extends ImageView {

    public static float radius = 18.0f;

    public SquareRoundCornerImageView(Context context) {
        super(context);
    }

    public SquareRoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 36.0f;
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
