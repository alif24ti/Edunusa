package com.example.edunusa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edunusa.databinding.ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengaktifkan Edge-to-Edge
        enableEdgeToEdge()

        // Menerapkan ViewBinding secara penuh tanpa findViewById
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Penanganan WindowInsets agar Toolbar tidak tertutup status bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Tombol Kembali (Back) di Toolbar
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Ambil data sesi username dinamis dari SharedPreferences "UserSession"
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", "Siswa") ?: "Siswa"

        // Pasang data ke komponen UI
        binding.tvProfileName.text = savedUsername
        binding.tvUsernameValue.text = savedUsername
        val emailFormat = "${savedUsername.lowercase().replace("\\s+".toRegex(), "")}@edunusa.sch.id"
        binding.tvEmailValue.text = emailFormat
        binding.tvLevelValue.text = "Kelas 7 SMP"
        binding.tvTargetValue.text = "30 Menit / Hari"

        // Event listener tombol Logout di halaman profil
        binding.btnLogoutProfile.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout() {
        // Ubah nilai SharedPreferences isLogin = false
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLogin", false)
        editor.apply()

        // Tampilkan pesan konfirmasi Toast
        Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()

        // Arahkan kembali ke LoginActivity dan bersihkan tumpukan activity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }
}
