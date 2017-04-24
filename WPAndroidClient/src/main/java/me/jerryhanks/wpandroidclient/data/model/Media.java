package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

/**
 * Created by Jerry < @Po10cio >  on 08/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class Media extends WPPost {
    private WPObject description;
    private WPObject caption;
    private String altText;
    private String mediaType;
    private String mimeType;
    private long post;
    private String sourceUrl;
    private MediaDetails mediaDetails;

    private Media() {

    }

    public WPObject getDescription() {
        return description;
    }

    public WPObject getCaption() {
        return caption;
    }

    public String getAltText() {
        return altText;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getPost() {
        return post;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Media")
                .add("Date", getDate())
                .add("DateGmt", getDateGmt())
                .add("Guid", getGuid())
                .add("Id", getId())
                .add("Link", getLink())
                .add("Modified", getModified())
                .add("ModifiedGmt", getModifiedGmt())
                .add("Slug", getSlug())
                .add("Status", getStatus())
                .add("Type", getType())
                .add("Title", getTitle())
                .add("Author", getAuthor())
                .add("Comment Status", getCommentStatus())
                .add("Ping status", getPingStatus())
                .add("Template", getTemplate())
                .add("Description", description)
                .add("Caption", caption)
                .add("AltText", altText)
                .add("Media Type", mediaType)
                .add("MimeType", mimeType)
                .add("Post", post)
                .add("Source Url", sourceUrl)
                .toString();
    }
}
