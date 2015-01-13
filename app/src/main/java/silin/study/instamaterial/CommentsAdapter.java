package silin.study.instamaterial;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private Context mContext;

    private int mLastAnimatedPosition = -1,
            mItemsCount = 4,
            mAvatarSize;

    private boolean mAnimationsLockednimationsLocked = false;
    private boolean mDelayEnterAnimation = true;


    public CommentsAdapter(Context context) {
        mContext = context;
        mAvatarSize = mContext.getResources().getDimensionPixelSize(R.dimen.fab_btn_size);
    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_comments, parent, false);

        return new ViewHolder(view);
    }

    private void runEnterAnimation(View view, int position) {
        if (mAnimationsLockednimationsLocked) return;

        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;

            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                .translationY(0).alpha(1.f)
                .setStartDelay(mDelayEnterAnimation ? 20 * (position) : 0)
                .setInterpolator(new DecelerateInterpolator(2.f))
                .setDuration(300)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mAnimationsLockednimationsLocked = true;
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

    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        ViewHolder viewHolder = (ViewHolder) holder;

        switch (position % 3) {
            case 0:
                viewHolder.commentTextView.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit.");
                break;
            case 1:
                viewHolder.commentTextView.setText("Cupcake ipsum dolor sit amet bear claw.");
                break;
            case 2:
                viewHolder.commentTextView.setText("Cupcake ipsum dolor sit. Amet gingerbread cupcake. Gummies ice cream dessert icing marzipan apple pie dessert sugar plum.");
                break;
        }

        Picasso.with(mContext)
                .load(R.drawable.ic_launcher)
                .centerCrop()
                .resize(mAvatarSize, mAvatarSize)
                .transform(new CircleTransform())
                .into(viewHolder.commentAvatarImageView);
    }

    @Override
    public int getItemCount() {
        return mItemsCount;
    }

    public void updateItems() {
        mItemsCount = 10;
        notifyDataSetChanged();
    }

    public void addItem() {
        mItemsCount++;
        notifyItemInserted(mItemsCount - 1);
    }

    public void setAnimationsLocked(boolean animationsLockednimationsLocked) {
        mAnimationsLockednimationsLocked = animationsLockednimationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        mDelayEnterAnimation = delayEnterAnimation;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.comment_avatar_iv)
        ImageView commentAvatarImageView;

        @InjectView(R.id.comment_tv)
        TextView commentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
