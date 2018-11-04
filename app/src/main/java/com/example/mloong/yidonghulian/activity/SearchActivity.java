package com.example.mloong.yidonghulian.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mloong.yidonghulian.R;
import com.example.mloong.yidonghulian.adapter.OnHistoryItemClickListener;
import com.example.mloong.yidonghulian.adapter.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.search_title_icon)
    ImageView searchTitleIcon;
    @BindView(R.id.search_keyword)
    AutoCompleteTextView searchKeyword;
    @BindView(R.id.product_list_search_clean)
    ImageButton productListSearchClean;
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.product_list_search_layout)
    RelativeLayout productListSearchLayout;
    @BindView(R.id.product_list_top_layout)
    RelativeLayout productListTopLayout;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.search_history_list)
    RecyclerView searchHistoryList;
    @BindView(R.id.del_history_button)
    Button delHistoryButton;
    @BindView(R.id.product_list_search_float_layout)
    LinearLayout productListSearchFloatLayout;

    private SharedPreferences sharedPreferences;
    private List<String> historyList = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences("user", 0);
//        searchHistoryAdapter = new SearchHistoryAdapter(this, historyList);

        //载入历史记录列表
        int historySize = sharedPreferences.getInt("history_size", 0);
        for (int i = 0; i < historySize; i++) {
            historyList.add(sharedPreferences.getString("history_" + i, ""));
        }
        searchHistoryAdapter = new SearchHistoryAdapter(this, historyList);
        if (historyList.size() > 0) {

            searchHistoryList.setAdapter(searchHistoryAdapter);
            delHistoryButton.setVisibility(View.VISIBLE);
        } else {
            delHistoryButton.setVisibility(View.GONE);
        }


        //搜索历史点击事件
        searchHistoryAdapter.setGoodsItemListener(new OnHistoryItemClickListener() {
            @Override
            public void onClick(View view, String data) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("keyword", data);
                setResult(RESULT_OK, returnIntent);
                finish();
            }

        });
    }


    @OnClick({R.id.title_back, R.id.search_btn, R.id.del_history_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.search_btn:
                beginSearch();
                break;
            case R.id.del_history_button:
                deleteHistory();
                break;
        }
    }

    /**
     * 删除历史记录
     */
    private void deleteHistory() {
        int historySize = sharedPreferences.getInt("history_size", 0);
        SharedPreferences.Editor localEditor = sharedPreferences.edit();
        for (int i = 0; i < historySize; i++) {
            localEditor.remove("history_" + i);
        }
        localEditor.putInt("history_size", 0);
        localEditor.commit();
        historyList.clear();
        searchHistoryAdapter.notifyDataSetChanged();
        Toast.makeText(SearchActivity.this, "删除历史记录成功！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始搜索
     */
    private void beginSearch() {
        //本地验证
        String keyword = searchKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(SearchActivity.this, "请输入要搜索的关键词！", Toast.LENGTH_SHORT).show();
            return;
        }
        //保存搜索记录
        int historySize = sharedPreferences.getInt("history_size", 0);
        SharedPreferences.Editor localEditor = sharedPreferences.edit();

        //取出之前的历史记录的前9条
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < historySize; i++) {
            if (tempList.size() < 9) {
                tempList.add(sharedPreferences.getString("history_" + i, ""));
            }
            localEditor.remove("history_" + i);
        }
        //增加新的历史记录到集合中
        tempList.add(0, keyword);
        //将集合中的历史记录写到本地
        for (int i = 0; i < tempList.size(); i++) {
            localEditor.putString("history_" + i, tempList.get(i));
        }
        localEditor.putInt("history_size", tempList.size());
        localEditor.commit();
        //开始搜索
        Intent returnIntent = new Intent();
        returnIntent.putExtra("keyword", keyword);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
