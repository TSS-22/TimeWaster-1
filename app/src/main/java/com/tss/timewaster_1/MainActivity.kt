package com.tss.timewaster_1


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tss.timewaster_1.data.AppState
import com.tss.timewaster_1.data.SettingsState
import com.tss.timewaster_1.domain.Preferences
import com.tss.timewaster_1.domain.SettingsViewModel
import com.tss.timewaster_1.domain.TimeWaster1ViewModel
import com.tss.timewaster_1.presentation.SettingsScreen
import com.tss.timewaster_1.presentation.StartScreen
import com.tss.timewaster_1.presentation.component.TimeWasterAppBar
import com.tss.timewaster_1.presentation.navigation.AppScreen
import com.tss.timewaster_1.presentation.theme.TimeWaster_1Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    private val viewModel: TimeWaster1ViewModel by viewModels()
    private val viewModelSetting: SettingsViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        // Pass screen constraints to the ViewModel
        viewModel.setScreenConstraints(screenWidth, screenHeight)

        setContent {
            TimeWaster_1Theme {
                val state: AppState by viewModel.state.collectAsState()

                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = AppScreen.valueOf(
                    backStackEntry?.destination?.route ?: AppScreen.Start.name
                )
                navController.enableOnBackPressed(false)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TimeWasterAppBar(
                                score = state.currentScore,
                                onClickSetting = { navController.navigate(AppScreen.Settings.name) },
                                currentScreen = currentScreen,
                                navigateUp = { navController.navigateUp() },
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            NavHost(
                                navController = navController,
                                startDestination = AppScreen.Start.name,
                                modifier = Modifier.padding(it)
                            ) {
                                composable(route = AppScreen.Start.name) {
                                    StartScreen(
                                        state = state,
                                        preferences = preferences,
                                        viewModel = viewModel
                                    )
                                }
                                composable(route = AppScreen.Settings.name) {
                                    SettingsScreen(
                                        preferences = preferences,
                                        viewModel = viewModelSetting
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
