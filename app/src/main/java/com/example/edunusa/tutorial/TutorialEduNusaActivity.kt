package com.example.edunusa.tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.example.edunusa.databinding.ActivityTutorialEdunusaBinding

class TutorialEduNusaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialEdunusaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTutorialEdunusaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.tutorialRoot) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val tutorialFragments = listOf(
            Tutorial1Fragment(),
            Tutorial2Fragment(),
            Tutorial3Fragment()
        )

        val adapter = TutorialFragmentAdapter(this, tutorialFragments)
        binding.viewPagerTutorial.adapter = adapter

        binding.dotsIndicator.attachTo(binding.viewPagerTutorial)

        binding.viewPagerTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == tutorialFragments.size - 1) {
                    binding.btnNextTutorial.text = "Mulai Belajar Sekarang 🚀"
                } else {
                    binding.btnNextTutorial.text = "Lanjut  →"
                }
            }
        })

        binding.btnNextTutorial.setOnClickListener {
            val currentItem = binding.viewPagerTutorial.currentItem
            if (currentItem < tutorialFragments.size - 1) {
                binding.viewPagerTutorial.currentItem = currentItem + 1
            } else {
                finish()
            }
        }

        binding.btnSkipTutorial.setOnClickListener {
            finish()
        }
        binding.btnBackTutorial.setOnClickListener {
            finish()
        }
    }
}
