package com.velaphi.tabby.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="response")
public class Response {

    @Element(name="data", required=false)
    Data data;

    public Data getData() {return this.data;}
    public void setData(Data value) {this.data = value;}

}