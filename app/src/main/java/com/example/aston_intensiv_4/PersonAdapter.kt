package com.example.aston_intensiv_4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class PersonAdapter(private val fragmentManager: FragmentManager) :
    ListAdapter<Person, PersonAdapter.PersonViewHolder>(PersonDiffUtil()) {

    inner class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img: ImageView
        private val name: TextView
        private val surname: TextView
        private val number: TextView

        init {
            img = view.findViewById(R.id.personItemImage)
            name = view.findViewById(R.id.personItemName)
            surname = view.findViewById(R.id.personItemSurname)
            number = view.findViewById(R.id.personItemTelephone)
        }

        fun bind(data: Person) {
            setImage(img,data.imgURL)
            name.text = data.name
            surname.text = data.surname
            number.text = data.telephone
        }
    }

    private val bitmapList: MutableList<BitmapWithId> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, SecondTaskChangeFragment.newInstance(data))
                .addToBackStack("menu")
                .commit()
        }
    }

    class PersonDiffUtil : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    private fun setImage(imageView: ImageView, urlString: String){
        val bmp = bitmapList.find { it.url == urlString }?.bitmap
        if (bmp == null) {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                addedBitmapToList(urlString)
                setImage(imageView, urlString)
            }
        } else{
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                imageView.setImageBitmap(bmp)
            }
        }
    }

    private fun addedBitmapToList(urlString: String): Bitmap {
        val url = URL(urlString)
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        bitmapList.add(
            BitmapWithId(
                urlString,
                bmp ?: Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            )
        )
        return bmp
    }
}

data class BitmapWithId(
    val url:String,
    val bitmap: Bitmap
)