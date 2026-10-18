package com.example.edunusa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edunusa.databinding.ActivityDetailMateriBinding

class DetailMateriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMateriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengaktifkan Edge-to-Edge
        enableEdgeToEdge()

        // Menerapkan ViewBinding secara penuh tanpa findViewById
        binding = ActivityDetailMateriBinding.inflate(layoutInflater)
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

        // Interaksi Visualisasi Model Pecahan
        binding.layoutModelHalf.setOnClickListener {
            Toast.makeText(this, "Model Pecahan 1/2: 1 bagian utuh dibagi menjadi 2", Toast.LENGTH_SHORT).show()
        }

        binding.layoutModelQuarter.setOnClickListener {
            Toast.makeText(this, "Model Pecahan 1/4: 1 bagian utuh dibagi menjadi 4", Toast.LENGTH_SHORT).show()
        }

        // Interaksi Pemutar Video
        binding.btnPlayVideo.setOnClickListener {
            Toast.makeText(this, "Memutar video 'Konsep Dasar Pecahan'...", Toast.LENGTH_SHORT).show()
        }

        // Tombol Aksi: Mulai Belajar & Kerjakan Kuis -> Berpindah ke KuisActivity
        binding.btnStartQuiz.setOnClickListener {
            val intent = Intent(this, KuisActivity::class.java)
            startActivity(intent)
        }
    }
}
