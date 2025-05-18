package com.behrouz.server.specification;

import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.modelOption.ProductProviderOption;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.specification
 * Project Name: behta-server
 * 17 December 2018
 **/
public class ProductProviderSpecification {


    public static Specification <ProductProviderEntity> searchAllBy(
            String productName,
            int providerId,
            int statusId ){

        return (Specification < ProductProviderEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {

            criteriaQuery.distinct( true );

            Collection< Predicate > predicates = new ArrayList<>();


            predicates.add(
                    criteriaBuilder.equal( root.get( "deleted" ), false )
            );

            predicates.add(
                    criteriaBuilder.equal( root.get( "customized" ), false )
            );


            if ( !StringUtil.isNullOrEmpty( productName ) ) {

                predicates.add( criteriaBuilder.like( root.get( "name" ), likeQuery(productName) ) );

            }


            if ( providerId != 0 ) {

                predicates.add( criteriaBuilder.equal( root.get( "provider" ).get( "id" ), providerId ) );

            }


            if ( statusId != 0 ){

                if ( statusId == ProductProviderOption.EXIST.getId() ){

                    predicates.add(
                            criteriaBuilder.equal( root.get( "productProviderExistence" ), true )
                    );

                } else if ( statusId == ProductProviderOption.NOT_EXIST.getId() ){

                    predicates.add(
                            criteriaBuilder.equal( root.get( "productProviderExistence" ), false )
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
