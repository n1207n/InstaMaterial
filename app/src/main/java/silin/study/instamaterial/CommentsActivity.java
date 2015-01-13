package silin.study.instamaterial;

import android.animation.Animator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CommentsActivity extends ActionBarActivity {
    @InjectView(R.id.comments_toolbar) Toolbar mCommentToolBar;
    @InjectView(R.id.content_layout) LinearLayout mCommentContentLayout;
    @InjectView(R.id.add_comment_widget) LinearLayout mAddCommentWidget;
    @InjectView(R.id.comments_rv) RecyclerView mCommentsRecyclerView;

    private CommentsAdapter mCommentsAdapter;

    public static final String ARG_DRAWING_START_LOCATION = "DRAWING_START_LOCATION";

    private int drawingStartLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ButterKnife.inject(this);

        setSupportActionBar(mCommentToolBar);
        mCommentToolBar.setNavigationIcon(R.drawable.ic_menu_white);

        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);

        if (savedInstanceState == null) {
            mCommentContentLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mCommentContentLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();

                    return true;
                }
            });
        }

        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentsAdapter = new CommentsAdapter(this);
        mCommentsRecyclerView.setAdapter(mCommentsAdapter);

        mCommentsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mCommentsAdapter.setAnimationsLocked(true);
                }
            }
        });
    }

    private void startIntroAnimation() {
        mCommentContentLayout.setScaleY(0.1f);
        mCommentContentLayout.setPivotY(drawingStartLocation);
        mAddCommentWidget.setTranslationY(100);

        mCommentContentLayout.animate().scaleY(1).setDuration(200).setInterpolator(new AccelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animateContent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void animateContent() {
        mCommentsAdapter.updateItems();

        mAddCommentWidget.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(200).start();
    }

    @OnClick(R.id.sendComment_btn)
    public void onSendCommentClick() {
        mCommentsAdapter.addItem();
        mCommentsAdapter.setAnimationsLocked(false);
        mCommentsAdapter.setDelayEnterAnimation(false);
        mCommentsRecyclerView.smoothScrollBy(0, mCommentsRecyclerView.getChildAt(0).getHeight() & mCommentsAdapter.getItemCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
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

    @Override
    public void onBackPressed() {
        mCommentContentLayout.animate()
                             .translationY(new Utils(this).getScreenSize().y)
                             .setDuration(200)
                             .setListener(new Animator.AnimatorListener() {
                                 @Override
                                 public void onAnimationStart(Animator animation) {

                                 }

                                 @Override
                                 public void onAnimationEnd(Animator animation) {
                                     CommentsActivity.super.onBackPressed();
                                     overridePendingTransition(0, 0);
                                 }

                                 @Override
                                 public void onAnimationCancel(Animator animation) {

                                 }

                                 @Override
                                 public void onAnimationRepeat(Animator animation) {

                                 }
                             }).start();
    }
}
