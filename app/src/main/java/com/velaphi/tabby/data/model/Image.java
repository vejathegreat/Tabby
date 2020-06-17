package com.velaphi.tabby.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;

public class Image implements Parcelable {
    @Element(name="id", required=false)
    String id;

    @Element(name="url", required=false)
    String url;

    @Element(name="source_url", required=false)
    String sourceUrl;

    String description;
    String title;

    protected Image(Parcel in) {
        id = in.readString();
        url = in.readString();
        sourceUrl = in.readString();
        description = in.readString();
        title = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getId() {return this.id;}
    public String getUrl() {return this.url;}
    public String getSourceUrl() {return this.sourceUrl;}

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(sourceUrl);
        dest.writeString(description);
        dest.writeString(title);
    }
}
