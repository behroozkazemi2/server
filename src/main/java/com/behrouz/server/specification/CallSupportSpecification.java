package com.behrouz.server.specification;

import com.behrouz.server.model.CallSupportEntity;
import com.behrouz.server.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.specification
 * Project Name: behta-server
 * 18 December 2018
 **/
public class CallSupportSpecification {


    public static Specification<CallSupportEntity> searchByTrackingCodeAndInsertDateBetweenAndCustomerIds (
            String trackingCode,
            @Temporal(TemporalType.DATE) Date from,
            @Temporal(TemporalType.DATE) Date to,
            int statusId,
            List< Long > customerIds ) {

        return (Specification < CallSupportEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {

            criteriaQuery.distinct( true );

            Collection< Predicate > predicates = new ArrayList<>();


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

}
