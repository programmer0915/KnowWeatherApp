package com.ice.weather.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.ice.weather.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 饼状图工具类
 */
public class PieChartUtil {

    public static  void configChart( PieChart mPieChart){
        mPieChart.setUsePercentValues(false);//是否使用百分比显示
        //设置没有描述
        mPieChart.getDescription().setEnabled(false);
        //给饼图设置左上右下的边距
        mPieChart.setExtraOffsets(20,0,20,0);
        //减速摩擦系数,值越大表示会缓慢停止
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //这个方法为true就是环形图，为false就是饼图
        mPieChart.setDrawHoleEnabled(false);
        //设置初始旋转角度
        mPieChart.setRotationAngle(0);
        //设置可以手动旋转
        mPieChart.setRotationEnabled(true);
        //这个方法默认是true，设置为false之后，点击每一块不能向外突出
        mPieChart.setHighlightPerTapEnabled(true);
        //从Y轴的动画
        mPieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);

        Legend legend = mPieChart.getLegend();//图例
        legend.setEnabled(true);//是否显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//对齐
        legend.setForm(Legend.LegendForm.DEFAULT);//设置图例的图形样式,默认为圆形
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例的排列走向:vertacal相当于分行
        legend.setFormSize(8f);//设置图例的大小
        legend.setTextSize(8f);//设置图注的字体大小
        legend.setFormToTextSpace(4f);//设置图例到图注的距离
        legend.setDrawInside(true);//不是很清楚
        legend.setWordWrapEnabled(false);//设置图列换行(注意使用影响性能,仅适用legend位于图表下面)，我也不知道怎么用的
        legend.setTextColor(Color.parseColor("#00D1FF"));


    }

    public static PieDataSet getPieChartData(ArrayList<PieEntry> pieEntries, String label){
        PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
        pieDataSet.setColors(Color.parseColor("#C1ED68"), Color.parseColor("#FFCB6C"),
                Color.parseColor("#FF70E2"), Color.parseColor("#B8B1FB"),
                Color.parseColor("#70CFFF"));
        pieDataSet.setSliceSpace(0f);//设置每块饼之间的空隙
        pieDataSet.setSelectionShift(20f);//点击某个饼时拉长的宽度

        PieData dataSet = new PieData(pieDataSet);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor( Color.parseColor("#E7E7E7"));

        return pieDataSet;
    }
    /**
     * 初始化数据
     *
     * @param chart
     */
    public static void initData(PieChart chart, PieData pieData) {

        chart.setData(pieData);
        //更新
        chart.invalidate();
    }
}
