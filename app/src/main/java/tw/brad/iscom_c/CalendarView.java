package tw.brad.iscom_c;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CalendarView extends View {
    private int year, month;
    private int firstWeek;  // 該月第一天星期幾
    private int days;   // 該月有幾天
    private int lastMonthCount; // 上個月有幾天
    private Paint topLinePaint, linePaint, textPaint, bg1Paint, bg2Paint;
    private boolean isInit;
    private float viewW, viewH, cellW, textSize, lastY, startY;

    public void setYearMonth(int year, int month){
        this.year = year; this.month = month;
        firstWeek = MyCalendar.count_first_day(year, month);
        days = MyCalendar.count_days(year, month);

        // 假設 firstWeek == 4, 則要跟上個月取 4 天
        // 所以需要 lasyMonthCount, 該數值也是上個月的最後一天
        lastMonthCount = MyCalendar.count_days(
                (month!=12?year:year-1),(month!=12)?month-1:1);

        invalidate();

    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    private void init(){
        isInit = true;

        viewW = getWidth(); viewH = getHeight();
        Log.v("brad", viewW + " x " + viewH);

        textSize = viewW / 30;

        topLinePaint = new Paint();
        topLinePaint.setColor(Color.GRAY);
        topLinePaint.setStrokeWidth(4);

        linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(2);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        bg1Paint = new Paint();
        bg1Paint.setColor(Color.rgb(107,205,253));

        bg2Paint = new Paint();
        bg2Paint.setColor(Color.rgb(205,253,112));

        cellW = viewW / 7;
        startY = 4;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();

        canvas.drawLine(0,startY, viewW, startY, topLinePaint);

        float titleY = 10 + textSize + 4;
        canvas.drawText("日", 0*cellW + cellW / 3, titleY, textPaint);
        canvas.drawText("一", 1*cellW + cellW / 3, titleY, textPaint);
        canvas.drawText("二", 2*cellW + cellW / 3, titleY, textPaint);
        canvas.drawText("三", 3*cellW + cellW / 3, titleY, textPaint);
        canvas.drawText("四", 4*cellW + cellW / 3, titleY, textPaint);
        canvas.drawText("五", 5*cellW + cellW / 3, titleY, textPaint);
        canvas.drawText("六", 6*cellW + cellW / 3, titleY, textPaint);

        float line1Y = titleY + (textSize / 3) + 4;
        canvas.drawLine(0,line1Y, viewW, line1Y, linePaint);

        // 第一週要處理上個月剩下的部分
        float week1Y = line1Y + textSize + 4;
        for (int i=firstWeek-1; i>=0; i--) {
            canvas.drawText("" + (lastMonthCount-i),
                    (firstWeek-1-i) * cellW + 6, week1Y, textPaint);
        }
        int day = 1;
        for (int i=firstWeek; i<7; i++){
            canvas.drawText("" + (day++),
                    i * cellW + 6, week1Y, textPaint);
        }

        // 第一週的內容第一列
        float week1cont1Y = week1Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week1cont1Y - textSize,
                1 * cellW - 6, week1cont1Y + 8,
                bg1Paint);
        canvas.drawText("個人",
                0 * cellW + 12, week1cont1Y, textPaint);

        // 第一週的內容第二列
        float week1cont2Y = week1cont1Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week1cont2Y - textSize,
                1 * cellW - 6, week1cont2Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week1cont2Y, textPaint);

        // 第一週的內容第三列
        float week1cont3Y = week1cont2Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week1cont3Y - textSize,
                1 * cellW - 6, week1cont3Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week1cont3Y, textPaint);

        float line2Y = week1cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line2Y, viewW, line2Y, linePaint);

        // 第二週
        float week2Y = line2Y + textSize + 4;
        for (int i=0; i<7; i++){
            canvas.drawText("" + (day++),
                    i * cellW + 6, week2Y, textPaint);
        }

        // 第二週的內容第一列
        float week2cont1Y = week2Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week2cont1Y - textSize,
                1 * cellW - 6, week2cont1Y + 8,
                bg1Paint);
        canvas.drawText("個人",
                0 * cellW + 12, week2cont1Y, textPaint);

        // 第二週的內容第二列
        float week2cont2Y = week2cont1Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week2cont2Y - textSize,
                1 * cellW - 6, week2cont2Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week2cont2Y, textPaint);

        // 第二週的內容第三列
        float week2cont3Y = week2cont2Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week2cont3Y - textSize,
                1 * cellW - 6, week2cont3Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week2cont3Y, textPaint);

        float line3Y = week2cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line3Y, viewW, line3Y, linePaint);

        // 第三週
        float week3Y = line3Y + textSize + 4;
        for (int i=0; i<7; i++){
            canvas.drawText("" + (day++),
                    i * cellW + 6, week3Y, textPaint);
        }

        // 第三週的內容第一列
        float week3cont1Y = week3Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week3cont1Y - textSize,
                1 * cellW - 6, week3cont1Y + 8,
                bg1Paint);
        canvas.drawText("個人",
                0 * cellW + 12, week3cont1Y, textPaint);

        // 第三週的內容第二列
        float week3cont2Y = week3cont1Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week3cont2Y - textSize,
                1 * cellW - 6, week3cont2Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week3cont2Y, textPaint);

        // 第三週的內容第三列
        float week3cont3Y = week3cont2Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week3cont3Y - textSize,
                1 * cellW - 6, week3cont3Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week3cont3Y, textPaint);

        float line4Y = week3cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line4Y, viewW, line4Y, linePaint);

        // 第四週
        float week4Y = line4Y + textSize + 4;
        for (int i=0; i<7; i++){
            canvas.drawText("" + (day++),
                    i * cellW + 6, week4Y, textPaint);
        }

        // 第四週的內容第一列
        float week4cont1Y = week4Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week4cont1Y - textSize,
                1 * cellW - 6, week4cont1Y + 8,
                bg1Paint);
        canvas.drawText("個人",
                0 * cellW + 12, week4cont1Y, textPaint);

        // 第四週的內容第二列
        float week4cont2Y = week4cont1Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week4cont2Y - textSize,
                1 * cellW - 6, week4cont2Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week4cont2Y, textPaint);

        // 第四週的內容第三列
        float week4cont3Y = week4cont2Y + textSize + 4;
        canvas.drawRect(0 * cellW + 6, week4cont3Y - textSize,
                1 * cellW - 6, week4cont3Y + 8,
                bg2Paint);
        canvas.drawText("會議",
                0 * cellW + 12, week4cont3Y, textPaint);

        float line5Y = week4cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line5Y, viewW, line5Y, linePaint);
        lastY = line5Y;

        // 還沒有呈現完畢
        if (day < days){
            int nextMonthDay = 1;
            // 第五週
            float week5Y = line5Y + textSize + 4;
            for (int i=0; i<7; i++){
                if (day <= days) {
                    canvas.drawText("" + (day++),
                            i * cellW + 6, week5Y, textPaint);
                }else{
                    canvas.drawText("" + (nextMonthDay++),
                            i * cellW + 6, week5Y, textPaint);
                }
            }

            // 第五週的內容第一列
            float week5cont1Y = week5Y + textSize + 4;
            canvas.drawRect(0 * cellW + 6, week5cont1Y - textSize,
                    1 * cellW - 6, week5cont1Y + 8,
                    bg1Paint);
            canvas.drawText("個人",
                    0 * cellW + 12, week5cont1Y, textPaint);

            // 第五週的內容第二列
            float week5cont2Y = week5cont1Y + textSize + 4;
            canvas.drawRect(0 * cellW + 6, week5cont2Y - textSize,
                    1 * cellW - 6, week5cont2Y + 8,
                    bg2Paint);
            canvas.drawText("會議",
                    0 * cellW + 12, week5cont2Y, textPaint);

            // 第四週的內容第三列
            float week5cont3Y = week5cont2Y + textSize + 4;
            canvas.drawRect(0 * cellW + 6, week5cont3Y - textSize,
                    1 * cellW - 6, week5cont3Y + 8,
                    bg2Paint);
            canvas.drawText("會議",
                    0 * cellW + 12, week5cont3Y, textPaint);

            float line6Y = week5cont3Y + (textSize / 3) + 4;
            canvas.drawLine(0,line6Y, viewW, line6Y, linePaint);
            lastY = line6Y;

            if (day < days) {
                // 第六週
                float week6Y = line6Y + textSize + 4;
                for (int i = 0; i < 7; i++) {
                    if (day <= days) {
                        canvas.drawText("" + (day++),
                                i * cellW + 6, week6Y, textPaint);
                    } else {
                        canvas.drawText("" + (nextMonthDay++),
                                i * cellW + 6, week6Y, textPaint);
                    }
                }

                // 第六週的內容第一列
                float week6cont1Y = week6Y + textSize + 4;
                canvas.drawRect(0 * cellW + 6, week6cont1Y - textSize,
                        1 * cellW - 6, week6cont1Y + 8,
                        bg1Paint);
                canvas.drawText("個人",
                        0 * cellW + 12, week6cont1Y, textPaint);

                // 第六週的內容第二列
                float week6cont2Y = week6cont1Y + textSize + 4;
                canvas.drawRect(0 * cellW + 6, week6cont2Y - textSize,
                        1 * cellW - 6, week6cont2Y + 8,
                        bg2Paint);
                canvas.drawText("會議",
                        0 * cellW + 12, week6cont2Y, textPaint);

                // 第六週的內容第三列
                float week6cont3Y = week6cont2Y + textSize + 4;
                canvas.drawRect(0 * cellW + 6, week6cont3Y - textSize,
                        1 * cellW - 6, week6cont3Y + 8,
                        bg2Paint);
                canvas.drawText("會議",
                        0 * cellW + 12, week6cont3Y, textPaint);

                float line7Y = week6cont3Y + (textSize / 3) + 4;
                canvas.drawLine(0, line7Y, viewW, line7Y, linePaint);
                lastY = line7Y;
            }
        }

        // 處理直線部分
        for (int i=1; i<=6; i++) {
            canvas.drawLine(i * cellW, startY, i * cellW, lastY, linePaint);
        }

        setLayoutParams(new LinearLayout.LayoutParams((int)viewW, (int)(lastY+4)));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX(), ey = event.getY();
        float titleY = 10 + textSize + 4;
        float line1Y = titleY + (textSize / 3) + 4;
        float week1Y = line1Y + textSize + 4;
        float week1cont1Y = week1Y + textSize + 4;

        if (ex >= 0 * cellW + 6 && ex <= 1 * cellW - 6 &&
                ey >= week1cont1Y - textSize && ey <= week1cont1Y + 8){
            Intent intent = new Intent(getContext(), DetailScheduleActivity.class);
            getContext().startActivity(intent);
        }

        return super.onTouchEvent(event);
    }
}
