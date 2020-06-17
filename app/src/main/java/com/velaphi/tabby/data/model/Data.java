package com.velaphi.tabby.data.model;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class Data {

    @ElementList(name="images", required=false)
    List<Image> images;

    public List<Image> getImages() {return this.images;}
    public void setImages(List<Image> value) {this.images = value;}
}
