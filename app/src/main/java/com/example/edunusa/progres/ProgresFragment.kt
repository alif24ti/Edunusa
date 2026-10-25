package com.example.edunusa.progres

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
import com.example.edunusa.LoginActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.FragmentProgresBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProgresFragment : Fragment() {

    private var _binding: FragmentProgresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgresBinding.inflate(inflater, container, false)
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


        binding.btnDownloadReport.setOnClickListener {
            Toast.makeText(requireContext(), "Mengunduh Rapor Belajar EduNusa (PDF)...", Toast.LENGTH_SHORT).show()
        }


        setupPencapaianListView()
    }

    private fun setupPencapaianListView() {
        val list = listOf(
            PencapaianModel(
                section = "Prestasi Kuis",
                title = "Pembalap Kuis",
                body = "Selesaikan kuis dengan nilai sempurna 100%.",
                badgeText = "Skor 100%",
                icon = R.drawable.ic_star,
                iconBg = R.drawable.bg_icon_squircle_blue,
                iconColor = R.color.brand_blue,
                isNew = true
            ),
            PencapaianModel(
                section = "Aktivitas Literasi",
                title = "Rajin Membaca",
                body = "Selesaikan 5 modul materi pembelajaran dalam 1 minggu.",
                badgeText = "Level 3",
                icon = R.drawable.ic_book,
                iconBg = R.drawable.bg_icon_squircle_blue,
                iconColor = R.color.brand_blue,
                isNew = false
            ),
            PencapaianModel(
                section = "Kebiasaan Belajar",
                title = "Konsistensi 5 Hari",
                body = "Buka modul dan belajar minimal 15 menit 5 hari berturut-turut.",
                badgeText = "5 Hari",
                icon = R.drawable.ic_timer,
                iconBg = R.drawable.bg_icon_squircle_green,
                iconColor = R.color.success_green,
                isNew = false
            ),
            PencapaianModel(
                section = "Matematika",
                title = "Pecahan Tuntas",
                body = "Berhasil menyelesaikan seluruh latihan sub-modul pecahan.",
                badgeText = "Selesai",
                icon = R.drawable.ic_check_circle,
                iconBg = R.drawable.bg_icon_squircle_green,
                iconColor = R.color.success_green,
                isNew = false
            )
        )

        val adapter = PencapaianAdapter(requireContext(), list)
        binding.listPencapaian.adapter = adapter
        setListViewHeightBasedOnChildren(binding.listPencapaian)
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
