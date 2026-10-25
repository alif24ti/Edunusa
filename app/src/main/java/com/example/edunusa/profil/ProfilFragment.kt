package com.example.edunusa.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.edunusa.BaseActivity
import com.example.edunusa.DetailMateriActivity
import com.example.edunusa.LoginActivity
import com.example.edunusa.R
import com.example.edunusa.databinding.FragmentProfilBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
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


        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Siswa") ?: "Siswa"


        binding.tvAvatar.text = if (username.length >= 2) username.take(2).uppercase() else username.uppercase()


        binding.tvProfileName.text = username
        binding.tvUsernameValue.text = username
        val cleanEmail = "${username.lowercase().replace("\\s+".toRegex(), "")}@edunusa.sch.id"
        binding.tvEmailValue.text = cleanEmail
        binding.tvLevelValue.text = "SMP EduNusa Digital • Kelas 7"
        binding.tvTargetValue.text = "30 Menit / Hari"

        binding.cardRiwayatMateri.setOnClickListener {
            val intent = Intent(requireContext(), DetailMateriActivity::class.java).apply {
                putExtra("EXTRA_SUBJECT", "Matematika")
                putExtra("EXTRA_MODUL", "Modul 3")
                putExtra("EXTRA_TITLE", "Matematika: Pecahan Sederhana")
                putExtra("EXTRA_DESC", "Pelajari konsep pecahan dasar, menyamakan penyebut, hingga operasi hitung pecahan.")
                putExtra("EXTRA_DURATION", "⏱ 45 Menit")
                putExtra("EXTRA_LEVEL", "SMP Kelas 7")
                putExtra("EXTRA_RATING", 4.9)
            }
            startActivity(intent)
        }


        binding.btnLogoutProfile.setOnClickListener {
            showLogoutDialog()
        }
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
