package webcrawler.data;

import java.util.Date;

public class TwitterTweetData {

    private long tweetId;

    private String tweetAuthor;

    private String tweetMessage;

    private String tweetTrainLine;

    private Date tweetTimestamp;


    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetAuthor() {
        return tweetAuthor;
    }

    public void setTweetAuthor(String tweetAuthor) {
        this.tweetAuthor = tweetAuthor;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public String getTweetTrainLine() {
        return tweetTrainLine;
    }

    public void setTweetTrainLine(String tweetTrainLine) {
        this.tweetTrainLine = tweetTrainLine;
    }

    public Date getTweetTimestamp() {
        return tweetTimestamp;
    }

    public void setTweetTimestamp(Date tweetTimestamp) {
        this.tweetTimestamp = tweetTimestamp;
    }
}
