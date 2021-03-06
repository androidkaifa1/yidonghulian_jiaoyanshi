package com.example.mloong.yidonghulian.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mloong.yidonghulian.MainActivity;
import com.example.mloong.yidonghulian.R;
import com.example.mloong.yidonghulian.activity.AddressActivity;
import com.example.mloong.yidonghulian.activity.ChangePWDActivity;
import com.example.mloong.yidonghulian.activity.LoginActivity;
import com.example.mloong.yidonghulian.activity.MyFavoriteActivity;
import com.example.mloong.yidonghulian.activity.MyOrderActivity;
import com.example.mloong.yidonghulian.common.ImageLoaderManager;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PersonFragment extends Fragment {

    @BindView(R.id.person_title)
    FrameLayout personTitle;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.layout_name)
    LinearLayout layoutName;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.name_balance_textview)
    RelativeLayout nameBalanceTextview;
    @BindView(R.id.personal_for_login)
    RelativeLayout personalForLogin;
    @BindView(R.id.personal_for_welcome)
    TextView personalForWelcome;
    @BindView(R.id.personal_login)
    Button personalLogin;
    @BindView(R.id.personal_click_to_login)
    LinearLayout personalClickToLogin;
    @BindView(R.id.personal_for_not_login)
    RelativeLayout personalForNotLogin;
    @BindView(R.id.personal_header)
    RelativeLayout personalHeader;
    @BindView(R.id.my_order_image)
    ImageView myOrderImage;
    @BindView(R.id.my_order_text)
    TextView myOrderText;
    @BindView(R.id.my_order_arrow)
    ImageView myOrderArrow;
    @BindView(R.id.person_my_order)
    RelativeLayout personMyOrder;
    @BindView(R.id.my_collect_image)
    ImageView myCollectImage;
    @BindView(R.id.my_collect_text)
    TextView myCollectText;
    @BindView(R.id.my_collect_arrow)
    ImageView myCollectArrow;
    @BindView(R.id.my_collect)
    RelativeLayout myCollect;
    @BindView(R.id.my_address_image)
    ImageView myAddressImage;
    @BindView(R.id.my_address_text)
    TextView myAddressText;
    @BindView(R.id.my_address_arrow)
    ImageView myAddressArrow;
    @BindView(R.id.my_address)
    RelativeLayout myAddress;
    @BindView(R.id.my_account_image)
    ImageView myAccountImage;
    @BindView(R.id.my_account_text)
    TextView myAccountText;
    @BindView(R.id.my_account_arrow)
    ImageView myAccountArrow;
    @BindView(R.id.my_account)
    RelativeLayout myAccount;
    @BindView(R.id.my_account_text1)
    TextView myAccountText1;
    @BindView(R.id.person_logout_layout)
    RelativeLayout personLogoutLayout;
    @BindView(R.id.my_order_layout)
    LinearLayout myOrderLayout;
    Unbinder unbinder;

    private final int MY_FAVORITE = 1;
    private final int MY_ORDER = 2;
    private final int MY_ADDRESS = 3;
    private final int MY_ACCOUNT_BEFORE = 4;
    private final int MY_ACCOUNT_AFTER = 5;
    @BindView(R.id.user_img_view)
    SimpleDraweeView userImgView;
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainActivity = (MainActivity) this.getActivity();
        init();
        return view;
    }

    //初始化布局，根据登陆状态显示不同的布局效果
    public void init() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", 0);
        String username = sharedPreferences.getString("uname", "");
        if (TextUtils.isEmpty(username)) {   //未登录
            personalForLogin.setVisibility(View.GONE);
            personalForNotLogin.setVisibility(View.VISIBLE);
            personLogoutLayout.setVisibility(View.GONE);
        } else {   //已登录
            personalForLogin.setVisibility(View.VISIBLE);
            personalForNotLogin.setVisibility(View.GONE);
            personLogoutLayout.setVisibility(View.VISIBLE);
            userName.setText(username);


            String image = sharedPreferences.getString("image", "");
            if (!TextUtils.isEmpty(image)) {

                // ImageLoader.getInstance().displayImage(image, userImgView, ImageLoaderManager.user_options);
                ImageLoaderManager.displayImage(image, userImgView);

            }
        }
    }

    @Override
    public void onResume() {
        init();
        super.onResume();
    }

    //退出登录时，清楚本地用户信息
    private void logout() {
        SharedPreferences.Editor localEditor = mainActivity.getSharedPreferences("user", 0).edit();
        localEditor.remove("member_id");
        localEditor.remove("uname");
        localEditor.remove("email");
        localEditor.remove("image");
        localEditor.commit();
        init();
        Toast.makeText(mainActivity, "退出登录成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.personal_login, R.id.my_collect, R.id.my_address, R.id.my_account, R.id.person_logout_layout, R.id.person_my_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_login:
                startActivity(new Intent(mainActivity, LoginActivity.class));
                break;
            case R.id.my_collect:
                if (mainActivity.isLogin()) {
                    startActivity(new Intent(mainActivity, MyFavoriteActivity.class));
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_FAVORITE);
                break;
            case R.id.my_address:
                if (mainActivity.isLogin()) {
                    Intent intent = new Intent(mainActivity, AddressActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_ADDRESS);
                break;
            case R.id.my_account:
                if (mainActivity.isLogin()) {
                    startActivityForResult(new Intent(mainActivity, ChangePWDActivity.class), MY_ACCOUNT_AFTER);
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_ACCOUNT_BEFORE);
                break;
            case R.id.person_logout_layout:
                new AlertDialog.Builder(mainActivity)
                        .setTitle("退出登录")
                        .setMessage("您确认要退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.person_my_order:
                if (mainActivity.isLogin()) {
                    startActivity(new Intent(mainActivity, MyOrderActivity.class));
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_ORDER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_ORDER:
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, MyOrderActivity.class);
                    startActivity(intent);
                }

                break;
            case MY_FAVORITE:
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, MyFavoriteActivity.class);
                    startActivity(intent);
                }

                break;
            case MY_ADDRESS:
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, AddressActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }

                break;
            case MY_ACCOUNT_BEFORE://未登录时修改密码，修改密码后进行登录
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, ChangePWDActivity.class);
                    startActivityForResult(intent, MY_ACCOUNT_AFTER);
                }
                break;
            case MY_ACCOUNT_AFTER://登录时修改密码，修改完毕后进行登录
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(new Intent(mainActivity, LoginActivity.class));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
