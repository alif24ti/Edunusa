package com.example.edunusa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edunusa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", "Siswa") ?: "Siswa"
        binding.tvGreetingName.text = "Halo, $savedUsername!"


        binding.btnLogout.setOnClickListener {
            performLogout()
        }


        binding.ivNotification.setOnClickListener {
            Toast.makeText(this, "Tidak ada notifikasi baru", Toast.LENGTH_SHORT).show()
        }


        binding.ivAvatar.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }

        // Event listener tombol Lanjutkan Belajar -> Berpindah ke DetailMateriActivity
        binding.btnContinueLearning.setOnClickListener {
            val intent = Intent(this, DetailMateriActivity::class.java)
            startActivity(intent)
        }

        binding.cardContinueLearning.setOnClickListener {
            val intent = Intent(this, DetailMateriActivity::class.java)
            startActivity(intent)
        }

        // Event listener tombol Lihat Semua Mata Pelajaran
        binding.tvSeeAll.setOnClickListener {
            Toast.makeText(this, "Menampilkan semua mata pelajaran", Toast.LENGTH_SHORT).show()
        }

        // Event listener Card Mata Pelajaran (Matematika -> Berpindah ke DetailMateriActivity)
        binding.cardMatematika.setOnClickListener {
            val intent = Intent(this, DetailMateriActivity::class.java)
            startActivity(intent)
        }

        binding.cardBahasaIndonesia.setOnClickListener {
            Toast.makeText(this, "Mata Pelajaran: Bahasa Indonesia dipilih", Toast.LENGTH_SHORT).show()
        }

        binding.cardIpa.setOnClickListener {
            Toast.makeText(this, "Mata Pelajaran: IPA (Ilmu Pengetahuan Alam) dipilih", Toast.LENGTH_SHORT).show()
        }

        // Event listener Bottom Navigation Bar
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_beranda -> {
                    Toast.makeText(this, "Halaman Beranda", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_kelas -> {
                    Toast.makeText(this, "Halaman Kelas (Daftar Pelajaran)", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_progres -> {
                    Toast.makeText(this, "Halaman Progres Belajar", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_profil -> {
                    val intent = Intent(this, ProfilActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
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

        // Arahkan kembali ke LoginActivity dan tutup MainActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}