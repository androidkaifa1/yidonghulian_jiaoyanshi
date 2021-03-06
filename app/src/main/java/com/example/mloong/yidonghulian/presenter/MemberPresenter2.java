package com.example.mloong.yidonghulian.presenter;

import com.example.mloong.yidonghulian.entity.HttpResult;
import com.example.mloong.yidonghulian.entity.MemberEntity;
import com.example.mloong.yidonghulian.http.HttpMethods2;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MemberPresenter2 extends HttpMethods2 {
//    public static void register(Subscriber<MemberEntity> subscriber, String uname, String password, String email) {
//        Observable<MemberEntity> observable = sMemberService.register(uname, password, email)
//                .map(new HttpResultFunc<>());
//        toSubscribe(observable,subscriber);
//    }
//
//    public static void register(Consumer<MemberEntity> subscriber, String uname, String password, String email) {
//        Observable<MemberEntity> observable = sMemberService.register(uname, password, email)
//                .map(new HttpResultFunc<>());
//        toSubscribe(observable,subscriber);
//    }



    public static void updateById2(Observer<HttpResult<MemberEntity>> subscriber, MemberEntity memberEntity) {
        Observable<HttpResult<MemberEntity>> observable = sMemberService.updateById(memberEntity)
                .map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }


}
