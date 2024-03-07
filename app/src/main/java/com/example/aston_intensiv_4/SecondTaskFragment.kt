package com.example.aston_intensiv_4

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aston_intensiv_4.databinding.FragmentTask2ListBinding
import java.io.Serializable

const val PERSON_LIST_KEY = "PERSON_LIST_KEY"

class SecondTaskFragment : BaseFragment<FragmentTask2ListBinding>(
    R.layout.fragment_task_2_list
) {
    private lateinit var data: MutableList<Person>
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTask2ListBinding {
        return FragmentTask2ListBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(): SecondTaskFragment {
            return SecondTaskFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = getPrimaryPersons().toMutableList()
        if (savedInstanceState != null) {
            val newData = getSerializableCompat<PersonList>(savedInstanceState, PERSON_LIST_KEY)?.list?.toMutableList()
            if (newData != null)
                data = newData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val adapter = PersonAdapter(parentFragmentManager)
        adapter.submitList(data)
        binding.recycler.adapter = adapter
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val result = getSerializableCompat<Person>(bundle, PERSON_KEY)
            if(result != null){
                data[result.id] = result
                adapter.submitList(data)
            }
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PERSON_LIST_KEY, PersonList(data))
    }

    private fun getPrimaryPersons(): List<Person> {
        return List(4) {
            Person(
                it,
                "https://dummyimage.com/600x400/0${it}0/fff",
                "Name $it",
                "Surname $it",
                (80290000000 + it).toString()
            )
        }
    }
}

data class PersonList(
    val list:List<Person>
) : Serializable

data class Person(
    val id: Int = -1,
    val imgURL: String = "https://dummyimage.com/600x400/000/fff",
    val name: String = "Jon",
    val surname: String = "Snow",
    val telephone: String = "80290000000"
) : Serializable

inline fun <reified T : Serializable> getSerializableCompat(bundle: Bundle?, key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        bundle?.getSerializable(key, T::class.java)
    } else {
        bundle?.getSerializable(key) as T?
    }
}