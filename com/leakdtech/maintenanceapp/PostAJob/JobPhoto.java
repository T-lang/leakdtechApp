package com.leakdtech.maintenanceapp.PostAJob;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LYB OJO on 9/27/2017.
 */

public class JobPhoto implements Parcelable {
    private String job_date;
    private String job_description;
    private String job_title;
    private String job_location;
    private String date_created;
    private String image_path;
    private String photo_id;
    private String user_id;
    private String tags;

    public JobPhoto() {
    }

    public JobPhoto(String job_date, String job_description,
                    String job_title, String job_location, String date_created, String image_path, String photo_id, String user_id, String tags) {
        this.job_date = job_date;
        this.job_description = job_description;
        this.job_title = job_title;
        this.job_location = job_location;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.tags = tags;
    }


    protected JobPhoto(Parcel in) {
        job_date = in.readString();
        job_description = in.readString();
        job_title = in.readString();
        job_location = in.readString();
        date_created = in.readString();
        image_path = in.readString();
        photo_id = in.readString();
        user_id = in.readString();
        tags = in.readString();
    }

    public static final Creator<JobPhoto> CREATOR = new Creator<JobPhoto>() {
        @Override
        public JobPhoto createFromParcel(Parcel in) {
            return new JobPhoto(in);
        }

        @Override
        public JobPhoto[] newArray(int size) {
            return new JobPhoto[size];
        }
    };

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getJob_date() {
        return job_date;
    }

    public void setJob_date(String job_date) {
        this.job_date = job_date;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "JobPhoto{" +
                "job_date='" + job_date + '\'' +
                ", job_description='" + job_description + '\'' +
                ", job_title='" + job_title + '\'' +
                ", job_location='" + job_location + '\'' +
                ", date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(job_date);
        dest.writeString(job_description);
        dest.writeString(job_title);
        dest.writeString(job_location);
        dest.writeString(date_created);
        dest.writeString(image_path);
        dest.writeString(photo_id);
        dest.writeString(user_id);
        dest.writeString(tags);
    }
}
