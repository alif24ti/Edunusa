package com.example.edunusa

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edunusa.databinding.ActivityKuisBinding

class KuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKuisBinding
    private var selectedOption: String = "C"
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengaktifkan Edge-to-Edge
        enableEdgeToEdge()

        // Menerapkan ViewBinding secara penuh tanpa findViewById
        binding = ActivityKuisBinding.inflate(layoutInflater)
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

        // Inisialisasi tampilan pilihan ganda awal
        selectOption("C")

        // Listener untuk setiap pilihan ganda
        binding.optionA.setOnClickListener { selectOption("A") }
        binding.optionB.setOnClickListener { selectOption("B") }
        binding.optionC.setOnClickListener { selectOption("C") }
        binding.optionD.setOnClickListener { selectOption("D") }

        // Tombol Bookmark
        binding.btnBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                binding.btnBookmark.setColorFilter(ContextCompat.getColor(this, R.color.brand_blue))
                Toast.makeText(this, "Soal disimpan ke Bookmark", Toast.LENGTH_SHORT).show()
            } else {
                binding.btnBookmark.setColorFilter(ContextCompat.getColor(this, R.color.text_secondary))
                Toast.makeText(this, "Soal dihapus dari Bookmark", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Lanjut (Evaluasi Kuis)
        binding.btnNextQuestion.setOnClickListener {
            if (selectedOption == "C") {
                Toast.makeText(this, "Jawaban Benar! (3/4). Poin +100!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Jawaban kamu belum tepat. Kunci jawaban: C (3/4)", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectOption(option: String) {
        selectedOption = option

        // Reset semua opsi ke keadaan normal (unselected)
        resetOptions()

        // Terapkan style aktif ke opsi yang dipilih
        when (option) {
            "A" -> {
                binding.optionA.setBackgroundResource(R.drawable.bg_quiz_option_selected)
                binding.tvOptionTextA.setTextColor(ContextCompat.getColor(this, R.color.brand_blue))
                binding.tvLabelA.setBackgroundResource(R.drawable.bg_button_brand)
                binding.tvLabelA.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.ivCheckA.visibility = View.VISIBLE
            }
            "B" -> {
                binding.optionB.setBackgroundResource(R.drawable.bg_quiz_option_selected)
                binding.tvOptionTextB.setTextColor(ContextCompat.getColor(this, R.color.brand_blue))
                binding.tvLabelB.setBackgroundResource(R.drawable.bg_button_brand)
                binding.tvLabelB.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.ivCheckB.visibility = View.VISIBLE
            }
            "C" -> {
                binding.optionC.setBackgroundResource(R.drawable.bg_quiz_option_selected)
                binding.tvOptionTextC.setTextColor(ContextCompat.getColor(this, R.color.brand_blue))
                binding.tvLabelC.setBackgroundResource(R.drawable.bg_button_brand)
                binding.tvLabelC.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.ivCheckC.visibility = View.VISIBLE
            }
            "D" -> {
                binding.optionD.setBackgroundResource(R.drawable.bg_quiz_option_selected)
                binding.tvOptionTextD.setTextColor(ContextCompat.getColor(this, R.color.brand_blue))
                binding.tvLabelD.setBackgroundResource(R.drawable.bg_button_brand)
                binding.tvLabelD.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.ivCheckD.visibility = View.VISIBLE
            }
        }
    }

    private fun resetOptions() {
        val unselectedBg = R.drawable.bg_quiz_option_unselected
        val navyColor = ContextCompat.getColor(this, R.color.primary_navy)
        val blueColor = ContextCompat.getColor(this, R.color.brand_blue)
        val labelBg = R.drawable.bg_icon_squircle_blue

        binding.optionA.setBackgroundResource(unselectedBg)
        binding.tvOptionTextA.setTextColor(navyColor)
        binding.tvLabelA.setBackgroundResource(labelBg)
        binding.tvLabelA.setTextColor(blueColor)
        binding.ivCheckA.visibility = View.GONE

        binding.optionB.setBackgroundResource(unselectedBg)
        binding.tvOptionTextB.setTextColor(navyColor)
        binding.tvLabelB.setBackgroundResource(labelBg)
        binding.tvLabelB.setTextColor(blueColor)
        binding.ivCheckB.visibility = View.GONE

        binding.optionC.setBackgroundResource(unselectedBg)
        binding.tvOptionTextC.setTextColor(navyColor)
        binding.tvLabelC.setBackgroundResource(labelBg)
        binding.tvLabelC.setTextColor(blueColor)
        binding.ivCheckC.visibility = View.GONE

        binding.optionD.setBackgroundResource(unselectedBg)
        binding.tvOptionTextD.setTextColor(navyColor)
        binding.tvLabelD.setBackgroundResource(labelBg)
        binding.tvLabelD.setTextColor(blueColor)
        binding.ivCheckD.visibility = View.GONE
    }
}
