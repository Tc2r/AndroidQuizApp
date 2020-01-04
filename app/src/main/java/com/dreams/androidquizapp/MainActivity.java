package com.dreams.androidquizapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.dreams.androidquizapp.fragments.NewsFragment;
import com.dreams.androidquizapp.fragments.dummy.DummyContent;
import com.dreams.androidquizapp.tools.DragFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity
    implements OnFragmentInteractionListener, NewsFragment.OnListFragmentInteractionListener
{

  private AppBarConfiguration mAppBarConfiguration;
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DragFloatingActionButton fab = findViewById(R.id.fab);

    fab.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {

        navController.navigate(R.id.nav_quiz);
        Snackbar.make(view, "Starting New Quiz", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });
    DrawerLayout drawer = findViewById(R.id.drawer_layout);

    if (savedInstanceState != null)
    {
      drawer.openDrawer(GravityCompat.START);
    }
    NavigationView navigationView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_news, R.id.nav_quiz, R.id.nav_share, R.id.nav_send)
                               .setDrawerLayout(drawer).build();

    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);

  }

  @Override
  public boolean onSupportNavigateUp()
  {

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public void fragmentInitialized()
  {

  }

  @Override
  public void onListFragmentInteraction(DummyContent.DummyItem item)
  {

  }
}
