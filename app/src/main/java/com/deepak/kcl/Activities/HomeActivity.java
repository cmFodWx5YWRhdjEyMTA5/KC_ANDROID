package com.deepak.kcl.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.deepak.kcl.Fragments.AccountFragment;
import com.deepak.kcl.Fragments.ExpenseFragment;
import com.deepak.kcl.Fragments.HomeFragment;
import com.deepak.kcl.R;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();
    ExpenseFragment expenseFragment = new ExpenseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        initializeView();
    }

    private void initializeView() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,homeFragment).commit();
                        return true;
                    case R.id.nav_expense:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,expenseFragment).commit();
                        return true;
                    case R.id.nav_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,accountFragment).commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_refresh)
        {
            this.finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }
}
