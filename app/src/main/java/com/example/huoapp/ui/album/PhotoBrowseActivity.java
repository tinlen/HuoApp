package com.example.huoapp.ui.album;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;
import com.example.huoapp.widget.BaseAnimCloseViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/9.
 */

public class PhotoBrowseActivity extends BaseActivity {

    private int firstDisplayImageIndex = 0;
    private boolean newPageSelected = false;
    private ImageView mCurImage;
    private BaseAnimCloseViewPager imageViewPager;
    private List<String> pictureList;
    @BindView(R.id.tv_count)
    TextView tvCount;

    PagerAdapter adapter;

    boolean canDrag =false;

    public static void startWithElement(Activity context, List<String> urls,
                                        int firstIndex, View view) {
        Intent intent = new Intent(context, PhotoBrowseActivity.class);
        intent.putStringArrayListExtra("urls", (ArrayList<String>) urls);
        intent.putExtra("index", firstIndex);
        ActivityOptionsCompat compat = null;
        if (view == null) {
            compat = ActivityOptionsCompat.makeSceneTransitionAnimation(context);
        } else {
            compat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view,
                    "tansition_view");
        }
        ActivityCompat.startActivity(context, intent, compat.toBundle());
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_photo_browse;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initView();
    }

    public void initView() {
        pictureList = getIntent().getStringArrayListExtra("urls");
        firstDisplayImageIndex = Math.min(getIntent().getIntExtra("index", firstDisplayImageIndex), pictureList.size());

        imageViewPager = (BaseAnimCloseViewPager) findViewById(R.id.viewpager);
        setViewPagerAdapter();

        setEnterSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                ViewGroup layout = (ViewGroup) imageViewPager.findViewWithTag(imageViewPager.getCurrentItem());
                if (layout == null) {
                    return;
                }
                View sharedView = layout.findViewById(R.id.image_view);
                sharedElements.clear();
                sharedElements.put("tansition_view", sharedView);
            }
        });

        tvCount.setText(String.format("%s/%s",(firstDisplayImageIndex+1),pictureList.size()));
    }

    private void setViewPagerAdapter() {
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pictureList == null ? 0 : pictureList.size();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View layout = (View) object;
                container.removeView(layout);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View layout;
                layout = LayoutInflater.from(PhotoBrowseActivity.this).inflate(R.layout.layout_browse, null);
//                layout.setOnClickListener(onClickListener);
                container.addView(layout);
                layout.setTag(position);

                if (position == firstDisplayImageIndex) {
                    onViewPagerSelected(position);
                }

                return layout;

            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };

        imageViewPager.setAdapter(adapter);
        imageViewPager.setOffscreenPageLimit(3);
        imageViewPager.setCurrentItem(firstDisplayImageIndex);
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0f && newPageSelected) {
                    newPageSelected = false;
                    onViewPagerSelected(position);
                }

                tvCount.setText(String.format("%s/%s",(position+1),pictureList.size()));
            }

            @Override
            public void onPageSelected(int position) {
                newPageSelected = true;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageViewPager.setiAnimClose(new BaseAnimCloseViewPager.IAnimClose() {
            @Override
            public boolean canDrag() {
                return canDrag;
            }

            @Override
            public void onPictureClick() {
                finishAfterTransition();
            }

            @Override
            public void onPictureRelease(View view) {
                finishAfterTransition();
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishAfterTransition();
        }
    };


    private void onViewPagerSelected(int position) {
        updateCurrentImageView(position);
        setImageView(pictureList.get(position));
    }

    /**
     * 设置图片
     *
     * @param path
     */
    private void setImageView(final String path) {
        if (mCurImage.getDrawable() != null)//判断是否已经加载了图片，避免闪动
            return;
        if (TextUtils.isEmpty(path)) {
            mCurImage.setBackgroundColor(Color.GRAY);
            return;
        }
        canDrag = false;
        Glide.with(this).load(path).into(mCurImage);
    }

    // 初始化每个view的image
    protected void updateCurrentImageView(final int position) {
        View currentLayout = imageViewPager.findViewWithTag(position);
        if (currentLayout == null) {
            ViewCompat.postOnAnimation(imageViewPager, new Runnable() {

                @Override
                public void run() {
                    updateCurrentImageView(position);
                }
            });
            return;
        }
        mCurImage = (ImageView) currentLayout.findViewById(R.id.image_view);
        imageViewPager.setCurrentShowView(mCurImage);
    }


    @Override
    public void finishAfterTransition() {
        Intent intent = new Intent();
        intent.putExtra("index", imageViewPager.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.finishAfterTransition();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }
}
