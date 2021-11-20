package com.example.apteki.ui.charts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apteki.R
import com.example.apteki.databinding.FragmentChartsBinding
import com.github.mikephil.charting.data.PieEntry


class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun setPieChartLoad() {

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}