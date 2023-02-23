package com.devanshsaxena.onestoreadmin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devanshsaxena.onestoreadmin.R
import com.devanshsaxena.onestoreadmin.activity.AllOrderActivity
import com.devanshsaxena.onestoreadmin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

//        binding.button.setOnClickListener{
//            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
//        }

//        binding.button5.setOnClickListener{
//            findNavController().navigate(R.id.action_homeFragment_to_mealFragment)
//        }

        binding.button2.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_productFragment)
        }

        binding.button3.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_sliderFragment)
        }

        binding.button4.setOnClickListener{
            startActivity(Intent(requireContext(),AllOrderActivity::class.java))
        }

        return binding.root

    }

}