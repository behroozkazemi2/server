package com.behrouz.server.rest.response.digestList;//package com.behrouz.server.rest.response.digestList;
//
//import ProductTagProductEntity;
//import com.behrouz.server.model.product.ProductUnitEntity;
//import RequestDetailResponse;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Created by Hapi KZM
// * Package com.behrouz.server.rest.response.digestList
// * Project Koala Server
// * 10 September 2018 11:10
// **/
//public class ProductListDigestResponse {
//
//    private int id;
//
//    private String name;
//
//    private RequestDetailResponse category;
//
//    private List<RequestDetailResponse> tags;
//
//    private List<RequestDetailResponse> units;
//
//    public ProductListDigestResponse( ProductEntity product,
//                                      List< ProductTagProductEntity > productTagProducts,
//                                      List< ProductUnitEntity > productUnits ) {
//
//        this.id = product.getId();
//
//        this.name = product.getName();
//
//        if ( product.getCategoryId() != null ){
//
//            this.category =
//                    new RequestDetailResponse(
//                            product.getCategoryId().getId(),
//                            product.getCategoryId().getName()
//                    );
//
//        }
//
//        if ( productTagProducts != null ){
//
//            this.tags = productTagProducts
//                    .stream()
//                    .map( e ->
//                            new RequestDetailResponse(
//                                    e.getProductTag().getId(),
//                                    e.getProductTag().getName()
//                            )
//                    )
//                    .collect( Collectors.toList());
//
//        }
//
//        if ( productUnits != null ){
//
//            this.units =
//                    productUnits
//                            .stream()
//                            .map( e ->
//                                    new RequestDetailResponse(
//                                            e.getUnit().getId(),
//                                            e.getUnit().getName()
//                                    )
//                            )
//                            .collect( Collectors.toList());
//
//        }
//
//    }
//
//
//
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
//
//
//
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//
//    public RequestDetailResponse getCategoryId() {
//        return category;
//    }
//    public void setCategoryId( RequestDetailResponse category ) {
//        this.category = category;
//    }
//
//
//
//    public List<RequestDetailResponse> getTagsId() {
//        return tags;
//    }
//    public void setTagsId(List<RequestDetailResponse> tags) {
//        this.tags = tags;
//    }
//
//
//
//    public List<RequestDetailResponse> getUnits() {
//        return units;
//    }
//    public void setUnits(List<RequestDetailResponse> units) {
//        this.units = units;
//    }
//
//
//}
