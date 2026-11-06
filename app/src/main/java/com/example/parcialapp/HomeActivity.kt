package com.example.parcialapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val toolbar = findViewById<MaterialToolbar>(
            R.id.toolbar
        )

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { opcion ->

            drawerLayout.closeDrawer(GravityCompat.START)

            when (opcion.itemId) {

                R.id.nav_inicio -> {
                    mostrarFragment(HomeFragment())
                    navigationView.setCheckedItem(R.id.nav_inicio)
                    true
                }

                R.id.nav_nosotros -> {
                    mostrarFragment(NosotrosFragment())
                    navigationView.setCheckedItem(R.id.nav_nosotros)
                    true
                }

                R.id.nav_contactos -> {
                    mostrarFragment(ContactosFragment())
                    navigationView.setCheckedItem(R.id.nav_contactos)
                    true
                }

                R.id.nav_login -> {
                    val intent = Intent(
                        this,
                        LoginActivity::class.java
                    )

                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        if (savedInstanceState == null) {
            mostrarFragment(HomeFragment())
            navigationView.setCheckedItem(R.id.nav_inicio)
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    when {
                        drawerLayout.isDrawerOpen(
                            GravityCompat.START
                        ) -> {
                            drawerLayout.closeDrawer(
                                GravityCompat.START
                            )
                        }

                        supportFragmentManager
                            .backStackEntryCount > 0 -> {

                            supportFragmentManager.popBackStack()
                        }

                        else -> {
                            isEnabled = false
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            }
        )
    }

    private fun mostrarFragment(fragment: Fragment) {

        supportFragmentManager.popBackStackImmediate(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.contenedorFragments,
                fragment
            )
            .commit()
    }
}
