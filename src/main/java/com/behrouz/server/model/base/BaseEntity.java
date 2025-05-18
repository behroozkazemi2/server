package com.behrouz.server.model.base;

import com.behrouz.server.model.account.AccountEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
//@Audit
@EntityListeners(BaseEntityToPersistListener.class)
public class BaseEntity implements Serializable {

    protected long id;

    protected boolean deleted;

    protected LocalDateTime insertDate;

    protected AccountEntity updater;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    @Column(columnDefinition = "boolean default false")
    public boolean isDeleted () {
        return deleted;
    }
    public void setDeleted ( boolean deleted ) {
        this.deleted = deleted;
    }



    @CreationTimestamp
    @Basic
    public LocalDateTime getInsertDate () {
        return insertDate;
    }
    public void setInsertDate ( LocalDateTime insertDate ) {
        this.insertDate = insertDate;
    }



    @ManyToOne
    @JoinColumn(name = "updater_id")
    public AccountEntity getUpdater() {
        return updater;
    }
    public void setUpdater(AccountEntity updater) {
        this.updater = updater;
    }
}
