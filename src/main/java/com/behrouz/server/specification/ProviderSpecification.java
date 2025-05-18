package com.behrouz.server.specification;

import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.rest.request.PanelSearchRequest;
import com.behrouz.server.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.specification
 * Project server
 * 23 September 2018 09:37
 **/

public class ProviderSpecification {


    private static final String PROVIDER_NAME       = "name";



    public static Specification<ProviderEntity> findAllBySearch (PanelSearchRequest searchRequest ) {

        return (Specification< ProviderEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {

            criteriaQuery.distinct( true );

            final Collection<Predicate > predicates = new ArrayList<>();

            String providerName = searchRequest.getpName();

            int statusId = searchRequest.getStatus();


            /*
                Provider name
             */
            if(!StringUtil.isNullOrEmpty( providerName )){
                final Predicate providerNamePredict = criteriaBuilder.like(
                        root.get( PROVIDER_NAME ),
                        likeQuery( providerName )
                );

                predicates.add( providerNamePredict );
            }


            if( statusId != 0 ) {

                final Predicate statusPredicate;

                if ( statusId == 1 ) {

                    statusPredicate =
                            criteriaBuilder.equal(
                                    root.get( "active" ),
                                    true
                            );

                    predicates.add( statusPredicate );

                } else if ( statusId == 2 ) {

                    statusPredicate =
                            criteriaBuilder.equal(
                                    root.get( "active" ),
                                    false
                            );

                    predicates.add( statusPredicate );

                }

            }



            return criteriaBuilder.and( predicates.toArray(new Predicate[predicates.size()]) );

        };

    }



    private static String likeQuery(String param) {
        return String.format( "%c%s%c",
                '%', param ,'%' );
    }
}
