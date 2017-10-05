package com.leakdtech.maintenanceapp.Utils;

/**
 * Created by LYB OJO on 9/17/2017.
 */

public class UserAccountSettings {
    private String fullname;
    private long jobs_posted;
    private String user_photo;
    private String username;
    private String user_id;

    public UserAccountSettings(String fullname, long jobs_posted, String user_photo, String username, String user_id) {
        this.fullname = fullname;
        this.jobs_posted = jobs_posted;
        this.user_photo = user_photo;
        this.username = username;
        this.user_id = user_id;
    }

    public UserAccountSettings() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getJobs_posted() {
        return jobs_posted;
    }

    public void setJobs_posted(long jobs_posted) {
        this.jobs_posted = jobs_posted;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "fullname='" + fullname + '\'' +
                ", jobs_posted=" + jobs_posted +
                ", user_photo='" + user_photo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
