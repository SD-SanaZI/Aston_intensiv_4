package com.example.aston_intensiv_4

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.aston_intensiv_4.databinding.FragmentTask1Binding


const val VIEW_DATA_LIST_KEY = "VIEW_DATA_LIST_KEY"

class FirstTaskFragment : BaseFragment<FragmentTask1Binding>(
    R.layout.fragment_task_1
) {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTask1Binding {
        return FragmentTask1Binding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(viewDataList: ViewDataList): FirstTaskFragment {
            val fragment = FirstTaskFragment()
            val bundle = Bundle()
            bundle.putSerializable(VIEW_DATA_LIST_KEY, viewDataList)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val bundle = arguments as Bundle?
        val viewDataList = getViewDataList(bundle)
        viewDataList.list.forEach { viewData ->
            val newView: View = when (viewData) {
                is ViewData.TextData -> {
                    val textView = TextView(context)
                    textView.text = viewData.text
                    textView.gravity = Gravity.CENTER
                    textView
                }

                is ViewData.ButtonData -> {
                    val button = Button(context)
                    button.text = viewData.text
                    val text =
                        if (viewDataList.tag == "B") "Secret string"
                        else ""
                    button.setOnClickListener {
                        if (viewData.tag == "back") {
                            parentFragmentManager.popBackStack()
                        } else {
                            val a =
                                parentFragmentManager.findFragmentByTag(viewData.tag)
                            if (a == null) {
                                parentFragmentManager.beginTransaction()
                                    .replace(
                                        R.id.fragment_container_view_tag, newInstance(
                                            FragmentDataAdapter.getViewDataList(viewData.tag, text)
                                        ), viewData.tag
                                    )
                                    .addToBackStack(viewData.tag)
                                    .commit()
                            }else{
                                parentFragmentManager.popBackStack(viewData.tag,0)
                            }
                        }
                    }
                    button
                }
            }
            newView.foregroundGravity = Gravity.CENTER
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            layoutParams.weight = 1f
            newView.layoutParams = layoutParams
            binding.layout.addView(newView)
        }
        return view
    }

    private fun getViewDataList(bundle: Bundle?): ViewDataList {
        return if (bundle != null) {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(VIEW_DATA_LIST_KEY, ViewDataList::class.java)
                    ?: throw Exception("Null Dialog data")
            } else {
                bundle.getSerializable(VIEW_DATA_LIST_KEY) as ViewDataList
            }
        } else {
            ViewDataList("", listOf())
        }
    }
}