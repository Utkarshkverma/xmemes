package com.crio_mongo.Xmemes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


import com.crio_mongo.Xmemes.entity.DBSequence;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int getSequenceNumber(String sequenceName) {
        // Define the query criteria
        Criteria criteria = Criteria.where("id").is(sequenceName);

        // Define the update operation
        Update update = new Update().inc("seq", 1);

        // Define and simplify query options
        FindAndModifyOptions options = options().returnNew(true).upsert(true);

        // Execute the findAndModify operation
        DBSequence counter = mongoOperations.findAndModify(
            new Query(criteria),
            update,
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            DBSequence.class
        );

        // Return the sequence number (handling null case)
        return counter != null ? counter.getSeq() : 1;
    }
}
