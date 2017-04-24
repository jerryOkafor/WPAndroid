package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jerry < @Po10cio >  on 10/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class WPPost {
    private Date date;
    private Date dateGmt;
    private WPObject guid;
    private int id;
    private String link;
    private String modified;
    private String modifiedGmt;
    private String slug;
    private String status;
    private String type;
    private WPObject title;
    private int author;
    private ArrayList meta;
    private String commentStatus;
    private String pingStatus;
    private String template;

    public WPPost() {

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("WPPOst")
                .add("Date", date)
                .add("DateGmt", dateGmt)
                .add("Guid", guid)
                .add("Id", id)
                .add("Link", link)
                .add("Modified", modified)
                .add("ModifiedGmt", modifiedGmt)
                .add("Slug", slug)
                .add("Status", status)
                .add("Type", type)
                .add("Title", title)
//                .add("Meta", meta)
                .add("Author", author)
                .add("Comment Status", commentStatus)
                .add("Ping status", pingStatus)
                .add("Template", template)
                .toString();
    }

    public Date getDate() {
        return date;
    }

    public Date getDateGmt() {
        return dateGmt;
    }

    public WPObject getGuid() {
        return guid;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getModified() {
        return modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public WPObject getTitle() {
        return title;
    }

    public int getAuthor() {
        return author;
    }

    public ArrayList getMeta() {
        return meta;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public String getTemplate() {
        return template;
    }
}
