package br.com.feedsync.functions.notification_service.model;

import java.util.Date;

public class Feedback {
    private String feedbackId;
    private Integer rating;
    private String comment;
    private Context context;
    private Boolean isUrgent;
    private Author author;
    private Date createdAt;
    private Date updatedAt;
    private Boolean active;

    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Context getContext() { return context; }
    public void setContext(Context context) { this.context = context; }

    public Boolean getUrgent() { return isUrgent; }
    public void setUrgent(Boolean urgent) { isUrgent = urgent; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
