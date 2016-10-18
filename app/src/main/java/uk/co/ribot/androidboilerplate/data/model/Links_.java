package uk.co.ribot.androidboilerplate.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("download")
    @Expose
    private String download;
    @SerializedName("download_location")
    @Expose
    private String downloadLocation;

    /**
     *
     * @return
     * The self
     */
    public String getSelf() {
        return self;
    }

    /**
     *
     * @param self
     * The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     *
     * @return
     * The html
     */
    public String getHtml() {
        return html;
    }

    /**
     *
     * @param html
     * The html
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     *
     * @return
     * The download
     */
    public String getDownload() {
        return download;
    }

    /**
     *
     * @param download
     * The download
     */
    public void setDownload(String download) {
        this.download = download;
    }

    /**
     *
     * @return
     * The downloadLocation
     */
    public String getDownloadLocation() {
        return downloadLocation;
    }

    /**
     *
     * @param downloadLocation
     * The download_location
     */
    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

}
