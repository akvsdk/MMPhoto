package com.ep.jyq.mmphoto.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ep.jyq.mmphoto.R;
import com.ep.jyq.mmphoto.bean.ImageInfo;
import com.ep.jyq.mmphoto.util.JsoupTool;
import com.ep.jyq.mmphoto.util.Validator;
import com.ep.jyq.mmphoto.util.ViewHolder;
import com.ep.jyq.mmphoto.view.ProgressBar_Modern;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 2015/8/27.
 */
public class PageSectionFragment extends Fragment {
    private ProgressBar_Modern progressBar_modern;
    BaseAttacher attacher;
    private String mCategoryId;
    private Context mContext;
    private ListView mListView;
    private ListvAdapet adapter;
    private List<ImageInfo> imgList;
    private List<ImageInfo> tempimgList;
    private int curLoadedPage = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final int REQUEST_FINISHED = 100;
    private static final String KEY_CONTENT = "PageSectionFragment:CategoryId";
    private static final String URL = "http://www.dbmeinv.com/dbgroup/show.htm?cid=";

    private final MyHandler mHandler = new MyHandler(this);

    public static Fragment newInstance(String tabId) {
        PageSectionFragment fragment = new PageSectionFragment();
        fragment.mCategoryId = tabId;
        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mCategoryId = savedInstanceState.getString(KEY_CONTENT);
        }

        if (imgList == null) {
            imgList = new ArrayList<ImageInfo>();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mListView = (ListView) view.findViewById(R.id.listv);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        //设置动画颜色
        progressBar_modern = (ProgressBar_Modern) view.findViewById(R.id.progressbar);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);


        adapter = new ListvAdapet();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        getImageData(1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getImageData(1);
                adapter.notifyDataSetChanged();
            }
        });
        attacher = Mugen.with(mListView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                progressBar_modern.setVisibility(View.VISIBLE);
                getImageData(curLoadedPage + 1);
                Log.e("Joy", "加载更多哦~！~！~！~");
                if (progressBar_modern.getVisibility() == View.VISIBLE) {
                    progressBar_modern.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean hasLoadedAllItems() {

                return false;
            }


        }).start();
        attacher.setLoadMoreOffset(2);

        //    getImageData(curLoadedPage + 1);  //加载的内容

        return view;
    }

    private void getImageData(int page) {
        if (Validator.isEffective(mCategoryId)) {
            final String pageUrl = URL + mCategoryId + "&pager_offset=" + page;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ImageInfo> imgList = JsoupTool.getInstance().getAllImages(pageUrl);
                    if (imgList != null) {
                        Message msg = new Message();
                        msg.what = REQUEST_FINISHED;
                        msg.obj = imgList;
                        mHandler.sendMessage(msg);

                    }
                }
            }).start();
        }

    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mCategoryId);
    }


    public class ListvAdapet extends BaseAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public Object getItem(int position) {
            return imgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            }

            TextView txt = ViewHolder.get(convertView, R.id.img_title);
            ImageView imgview = ViewHolder.get(convertView, R.id.img);
            final ImageInfo imginfo = imgList.get(position);
            String tv = imginfo.getImgTitle();
            final String img = imginfo.getImgUrl();
            if (Validator.isEffective(tv)) {
                txt.setText(tv);
            }
            if (Validator.isEffective(img)) {
                Glide.with(mContext).load(img).into(imgview);
            }
            imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "" + img, Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                        public void onClick(View view) {
                          /*  Intent in = new Intent(getActivity(),ImageActivity.class);
                            in.putExtra("imgurl",img);
                            startActivity(in);*/
                        }
                    }).show();

                }
            });
            return convertView;
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<PageSectionFragment> mFragment;

        public MyHandler(PageSectionFragment fragment) {
            mFragment = new WeakReference<PageSectionFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PageSectionFragment fragment = mFragment.get();
            if (fragment != null) {
                switch (msg.what) {
                    case REQUEST_FINISHED:
                        fragment.tempimgList = (List<ImageInfo>) msg.obj;
                        fragment.showContent(fragment.tempimgList);
                        break;
                }
            }

        }
    }

    private void showContent(List<ImageInfo> tempimgList) {
        if (tempimgList != null && tempimgList.size() > 0) {
            //第一次加载
            if (imgList == null) {
                imgList = new ArrayList<ImageInfo>();
            }
            imgList.addAll(tempimgList);
            tempimgList.clear();


           /* curLoadedPage = 1;
            if (imgList != null) {
                imgList.clear();
                imgList.addAll(tempimgList);
                tempimgList.clear();
            }*/

            if (imgList == null) {
                imgList = new ArrayList<ImageInfo>();
            }
            imgList.addAll(tempimgList);
            tempimgList.clear();
            curLoadedPage++;

            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "没有更多的图片了", Toast.LENGTH_SHORT).show();
        }
    }

}




