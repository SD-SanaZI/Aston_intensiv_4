package com.example.aston_intensiv_4

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.aston_intensiv_4.databinding.FragmentTask2PersonBinding

const val PERSON_KEY = "PERSON_KEY"

class SecondTaskChangeFragment : BaseFragment<FragmentTask2PersonBinding>(
    R.layout.fragment_task_2_person
) {
    private lateinit var data: Person
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTask2PersonBinding {
        return FragmentTask2PersonBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(data: Person): SecondTaskChangeFragment {
            val fragment = SecondTaskChangeFragment()
            val bundle = Bundle()
            bundle.putSerializable(PERSON_KEY,data)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = getSerializableCompat<Person>(arguments, PERSON_KEY) ?: throw Exception("Empty arguments")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.textEditImgUrl.setText(data.imgURL)
        binding.textEditName.setText(data.name)
        binding.textEditSurname.setText(data.surname)
        binding.textEditTelephone.setText(data.telephone)
        binding.button.setOnClickListener {
            val result = data.copy(
                imgURL = binding.textEditImgUrl.text.toString(),
                name = binding.textEditName.text.toString(),
                surname = binding.textEditSurname.text.toString(),
                telephone = binding.textEditTelephone.text.toString()
                )
            parentFragmentManager.setFragmentResult("requestKey", bundleOf(PERSON_KEY to result))
            parentFragmentManager.popBackStack()
        }
        return view
    }
}