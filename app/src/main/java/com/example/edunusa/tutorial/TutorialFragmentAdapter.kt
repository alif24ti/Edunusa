package com.example.edunusa.tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TutorialFragmentAdapter(
    activity: androidx.fragment.app.FragmentActivity,
    private val fragments: List<androidx.fragment.app.Fragment>
) : androidx.viewpager2.adapter.FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): androidx.fragment.app.Fragment = fragments[position]
}
