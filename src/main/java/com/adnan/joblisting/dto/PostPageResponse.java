package com.adnan.joblisting.dto;

import java.util.List;
import com.adnan.joblisting.model.Post;

public class PostPageResponse {

    private List<Post> posts;
    private long total;
    private int page;
    private int size;

    public PostPageResponse(List<Post> posts, long total, int page, int size) {
        this.posts = posts;
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
