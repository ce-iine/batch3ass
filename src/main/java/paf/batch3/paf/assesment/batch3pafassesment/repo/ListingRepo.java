package paf.batch3.paf.assesment.batch3pafassesment.repo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ListingRepo {

    @Autowired 
    MongoTemplate mongoTemplate;

/*
db.listings.aggregate([
    {
        $group: {
            _id: "$address.country",
        }
    },
    {
        $project: {
            country: 1
        }
    }
]);
*/

/*
db.listings.distinct("address.country");
 */
    public List<Document> getListOfCountry(){
        GroupOperation group = Aggregation.group("address.country");
        ProjectionOperation pOperation = Aggregation.project("country");
        Aggregation pipeline = Aggregation.newAggregation(group, pOperation);
        List<Document> output = mongoTemplate.aggregate(pipeline,"listings", Document.class).getMappedResults();
        System.out.println("COUNTRY LIST>>>>" +output);
        return output;

        // return mongoTemplate.findDistinct(new Query(), "address.country", "listings", Document.class);
    }

    /*
     db.listings.aggregate([
    {
        $match: {
            "address.country": "Australia",
            price: { $gte: 100, $lte: 800 },
            accommodates: 2
        }
    }, {
        $sort: { price: -1 }
    },
    {
        $project: {
            "_id":1,
            "address.street": 1,
            "price": 1,
            "name": 1,
            "accommodates": 1,
            "images.picture_url": 1
        }
    }
]);
     */

     /*
    db.listings.find(
    {
        "address.country": "Australia",
        price: { $gte: 100, $lte: 800 },
        accommodates: 2
    }
).sort({ price: -1 });
      */

     public List<Document> querySearch(String country, Double minPrice, Double maxPrice, Integer accommodates){
        Criteria criteria = Criteria.where("address.country").regex(country, "i")
            .and("price").gte(minPrice).lte(maxPrice)
            .and("accommodates").is(accommodates);
        
        Sort sort = Sort.by(Direction.DESC,"price");

        Query query = Query.query(criteria).with(sort);

        List<Document> output = mongoTemplate.find(query, Document.class, "listings");
        
        // MatchOperation matchOps = Aggregation.match(Criteria.where("address.country").is(country)
        //     .and("accommodates").is(accommodates)
        //     .and("price").gte(minPrice).lte(maxPrice));

        // SortOperation sortBy = Aggregation.sort(Sort.by(Direction.DESC,"price"));
        // ProjectionOperation pOperation = Aggregation.project("_id","address.street","price","name", "images.picture_url");

        // Aggregation pipeline = Aggregation.newAggregation(matchOps, sortBy, pOperation);

        // List<Document> output = mongoTemplate.aggregate(pipeline,"listings", Document.class).getMappedResults();

        System.out.println("QUERY SEARCH:>>>>>>>" + output);
        return output;
    }

    /*
    db.listings.aggregate([
    {
        $match: {
            _id: "5604994"
        }
    },
    {
        $project: {
            "_id": 1,
            "description": 1,
            "address.street": 1,
            "address.suburb": 1,
            "address.country": 1,
            "price": 1,
            "name": 1,
            "images.picture_url": 1,
            amenities:1
        }
    }
]);
     */

    /*
    db.listings.find(
    {
       _id: "5604994"
    }
    );
     */

    public Document getAccomDetails(String id){
        Query query = Query.query(Criteria.where("_id").is(id));

        Document output = mongoTemplate.findOne(query, Document.class, "listings");

        // MatchOperation matchOps = Aggregation.match(Criteria.where("_id").is(id));
        // ProjectionOperation pOperation = Aggregation.project("_id", "description","address.street",
        //     "address.suburb", "address.country", "price","name", "images.picture_url", "amenities");

        // Aggregation pipeline = Aggregation.newAggregation(matchOps, pOperation);

        // Document output = mongoTemplate.aggregate(pipeline,"listings", Document.class).getMappedResults().get(0);

        System.out.println("------------------------------------------------------------------------------------------");

        System.out.println("DETAILS SEARCH:>>>>>>>" + output);

        return output;
    }





    
    
}
