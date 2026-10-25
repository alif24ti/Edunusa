package com.example.edunusa.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.edunusa.DetailMateriActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.ItemMateriBinding

class MateriAdapter(
    context: Context,
    private val materiList: List<MateriModel>
) : ArrayAdapter<MateriModel>(context, 0, materiList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemMateriBinding
        val view: View

        if (convertView == null) {
            binding = ItemMateriBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemMateriBinding
            view = convertView
        }

        val item = getItem(position) ?: return view

        binding.tvSubject.text = item.subject
        binding.tvTitle.text = item.title
        binding.tvDesc.text = item.desc
        binding.tvDuration.text = item.duration
        binding.tvLevel.text = item.level
        binding.tvRating.text = "★ ${item.rating}"
        binding.tvProgress.text = "Progres ${item.progress}%"
        binding.progressBarMateri.progress = item.progress


        if (item.imageUrl.isNotEmpty()) {
            Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.logo_edunusa)
                .error(R.drawable.logo_edunusa)
                .into(binding.imgThumbnail)
        } else {
            binding.imgThumbnail.setImageResource(R.drawable.logo_edunusa)
        }


        view.setOnClickListener {
            val intent = Intent(context, DetailMateriActivity::class.java).apply {
                putExtra("EXTRA_SUBJECT", item.subject)
                putExtra("EXTRA_MODUL", item.modulInfo)
                putExtra("EXTRA_TITLE", item.title)
                putExtra("EXTRA_DESC", item.desc)
                putExtra("EXTRA_DURATION", item.duration)
                putExtra("EXTRA_LEVEL", item.level)
                putExtra("EXTRA_RATING", item.rating)
                putExtra("EXTRA_IMAGE_URL", item.imageUrl)
            }
            context.startActivity(intent)
        }

        return view
    }
}
