package com.example.ctrading.mvvm.ui.parts;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: JianTours
 * @Data: 2022/5/7 23:03
 * @Description:
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private int space = 0;

    public SpaceItemDecoration(int space){
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    @Override
    public void getItemOffsets( Rect outRect,  View view,  RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = 0;
        outRect.left = 0;
        outRect.right = 0;
    }

    @Override
    public void onDraw( Canvas c,  RecyclerView parent,  RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c,parent);
    }

    private void drawHorizontal(Canvas canvas,RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth()-parent.getPaddingRight();
        int childSize = parent.getChildCount();
        for (int i=0;i<childSize;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom()+layoutParams.bottomMargin;
            int bottom = top+space;
            if (mPaint!=null){
                canvas.drawRect((float) left,(float) top,(float) right,(float) bottom,mPaint);
            }
        }
    }


}
