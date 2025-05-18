package com.behrouz.server.model;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.model
 * Project Name: behta-server
 * 13 December 2018
 **/

@Entity
@Table(name = "comment_status", schema = "public")
public class CommentStatusEntity extends BaseIdNameEntity {


    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "status")
    @JsonBackReference
    public Set < CommentEntity > getComments () {
        return comments;
    }
    public void setComments ( Set < CommentEntity > comments ) {
        this.comments = comments;
    }
}
