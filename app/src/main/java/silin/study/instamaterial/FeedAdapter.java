package silin.study.instamaterial;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context mContext;
    private int lastAnimatedPosition = -1,
        itemsCount = 4;

    public FeedAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_feed, parent, false);

        return new ViewHolder(view);
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;

            Display display = view.getDisplay();
            Point point = new Point();
            display.getSize(point);

            view.setTranslationY(point.y);
            view.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3.f)).setDuration(700).start();
        }
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        if (position % 2 == 0) {
            viewHolder.feedBottomImageView.setImageResource(R.drawable.img_feed_bottom_2);
            viewHolder.feedImageView.setImageResource(R.drawable.img_feed_center_2);
        } else {
            viewHolder.feedBottomImageView.setImageResource(R.drawable.img_feed_bottom_1);
            viewHolder.feedImageView.setImageResource(R.drawable.img_feed_center_1);
        }
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.feed_iv)
        ImageView feedImageView;

        @InjectView(R.id.feedBottom_iv)
        ImageView feedBottomImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
