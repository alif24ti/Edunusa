package com.example.edunusa.kelas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.edunusa.BaseActivity
import com.example.edunusa.DetailMateriActivity
import com.example.edunusa.LoginActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.FragmentKelasBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class KelasFragment : Fragment() {

    private var _binding: FragmentKelasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (requireActivity() as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)


        binding.btnNavNotif.setOnClickListener {
            (activity as? BaseActivity)?.pindahTab(R.id.progres)
        }
        binding.btnNavProfile.setOnClickListener {
            (activity as? BaseActivity)?.pindahTab(R.id.profil)
        }
        binding.btnNavLogout.setOnClickListener {
            showLogoutDialog()
        }


        Glide.with(this)
            .load("https://images.unsplash.com/photo-1509228468518-180dd4864904?w=600&q=80")
            .placeholder(R.drawable.logo_edunusa)
            .into(binding.imgBannerMath)


        binding.btnLihatMateriMath.setOnClickListener {
            bukaDetailMateri(
                subject = "Matematika",
                modul = "Modul 3 dari 12",
                title = "Matematika: Pecahan Sederhana",
                desc = "Pelajari konsep pecahan dasar, menyamakan penyebut, hingga operasi hitung pecahan.",
                duration = "⏱ 45 Menit",
                level = "SMP Kelas 7",
                rating = 4.9
            )
        }


        setupSimpleAdapterCatalog()
    }

    private fun setupSimpleAdapterCatalog() {
        val dataListWithDesc = listOf(
            mapOf("title" to "Matematika", "desc" to "18 Modul • 12 Kuis • Aljabar, Geometri & Pecahan"),
            mapOf("title" to "Bahasa Indonesia", "desc" to "14 Modul • 10 Kuis • Teks Deskripsi, Puisi & Tata Bahasa"),
            mapOf("title" to "IPA (Ilmu Pengetahuan Alam)", "desc" to "22 Modul • 16 Kuis • Ekosistem, Energi & Biologi"),
            mapOf("title" to "Bahasa Inggris", "desc" to "16 Modul • 12 Kuis • Grammar, Vocabularies & Conversation"),
            mapOf("title" to "IPS & Sejarah", "desc" to "12 Modul • 8 Kuis • Kenampakan Alam & Sejarah Nusantara"),
            mapOf("title" to "Informatika & Koding", "desc" to "10 Modul • 8 Kuis • Berpikir Komputasional & Logika")
        )

        val simpleAdapter = SimpleAdapter(
            requireContext(),
            dataListWithDesc,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listViewMapel.adapter = simpleAdapter
        setListViewHeightBasedOnChildren(binding.listViewMapel)

        binding.listViewMapel.setOnItemClickListener { _, _, position, _ ->
            val item = dataListWithDesc[position]
            val title = item["title"] ?: ""
            val desc = item["desc"] ?: ""
            Toast.makeText(requireContext(), "Mata Pelajaran: $title", Toast.LENGTH_SHORT).show()

            bukaDetailMateri(
                subject = title,
                modul = "Modul 1",
                title = "$title: Pengenalan Konsep",
                desc = desc,
                duration = "⏱ 45 Menit",
                level = "SMP Kelas 7",
                rating = 4.9
            )
        }
    }

    private fun bukaDetailMateri(
        subject: String,
        modul: String,
        title: String,
        desc: String,
        duration: String,
        level: String,
        rating: Double
    ) {
        val intent = Intent(requireContext(), DetailMateriActivity::class.java).apply {
            putExtra("EXTRA_SUBJECT", subject)
            putExtra("EXTRA_MODUL", modul)
            putExtra("EXTRA_TITLE", title)
            putExtra("EXTRA_DESC", desc)
            putExtra("EXTRA_DURATION", duration)
            putExtra("EXTRA_LEVEL", level)
            putExtra("EXTRA_RATING", rating)
        }
        startActivity(intent)
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.resources.displayMetrics.widthPixels, View.MeasureSpec.AT_MOST)
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1)) + 40
        listView.layoutParams = params
        listView.requestLayout()
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Keluar")
            .setMessage("Apakah Anda yakin ingin keluar dari akun EduNusa?")
            .setPositiveButton("Keluar") { dialog, _ ->
                val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()
                Toast.makeText(requireContext(), "Berhasil keluar dari akun", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
