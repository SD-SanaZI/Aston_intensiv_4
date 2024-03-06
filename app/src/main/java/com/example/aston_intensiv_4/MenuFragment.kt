package com.example.aston_intensiv_4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aston_intensiv_4.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment<FragmentMenuBinding>(
    R.layout.fragment_task_1
) {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.button.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, FirstTaskFragment.newInstance(
                    FragmentDataAdapter.getViewDataList("A", "")
                ),"A")
                .addToBackStack("A")
                .commit()
        }
        return view
    }
}