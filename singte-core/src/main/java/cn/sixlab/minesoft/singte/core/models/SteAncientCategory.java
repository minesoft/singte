package cn.sixlab.minesoft.singte.core.models;

import cn.sixlab.minesoft.singte.core.common.config.BaseModel;

public class SteAncientCategory extends BaseModel {

    private String ancientSet;

    private String ancientCategory;

    private Integer count;

    private Integer weight;

    private String intro;

    public String getAncientSet() {
        return ancientSet;
    }

    public void setAncientSet(String ancientSet) {
        this.ancientSet = ancientSet;
    }

    public String getAncientCategory() {
        return ancientCategory;
    }

    public void setAncientCategory(String ancientCategory) {
        this.ancientCategory = ancientCategory;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}