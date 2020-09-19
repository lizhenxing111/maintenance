package com.lq.maintenance.core.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * hz_log
 *
 * @author
 */
@Table(name = "hz_log")
public class HzLog implements Serializable {
    @Id
    @GeneratedValue(
            generator = "JDBC"
    )
    private Integer id;

    private Integer hzClientId;

    private String clientId;

    private Date generateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHzClientId() {
        return hzClientId;
    }

    public void setHzClientId(Integer hzClientId) {
        this.hzClientId = hzClientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }
}