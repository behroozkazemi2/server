package com.behrouz.server.specification;

import com.behrouz.server.model.OffCodeEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.specification
 * Project Name: behta-server
 * 30 January 2019
 **/
public class OffCodeSpecification {



    public static Specification<OffCodeEntity> findFirstByCodeForCustomer(List<Long> productProviderIds,
                                                                          List<Long> providerIds,
                                                                          long customerId,
                                                                          String code ){

        return (Specification < OffCodeEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {

            criteriaQuery.distinct( true );

            Collection< Predicate > predicates = new ArrayList<>();


            predicates.add(
                    criteriaBuilder.equal( root.get( "deleted" ), false )
            );


            if ( code != null ) {

                predicates.add( criteriaBuilder.equal( root.get( "code" ), code ) );

            }


            Collection<Predicate> orPredicates = new ArrayList<>(  );

            if ( productProviderIds != null && !productProviderIds.isEmpty() ) {

                orPredicates.add( root.get( "productProvider" ).get( "id" ).in( productProviderIds ) );

            }


            if ( providerIds != null && !providerIds.isEmpty() ) {

                orPredicates.add( root.get( "provider" ).get( "id" ).in( providerIds ) );

            }


            if ( customerId != 0 ){

                orPredicates.add( criteriaBuilder.equal( root.get( "customer" ).get( "id" ), customerId ) );

            }

            orPredicates.add( criteriaBuilder.equal( root.get( "forAll" ), true ) );

            predicates.add( criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])) );


            return criteriaBuilder.and( predicates.toArray( new Predicate[predicates.size()] ) );
        };
    }

}
