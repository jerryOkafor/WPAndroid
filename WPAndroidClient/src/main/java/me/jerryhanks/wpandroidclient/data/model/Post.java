package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by @Po10cio on 06/04/2017 for WPAndroid.
 */

public class Post {
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
    private String password;
    private WPObject title;
    private WPObject content;
    private int author;
    private WPObject excerpt;
    private int featuredMedia;
    private String commentStatus;
    private String pingStatus;
    private String format;
    private ArrayList meta;
    private boolean sticky;
    private String template;
    private ArrayList categories;
    private ArrayList tags;
    private int liveblogLikes;

    private Post() {
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

    public String getPassword() {
        return password;
    }

    public WPObject getTitle() {
        return title;
    }

    public WPObject getContent() {
        return content;
    }

    public int getAuthor() {
        return author;
    }

    public WPObject getExcerpt() {
        return excerpt;
    }

    public int getFeaturedMedia() {
        return featuredMedia;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public String getFormat() {
        return format;
    }

    public ArrayList getMeta() {
        return meta;
    }

    public boolean isSticky() {
        return sticky;
    }

    public String getTemaplate() {
        return template;
    }

    public ArrayList getCategories() {
        return categories;
    }

    public ArrayList getTags() {
        return tags;
    }

    public int getLiveblogLikes() {
        return liveblogLikes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Post")
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
                .add("Password", password)
                .add("Title", title)
//                .add("Content", content)
                .add("Author", author)
                .add("Excerpt", excerpt)
                .add("Featured Image", featuredMedia)
                .add("Comment Status", commentStatus)
                .add("Ping status", pingStatus)
                .add("Format", format)
                .add("Meta", meta)
                .add("Sticky", sticky)
                .add("Template", template)
                .add("Categories", categories)
                .add("Tags", tags)
                .add("LiveBlogLikes", liveblogLikes)
                .toString();
    }
}
