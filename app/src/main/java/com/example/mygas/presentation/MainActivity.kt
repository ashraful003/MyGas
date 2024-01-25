package com.example.mygas.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.ActivityMainBinding
import com.gas.mygasbd.util.MGActivityUtil
import com.gas.mygasbd.util.SharePreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MGActivityUtil.ActivityListener {
    @Inject
    lateinit var sharePrefs: SharePreferencesUtil
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        val authUser: Boolean = try {
            !sharePrefs.getAuthToken().isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        with(navHostFragment) {
            val infalter = findNavController().navInflater
            if (authUser) {
                findNavController().graph = (infalter.inflate(R.navigation.dashboard_navigation))
            } else {
                findNavController().graph = (infalter.inflate(R.navigation.login_navigation))
            }
        }
        navController = navHostFragment.navController
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> navHostFragment.findNavController().navigate(R.id.homeFragment)
                R.id.nearBy -> {}
                R.id.order -> {}
                R.id.favorites -> {}
                R.id.profile -> {}
            }

            true
        }
    }

    override fun hideBottomNavigation(hide: Boolean) {
        if (hide) {
            binding.bottomNavigationView.visibility = View.GONE
        } else {
            binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }

    override fun setFullScreenLoading(show: Boolean) {
        if (show) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }
}