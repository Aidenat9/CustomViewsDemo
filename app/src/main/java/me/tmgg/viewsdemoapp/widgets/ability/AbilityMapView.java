package me.tmgg.viewsdemoapp.widgets.ability;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

import me.tmgg.viewsdemoapp.DpUtils;
import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/3/25 10:23
 * package：me.tmgg.viewsdemoapp.widgets.ability
 * version：1.0
 * <p>description：   蛛网（多项值）分析图           </p>
 */
public class AbilityMapView extends View {
    private final static int NUMBER_OF_EDGES = 5;
    private final static float DEFAULT_TEXTSIZE = 14F;
    private final static int POLYGON_COUNTS = 2;

    private ArrayList<AbilityBean> dataLists; //元数据
    private int n; //边的数量或者能力的个数
    private float Radius; //最外圈的半径，顶点到中心点的距离
    private int intervalCount; //间隔数量，就把半径分为几段
    private float angle; //两条顶点到中线点的线之间的角度
    private Paint linePaint; //画线的笔
    private Paint textPaint; //画文字的笔
    private int viewHeight; //控件宽度
    private int viewWidth; //控件高度
    private ArrayList<ArrayList<PointF>> pointsArrayList; //存储多边形顶点数组的数组
    private ArrayList<PointF> abilityPoints; //存储能力点的数组

    public AbilityMapView(Context context) {
        this(context, null);
    }

    public AbilityMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbilityMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSize(context);
        initPoints();
        initPaint(context);
    }

    /**
     * 传入元数据
     *
     * @param datas
     */
    public void setData(ArrayList<AbilityBean> datas) {

        if (datas == null) {
            return;
        }
        this.dataLists = datas;
        n = datas.size();
        //View本身调用迫使view重画
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把画布的原点移动到控件的中心点
        canvas.translate(viewWidth / 2, viewHeight / 2);
        //多边形
        drawPolygon(canvas);
        //边线
//        drawOutLine(canvas);
        //能力线
        drawAbilityLine(canvas);
        //画中心点到边角的线
        drawCenter2Edgepoints(canvas);
        //文字
        drawAbilityText(canvas);
        //画文字上图片
        drawIconsAboveTexts(canvas);
        //画中心文字
        drawCenterText(canvas);
    }

    private void drawCenterText(Canvas canvas) {
        canvas.save();
        String text = "432";
        Paint centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setColor(Color.WHITE);
        centerTextPaint.setTextAlign(Paint.Align.CENTER); //设置文字居中
        centerTextPaint.setTextSize(DpUtils.sp2px(getContext().getResources(), 50F));
        float dp5 = DpUtils.dp2px(getContext().getResources(), 3.0F);
        centerTextPaint.setShadowLayer(dp5, -5, 5, Color.parseColor("#999999"));

        Paint.FontMetrics fontMetrics = centerTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        canvas.translate(0, 0);
        int baseLineY = (int) (0 - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(text, 0
                , baseLineY, centerTextPaint);
        canvas.restore();
    }

    private void drawIconsAboveTexts(Canvas canvas) {
        if(dataLists==null||dataLists.size()<=0)return;
        canvas.save();
        ArrayList<PointF> iconsPoints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            float r = Radius + DpUtils.dp2px(getContext().getResources(), 18);
            float x = (float) (r * Math.cos(angle * i - Math.PI / 2));
            float y = (float) (r * Math.sin(angle * i - Math.PI / 2));
            iconsPoints.add(new PointF(x, y));
        }
        Paint photoPaint = new Paint();
        photoPaint.setDither(true);
        photoPaint.setFilterBitmap(true);

        for (int i = 0; i < n; i++) {
            float x = iconsPoints.get(i).x;
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_golf);
            int iconHeight = bitmap.getHeight();
            int iconWidth = bitmap.getWidth();
            float textHeight = getTextHeight(dataLists.get(i).getDesc());
            float y = iconsPoints.get(i).y;
            if (y > 0) {
                y -= (float) iconHeight + textHeight / 2;
            } else {
                y -= (float) iconHeight + textHeight / 2;
            }

            if (0 == i) {
                x -= (float) iconWidth / 2;
            } else {
                if (x < 0) {
                    x -= (float) iconWidth;
                }
            }

            canvas.drawBitmap(bitmap, x, y, photoPaint);
        }

        canvas.restore();
    }

    private static final String TAG = "ability";

    private void drawAbilityText(Canvas canvas) {
        if(dataLists==null||dataLists.size()<=0)return;

        canvas.save();
        ArrayList<PointF> textPoints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            float r = Radius + DpUtils.dp2px(getContext().getResources(), 18);
            float x = (float) (r * Math.cos(angle * i - Math.PI / 2));
            float y = (float) (r * Math.sin(angle * i - Math.PI / 2));
            textPoints.add(new PointF(x, y));
        }
        if (dataLists.size() < n) {
            throw new IndexOutOfBoundsException("数据少于多边形的边数.");
        }
        for (int j = 0; j < n; j++) {
            float x = textPoints.get(j).x;
            float y = textPoints.get(j).y;
            //图片
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_golf);
            int iconWidth = bitmap.getWidth();
            String textContent = dataLists.get(j).getDesc();
            float textHeight = getTextHeight(textContent);

            y += textHeight / 2;
            if (0 != j) {
                if (x > 0) {
                    x += (float) iconWidth / 2;
                } else {
                    x -= (float) iconWidth / 2;
                }
            }
            canvas.drawText(textContent, x, y, textPaint);
        }
        canvas.restore();
    }

    private void drawAbilityLine(Canvas canvas) {
        canvas.save();

        abilityPoints = new ArrayList<>();
        if (dataLists.size() < n) {
            throw new IndexOutOfBoundsException("数据少于多边形的边数.");
        }
        for (int i = 0; i < n; i++) {
            float value = dataLists.get(i).getValue();

            float r = Radius * (float) value / 100;
            float x = (float) (r * Math.cos(angle * i - Math.PI / 2));
            float y = (float) (r * Math.sin(angle * i - Math.PI / 2));
            abilityPoints.add(new PointF(x, y));
        }
        linePaint.setStrokeWidth(DpUtils.dp2px(getContext().getResources(), 1f));
        linePaint.setColor(Color.parseColor("#BDF4D4"));
        linePaint.setStyle(Paint.Style.FILL); //设置空心的

        Path path = new Path();
        for (int i = 0; i < n; i++) {
            float x = abilityPoints.get(i).x;
            float y = abilityPoints.get(i).y;
            if (0 == i) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        //别忘了闭合
        path.close();
        canvas.drawPath(path, linePaint);

        canvas.restore();
    }

    private void drawCenter2Edgepoints(Canvas canvas) {
        if(dataLists==null||dataLists.size()<=0)return;

        canvas.save();

        linePaint.setColor(Color.parseColor("#ebebeb"));
        linePaint.setStyle(Paint.Style.STROKE);  //设置空心的
        for (int i = 0; i < n; i++) {
            float x = pointsArrayList.get(0).get(i).x;
            float y = pointsArrayList.get(0).get(i).y;
            canvas.drawLine(0, 0, x, y, linePaint);
        }
        canvas.restore();
    }

    private void drawOutLine(Canvas canvas) {
        canvas.save();
        linePaint.setColor(Color.parseColor("#EBEBEB"));
        linePaint.setStyle(Paint.Style.STROKE);  //设置空心的
        Path path = new Path();
        for (int i = 0; i < n; i++) {
            float x = pointsArrayList.get(0).get(i).x;
            float y = pointsArrayList.get(0).get(i).y;
            if (0 == i) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, linePaint);
        path.reset();

        canvas.restore();
    }

    private void drawPolygon(Canvas canvas) {
        canvas.save();
        linePaint.setStyle(Paint.Style.STROKE);  //设置为填充且描边
        Path path = new Path();
        //1.每层的颜色
        for (int k = 0; k < intervalCount; k++) {
            if (0 == k) {
                linePaint.setColor(Color.parseColor("#ebebeb"));
            } else if (1 == k) {
                linePaint.setColor(Color.parseColor("#ebebeb"));
            } else if (2 == k) {
                linePaint.setColor(Color.parseColor("#56C1C7"));
            } else if (3 == k) {
                linePaint.setColor(Color.parseColor("#278891"));
            }
            //2.画多边形
            for (int p = 0; p < n; p++) {
                float x = pointsArrayList.get(k).get(p).x;
                float y = pointsArrayList.get(k).get(p).y;
                if (p == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();  //设置为闭合的
            canvas.drawPath(path, linePaint);
            path.reset();   //清除path存储的路径
        }


        canvas.restore();
    }

    private void initPaint(Context context) {
        //1.linepaint
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(DpUtils.dp2px(context.getResources(), 1.0F));
        //2.textpaint
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER); //设置文字居中
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(DpUtils.sp2px(context.getResources(), DEFAULT_TEXTSIZE));
    }

    private void initPoints() {
        //一个数组中每个元素又一是一个点数组,有几个多边形就有几个数组
        pointsArrayList = new ArrayList<>();
        float x;
        float y;
        float r;
        for (int i = 0; i < intervalCount; i++) {

            //创建一个存储点的数组
            ArrayList<PointF> points = new ArrayList<>();
            if(n<=0)return;
            for (int j = 0; j < n; j++) {
                if (i == 1) {
                    r = Radius * ((float) 3 / 4);
                } else {
                    r = Radius * ((float) (intervalCount - i) / intervalCount);
                }
                //每一圈的半径都按比例减少 //这里减去Math.PI /
                // 2 是为了让多边形逆时针旋转90度，所以后面的所有用到cos,sin的都要减
                x = (float) (r * Math.cos(j * angle - Math.PI / 2));
                y = (float) (r * Math.sin(j * angle - Math.PI / 2));
                points.add(new PointF(x, y));
            }
            pointsArrayList.add(points);
        }

    }

    private void initSize(Context context) {
        n = NUMBER_OF_EDGES;
        float dp100 = DpUtils.dp2px(context.getResources(), 100);
        Radius = dp100;
        intervalCount = POLYGON_COUNTS;
        angle = (float) ((2 * Math.PI) / n);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        viewWidth = displayMetrics.widthPixels;
        viewHeight = displayMetrics.widthPixels;
    }


    private float getTextHeight(String text) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    private float getTextWidth(String text) {
        float textWidth = textPaint.measureText(text);
        return textWidth;
    }
}
