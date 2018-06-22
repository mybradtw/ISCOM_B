package tw.brad.iscom_b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedList;

public class CalendarView extends View {
    private int year, month, lastyear, lastmonth, nextyear, nextmonth;
    private int firstWeek;  // 該月第一天星期幾
    private int days;   // 該月有幾天
    private int lastMonthCount; // 上個月有幾天
    private Paint topLinePaint, linePaint, textPaint, bg1Paint, bg2Paint;
    private boolean isInit;
    private float viewW, viewH, cellW, textSize, lastY, startY;

    // date => Schedule
    private HashMap<String,LinkedList<Schedule>> schedules = new HashMap<>();

    private String[][] keys = new String[6][7];
    private Schedule[][][] cells = new Schedule[6][7][3];

    public void setYearMonth(int year, int month){
        this.year = year; this.month = month;
        firstWeek = MyCalendar.count_first_day(year, month);
        days = MyCalendar.count_days(year, month);

        if (this.month == 1){
            lastmonth = 12; lastyear = this.year-1;
            nextmonth = 2; nextyear = this.year;
        }else if (this.month == 12){
            lastmonth = 11; lastyear = this.year;
            nextmonth = 1; nextyear = this.year+1;
        }else{
            lastmonth = this.month-1; lastyear = this.year;
            nextmonth = this.month+1; nextyear = this.year;
        }

        // 假設 firstWeek == 4, 則要跟上個月取 4 天
        // 所以需要 lasyMonthCount, 該數值也是上個月的最後一天
        lastMonthCount = MyCalendar.count_days(
                (month!=12?year:year-1),(month!=12)?month-1:1);
        invalidate();
    }


    public void setSchedules(HashMap<String,LinkedList<Schedule>> schedules){
        this.schedules = schedules;
        invalidate();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    private void init(){
        isInit = true;

        viewW = getWidth(); viewH = getHeight();

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
        // 第一週的內容第一列的 y 位置
        float week1cont1Y = week1Y + textSize + 4;
        // 第一週的內容第二列的 y 位置
        float week1cont2Y = week1cont1Y + textSize + 4;
        // 第一週的內容第三列的 y 位置
        float week1cont3Y = week1cont2Y + textSize + 4;
        float[] bgY = {week1cont1Y,week1cont2Y,week1cont3Y};

        // 上個月的部分
        for (int i=firstWeek-1; i>=0; i--) {
            canvas.drawText("" + (lastMonthCount-i),
                    (firstWeek-1-i) * cellW + 6, week1Y, textPaint);

            String key = (lastyear) + "-" + BradUtils.preZero(lastmonth)+"-"+ BradUtils.preZero(lastMonthCount-firstWeek+i+1);

            keys[0][i] = key;   // 處理[列][行] = key

            if (schedules.containsKey(key)){
                LinkedList<Schedule> ss = schedules.get(key);
                for (int j=0; j<Math.min(3,ss.size()); j++){
                    Schedule schedule = ss.get(j);
                    canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                            (i+1) * cellW - 6, bgY[j] + 8,
                            bg1Paint);
                    canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                            i * cellW + 12, bgY[j], textPaint);

                    cells[0][i][j] = schedule;
                }
            }
        }
        // 開始這個月的部分
        int day = 1;
        for (int i=firstWeek; i<7; i++){
            canvas.drawText("" + (day),
                    i * cellW + 6, week1Y, textPaint);

            String key = (year) + "-" + BradUtils.preZero(month)+"-"+ BradUtils.preZero(day);
            keys[0][i] = key;

            if (schedules.containsKey(key)){
                LinkedList<Schedule> ss = schedules.get(key);
                for (int j=0; j<Math.min(3,ss.size()); j++){
                    Schedule schedule = ss.get(j);
                    canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                            (i+1) * cellW - 6, bgY[j] + 8,
                            bg1Paint);
                    canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                            i * cellW + 12, bgY[j], textPaint);

                    cells[0][i][j] = schedule;
                }
            }
            day++;
        }

        float line2Y = week1cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line2Y, viewW, line2Y, linePaint);

        // 第二週
        float week2Y = line2Y + textSize + 4;
        // 第二週的內容第一列
        float week2cont1Y = week2Y + textSize + 4;
        // 第二週的內容第二列
        float week2cont2Y = week2cont1Y + textSize + 4;
        // 第二週的內容第三列
        float week2cont3Y = week2cont2Y + textSize + 4;
        bgY = new float[]{week2cont1Y,week2cont2Y,week2cont3Y};

        for (int i=0; i<7; i++){
            canvas.drawText("" + (day),
                    i * cellW + 6, week2Y, textPaint);

            String key = (year) + "-" + BradUtils.preZero(month)+"-"+ BradUtils.preZero(day);
            keys[1][i] = key;
            if (schedules.containsKey(key)){
                LinkedList<Schedule> ss = schedules.get(key);
                for (int j=0; j<Math.min(3,ss.size()); j++){
                    Schedule schedule = ss.get(j);
                    canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                            (i+1) * cellW - 6, bgY[j] + 8,
                            bg1Paint);
                    canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                            i * cellW + 12, bgY[j], textPaint);
                    cells[1][i][j] = schedule;
                }
            }


            day++;
        }

        float line3Y = week2cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line3Y, viewW, line3Y, linePaint);

        // 第三週
        float week3Y = line3Y + textSize + 4;
        // 第三週的內容第一列
        float week3cont1Y = week3Y + textSize + 4;
        // 第三週的內容第二列
        float week3cont2Y = week3cont1Y + textSize + 4;
        // 第三週的內容第三列
        float week3cont3Y = week3cont2Y + textSize + 4;
        bgY = new float[]{week3cont1Y,week3cont2Y,week3cont3Y};

        for (int i=0; i<7; i++){
            canvas.drawText("" + (day),
                    i * cellW + 6, week3Y, textPaint);

            String key = (year) + "-" + BradUtils.preZero(month)+"-"+ BradUtils.preZero(day);
            keys[2][i] = key;
            if (schedules.containsKey(key)){
                LinkedList<Schedule> ss = schedules.get(key);
                for (int j=0; j<Math.min(3,ss.size()); j++){
                    Schedule schedule = ss.get(j);
                    canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                            (i+1) * cellW - 6, bgY[j] + 8,
                            bg1Paint);
                    canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                            i * cellW + 12, bgY[j], textPaint);
                    cells[2][i][j] = schedule;
                }
            }

            day++;
        }

        float line4Y = week3cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line4Y, viewW, line4Y, linePaint);

        // 第四週
        float week4Y = line4Y + textSize + 4;
        // 第四週的內容第一列
        float week4cont1Y = week4Y + textSize + 4;
        // 第四週的內容第二列
        float week4cont2Y = week4cont1Y + textSize + 4;
        // 第四週的內容第三列
        float week4cont3Y = week4cont2Y + textSize + 4;
        bgY = new float[]{week4cont1Y,week4cont2Y,week4cont3Y};

        for (int i=0; i<7; i++){
            canvas.drawText("" + (day),
                    i * cellW + 6, week4Y, textPaint);

            String key = (year) + "-" + BradUtils.preZero(month)+"-"+ BradUtils.preZero(day);
            keys[3][i] = key;
            if (schedules.containsKey(key)){
                LinkedList<Schedule> ss = schedules.get(key);
                for (int j=0; j<Math.min(3,ss.size()); j++){
                    Schedule schedule = ss.get(j);
                    canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                            (i+1) * cellW - 6, bgY[j] + 8,
                            bg1Paint);
                    canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                            i * cellW + 12, bgY[j], textPaint);
                    cells[3][i][j] = schedule;
                }
            }
            day++;
        }

        float line5Y = week4cont3Y + (textSize / 3) + 4;
        canvas.drawLine(0,line5Y, viewW, line5Y, linePaint);
        lastY = line5Y;

        // 還沒有呈現完畢
        if (day <= days){
            int nextMonthDay = 1;
            // 第五週
            float week5Y = line5Y + textSize + 4;
            // 第五週的內容第一列
            float week5cont1Y = week5Y + textSize + 4;
            // 第五週的內容第二列
            float week5cont2Y = week5cont1Y + textSize + 4;
            // 第五週的內容第三列
            float week5cont3Y = week5cont2Y + textSize + 4;
            bgY = new float[]{week5cont1Y,week5cont2Y,week5cont3Y};
            String key = "";

            for (int i=0; i<7; i++){
                if (day <= days) {
                    canvas.drawText("" + (day),
                            i * cellW + 6, week5Y, textPaint);
                    key = (year) + "-" + BradUtils.preZero(month)+"-"+ BradUtils.preZero(day);
                    day++;
                }else{
                    canvas.drawText("" + (nextMonthDay),
                            i * cellW + 6, week5Y, textPaint);
                    key = (nextyear) + "-" + BradUtils.preZero(nextmonth)+"-"+ BradUtils.preZero(nextMonthDay);
                    nextMonthDay++;
                }
                keys[4][i] = key;

                if (schedules.containsKey(key)){
                    LinkedList<Schedule> ss = schedules.get(key);
                    for (int j=0; j<Math.min(3,ss.size()); j++){
                        Schedule schedule = ss.get(j);
                        canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                                (i+1) * cellW - 6, bgY[j] + 8,
                                bg1Paint);
                        canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                                i * cellW + 12, bgY[j], textPaint);
                        cells[4][i][j] = schedule;
                    }
                }

            }

            float line6Y = week5cont3Y + (textSize / 3) + 4;
            canvas.drawLine(0,line6Y, viewW, line6Y, linePaint);
            lastY = line6Y;

            if (day <= days) {
                // 第六週
                float week6Y = line6Y + textSize + 4;
                // 第六週的內容第一列
                float week6cont1Y = week6Y + textSize + 4;
                // 第六週的內容第二列
                float week6cont2Y = week6cont1Y + textSize + 4;
                // 第六週的內容第三列
                float week6cont3Y = week6cont2Y + textSize + 4;
                bgY = new float[]{week6cont1Y,week6cont2Y,week6cont3Y};
                key = "";

                for (int i = 0; i < 7; i++) {
                    if (day <= days) {
                        canvas.drawText("" + (day++),
                                i * cellW + 6, week6Y, textPaint);
                        key = (year) + "-" + BradUtils.preZero(month)+"-"+ BradUtils.preZero(day);
                        day++;
                    } else {
                        canvas.drawText("" + (nextMonthDay++),
                                i * cellW + 6, week6Y, textPaint);
                        key = (nextyear) + "-" + BradUtils.preZero(nextmonth)+"-"+ BradUtils.preZero(nextMonthDay);
                        nextMonthDay++;
                    }
                    keys[5][i] = key;

                    if (schedules.containsKey(key)){
                        LinkedList<Schedule> ss = schedules.get(key);
                        for (int j=0; j<Math.min(3,ss.size()); j++){
                            Schedule schedule = ss.get(j);
                            canvas.drawRect(i * cellW + 6, bgY[j] - textSize,
                                    (i+1) * cellW - 6, bgY[j] + 8,
                                    bg1Paint);
                            canvas.drawText(BradUtils.getStringByLength(schedule.meeting_title,8),
                                    i * cellW + 12, bgY[j], textPaint);
                            cells[5][i][j] = schedule;
                        }
                    }

                }

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
        float week1cont2Y = week1cont1Y + textSize + 4;
        float week1cont3Y = week1cont2Y + textSize + 4;

        float line2Y = week1cont3Y + (textSize / 3) + 4;
        float week2Y = line2Y + textSize + 4;
        float week2cont1Y = week2Y + textSize + 4;
        float week2cont2Y = week2cont1Y + textSize + 4;
        float week2cont3Y = week2cont2Y + textSize + 4;

        float line3Y = week2cont3Y + (textSize / 3) + 4;
        float week3Y = line3Y + textSize + 4;
        float week3cont1Y = week3Y + textSize + 4;
        float week3cont2Y = week3cont1Y + textSize + 4;
        float week3cont3Y = week3cont2Y + textSize + 4;

        float line4Y = week3cont3Y + (textSize / 3) + 4;
        float week4Y = line4Y + textSize + 4;
        float week4cont1Y = week4Y + textSize + 4;
        float week4cont2Y = week4cont1Y + textSize + 4;
        float week4cont3Y = week4cont2Y + textSize + 4;

        float line5Y = week4cont3Y + (textSize / 3) + 4;
        float week5Y = line5Y + textSize + 4;
        float week5cont1Y = week5Y + textSize + 4;
        float week5cont2Y = week5cont1Y + textSize + 4;
        float week5cont3Y = week5cont2Y + textSize + 4;

        float line6Y = week5cont3Y + (textSize / 3) + 4;
        float week6Y = line6Y + textSize + 4;
        float week6cont1Y = week6Y + textSize + 4;
        float week6cont2Y = week6cont1Y + textSize + 4;
        float week6cont3Y = week6cont2Y + textSize + 4;

        int row=-1, col=-1;

        // touch 第一列每一格日期標題
        if (ey >= week1Y - textSize && ey <= week1Y + 8){
            row = 0;
        }else if (ey >= week2Y - textSize && ey <= week2Y + 8){
            row = 1;
        }else if (ey >= week3Y - textSize && ey <= week3Y + 8){
            row = 2;
        }else if (ey >= week4Y - textSize && ey <= week4Y + 8){
            row = 3;
        }else if (ey >= week5Y - textSize && ey <= week5Y + 8){
            row = 4;
        }else if (ey >= week6Y - textSize && ey <= week6Y + 8){
            row = 5;
        }

        if (ex >= 0 * cellW + 6 && ex <= 1 * cellW - 6){
            col = 0;
        }else if (ex >= 1 * cellW + 6 && ex <= 2 * cellW - 6){
            col = 1;
        }else if (ex >= 2 * cellW + 6 && ex <= 3 * cellW - 6){
            col = 2;
        }else if (ex >= 3 * cellW + 6 && ex <= 4 * cellW - 6){
            col = 3;
        }else if (ex >= 4 * cellW + 6 && ex <= 5 * cellW - 6){
            col = 4;
        }else if (ex >= 5 * cellW + 6 && ex <= 6 * cellW - 6){
            col = 5;
        }else if (ex >= 6 * cellW + 6 && ex <= 7 * cellW - 6){
            col = 6;
        }
        if (row>=0 && col>=0) {
            ((MainActivity)getContext()).calendarFragment.updateEventList(keys[row][col]);

        }else{
            int crow=-1, ci = -1;
            if (ey >= week1cont1Y - textSize && ey <= week1cont1Y + 8){
                crow = 0; ci = 0;
            }else if(ey >= week1cont2Y - textSize && ey <= week1cont2Y + 8){
                crow = 0; ci = 1;
            }else if(ey >= week1cont3Y - textSize && ey <= week1cont3Y + 8){
                crow = 0; ci = 2;
            }else if(ey >= week2cont1Y - textSize && ey <= week2cont1Y + 8){
                crow = 1; ci = 0;
            }else if(ey >= week2cont2Y - textSize && ey <= week2cont2Y + 8){
                crow = 1; ci = 1;
            }else if(ey >= week2cont3Y - textSize && ey <= week2cont3Y + 8){
                crow = 1; ci = 2;
            }else if(ey >= week3cont1Y - textSize && ey <= week3cont1Y + 8){
                crow = 2; ci = 0;
            }else if(ey >= week3cont2Y - textSize && ey <= week3cont2Y + 8){
                crow = 2; ci = 1;
            }else if(ey >= week3cont3Y - textSize && ey <= week3cont3Y + 8){
                crow = 2; ci = 2;
            }else if(ey >= week4cont1Y - textSize && ey <= week4cont1Y + 8){
                crow = 3; ci = 0;
            }else if(ey >= week4cont2Y - textSize && ey <= week4cont2Y + 8){
                crow = 3; ci = 1;
            }else if(ey >= week4cont3Y - textSize && ey <= week4cont3Y + 8){
                crow = 3; ci = 2;
            }else if(ey >= week5cont1Y - textSize && ey <= week5cont1Y + 8){
                crow = 4; ci = 0;
            }else if(ey >= week5cont2Y - textSize && ey <= week5cont2Y + 8){
                crow = 4; ci = 1;
            }else if(ey >= week5cont3Y - textSize && ey <= week5cont3Y + 8){
                crow = 4; ci = 2;
            }else if(ey >= week6cont1Y - textSize && ey <= week6cont1Y + 8){
                crow = 5; ci = 0;
            }else if(ey >= week6cont2Y - textSize && ey <= week6cont2Y + 8){
                crow = 5; ci = 1;
            }else if(ey >= week6cont3Y - textSize && ey <= week6cont3Y + 8){
                crow = 5; ci = 2;
            }

            if (crow>=0 && col>=0 && ci>=0 && cells[crow][col][ci] != null) {
                Intent intent = new Intent(getContext(), DetailScheduleActivity.class);
                intent.putExtra("month", month);
                intent.putExtra("schedule", cells[crow][col][ci]);
                intent.putExtra("isEditable", false);
                getContext().startActivity(intent);
            }
        }

        return super.onTouchEvent(event);
    }


}
