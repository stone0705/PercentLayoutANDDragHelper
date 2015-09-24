package com.example.stone.percentlayoutanddraghelper;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by stone on 2015/9/22.
 */
public class DragLayout extends LinearLayout {
    private ViewDragHelper mDragger;
    public DragLayout(Context context,AttributeSet attrs){
        super(context,attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback()
        {
            @Override
            public boolean tryCaptureView(View child, int pointerId)
            {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx)
            {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy)
            {
                final int topBound = getPaddingTop();
                final int botBound = getHeight() - child.getHeight() - topBound;
                final int newTop = Math.min(Math.max(top,topBound),botBound);
                return newTop;
            }
            @Override
            public int getViewHorizontalDragRange(View child)
            {
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child)
            {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }
            @Override
            public void onViewReleased (View releasedChild, float xvel, float yvel){
                Toast.makeText(DragLayout.this.getContext(),"RELEASE",Toast.LENGTH_SHORT).show();
                if(releasedChild.getId() == R.id.dragButton){
                    mDragger.settleCapturedViewAt(100, 100);
                    invalidate();
                }
            }
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId)
            {
                mDragger.captureChildView(findViewById(R.id.text), pointerId);
            }
            @Override
            public void onViewCaptured (View capturedChild, int activePointerId){
                Toast.makeText(DragLayout.this.getContext(),"onCaptured",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onViewPositionChanged (View changedView, int left, int top, int dx, int dy){
                double midy = getHeight()/2.0;
                double midx = getWidth()/2.0;
                if(left<=midx){
                    if(top<=midy){
                        System.out.println("左上");
                    }else{
                        System.out.println("左下");
                    }
                }else{
                    if(top<=midy){
                        System.out.println("右上");
                    }else{
                        System.out.println("右下");
                    }
                }

            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragger.processTouchEvent(event);
        return true;
    }
    @Override
    public void computeScroll()
    {
        if(mDragger.continueSettling(true))
        {
            invalidate();
        }
    }
}
