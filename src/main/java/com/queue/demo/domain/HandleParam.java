package com.queue.demo.domain;

import java.util.List;

public class HandleParam {
    private String handleId;
    private String productId;
    private String produceModel;
    private String productName;
    private String chuchangDate;
    private String productCompany;
    private String lingProductHandleCode;
    private String url;
    private String relationName;
    private String basicInfoTitle;
    private List<Metadata> metadataList;

    public List<Metadata> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<Metadata> metadataList) {
        this.metadataList = metadataList;
    }

    public String getBasicInfoTitle() {
        return basicInfoTitle;
    }

    public void setBasicInfoTitle(String basicInfoTitle) {
        this.basicInfoTitle = basicInfoTitle;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    private String childHandleId;

    public String getChildHandleId() {
        return childHandleId;
    }

    public void setChildHandleId(String childHandleId) {
        this.childHandleId = childHandleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProduceModel() {
        return produceModel;
    }

    public void setProduceModel(String produceModel) {
        this.produceModel = produceModel;
    }

    public String getHandleId() {
        return handleId;
    }

    public void setHandleId(String handleId) {
        this.handleId = handleId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getChuchangDate() {
        return chuchangDate;
    }

    public void setChuchangDate(String chuchangDate) {
        this.chuchangDate = chuchangDate;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(String productCompany) {
        this.productCompany = productCompany;
    }

    public String getLingProductHandleCode() {
        return lingProductHandleCode;
    }

    public void setLingProductHandleCode(String lingProductHandleCode) {
        this.lingProductHandleCode = lingProductHandleCode;
    }
}
