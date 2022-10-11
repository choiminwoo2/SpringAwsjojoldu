package com.jojoldu.webservice.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // 게시글 등록하면 자동으로 날짜가 로그에 남는다.
    @CreatedDate
    private LocalDateTime createdDate;

    // 게시글 수정하면 자동으로 날자가 로그에 남는다.
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
