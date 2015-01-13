package silin.study.instamaterial;

import android.animation.Animator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.main_toolbar) Toolbar mMainToolbar;
    @InjectView(R.id.feed_rv) RecyclerView mRecyclerView;
    @InjectView(R.id.create_btn) ImageButton mCreateImageButton;
    @InjectView(R.id.logo_iv) ImageView mLogoImageView;

    private MenuItem mInboxMenuItem;

    private FeedAdapter mFeedAdapter;

    private Utils mUtils;

    private boolean pendingIntroAnimation = false;

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        mUtils = new Utils(this);

        if (savedInstanceState == null) pendingIntroAnimation = true;

        setSupportActionBar(mMainToolbar);
        mMainToolbar.setNavigationIcon(R.drawable.ic_menu_white);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFeedAdapter = new FeedAdapter(this);
        mRecyclerView.setAdapter(mFeedAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mInboxMenuItem = menu.findItem(R.id.action_inbox);
        mInboxMenuItem.setActionView(R.layout.menu_item_view);

        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_inbox) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startIntroAnimation() {
        mCreateImageButton.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.fab_btn_size));

        int actionbarSize = getSupportActionBar().getHeight();

        mMainToolbar.setTranslationY(-actionbarSize);
        mLogoImageView.setTranslationY(-actionbarSize);
        mInboxMenuItem.getActionView().setTranslationY(-actionbarSize);
        mRecyclerView.setTranslationY(+mRecyclerView.getHeight());

        mMainToolbar.animate().translationY(0).setDuration(ANIM_DURATION_TOOLBAR).setStartDelay(300);
        mLogoImageView.animate().translationY(0).setDuration(ANIM_DURATION_TOOLBAR).setStartDelay(400);
        mInboxMenuItem.getActionView().animate().translationY(0).setDuration(ANIM_DURATION_TOOLBAR).setStartDelay(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startContentAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void startContentAnimation() {
        mCreateImageButton.animate().translationY(0).setInterpolator(new OvershootInterpolator(1.f)).setStartDelay(300).setDuration(ANIM_DURATION_FAB).start();

        mFeedAdapter.updateItems();
        mRecyclerView.animate().translationY(0).setDuration(ANIM_DURATION_TOOLBAR).setStartDelay(100);
    }
}
