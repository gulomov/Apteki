package com.digitalcity.apteki.ui.charts

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.digitalcity.apteki.databinding.FragmentChartsBinding
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.network.pojo.Chart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartsFragment : Fragment() {

    private var scoreList = ArrayList<Chart>()
    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding
    private val viewModel: ChartsViewModel by viewModel()

    private val pieEntries: ArrayList<PieEntry> = ArrayList()
    private val barEntries: ArrayList<BarEntry> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        getChart()
        return binding?.root
    }

    private fun getChart() {
        viewModel.getCharts()
        viewModel.resourceCharts.observe(viewLifecycleOwner, Observer { it ->
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                        setPieChartLoad(resource.data.data.savdo_chart)
                        setBar(resource.data.data.savdo_chart)
                        scoreList = resource.data.data.savdo_chart as ArrayList<Chart>
                    }
                    is Resource.Error -> {
                        Log.d("error", "${resource.exception.message}")

                    }
                    is Resource.GenericError -> {
                        Log.d("error", resource.errorResponse.message)
                    }
                }
            }
        })
    }

    private fun setPieChartLoad(info: List<Chart>) {

        for (chart in info)
            pieEntries.add(PieEntry(chart.summa.toFloat(), chart.branch_name))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))
        colors.add(Color.parseColor("#476567"))


        val pieDataSet = PieDataSet(pieEntries, "")

        pieDataSet.valueTextSize = 12f

        pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)


        val pieData = PieData(pieDataSet)

        binding?.piChart?.data = pieData
        binding?.piChart?.invalidate()
        binding?.piChart?.animate()
    }

    private fun setBarChartLoad(info: List<Chart>) {
        val dates = ArrayList<String>()
        for (chars in info) {
            barEntries.add(BarEntry(100f, 100f))
            dates.add(chars.branch_name)
        }


        val barDataset = BarDataSet(barEntries, " ")
        barDataset.setDrawIcons(false)
        barDataset.setDrawValues(false)
        barDataset.isHighlightEnabled = false
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataset)

        val data = BarData(dataSets)
        binding?.barChart?.data = data

        val xAxis = binding?.barChart?.xAxis
        xAxis?.valueFormatter = IndexAxisValueFormatter(dates)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawAxisLine(false)
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
    }

    private fun setBar(info: List<Chart>) {
        val entries: ArrayList<BarEntry> = ArrayList()
        initBarChart()
        for (i in info.indices) {
            val score = info[i]
            entries.add(BarEntry(i.toFloat(), score.summa.toFloat()))
        }
        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val data = BarData(barDataSet)
        binding?.barChart?.data = data
        binding?.barChart?.invalidate()
    }

    private fun initBarChart() {

//        hide grid lines
        binding?.barChart?.axisLeft?.setDrawGridLines(false)
        val xAxis: XAxis = binding?.barChart?.xAxis!!
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        binding?.barChart?.axisRight?.isEnabled = false

        //remove legend
        binding?.barChart?.legend?.isEnabled = false


        //remove description label
        binding?.barChart?.description?.isEnabled = false


        //add animation
        binding?.barChart?.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d(TAG, "getAxisLabel: index $index")
            return if (index < scoreList.size) {
                scoreList[index].branch_name
            } else {
                ""
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}