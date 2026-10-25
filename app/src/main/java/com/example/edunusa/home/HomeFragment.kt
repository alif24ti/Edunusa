package com.example.edunusa.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.edunusa.BaseActivity
import com.example.edunusa.DetailMateriActivity
import com.example.edunusa.LoginActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val allMateriList = mutableListOf<MateriModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Siswa") ?: "Siswa"


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


        binding.tvGreetingName.text = "Halo, $username!"


        binding.btnContinueLearning.setOnClickListener {
            val intent = Intent(requireContext(), DetailMateriActivity::class.java).apply {
                putExtra("EXTRA_SUBJECT", "Matematika")
                putExtra("EXTRA_MODUL", "Modul 3 dari 12")
                putExtra("EXTRA_TITLE", "Matematika: Pecahan Sederhana")
                putExtra("EXTRA_DESC", "Pelajari konsep pecahan dasar, menyamakan penyebut, hingga operasi hitung pecahan.")
                putExtra("EXTRA_DURATION", "⏱ 45 Menit")
                putExtra("EXTRA_LEVEL", "SMP Kelas 7")
                putExtra("EXTRA_RATING", 4.9)
            }
            startActivity(intent)
        }


        initMateriData()
        updateListView(allMateriList)


        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) {
                updateListView(allMateriList)
                return@setOnCheckedStateChangeListener
            }
            when (checkedIds[0]) {
                R.id.chipMatematika -> updateListView(allMateriList.filter { it.subject == "Matematika" })
                R.id.chipIndo -> updateListView(allMateriList.filter { it.subject == "Bahasa Indonesia" })
                R.id.chipIPA -> updateListView(allMateriList.filter { it.subject == "IPA" })
                else -> updateListView(allMateriList)
            }
        }


        binding.tabLayoutMateri.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    updateListView(allMateriList.reversed())
                } else {
                    updateListView(allMateriList)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initMateriData() {
        allMateriList.clear()
        allMateriList.add(
            MateriModel(
                subject = "Matematika",
                modulInfo = "Modul 3 dari 12",
                title = "Matematika: Pecahan Sederhana",
                desc = "Pelajari konsep pecahan dasar, penyebut sama dan operasi penjumlahan.",
                duration = "⏱ 45 Menit",
                level = "SMP Kelas 7",
                rating = 4.9,
                progress = 65,
                imageUrl = "https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=400&q=80"
            )
        )
        allMateriList.add(
            MateriModel(
                subject = "Bahasa Indonesia",
                modulInfo = "Modul 2 dari 10",
                title = "Teks Deskripsi & Struktur Bahasa",
                desc = "Menulis dan memahami objek teks deskriptif secara lugas dan terstruktur.",
                duration = "⏱ 35 Menit",
                level = "SMP Kelas 7",
                rating = 4.8,
                progress = 40,
                imageUrl = "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=400&q=80"
            )
        )
        allMateriList.add(
            MateriModel(
                subject = "IPA",
                modulInfo = "Modul 4 dari 16",
                title = "Klasifikasi Makhluk Hidup & Ekosistem",
                desc = "Karakteristik biotik dan abiotik dalam lingkungan hidup sekitar.",
                duration = "⏱ 50 Menit",
                level = "SMP Kelas 7",
                rating = 4.9,
                progress = 60,
                imageUrl = "https://images.unsplash.com/photo-1532094349884-543bc11b234d?w=400&q=80"
            )
        )
    }

    private fun updateListView(list: List<MateriModel>) {
        val adapter = MateriAdapter(requireContext(), list)
        binding.listMateri.adapter = adapter
        setListViewHeightBasedOnChildren(binding.listMateri)
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
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1)) + 50
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
