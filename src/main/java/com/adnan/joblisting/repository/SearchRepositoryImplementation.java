package com.adnan.joblisting.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.adnan.joblisting.dto.PostFilterRequest;
import com.adnan.joblisting.model.Post;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class SearchRepositoryImplementation implements SearchRepository {

    @Autowired
    private MongoClient client;

    @Autowired
    private MongoConverter converter;

    @Override
    public List<Post> searchByFilters(PostFilterRequest filter) {

        List<Post> posts = new ArrayList<>();

        MongoDatabase database = client.getDatabase("adnan");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        List<Document> pipeline = new ArrayList<>();
        List<Document> mustConditions = new ArrayList<>();

        /* -------------------- TEXT SEARCH FILTERS -------------------- */

        if (filter.getProfile() != null && !filter.getProfile().isBlank()) {
            mustConditions.add(
                new Document("text",
                    new Document("query", filter.getProfile())
                        .append("path", "profile"))
            );
        }

        if (filter.getTech() != null && !filter.getTech().isBlank()) {
            mustConditions.add(
                new Document("text",
                    new Document("query", filter.getTech())
                        .append("path", "techs"))
            );
        }

        if (filter.getLocation() != null && !filter.getLocation().isBlank()) {
            mustConditions.add(
                new Document("text",
                    new Document("query", filter.getLocation())
                        .append("path", "location"))
            );
        }

        // Add $search ONLY if text filters exist
        if (!mustConditions.isEmpty()) {
            pipeline.add(
                new Document("$search",
                    new Document("compound",
                        new Document("must", mustConditions)))
            );
        }

        /* -------------------- EXPERIENCE FILTER -------------------- */

        Document expRange = new Document();

        if (filter.getMinExp() != null) {
            expRange.append("$gte", filter.getMinExp());
        }

        if (filter.getMaxExp() != null) {
            expRange.append("$lte", filter.getMaxExp());
        }

        // Add $match ONLY if experience filter is present
        if (!expRange.isEmpty()) {
            pipeline.add(
                new Document("$match",
                    new Document("exp", expRange))
            );
        }

        /* -------------------- SORT & LIMIT -------------------- */

//        pipeline.add(new Document("$sort", new Document("exp", 1)));
//        pipeline.add(new Document("$limit", 20));
        int page = filter.getPage();
        int size = filter.getSize();

        pipeline.add(new Document("$sort", new Document("exp", 1)));
        pipeline.add(new Document("$skip", page * size));
        pipeline.add(new Document("$limit", size));


        /* -------------------- EXECUTION -------------------- */

        AggregateIterable<Document> result = collection.aggregate(pipeline);
        result.forEach(doc -> posts.add(converter.read(Post.class, doc)));

        return posts;
    }
    @Override
    public long countByFilters(PostFilterRequest filter) {

        MongoDatabase database = client.getDatabase("adnan");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        List<Document> pipeline = new ArrayList<>();
        List<Document> mustConditions = new ArrayList<>();

        if (filter.getProfile() != null && !filter.getProfile().isBlank()) {
            mustConditions.add(new Document("text",
                new Document("query", filter.getProfile()).append("path", "profile")));
        }

        if (filter.getTech() != null && !filter.getTech().isBlank()) {
            mustConditions.add(new Document("text",
                new Document("query", filter.getTech()).append("path", "techs")));
        }

        if (filter.getLocation() != null && !filter.getLocation().isBlank()) {
            mustConditions.add(new Document("text",
                new Document("query", filter.getLocation()).append("path", "location")));
        }

        if (!mustConditions.isEmpty()) {
            pipeline.add(new Document("$search",
                new Document("compound", new Document("must", mustConditions))));
        }

        Document expRange = new Document();
        if (filter.getMinExp() != null) expRange.append("$gte", filter.getMinExp());
        if (filter.getMaxExp() != null) expRange.append("$lte", filter.getMaxExp());

        if (!expRange.isEmpty()) {
            pipeline.add(new Document("$match", new Document("exp", expRange)));
        }

        pipeline.add(new Document("$count", "total"));

        Document doc = collection.aggregate(pipeline).first();

        // ✅ FIX
        return doc == null ? 0L : ((Number) doc.get("total")).longValue();
    }



}
