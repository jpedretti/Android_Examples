package com.example.jpedretti.doodlz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 Created by jpedretti on 05/12/2016.
 */

public class DoodleView extends View {

    // used to determine whether user moved a finger enough to draw again
    private static final float TOUCH_TOLERANCE = 10;

    private Bitmap bitmap; // drawing area for displaying or saving
    private Canvas bitmapCanvas; // used to to draw on the bitmap
    private final Paint paintScreen; // used to draw bitmap onto screen
    private final Paint paintLine; // used to draw lines onto bitmap

    // Maps of current Paths being drawn and Points in those Paths
    private final Map<Integer, Path> pathMap = new HashMap<>();
    private final Map<Integer, Point> previousPointMap = new HashMap<>();

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintScreen = new Paint();

        // set the initial display settings for the painted line
        paintLine = new Paint();
        paintLine.setAntiAlias(true); // smooth edges of drawn line
        paintLine.setColor(Color.BLACK); // default color is black
        paintLine.setStyle(Paint.Style.STROKE); // solid line
        paintLine.setStrokeWidth(5); // set the default line width
        paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);

        for (Integer key : pathMap.keySet())
            canvas.drawPath(pathMap.get(key), paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); //event type
        int actionIndex = event.getActionIndex(); //pointer (i.e., finger)

        // determine whether touch started, ended or is moving
        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex)
                    , event.getPointerId(actionIndex));
        } else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP) {
            touchEnded(event.getPointerId(actionIndex));
        } else {
            touchMoved(event);
        }

        invalidate(); //redraw
        return true;
    }

    public void printImage() {
        if (PrintHelper.systemSupportsPrint()) {
            PrintHelper printHelper = new PrintHelper(getContext());

            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            printHelper.printBitmap("Doodlz Image", bitmap);
        } else {
            makeToast(R.string.message_error_printing);
        }
    }

    public void saveImage() {
        final String name = "Doodlz" + System.currentTimeMillis() + ".jpg";

        String location = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                bitmap, name, "Doodlz Drawing");

        if (location != null) {
            makeToast(R.string.message_saved);
        } else {
            makeToast(R.string.message_error_saving);
        }
    }



    public void clear() {
        pathMap.clear();
        previousPointMap.clear();
        bitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void setDrawingColor(int color) {
        paintLine.setColor(color);
    }

    public int getDrawingColor() {
        return paintLine.getColor();
    }

    public void setLineWidth(int width) {
        paintLine.setStrokeWidth(width);
    }

    public int getLineWidth() {
        return (int) paintLine.getStrokeWidth();
    }

    private void touchMoved(MotionEvent event) {
        //for each of the pointers in the given TouchEvent
        for (int i = 0; i < event.getPointerCount(); i++) {
            int pointerId = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerId);

            //if there is a map associated with the pointer
            if (pathMap.containsKey(pointerId)) {
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                Path path = pathMap.get(pointerId);
                Point point = previousPointMap.get(pointerId);

                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) {
                    path.quadTo(point.x, point.y, (newX + point.x) / 2,
                            (newY + point.y) / 2);

                    point.x = (int) newX;
                    point.y = (int) newY;
                }
            }
        }
    }

    private void touchEnded(int lineId) {
        Path path = pathMap.get(lineId);
        bitmapCanvas.drawPath(path, paintLine);
        path.reset();
    }

    private void touchStarted(float x, float y, int lineId) {
        Path path; // used to store the path for the given touch id
        Point point; // used to store the last point in path

        if (pathMap.containsKey(lineId)) {
            path = pathMap.get(lineId);
            path.reset(); //reset the path ecause a new touch has started
            point = previousPointMap.get(lineId);
        } else {
            path = new Path();
            pathMap.put(lineId, path);
            point = new Point();
            previousPointMap.put(lineId, point);
        }

        path.moveTo(x, y);
        point.x = (int) x;
        point.y = (int) y;
    }

    private void makeToast(int resId) {
        Toast message = Toast.makeText(getContext(), resId,
                Toast.LENGTH_SHORT);
        message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                message.getYOffset() / 2);
        message.show();
    }
}
