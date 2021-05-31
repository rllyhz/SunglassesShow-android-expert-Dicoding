package id.rllyhz.sunglassesshow.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var useCase: SunGlassesShowUseCase // this is for dynamic module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = mainNavHostFragment.findNavController()

        setupMainBottomNavigationView()
    }

    private fun setupMainBottomNavigationView() {
        with(binding) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.moviesFragment,
                    R.id.tvShowsFragment,
                    R.id.favoritesFragment
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            mainBottomNavigation.setupWithNavController(navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}