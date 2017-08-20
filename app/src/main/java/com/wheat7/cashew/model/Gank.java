package com.wheat7.cashew.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wheat7 on 2017/8/5.
 */

public class Gank implements Serializable {

    /**
     * _id : 59811832421aa90ca209c53d
     * createdAt : 2017-08-02T08:09:22.422Z
     * desc : 在 React Native 环境下，运行一个 Node Server Http 服务。
     * images : ["http://img.gank.io/e0273ca9-fef5-4fef-ba03-86d0e3b390d2"]
     * publishedAt : 2017-08-02T12:21:45.220Z
     * source : chrome
     * type : Android
     * url : https://github.com/staltz/react-native-node
     * used : true
     * who : 代码家
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
