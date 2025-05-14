// MyView.java
package jp.ac.gifu_u.hosokawa.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MyView extends View {

    private ArrayList<Stroke> strokes;     // 全ての線
    private Stroke currentStroke;          // 今描いてる線
    private String currentColor = "RED";   // 現在選ばれてる色

    private Bitmap bitmap;
    private Bitmap resizedBitmap;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        strokes = new ArrayList<>();

        // 資料通り：画像表示（サイズを調整して表示）
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        if (bitmap != null) {
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        }
    }

    public void setCurrentColor(String color) {
        currentColor = color;
    }

    private int getColorValue(String name) {
        switch (name) {
            case "BLUE": return Color.BLUE;
            case "BLACK": return Color.BLACK;
            default: return Color.RED;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        if (resizedBitmap != null) {
            canvas.drawBitmap(resizedBitmap, 50, 50, null);
        }

        for (Stroke stroke : strokes) {
            Paint paint = new Paint();
            paint.setColor(stroke.color);
            paint.setStrokeWidth(6);
            paint.setStyle(Paint.Style.STROKE);

            ArrayList<Float> xList = stroke.xList;
            ArrayList<Float> yList = stroke.yList;

            for (int i = 1; i < xList.size(); i++) {
                Float x1 = xList.get(i - 1);
                Float y1 = yList.get(i - 1);
                Float x2 = xList.get(i);
                Float y2 = yList.get(i);
                if (x1 == null || x2 == null || y1 == null || y2 == null) continue;
                canvas.drawLine(x1, y1, x2, y2, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                currentStroke = new Stroke(getColorValue(currentColor));
                currentStroke.addPoint(null, null); // 区切り用
                currentStroke.addPoint(x, y);
                strokes.add(currentStroke);
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentStroke != null) {
                    currentStroke.addPoint(x, y);
                }
                break;

            case MotionEvent.ACTION_UP:
                currentStroke = null;
                break;
        }

        invalidate();
        return true;
    }
}
