package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;

/**
 * Created by @Po10cio on 06/04/2017 for WPAndroid.
 */

public class Post extends WPPost {
    private String password;
    private WPObject content;
    private WPObject excerpt;
    private int featuredMedia;
    private String format;
    private boolean sticky;
    private ArrayList categories;
    private ArrayList tags;
    private int liveblogLikes;

    private Post() {
        super();
    }


    public String getPassword() {
        return password;
    }

    public WPObject getContent() {
        return content;
    }

    public WPObject getExcerpt() {
        return excerpt;
    }

    public int getFeaturedMedia() {
        return featuredMedia;
    }


    public String getFormat() {
        return format;
    }


    public boolean isSticky() {
        return sticky;
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
                .add("Password", password)
                .add("Title", getTitle())
//                .add("Content", content)
                .add("Author", getAuthor())
                .add("Excerpt", excerpt)
                .add("Featured Image", featuredMedia)
                .add("Comment Status", getCommentStatus())
                .add("Ping status", getPingStatus())
                .add("Format", format)
                .add("Meta", getMeta())
                .add("Sticky", sticky)
                .add("Template", getTemplate())
                .add("Categories", categories)
                .add("Tags", tags)
                .add("LiveBlogLikes", liveblogLikes)
                .toString();
    }
}
