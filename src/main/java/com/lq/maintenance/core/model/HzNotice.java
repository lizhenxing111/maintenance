package com.lq.maintenance.core.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(
        name = "hz_notice"
)
public class HzNotice implements Serializable {
    @Id
    @GeneratedValue(
            generator = "JDBC"
    )
    private Integer id;
    private Integer noticeId;
    private String noticeTitle;
    private Integer hitCount;
    private Integer likeCount;
    private Date publishDate;
    private String pcLink;

    public String getPcLink() {
        return pcLink;
    }

    public void setPcLink(String pcLink) {
        this.pcLink = pcLink;
    }

    private static final long serialVersionUID = 1L;

    public HzNotice() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoticeId() {
        return this.noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Integer getHitCount() {
        return this.hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public Integer getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
