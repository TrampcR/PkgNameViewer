package com.trampcr.pkgnamedemo.base.ui.view;

/**
 * Created by trampcr on 2018/3/28.
 * interface IViewInit
 */

public interface IViewInit {

    /**
     * 获取布局资源ID
     *
     * @return 布局资源ID
     */
    int getContentViewResId();

    /**
     * 初始化成员变量、获取intent的参数值
     */
    void init();

    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化事件监听
     */
    void initListener();

    /**
     * 初始化视图数据
     */
    void setViewsValue();
}
