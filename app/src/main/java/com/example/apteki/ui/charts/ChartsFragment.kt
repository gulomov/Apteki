package com.example.apteki.ui.charts

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.apteki.databinding.FragmentChartsBinding
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.Chart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding
    private val viewModel: ChartsViewModel by viewModel()

    private val pieEntries: ArrayList<PieEntry> = ArrayList()


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
        pieEntries.add(PieEntry(100f, chart.branch_name))

    val colors: ArrayList<Int> = ArrayList()
    colors.add(Color.parseColor("#304567"))
    colors.add(Color.parseColor("#309967"))
    colors.add(Color.parseColor("#476567"))
    colors.add(Color.parseColor("#476567"))


    val pieDataSet = PieDataSet(pieEntries, "")

    pieDataSet.valueTextSize = 12f

    pieDataSet.colors = colors


    val pieData = PieData(pieDataSet)

    binding?.piChart?.data = pieData
    binding?.piChart?.invalidate()
    binding?.piChart?.animate()
}


override fun onDestroy() {
    super.onDestroy()
    _binding = null
}

}