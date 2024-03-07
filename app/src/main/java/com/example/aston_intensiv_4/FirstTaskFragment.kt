package com.example.aston_intensiv_4

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
        val viewDataList =
            getSerializableCompat<ViewDataList>(arguments, VIEW_DATA_LIST_KEY) ?: ViewDataList()
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        layoutParams.weight = 1f
        viewDataList.list.forEach { viewData ->
            val newView: View = when (viewData) {
                is ViewData.TextData -> createdTextView(viewData)
                is ViewData.ButtonData -> createdButton(viewData, viewDataList.tag)
            }
            newView.foregroundGravity = Gravity.CENTER
            newView.layoutParams = layoutParams
            binding.layout.addView(newView)
        }
        return view
    }

    private fun createdTextView(viewData: ViewData.TextData): TextView {
        val textView = TextView(context)
        textView.text = viewData.text
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun createdButton(viewData: ViewData.ButtonData, tag: String): Button {
        val button = Button(context)
        button.text = viewData.text
        val nextFragmentText =
            if (tag == "B") "Secret string"
            else ""
        button.setOnClickListener {
            if (viewData.tag == "back") {
                parentFragmentManager.popBackStack()
            } else {
                val fragment = parentFragmentManager.findFragmentByTag(viewData.tag)
                if (fragment == null)
                    nextFragmentTransaction(viewData, nextFragmentText)
                else
                    parentFragmentManager.popBackStack(viewData.tag, 0)
            }
        }
        return button
    }

    private fun nextFragmentTransaction(viewData: ViewData.ButtonData, nextFragmentText: String) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view_tag, newInstance(
                    FragmentDataAdapter.getViewDataList(viewData.tag, nextFragmentText)
                ), viewData.tag
            )
            .addToBackStack(viewData.tag)
            .commit()
    }
}