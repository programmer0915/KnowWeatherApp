package com.ice.weather.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.ice.weather.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM;

public class LineChartUtil {

    /**
     *  配置Chart 基础设置
     * @param mLineChart 图表
     * @param isShowLegend 是否显示图例
     */
    public static void configChart(LineChart mLineChart,Typeface typeface,
                                   boolean isShowLegend) {
        //设置描述文本不显示
        mLineChart.getDescription().setEnabled(false);
        //设置是否显示表格背景
        mLineChart.setDrawGridBackground(false);
        //设置是否可以触摸
        mLineChart.setTouchEnabled(true);
        //拖动降速速率
        mLineChart.setDragDecelerationFrictionCoef(0.9f);
        //设置是否可以拖拽
        mLineChart.setDragEnabled(true);
        //设置是否可以缩放
        mLineChart.setScaleEnabled(true);
        //x y轴同时缩放坐标尺比例
        mLineChart.setPinchZoom(true);
        //设置XY轴动画
        mLineChart.animateXY(1500,1500,
                Easing.EasingOption.EaseInSine, Easing.EasingOption.EaseInSine);
        //获取图例对象
        Legend legend = mLineChart.getLegend();
        // 是否显示图例
        if (isShowLegend) {
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setTextSize(8f);
            //设置图例颜色
            legend.setTextColor(Color.parseColor("#00D1FF"));
            //文字相隔图例距离
            legend.setFormToTextSpace(5f);
            //显示位置 左下方
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            //是否绘制在图表里面
            legend.setDrawInside(false);
        } else {
            legend.setEnabled(false);
        }
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置最大值
        xAxis.setAxisMaximum(5f);
        //设置最小值
        xAxis.setAxisMinimum(1f);
        //设置间隔
        xAxis.setGranularity(1f);
        //更改x轴颜色
        xAxis.setTextColor(Color.parseColor("#ffffff"));
        //字体大小
        xAxis.setTextSize(10f);
        //设置x轴网格线
        xAxis.setDrawGridLines(false);
        //网格线颜色
        xAxis.setGridColor(Color.parseColor("#ffffff"));
        //-----------------------------限制线-----------------------------------
        LimitLine limitLine = new LimitLine(30,"高温");
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTypeface(typeface);
        limitLine.setYOffset(7f);
        limitLine.setLineColor(Color.parseColor("#f1788f"));
        limitLine.setTextColor(Color.parseColor("#f7fdf9"));

        limitLine.setTextSize(8f);

        LimitLine limitLine2 = new LimitLine(0, "低温");
        limitLine2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine2.setTypeface(typeface);
        limitLine2.setYOffset(7f);
        limitLine2.setLineColor(Color.parseColor("#f1788f"));
        limitLine2.setTextColor(Color.parseColor("#f7fdf9"));
        limitLine2.setTextSize(8f);

        YAxis yAxis = mLineChart.getAxisLeft();
        yAxis.addLimitLine(limitLine);
        yAxis.addLimitLine(limitLine2);
        yAxis.setAxisMaximum(40f);
        yAxis.setAxisMinimum(-10f);
        //绘制左侧y轴网格线
        yAxis.setDrawGridLines(false);
        //设置网格线的颜色
        yAxis.setGridColor(Color.parseColor("#11ffffff"));
        //隐藏y轴右边的坐标轴
        mLineChart.getAxisRight().setEnabled(false);
        yAxis.setTextColor(Color.parseColor("#ffffff"));
        //字体大小
        yAxis.setTextSize(7f);

    }

    /**
     * 获取LineDataSet
     * @param entries
     * @param label
     * @param lineColor 曲线颜色
     * @param textColor 文字颜色
     * @param CircleColor 数据点颜色
     * @param CircleColorHole 圆心点颜色
     * @return
     */

    public static  LineDataSet getLineData (ArrayList<Entry> entries,
                                          String label,
                                          Typeface typeface,
                                          @ColorInt int lineColor,
                                          @ColorInt int textColor,
                                          @ColorInt int CircleColor,
                                          @ColorInt int CircleColorHole,
                                          boolean isFill) {

           LineDataSet dataSet = new LineDataSet(entries, label);
            // 设置曲线颜色
            dataSet.setColor(lineColor);
            // 设置数值文字颜色
            dataSet.setValueTextColor(textColor);
            // 是否绘制数据值
            dataSet.setDrawValues(true);
            // 设置字型
            dataSet.setValueTypeface(typeface);
            // 是否绘制圆点
            dataSet.setDrawCircles(true);
            // 设置数据点颜色
            dataSet.setCircleColor(CircleColor);
            // 设置数据点中间填充颜色
            dataSet.setCircleColorHole(CircleColorHole);
            // 是否显示圆心
            dataSet.setDrawCircleHole(false);
            //不显示十字线
            dataSet.setHighlightEnabled(false);
            //设置高亮线为透明色
            dataSet.setHighLightColor(Color.TRANSPARENT);

            if (isFill) {
                //是否设置填充曲线到x轴之间的区域
                dataSet.setDrawFilled(true);
                // 填充颜色
                dataSet.setFillColor(lineColor);
            }
            //设置圆点的颜色
            dataSet.setCircleColor(lineColor);
            // 设置圆点半径
            dataSet.setCircleRadius(4f);
            // 设置线的宽度
            dataSet.setLineWidth(1f);
            return dataSet;
    }
    /**
     * 初始化数据
     *
     * @param chart
     */
    public static void initData(LineChart chart,LineData lineData) {

        chart.setData(lineData);
        //更新
        chart.invalidate();
    }

}