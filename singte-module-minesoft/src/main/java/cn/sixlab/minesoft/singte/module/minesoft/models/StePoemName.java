package cn.sixlab.minesoft.singte.module.minesoft.models;

import cn.sixlab.minesoft.singte.core.common.config.BaseModel;

import java.util.Date;

public class StePoemName extends BaseModel {

    private Integer poemId;

    private Integer atomId;

    private String nameKeywords;

    private String peopleName;

    private String poemName;

    private String atomContent;

    public Integer getPoemId() {
        return poemId;
    }

    public void setPoemId(Integer poemId) {
        this.poemId = poemId;
    }

    public Integer getAtomId() {
        return atomId;
    }

    public void setAtomId(Integer atomId) {
        this.atomId = atomId;
    }

    public String getNameKeywords() {
        return nameKeywords;
    }

    public void setNameKeywords(String nameKeywords) {
        this.nameKeywords = nameKeywords == null ? null : nameKeywords.trim();
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName == null ? null : peopleName.trim();
    }

    public String getPoemName() {
        return poemName;
    }

    public void setPoemName(String poemName) {
        this.poemName = poemName == null ? null : poemName.trim();
    }

    public String getAtomContent() {
        return atomContent;
    }

    public void setAtomContent(String atomContent) {
        this.atomContent = atomContent == null ? null : atomContent.trim();
    }
}