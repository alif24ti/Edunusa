package com.example.edunusa

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edunusa.databinding.ActivityKuisBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class KuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKuisBinding
    private var selectedOption: String = "C" // Sesuai desain Figma Screen 4, C dipilih secara default
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityKuisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.rootKuis) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            title = "Kuis Pecahan"
        }


        selectOption("C")


        binding.optionA.setOnClickListener { selectOption("A") }
        binding.optionB.setOnClickListener { selectOption("B") }
        binding.optionC.setOnClickListener { selectOption("C") }
        binding.optionD.setOnClickListener { selectOption("D") }


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


        binding.btnNextQuestion.setOnClickListener {
            evaluasiJawaban()
        }
    }

    private fun evaluasiJawaban() {
        if (selectedOption == "C") {
            MaterialAlertDialogBuilder(this)
                .setTitle("Jawaban Benar! 🎉")
                .setMessage("Hebat! 1/2 + 1/4 = 2/4 + 1/4 = 3/4. Kamu mendapatkan +100 Poin Belajar!")
                .setPositiveButton("Selesai & Kembali") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .setCancelable(false)
                .show()
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle("Jawaban Belum Tepat")
                .setMessage("Jawaban yang kamu pilih belum tepat. Petunjuk: Samakan penyebut pecahan menjadi 4, sehingga 2/4 + 1/4 = 3/4 (Pilihan C).")
                .setPositiveButton("Coba Lagi") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun selectOption(option: String) {
        selectedOption = option
        resetOptions()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.kuis_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_draft -> {
                Toast.makeText(this, "Jawaban kuis sementara disimpan sebagai draf", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_guide -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Panduan Kuis Pecahan")
                    .setMessage("1. Waktu pengerjaan maksimal 10 menit.\n2. Pilih salah satu jawaban A, B, C, atau D.\n3. Tekan Lanjut untuk menyelesaikan dan melihat skor.")
                    .setPositiveButton("Mengerti") { dialog, _ -> dialog.dismiss() }
                    .show()
                true
            }
            R.id.action_reset -> {
                selectOption("A")
                Toast.makeText(this, "Pilihan jawaban direset ke Opsi A", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
