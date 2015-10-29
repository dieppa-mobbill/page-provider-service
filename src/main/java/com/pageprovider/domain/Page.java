package com.pageprovider.domain;

import javax.persistence.Column;

public class Page {

    @Column(name="id")
    private long     id;

    @Column(name="aggregators_id")
    private int     aggregatorsId;

    @Column(name="payment_page_type_id")
    private int     paymentPageTypeId;

    @Column(name="content_id")
    private long     contentId;

    @Column(name="html_file")
    private String  htmlFile;

    @Column(name="factor")
    private String factor;

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getAggregatorsId() {
        return aggregatorsId;
    }

    public void setAggregatorsId(int aggregatorsId) {
        this.aggregatorsId = aggregatorsId;
    }

    public int getPaymentPageTypeId() {
        return paymentPageTypeId;
    }

    public void setPaymentPageTypeId(int paymentPageTypeId) {
        this.paymentPageTypeId = paymentPageTypeId;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page that = (Page) o;

        if (aggregatorsId != that.aggregatorsId) return false;
        if (contentId != that.contentId) return false;
        if (paymentPageTypeId != that.paymentPageTypeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + aggregatorsId;
        result = 31 * result + paymentPageTypeId;
        result = 31 * result + (int) (contentId ^ (contentId >>> 32));
        result = 31 * result + (htmlFile != null ? htmlFile.hashCode() : 0);
        result = 31 * result + (factor != null ? factor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", aggregatorsId=" + aggregatorsId +
                ", paymentPageTypeId=" + paymentPageTypeId +
                ", contentId=" + contentId +
                ", htmlFile='" + htmlFile + '\'' +
                ", factor='" + factor + '\'' +
                '}';
    }
}
