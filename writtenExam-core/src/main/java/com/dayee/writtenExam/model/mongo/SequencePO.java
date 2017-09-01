
package com.dayee.writtenExam.model.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by admin_yk on 2017/4/26.
 */
@Document(collection = "sequence")
public class SequencePO {

    @Id
    private ObjectId id;

    private Long     seqId;

    private String   collName;

    public ObjectId getId() {

        return id;
    }

    public void setId(ObjectId id) {

        this.id = id;
    }

    public Long getSeqId() {

        return seqId;
    }

    public void setSeqId(Long seqId) {

        this.seqId = seqId;
    }

    public String getCollName() {

        return collName;
    }

    public void setCollName(String collName) {

        this.collName = collName;
    }
}
