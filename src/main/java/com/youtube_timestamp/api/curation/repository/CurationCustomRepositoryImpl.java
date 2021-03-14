package com.youtube_timestamp.api.curation.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.youtube_timestamp.api.curation.entity.Curation;

import com.youtube_timestamp.api.curation.entity.QCuration;
import com.youtube_timestamp.api.curation.entity.QCurationTodayCnt;
import com.youtube_timestamp.api.user.entity.QUserEntity;
import com.youtube_timestamp.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CurationCustomRepositoryImpl extends QuerydslRepositorySupport implements CurationCustomRepository {

    QCuration qCuration = QCuration.curation;

    QUserEntity qUser = QUserEntity.userEntity;

    QCurationTodayCnt qCurationTodayCnt = QCurationTodayCnt.curationTodayCnt;

    public CurationCustomRepositoryImpl() {
        super(Curation.class);
    }

    @Override
    public List<Curation> myList(UserEntity user) {
        return from(qCuration).join(qCuration.user,qUser).orderBy(new OrderSpecifier<>(Order.DESC,qCuration.createdAt)).limit(6).where(qUser.email.eq(user.getEmail())).fetch();
    }
}
