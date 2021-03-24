package com.trimeo.Broadcastservice.mongo_sequences;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class GenerateOutSequence {

    @NonNull
    private final MongoOperations mongoOperations;

    public Integer generateSequence(String seqName){
        OutboundSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                OutboundSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
