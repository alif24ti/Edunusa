package com.example.edunusa.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.edunusa.BaseActivity
import com.example.edunusa.DetailMateriActivity
import com.example.edunusa.LoginActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.FragmentHomeBinding
import com.example.edunusa.tutorial.TutorialEduNusaActivity
import com.google.android.material.chip.Chip
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

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_notif -> {
                        (activity as? BaseActivity)?.pindahTab(R.id.progres)
                        true
                    }
                    R.id.action_profil -> {
                        (activity as? BaseActivity)?.pindahTab(R.id.profil)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        binding.btnNavInfo.setOnClickListener {
            val intent = Intent(requireContext(), TutorialEduNusaActivity::class.java)
            startActivity(intent)
        }
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

        setupGridLayoutMenu()

        binding.btnContinueLearning.setOnClickListener {
            bukaDetailPelajaran(
                subject = "Matematika",
                modul = "Modul 3 dari 12",
                title = "Matematika: Pecahan Sederhana",
                desc = "Pelajari konsep pecahan dasar, menyamakan penyebut, hingga operasi hitung pecahan.",
                duration = "⏱ 45 Menit",
                level = "SMP Kelas 7",
                rating = 4.9
            )
        }

        initMateriData()
        updateListView(allMateriList)

        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedId = checkedIds.firstOrNull()
            if (selectedId != null) {
                val chip = group.findViewById<Chip>(selectedId)
                Toast.makeText(requireContext(), "Filter Materi: ${chip.text}", Toast.LENGTH_SHORT).show()
                when (selectedId) {
                    R.id.chipMatematika -> updateListView(allMateriList.filter { it.subject == "Matematika" })
                    R.id.chipIndo -> updateListView(allMateriList.filter { it.subject == "Bahasa Indonesia" })
                    R.id.chipIPA -> updateListView(allMateriList.filter { it.subject == "IPA" })
                    else -> updateListView(allMateriList)
                }
            } else {
                updateListView(allMateriList)
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

    private fun setupGridLayoutMenu() {
        binding.btnMenuMath.setOnClickListener {
            bukaDetailPelajaran(
                subject = "Matematika",
                modul = "Modul 3 dari 12",
                title = "Matematika: Pecahan Sederhana",
                desc = "Pelajari konsep pecahan dasar, menyamakan penyebut, hingga operasi hitung pecahan.",
                duration = "⏱ 45 Menit",
                level = "SMP Kelas 7",
                rating = 4.9
            )
        }

        binding.btnMenuIPA.setOnClickListener {
            bukaDetailPelajaran(
                subject = "IPA",
                modul = "Modul 4 dari 16",
                title = "IPA: Klasifikasi Makhluk Hidup & Ekosistem",
                desc = "Karakteristik biotik dan abiotik dalam lingkungan hidup sekitar.",
                duration = "⏱ 50 Menit",
                level = "SMP Kelas 7",
                rating = 4.9
            )
        }

        binding.btnMenuIndo.setOnClickListener {
            bukaDetailPelajaran(
                subject = "Bahasa Indonesia",
                modul = "Modul 2 dari 10",
                title = "Bahasa Indonesia: Teks Deskripsi",
                desc = "Menulis dan memahami objek teks deskriptif secara lugas dan terstruktur.",
                duration = "⏱ 35 Menit",
                level = "SMP Kelas 7",
                rating = 4.8
            )
        }

        binding.btnMenuEng.setOnClickListener {
            bukaDetailPelajaran(
                subject = "Bahasa Inggris",
                modul = "Modul 1 dari 8",
                title = "Bahasa Inggris: Greetings & Daily Conversation",
                desc = "Percakapan sehari-hari bahasa Inggris untuk siswa SMP tingkat dasar.",
                duration = "⏱ 40 Menit",
                level = "SMP Kelas 7",
                rating = 4.7
            )
        }

        binding.btnMenuHistory.setOnClickListener {
            bukaDetailPelajaran(
                subject = "Sejarah",
                modul = "Modul 2 dari 6",
                title = "Sejarah: Perjuangan Kemerdekaan Indonesia",
                desc = "Memahami peristiwa penting menjelang proklamasi kemerdekaan Republik Indonesia.",
                duration = "⏱ 35 Menit",
                level = "SMP Kelas 7",
                rating = 4.8
            )
        }

        binding.btnMenuCode.setOnClickListener {
            bukaDetailPelajaran(
                subject = "Koding",
                modul = "Modul 1 dari 10",
                title = "Koding: Pengenalan Logika & Algoritma",
                desc = "Dasar berpikir komputasional, percabangan logika, dan perulangan.",
                duration = "⏱ 45 Menit",
                level = "SMP Kelas 7",
                rating = 5.0
            )
        }
    }

    private fun bukaDetailPelajaran(
        subject: String,
        modul: String,
        title: String,
        desc: String,
        duration: String,
        level: String,
        rating: Double
    ) {
        Toast.makeText(requireContext(), "Membuka Menu: $subject", Toast.LENGTH_SHORT).show()
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
