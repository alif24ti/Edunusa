package com.example.edunusa.progres

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.edunusa.DetailMateriActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.ItemPencapaianBinding

class PencapaianAdapter(
    context: Context,
    private val pencapaianList: List<PencapaianModel>
) : ArrayAdapter<PencapaianModel>(context, 0, pencapaianList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemPencapaianBinding
        val view: View

        if (convertView == null) {
            binding = ItemPencapaianBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemPencapaianBinding
            view = convertView
        }

        val item = getItem(position) ?: return view

        binding.tvTitlePencapaian.text = item.title
        binding.tvBodyPencapaian.text = item.body
        binding.badgeStatus.text = item.badgeText
        binding.tvSectionPencapaian.text = "Kategori: ${item.section}"

        if (item.icon != 0) {
            binding.imgPencapaian.setImageResource(item.icon)
        }
        if (item.iconColor != 0) {
            binding.imgPencapaian.setColorFilter(ContextCompat.getColor(context, item.iconColor))
        }


        if (item.isNew) {
            binding.cardPencapaianRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_surface))
            binding.badgeStatus.setBackgroundResource(R.drawable.bg_badge_yellow)
            binding.badgeStatus.setTextColor(ContextCompat.getColor(context, R.color.warning_orange))
        } else {
            binding.cardPencapaianRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            binding.badgeStatus.setBackgroundResource(R.drawable.bg_badge_green)
            binding.badgeStatus.setTextColor(ContextCompat.getColor(context, R.color.success_green))
        }

        view.setOnClickListener {
            val intent = Intent(context, DetailMateriActivity::class.java).apply {
                putExtra("EXTRA_SUBJECT", "Prestasi Belajar")
                putExtra("EXTRA_MODUL", item.section)
                putExtra("EXTRA_TITLE", item.title)
                putExtra("EXTRA_DESC", item.body)
                putExtra("EXTRA_DURATION", "⏱ 45 Menit")
                putExtra("EXTRA_LEVEL", "SMP Kelas 7")
                putExtra("EXTRA_RATING", 5.0)
            }
            context.startActivity(intent)
        }

        return view
    }
}
