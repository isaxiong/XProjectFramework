package com.xiong.xprojectframework.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author xiong
 * @description 用户信息（保存到数据库，注意：GreenDao暂不支持kotlin的bean类，因此需要声明为javaClassBean）
 * @since   2020/11/20
 **/
@Entity
public class UserInfo {

    @Id(autoincrement = true) //设置自增长
    private Long id;

    @Index(unique = true) //设置唯一性
    private String perNo;  //人员编号

    private String name; //人员姓名

    private String sex; //人员姓别

    @Generated(hash = 372221082)
    public UserInfo(Long id, String perNo, String name, String sex) {
        this.id = id;
        this.perNo = perNo;
        this.name = name;
        this.sex = sex;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerNo() {
        return this.perNo;
    }

    public void setPerNo(String perNo) {
        this.perNo = perNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
