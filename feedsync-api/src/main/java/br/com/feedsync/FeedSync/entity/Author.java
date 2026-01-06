package br.com.feedsync.FeedSync.entity;

public class Author {

    String userid;
    String name;
    String email;

    public Author() {
    }

    public Author(String userid, String name, String email) {
        this.userid = userid;
        this.name = name;
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
