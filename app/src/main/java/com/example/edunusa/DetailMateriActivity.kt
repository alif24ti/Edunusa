package com.example.edunusa

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.edunusa.databinding.ActivityDetailMateriBinding

class DetailMateriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMateriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.rootDetailMateri) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            title = "Detail Materi"
        }


        val subject = intent.getStringExtra("EXTRA_SUBJECT") ?: "Matematika"
        val modul = intent.getStringExtra("EXTRA_MODUL") ?: "Modul 03 dari 12"
        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Pecahan Sederhana"
        val desc = intent.getStringExtra("EXTRA_DESC") ?: "Pelajari konsep pecahan dasar, menyamakan penyebut, hingga operasi hitung pecahan."
        val duration = intent.getStringExtra("EXTRA_DURATION") ?: "⏱ 45 Menit"
        val level = intent.getStringExtra("EXTRA_LEVEL") ?: "SMP Kelas 7"
        val rating = intent.getDoubleExtra("EXTRA_RATING", 4.9)
        val imageUrl = intent.getStringExtra("EXTRA_IMAGE_URL") ?: ""


        binding.badgeSubject.text = subject
        binding.badgeModuleNumber.text = modul
        binding.tvMaterialTitle.text = title
        binding.tvMaterialSubtitle.text = desc
        binding.tvDurationDetail.text = duration
        binding.tvLevelDetail.text = "🏫 $level"
        binding.tvRatingDetail.text = "★ $rating"


        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo_edunusa)
                .into(binding.imgDetailBanner)
        } else {
            Glide.with(this)
                .load("https://images.unsplash.com/photo-1509228468518-180dd4864904?w=600&q=80")
                .placeholder(R.drawable.logo_edunusa)
                .into(binding.imgDetailBanner)
        }


        binding.layoutModelHalf.setOnClickListener {
            Toast.makeText(this, "Model Pecahan 1/2: 1 bagian utuh dibagi rata menjadi 2", Toast.LENGTH_SHORT).show()
        }

        binding.layoutModelQuarter.setOnClickListener {
            Toast.makeText(this, "Model Pecahan 1/4: 1 bagian utuh dibagi rata menjadi 4", Toast.LENGTH_SHORT).show()
        }


        binding.btnPlayVideo.setOnClickListener {
            Toast.makeText(this, "Memutar video pembelajaran '$title'...", Toast.LENGTH_SHORT).show()
        }


        binding.btnSapa.setOnClickListener {
            Toast.makeText(this, "Kamu menyapa 42 siswa di ruang belajar!", Toast.LENGTH_SHORT).show()
        }


        binding.btnStartQuiz.setOnClickListener {
            val quizIntent = Intent(this, KuisActivity::class.java).apply {
                putExtra("EXTRA_SUBJECT", subject)
                putExtra("EXTRA_TITLE", title)
            }
            startActivity(quizIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_bookmark -> {
                Toast.makeText(this, "Materi disimpan ke Bookmark", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_share -> {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Ayo belajar materi '${binding.tvMaterialTitle.text}' di aplikasi EduNusa!")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(sendIntent, "Bagikan Materi"))
                true
            }
            R.id.action_report -> {
                Toast.makeText(this, "Laporan materi telah dikirimkan ke tim EduNusa", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
