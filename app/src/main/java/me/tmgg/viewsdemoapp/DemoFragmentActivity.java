package me.tmgg.viewsdemoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import me.tmgg.viewsdemoapp.fragments.ClippingBasicFragment;
import me.tmgg.viewsdemoapp.fragments.ConstraintlayoutFragment;
import me.tmgg.viewsdemoapp.fragments.ElevationDragFragment;
import me.tmgg.viewsdemoapp.fragments.ShopCartFragment;

public class DemoFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_fragment);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_clip_view:
                transaction.replace(R.id.framelayout, new ClippingBasicFragment());
                break;
            case R.id.menu_elevation_view:
                ElevationDragFragment fragment = new ElevationDragFragment();
                transaction.replace(R.id.framelayout, fragment);
                break;
            case R.id.menu_constraintlayout:
                transaction.replace(R.id.framelayout, new ConstraintlayoutFragment());
                break;
            case R.id.menu_shopcart:
                transaction.replace(R.id.framelayout, new ShopCartFragment());
                break;
            default:
                break;
        }
        transaction.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
