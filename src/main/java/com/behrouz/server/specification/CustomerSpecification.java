package com.behrouz.server.specification;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Hapi KZM
 * Package com.behrouz.server.specification
 * Project server
 * 1 December 2018 11:00
 **/
public class CustomerSpecification {



    public static Specification <CustomerEntity> searchByFirstNameAndLastNameAndMobile (
            String firstName,
            String lastName,
            String mobile,
            long statusId ) {

        return (Specification < CustomerEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {

            criteriaQuery.distinct( true );

            Collection< Predicate > predicates = new ArrayList<>();


            if ( !StringUtil.isNullOrEmpty( firstName ) ) {

                predicates.add( criteriaBuilder.like( root.get( "firstName" ), likeQuery(firstName) ) );

            }

            if ( !StringUtil.isNullOrEmpty( lastName ) ) {

                predicates.add( criteriaBuilder.like( root.get( "lastName" ), likeQuery(lastName) ) );

            }

            if ( !StringUtil.isNullOrEmpty( mobile ) ) {

                predicates.add( criteriaBuilder.equal( root.get( "mobile" ), mobile ) );

            }

            if ( statusId != 0 ){


                if ( statusId == 1 ){

                    predicates.add(
                            criteriaBuilder.equal( root.get( "banned" ), false )
                    );

                } else if ( statusId == 2 ){

                    predicates.add(
                            criteriaBuilder.equal( root.get( "banned" ), true )
                    );

                }

            }


            return criteriaBuilder.and( predicates.toArray( new Predicate[predicates.size()] ) );

        };

    }


    private static String likeQuery(String param) {
        return String.format( "%c%s%c",
                '%', param ,'%' );
    }
}
