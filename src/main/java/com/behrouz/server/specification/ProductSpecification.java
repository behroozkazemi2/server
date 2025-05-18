package com.behrouz.server.specification;//package com.behrouz.server.specification;
//
//import com.behrouz.server.model.product.ProductEntity;
//import StringUtil;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.Collection;
//
///**
// * Created by Hapi KZM
// * Package com.behrouz.server.specification
// * Project server
// * 24 September 2018 14:56
// **/
//public class ProductSpecification {
//
//
//    public static Specification<ProductEntity > findAllBySearch ( String productName ) {
//
//        return (Specification < ProductEntity >) ( root, criteriaQuery, criteriaBuilder ) -> {
//
//            criteriaQuery.distinct( true );
//
//            final Collection < Predicate > predicates = new ArrayList <>();
//
//
//            predicates.add(
//                    criteriaBuilder.equal( root.get( "deleted" ), false )
//            );
//
//
//            /*
//                Product name
//             */
//            if ( !StringUtil.isNullOrEmpty( productName ) ) {
//
//                predicates.add(
//                        criteriaBuilder.like( root.get( "name" ), likeQuery( productName ) )
//                );
//
//            }
//
//
//            return criteriaBuilder.and( predicates.toArray( new Predicate[predicates.size()] ) );
//        };
//
//    }
//
//
//
//    private static String likeQuery(String param) {
//        return String.format( "%c%s%c",
//                '%', param ,'%' );
//    }
//
//}
