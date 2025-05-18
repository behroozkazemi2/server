package com.behrouz.server.specification;

import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.specification
 * Project server
 * 1 December 2018 11:00
 **/
public class BillSpecification {


    public static Specification <BillEntity> searchByTrackingCodeAndInsertDateBetweenAndCustomerIds (
            String trackingCode,
            @Temporal(TemporalType.DATE) Date from,
            @Temporal(TemporalType.DATE) Date to,
            int statusId,
            List < Integer > customerIds ) {

        return (Specification < BillEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {

            criteriaQuery.distinct( true );

            Collection < Predicate > predicates = new ArrayList <>();


            if ( !StringUtil.isNullOrEmpty( trackingCode ) ) {

                predicates.add( criteriaBuilder.equal( root.get( "trackingCode" ), trackingCode ) );

            }


            if ( from != null ) {

                Date fromTemporalDate = changeTimestampToDate( from, 0, 0, 0 );

                final Predicate fromDatePredicate =
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get( "insertDate" ),
                                fromTemporalDate
                        );

                predicates.add( fromDatePredicate );
            }

            if ( to != null ) {

                Date toTemporalDate = changeTimestampToDate( to, 23, 59, 59 );

                final Predicate toDatePredicate =
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get( "insertDate" ),
                                toTemporalDate
                        );

                predicates.add( toDatePredicate );
            }


            if ( customerIds != null && !customerIds.isEmpty() ) {

                predicates.add( root.get( "customer" ).get( "id" ).in( customerIds ) );

            } else {

                predicates.add( root.get( "customer" ).get( "id" ).in( 0 ) );

            }

            if( statusId != 0 ) {

                final Predicate statusPredicate =
                        criteriaBuilder.equal(
                                root.get( "status" ).get("id"),
                                statusId
                        );

                predicates.add( statusPredicate );

            }


            return criteriaBuilder.and( predicates.toArray( new Predicate[predicates.size()] ) );
        };

    }

    private static Date changeTimestampToDate ( Date date, int hour, int minute, int second ) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set( Calendar.HOUR, hour );
        calendar.set( Calendar.MINUTE, minute );
        calendar.set( Calendar.SECOND, second );

        return calendar.getTime();
    }


    public static Specification < BillEntity > searchByTrackingCodeAndInsertDateBetweenAndCustomerIdsTEST (
            EntityManagerFactory entityManagerFactory,
            String trackingCode,
            Date from,
            Date to,
            List < Integer > customerIds ) {

//        CreateSpecification specification = new CreateSpecification <BillEntity>( BillEntity.class, entityManagerFactory );
//
//        specification.stringEqual( specification.getRoot().get( "trackingCode" ), trackingCode );
//
////        specification.dateBetween( specification.getRoot().get( "insertDate" ), from, to );
//
//        specification.idsIn( specification.getRoot().get( "customer" ).get( "id" ), customerIds );
//
//        return specification.execute();

        return null;
    }

}
