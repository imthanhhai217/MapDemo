package com.jaroidx.mapdemo;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CustomMotionLayout extends MotionLayout {
    public CustomMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inits(context);
    }

    private View anchorView;
    private View motionContainerView;
    private RecyclerView mRecyclerView;
    private Rect hitRect;
    private GestureDetector.SimpleOnGestureListener gestureListener;
    private GestureDetector gestureDetector;

    private short touchMoveFactor = 10;
    private short touchTimeFactor = 200;
    private PointF actionDownPoint = new PointF(0f, 0f);
    private long touchDownTime = 0L;

    public void inits(@NonNull Context context) {
        hitRect = new Rect();

        gestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                getTouchView().getHitRect(hitRect);
                Point point = getOrigin(e1);
                boolean check = hitRect.contains(point.x, point.y);
                RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
                recyclerView.setNestedScrollingEnabled(check);
                return check;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                getTouchView().getHitRect(hitRect);
                Point point = getOrigin(e);
                if (hitRect.contains(point.x, point.y)) {
                    return true;
                }
                return super.onDown(e);
            }
        };

        gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    public void setTransitionListener(TransitionListener listener) {
        super.setTransitionListener(listener);
    }

    public void setAnchorView(View anchorView) {
        this.anchorView = anchorView;
    }

    private View getTouchView() {
        return findViewById(R.id.swipe_view);
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    private View getMotionContainerView() {
        if (motionContainerView == null) {
            motionContainerView = findViewById(R.id.swipe);
        }
        return motionContainerView;
    }

    private Point getOrigin(MotionEvent event) {
        float x = event.getX() - getMotionContainerView().getX();
        float y = event.getY() - getMotionContainerView().getY();
        return new Point((int) x,(int) y);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                actionDownPoint.x = event.getX();
                actionDownPoint.y = event.getY();
                touchDownTime = System.currentTimeMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                //on touch released, check if the finger was still close to the point he/she clicked
                boolean isTouchLength = (Math.abs(event.getX() - actionDownPoint.x)+ Math.abs(event.getY() - actionDownPoint.y)) < touchMoveFactor;
                boolean isClickTime = System.currentTimeMillis() - touchDownTime < touchTimeFactor;

                //if it's been more than 200ms since user first touched and, the finger was close to the same place when released, consider it a click event
                //Please note that this is a workaround :D
                if (isTouchLength && isClickTime){
                    //call this method on the view, e.g ivPic.performClick();
                    if (getProgress() > 0) {
                        transitionToStart();
                    } else {
                        transitionToEnd();
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}
