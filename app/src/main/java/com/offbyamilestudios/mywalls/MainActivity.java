package com.offbyamilestudios.mywalls;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private WallpaperAdapter mAdapter;
	private List<Wallpaper> wallpaperList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		initCollapsingToolbar();

		mRecyclerView = findViewById(R.id.recycler_view);
		wallpaperList = new ArrayList<>();

		mAdapter = new WallpaperAdapter(this, wallpaperList);

		RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mAdapter);

		prepareWalls();

		try {
			Glide.with(this).load(R.drawable.wall_six).into((ImageView) findViewById(R.id.backdrop));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initCollapsingToolbar() {
		final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		collapsingToolbarLayout.setTitle(" ");
		AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
		appBarLayout.setExpanded(true);

		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			boolean isShow = false;
			int scrollRange = -1;

			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (scrollRange == -1) {
					scrollRange = appBarLayout.getTotalScrollRange();
				}
				if (scrollRange + verticalOffset == 0) {
					collapsingToolbarLayout.setTitle("Card View");
					isShow = true;
				} else if (isShow) {
					collapsingToolbarLayout.setTitle(" ");
					isShow = false;
				}
			}
		});
	}

	private void prepareWalls() {
		int[] walls = new int[] {
				R.drawable.wall_one,
				R.drawable.wall_two,
				R.drawable.wall_three,
				R.drawable.wall_four,
				R.drawable.wall_five,
				R.drawable.wall_six
		};

		Wallpaper a = new Wallpaper("Wallpaper one", 7, walls[0]);
		wallpaperList.add(a);

		a = new Wallpaper("Wallpaper two", 2, walls[1]);
		wallpaperList.add(a);

		a = new Wallpaper("Wallpaper three", 7, walls[2]);
		wallpaperList.add(a);

		a = new Wallpaper("Wallpaper four", 7, walls[3]);
		wallpaperList.add(a);

		a = new Wallpaper("Wallpaper five", 5, walls[4]);
		wallpaperList.add(a);

		a = new Wallpaper("Wallpaper six", 7, walls[5]);
		wallpaperList.add(a);

		mAdapter.notifyDataSetChanged();

	}

	// TODO(4): add item spacing decoration for GridItemSpacingDecoration
	// TODO(5): add item Offsets for CardView items
	// TODO(6): setup data model entitled Wallpaper
	// TODO(7): setup and implement the adapter
}
