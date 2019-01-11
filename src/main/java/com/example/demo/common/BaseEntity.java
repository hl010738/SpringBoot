package com.example.demo.common;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
	
	@Enumerated(EnumType.STRING)
	private Status status=Status.ACTIVE;
	
    @CreatedBy
    private String createdBy;
    @CreatedDate
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedOn;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }
    
    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public enum Status{
    	ACTIVE,
    	INACTIVE,
    	DELETED,
    	DRAFT, //quotations status
    	CONFIRMED, //quotation status
    	TO_BE_CONFIRMED, //quotation status
    	CLASS_A, //quotation status
        PIPELINE //quotation status

    }
    public enum RenewStatusEnum {
        Success,
        Failed
    }

}
