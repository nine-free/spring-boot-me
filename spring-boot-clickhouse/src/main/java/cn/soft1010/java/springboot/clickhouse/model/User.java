package cn.soft1010.java.springboot.clickhouse.model;

/**
 * Created by zhangjifu on 2020/12/14.
 */
public class User {
    private Integer id;
    private String name;
    private String createDate;

    public User() {
    }

    public User(Integer id, String name, String createDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
