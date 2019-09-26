package webcrawler.data;

import com.google.gson.annotations.SerializedName;

public class TwitterSearchData {

    @SerializedName("min_position")
    private String minPosition;

    @SerializedName("has_more_items")
    private boolean hasMoreItems;

    @SerializedName("items_html")
    private String itemsHtml;

    @SerializedName("new_latent_count")
    private int newLatentCount;

    @SerializedName("focused_refresh_interval")
    private int focusedRefreshInterval;

    public String getMinPosition() {
        return minPosition;
    }

    public void setMinPosition(String minPosition) {
        this.minPosition = minPosition;
    }

    public boolean isHasMoreItems() {
        return hasMoreItems;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }

    public String getItemsHtml() {
        return itemsHtml;
    }

    public void setItemsHtml(String itemsHtml) {
        this.itemsHtml = itemsHtml;
    }

    public int getNewLatentCount() {
        return newLatentCount;
    }

    public void setNewLatentCount(int newLatentCount) {
        this.newLatentCount = newLatentCount;
    }

    public int getFocusedRefreshInterval() {
        return focusedRefreshInterval;
    }

    public void setFocusedRefreshInterval(int focusedRefreshInterval) {
        this.focusedRefreshInterval = focusedRefreshInterval;
    }
}
