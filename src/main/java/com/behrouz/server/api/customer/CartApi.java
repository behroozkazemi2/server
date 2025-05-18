package com.behrouz.server.api.customer;

import com.behrouz.server.api.customer.response.*;
import com.behrouz.server.modelOption.PaymentMethodOption;
import com.behrouz.server.repository.*;
import com.behrouz.server.rest.request.IdLong;
import com.behrouz.server.values.BankValuesConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.api.customer.request.CartAddProductRequest;
import com.behrouz.server.api.customer.request.FactorRequest;
import com.behrouz.server.api.customer.request.PaymentAmount;
import com.behrouz.server.api.customer.request.ProductProviderCategoryRequest;
import com.behrouz.server.api.customer.response.*;
import com.behrouz.server.bank.saman.MelliBankComponent;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.base.exception.ApiActionSecurityException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.PaymentComponent;
import com.behrouz.server.component.ProductProviderComponent;
import com.behrouz.server.component.ProviderComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.CandidateTimeEntity;
import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.model.balance.BalanceHistoryEntity;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.*;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.LocalDateTimeUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.utils.city.CityLocationUtils;
import com.behrouz.server.utils.city.CityOption;
import com.behrouz.server.utils.date.PersianDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer
 * Project server
 * 06 October 2018 13:49
 **/

/**
 * When user add something into cart, We add it in {@link CustomerOrderEntity}
 *
 * We keep every single movement into {@link }
 *
 * 1) Add {@link #cartAdd(CartAddProductRequest, int)} :
 *
 *      provider has this product right now ?
 *
 *          is same order ?
 *              change number of it
 *          else
 *              add it to {@link CustomerOrderEntity}
 *
 * 2) delete {@link #cartDelete(IdRequest, int)} :
 *
 *      Just delete it from cart
 *
 * 3) deleteAll {@link #cartDeleteAll(int)} :
 *
 *      delete every single order that a customer has
 *
 * 4) cartList {@link #cartList(int)} :
 *
 *      show all order for a customer
 *
 */
public class CartApi extends BaseApi {


    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private ProductProviderComponent productProviderComponent;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OffCodeRepository offCodeRepository;

    @Autowired
    private CandidateTimeRepository candidateTimeRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;

    @Autowired
    private PaymentComponent paymentComponent;

    @Autowired
    private BillProductProviderRepository billProductProviderRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private MelliBankComponent melliBankComponent;

    @Autowired
    private ProviderComponent providerComponent;


    @ApiAction( value = "app.customer.cart.add" )
    public ApiResponseBody cartAdd(CartAddProductRequest request, int customerId ) throws ApiActionException {

        if(request.getCount() == 0){
            return cartDelete(new IdRequest(request.getId()), customerId);
        }

        ProductProviderEntity productProvider =
                productProviderRepository.findFirstByIdAndDeletedIsFalse( request.getId() );


        AddressEntity currentAddress =
                addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId,request.getAddressId());
        LatLngData currentLatLng = currentAddress == null ? new LatLngData() : new LatLngData(currentAddress);

        List<Long> currentAreaProviders = providerComponent.getAllProviderInRegional(currentLatLng)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());

        // TODO CHANGE PROVIDER ID TO LIST LATER
//        if ( request.getAddressId() != -1 && (currentAreaProviders != null && currentAreaProviders.get(0) != productProvider.getProvider().getId() )){
//            throw new ApiActionNotFoundException( "تامیین کننده مورد‌نظر در این محدوده فعالیت ندارد." );
//        }

        if ( productProvider == null ) {
            throw new ApiActionNotFoundException( "کالا مورد نظر یافت نشد!" );
        }

        if ( !productProvider.isProductProviderExistence() ){
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "کالا مورد نظر تمام شده است!"
            );
        }

        if(request.getCount() < productProvider.getMinAllow()){
            throw new ApiActionNotFoundException( "حداقل میزان سفارش "  + productProvider.getMinAllow() + " می باشد.");
        }

        if(request.getCount() > productProvider.getMaxAllow()){
            throw new ApiActionNotFoundException( "حداکثر میزان سفارش "  + productProvider.getMaxAllow() + " می باشد.");
        }

        CustomerEntity customer = getCustomerById( customerId );
        CustomerOrderEntity oldOrder = customerOrderRepository.findFirstByCustomer_IdAndProductProvider_IdAndDeletedIsFalse(customerId, request.getId());
        if(oldOrder != null){
            oldOrder.setDeleted(true);
            customerOrderRepository.save(oldOrder);
        }

        CustomerOrderEntity order =
                new CustomerOrderEntity(
                        productProvider,
                        customer,
                        request.getCount()
                );
        customerOrderRepository.save(order);


        return new ApiResponseBody<>().ok();
    }

    @ApiAction( value = "app.customer.cart.product.check.count" ,tokenRequired = false)
    public ApiResponseBody<Void> checkProductCount(@ApiActionParam(nullable = false) IdLong productIdCount, int customerId ) throws ApiActionException  {

        if(productIdCount.getId() == 0 ){
            throw new ApiActionNotFoundException( " محصول یافت نشد."  );
        }

        ProductProviderEntity productProvider =
                productProviderRepository.findFirstByIdAndDeletedIsFalse( productIdCount.getId() );

        if ( productProvider == null ) {
            throw new ApiActionNotFoundException( "کالا مورد نظر یافت نشد!" );
        }

        if ( !productProvider.isProductProviderExistence() ){
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "محصول " +
                            productProvider.getProduct().getName() +
                            " ناموجود می‌باشد."
            );
        }

        if(productIdCount.getValue() > productProvider.getProductCount()){
            throw new ApiActionNotFoundException(
                    "میزان سفارش شما برای محصول " +
                    productProvider.getProduct().getName() +
                    " بیشتر از تعداد کالای موجود می‌باشد"
            );
        }

        return new ApiResponseBody<>().ok();
    }
    public void checkProductCount( ProductProviderEntity pp, long productInCartCount ) throws ApiActionException {
        if ( pp == null ) {
            throw new ApiActionNotFoundException( "کالا مورد نظر یافت نشد!" );
        }

        if ( !pp.isProductProviderExistence() ){
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "محصول " +
                            pp.getProduct().getName() +
                            " ناموجود می‌باشد."
            );
        }

        if(productInCartCount > pp.getProductCount()){
            throw new ApiActionNotFoundException(
                    "میزان سفارش شما برای محصول " +
                    pp.getProduct().getName() +
                    " بیشتر از تعداد کالای موجود می‌باشد"
            );
        }
    }


    @ApiAction( value = "app.customer.cart.delete")
    public ApiResponseBody cartDelete(@ApiActionParam(nullable = false) IdRequest request, int customerId ) throws ApiActionException {

        CustomerOrderEntity oldOrder =
                customerOrderRepository.findFirstByCustomer_IdAndProductProvider_IdAndDeletedIsFalse(customerId, request.getId());


        if(oldOrder == null){
            throw new ApiActionWrongDataException(
                    "مشکل در حذف کالا! لطفا با پشتیبانی تماس بگیرید."
            );
        }

        oldOrder.setDeleted(true);
        customerOrderRepository.save(oldOrder);

        return new ApiResponseBody().ok();
    }


    @ApiAction( value = "app.customer.cart.delete.all")
    public ApiResponseBody cartDeleteAll( int customerId ) throws ApiActionException {
        productProviderComponent.clearInCartProduct(customerId);
        return new ApiResponseBody().ok();
    }

    @ApiAction( value = "app.customer.cart.list")
    public ApiResponseBody<List<CartItemResponse>> cartList(int customerId ) throws ApiActionException {

        List < CustomerOrderEntity > customerOrders =
                customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse( customerId );

        List<Long> ids =
                customerOrders.stream().filter(m -> m.getProductProvider() != null).map(m -> m.getProductProvider().getId()).collect(Collectors.toList());

//        List<Long> specIds =
//                customerOrders.stream().filter(m -> m.getSpecialProduct() != null).map(m -> m.getSpecialProduct().getId()).collect(Collectors.toList());
//

        List<CartItemResponse> responseBody = new ArrayList<>();

//        if(ArraysUtil.isNullOrEmpty(ids) && ArraysUtil.isNullOrEmpty(specIds)){
//            return new ApiResponseBody().ok(responseBody);
//        }

        ProductProviderCategoryRequest request = new ProductProviderCategoryRequest();
        request.setLength(200);
        request.setProductIds(ids);
        request.setAddressId(-1);


        List<Long> orderIds =
                customerOrders.stream().filter(e -> e.getProductProvider() != null).map(CustomerOrderEntity::getId).collect(Collectors.toList());
//
//        List<CustomerOrderSubFeatureEntity> ordersFeature =  ArraysUtil.isNullOrEmpty(orderIds) ? new ArrayList<>() :
//                customerOrderSubFeatureRepository.findAllByOrderIdIn(orderIds);

//        Map<Integer, List<Integer>> ordersFeatureMap = ordersFeature.stream()
//                .collect(
//                        Collectors.groupingBy(
//                                g -> g.getOrder().getId()
//                        )
//                ).entrySet()
//                .stream()
//                .collect(
//                        Collectors.toMap(
//                                Map.Entry::getKey,
//                                v -> v.getValue().stream().map(m2 -> m2.getSubFeature().getId()).collect(Collectors.toList())
//                        )
//                );

        Map<Long, CustomerOrderEntity> orderMap = customerOrders.stream()
                .filter(f -> f.getProductProvider() != null)
                .collect(
                        Collectors.groupingBy(
                                g -> g.getProductProvider().getId()
                        )
                ).entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                v -> v.getValue().get(0)
                        )
                );

        List<ProductProviderPriceEntity> pricing = ArraysUtil.isNullOrEmpty(ids) ? new ArrayList<>() :
                jdbcTemplate.query(
                        "SELECT " +
                                "ppl.id id, " +
                                "ppl.primitive_amount pAm, " +
                                "ppl.off_percent oPer, " +
                                "ppl.off_price oPri, " +
                                "ppl.final_amount fA, " +
                                "ppl.product_provider_id pid " +
                                "FROM product_price_last ppl WHERE ppl.product_provider_id in (:ids) ",
                        new MapSqlParameterSource("ids", ids),
                        (res, rowId) -> new ProductProviderPriceEntity(
                            res.getInt("id"),
                                res.getLong("pAm"),
                                res.getLong("oPer"),
                                res.getLong("oPri"),
                                res.getLong("fA"),
                                new ProductProviderEntity(){{setId(res.getInt("pid"));}}
                        )
                );



        Map<Long, ProductProviderPriceEntity> pricingMap = pricing.stream().collect(
                Collectors.groupingBy(g -> g.getProductProvider().getId())
        ).entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                v -> v.getValue().stream().max(
                                        Comparator.comparingLong(o -> LocalDateTimeUtil.localDateTimeToDate(o.getInsertDate()).getTime())
                                ).get()
                        )
                );
//
//        List<ProductProductSubFeatureEntity> productSubFeature = ArraysUtil.isNullOrEmpty(ids) ? new ArrayList<>() :
//                productProductSubFeatureRepository.findAllByDeletedIsFalseAndProductProvider_IdIn(ids);
//        Map<Integer, List<ProductProductSubFeatureEntity>> productSubFeatureMap = productSubFeature.stream()
//                .collect(
//                        Collectors.groupingBy(
//                                g -> g.getProductProvider().getId()
//                        )
//                );

        DataTableResponse<ProductResponse> productProviders = ArraysUtil.isNullOrEmpty(ids) ? null :
                productProviderComponent.searchProduct(
                        request,
                        customerId
                );


        responseBody = productProviders == null ? new ArrayList<>() : productProviders.getData()
                .stream()
                .map(m -> {

                    CustomerOrderEntity order = orderMap.get(m.getId());
//                    List<Integer> feature = ordersFeatureMap.get(order.getId());


//                    List<CustomerOrderSubFeatureEntity> foundFeature = ArraysUtil.isNullOrEmpty(feature) ? null :
//                            ordersFeature.stream().filter(e -> feature.contains(e.getSubFeature().getId())).collect(Collectors.toList());

                    ProductProviderPriceEntity price = pricingMap.get(m.getId());

                    long a, b = 0;
                    a = price.getFinalAmount();

//                    if (!ArraysUtil.isNullOrEmpty(foundFeature)) {
//                        for (CustomerOrderSubFeatureEntity pSub : foundFeature) {
//                            Optional<ProductProductSubFeatureEntity> sub =
//                                    productSubFeatureMap.get(m.getId()).stream().filter(f3 -> f3.getSubFeature().getId() == pSub.getSubFeature().getId()).findFirst();
//                            if (sub.isPresent()) {
//                                if (sub.get().isChangedByUnit()) {
//                                    a += sub.get().getAmount();
//                                } else {
//                                    b += sub.get().getAmount();
//                                }
//                            }
//                        }
//                    }


                    long totalAmount = (long) (a * order.getOrderCount() + b);

                    return new CartItemResponse(
                            m,
                            "",
                            order.getOrderCount(),
                            order.getId(),
                            totalAmount

                    );
                }).collect(Collectors.toList());


//        if(!ArraysUtil.isNullOrEmpty(specIds)){
//            List<SpecialProductProviderEntity> specials =
//                    specialProductProviderRepository.findAllByIdIn(specIds);
//
//            List<ProviderSpecialProductSuggestionEntity> specialSuggestion =
//                    providerSpecialProductSuggestionRepository.findAllBySpecialProduct_IdIn(specIds);
//
//            Map<Long, Map<Integer, List<ProviderSpecialProductSuggestionEntity>>> suggestionMap =
//                    specialSuggestion.stream().collect(Collectors.groupingBy(g -> g.getSpecialProduct().getId(), Collectors.groupingBy(g2 -> g2.getProvider().getId())));
//
//
//            List<IdNameType> specialImages = jdbcTemplate.query(
//                    "SELECT sppi.special_product_provider_id id, sppi.image_id img FROM special_product_provider_image sppi WHERE sppi.deleted = FALSE AND sppi.special_product_provider_id in (:ids) ",
//                    new MapSqlParameterSource("ids", specIds),
//                    (res, rowId) -> new IdNameType(
//                            res.getInt("id"),
//                            res.getInt("img")
//                    )
//            );
//
//            Map<Integer, List<Integer>> specialImageMap = specialImages.stream().collect(Collectors.groupingBy(IdNameType::getId)).entrySet().stream().collect(Collectors.toMap(
//                    Map.Entry::getKey,
//                    v -> v.getValue().stream().map(IdNameType::getType).collect(Collectors.toList())
//            ));
//
//
//            List<CartItemResponse> addResult = specials.stream().map(m -> {
//
//                List<ProviderSpecialProductSuggestionEntity> curSuggests =
//                        suggestionMap.containsKey(m.getId()) && suggestionMap.get(m.getId()).containsKey(m.getAcceptedProvider().getId()) ?
//                                suggestionMap.get(m.getId()).get(m.getAcceptedProvider().getId()) : null;
//
//                if (ArraysUtil.isNullOrEmpty(curSuggests)) {
//                    return null;
//                }
//                ProviderSpecialProductSuggestionEntity curSuggest = curSuggests.get(0);
//
//                List<ProductFeatureResponse> curFeature = curSuggest.getExtraAmount() == 0 ? null :
//                        Collections.singletonList(
//                                new ProductFeatureResponse(
//                                        0,
//                                        m.getCategory().getId(),
//                                        "هزینه اضافی",
//                                        Collections.singletonList(
//                                                new ProductSubFeatureResponse(
//                                                        0,
//                                                        "هزینه اضافی",
//                                                        curSuggest.getExtraAmount(),
//                                                        false
//                                                )
//                                        )
//                                )
//                        );
//
//
//                ProductResponse product =
//                        new ProductResponse(
//                                (int) m.getId(),
//                                "سفارش ویژه",
//                                0,
//                                0,
//                                true,
//                                curSuggest.getDescription(),
//                                curSuggest.getDescription(),
//                                new IdName(curSuggest.getProvider().getId(), curSuggest.getProvider().getName()),
//                                null,
//                                new IdName(curSuggest.getUnit().getId(), curSuggest.getUnit().getName()),
//                                new IdName(m.getCategory().getId(), m.getCategory().getName()),
//                                0, 0, 0,
//                                curSuggest.getHourToCreation(),
//                                curSuggest.getUnitAmount(),
//                                0,
//                                0,
//                                curSuggest.getUnitAmount(),
//                                specialImageMap.get((int) m.getId()),
//                                null,
//                                curFeature
//                        );
//
//                return new CartItemResponse(
//                        product,
//                        m.getDescription(),
//                        (float) curSuggest.getCountValue(),
//                        curSuggest.getExtraAmount() == 0 ? null : Collections.singletonList(0),
//                        0,
//                        curSuggest.getFinalAmount(),
//                        true
//
//                );
//
//            }).collect(Collectors.toList());
//
//            responseBody.addAll(addResult);
//
//        }

        responseBody = responseBody.stream().map(CartItemResponse::toToman).collect(Collectors.toList());


        return new ApiResponseBody <>().ok(responseBody);
    }

    @ApiAction( value = "app.customer.factor.get.candidate" )
    public ApiResponseBody getCandidateDateTime ( int customerId ) throws ApiActionException {

        ApiResponseBody<List<CartItemResponse>> listResponse =
                cartList(customerId);

        List<CartItemResponse> cart = listResponse.getData();

        OptionalLong maxOption =
                cart.stream().mapToLong(ProductResponse::getPrepareHour).max();

        if(!maxOption.isPresent()){
            return new ApiResponseBody <>().ok( new CandidateDateTimeResponse( new ArrayList<>(), new ArrayList<>()) );
        }

        long minPrepareHour = maxOption.getAsLong();


        LocalDateTime fromDate =
                LocalDateTime.now().plusHours(minPrepareHour);

        LocalDateTime toDate =
                fromDate.plusWeeks(1);

        List < Date > dateList = Stream.iterate( fromDate, date -> date.plusDays( 1 ) )
                .limit( ChronoUnit.DAYS.between( fromDate, toDate ) )
                .map( e -> new Date( e.atZone( ZoneId.systemDefault() ).toInstant().toEpochMilli() ) )
                .collect( Collectors.toList() );


        List <RequestDetailResponse> timeList =
                jdbcTemplate.query(
                        "SELECT ct.id id, ct.name nm FROM candidate_time ct WHERE ct.deleted = false ORDER BY ct.time" ,
                        (res, rowId) -> new RequestDetailResponse(
                                res.getLong("id"),
                                res.getString("nm")
                        )
                );


        return new ApiResponseBody <>().ok( new CandidateDateTimeResponse( dateList, timeList ) );
    }

    @ApiAction( value = "app.customer.factor.candidate.check" )
    public ApiResponseBody<Void> chandidateCheck (@ApiActionParam(nullable = false) FactorRequest request, int customerId ) throws ApiActionException {


        ApiResponseBody<List<CartItemResponse>> listResponse =
                cartList(customerId);

        List<CartItemResponse> cart = listResponse.getData();

        if (ArraysUtil.isNullOrEmpty(cart)) {
            return new ApiResponseBody().ok(new FactorPaymentResponse());
        }


        if((request.getSelectedDate() == null && request.getTimeId() != 0) || (request.getSelectedDate() != null && request.getTimeId() == 0) ){
            throw new ApiActionWrongDataException("ساعت و زمان تحویل را انتخاب کنید");
        }

        if(request.getSelectedDate() != null) {
            CandidateTimeEntity time = request.getTimeId() == 0 ? null :
                    candidateTimeRepository.findFirstById(request.getTimeId());
            if (time == null) {
                throw new ApiActionWrongDataException("لطفا زمان تحویل را مشخص کنید.");
            }
            long prepareHour = cart.stream().mapToLong(ProductResponse::getPrepareHour).max().getAsLong();
            LocalDateTime minPrepareHour = LocalDateTime.now().plusHours(prepareHour);


            LocalDateTime selectedHour = LocalDateTime.of(
                    request.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    time.getTime()
            );

            if(!selectedHour.isAfter(minPrepareHour)){
                throw new ApiActionWrongDataException("حداقل زمان تحویل این سفارش در تاریخ " +
                        PersianDateUtil.getPersianDate(request.getSelectedDate().getTime()) + " " +
                        "ساعت " + minPrepareHour.getHour() + " می باشد."
                );
            }


        }
        return new ApiResponseBody().ok();
    }

    @ApiAction( value = "app.customer.factor.amount" )
    public ApiResponseBody<FactorPaymentResponse> factorAmount (@ApiActionParam(nullable = false) FactorRequest request, int customerId ) throws ApiActionException {



        ApiResponseBody<List<CartItemResponse>> listResponse =
                cartList(customerId);

        List<CartItemResponse> cart = listResponse.getData();

        if(ArraysUtil.isNullOrEmpty(cart)){
            return new ApiResponseBody().ok(new FactorPaymentResponse());
        }


//        if((request.getSelectedDate() == null && request.getTimeId() != 0) || (request.getSelectedDate() != null && request.getTimeId() == 0) ){
//            throw new ApiActionWrongDataException("ساعت و زمان تحویل را انتخاب کنید");
//        }
//
//        if(request.getSelectedDate() != null) {
//            CandidateTimeEntity time = request.getTimeId() == 0 ? null :
//                    candidateTimeRepository.findFirstById(request.getTimeId());
//            if (time == null) {
//                throw new ApiActionWrongDataException("لطفا زمان تحویل را مشخص کنید.");
//            }
//            long prepareHour = cart.stream().mapToLong(ProductResponse::getPrepareHour).max().getAsLong();
//            LocalDateTime minPrepareHour = LocalDateTime.now().plusHours(prepareHour);
//
//
//            LocalDateTime selectedHour = LocalDateTime.of(
//                    request.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
//                    time.getTime()
//            );
//
//            if(!selectedHour.isAfter(minPrepareHour)){
//                throw new ApiActionWrongDataException("حداقل زمان تحویل این سفارش در تاریخ " +
//                        PersianDateUtil.getPersianDate(request.getSelectedDate().getTime()) + " " +
//                        "ساعت " + minPrepareHour.getHour() + " می باشد."
//                );
//            }
//
//
//        }

        long primitiveAmount = 0;
        long offPriceAmount = 0;
        long deliveryAmount = 0; // TODO
        long availableInBalance = 0;
        long offCodeAmount = 0;
        String offCodeMessage = "";
        boolean offCodeMessageState = false;
        long finalAmount = 0;
        long taxAmount = 0;
        long totalOffPricePercent = 0;
        long count = 0;

        count = cart.stream().mapToLong(m -> (long) (m.getInCartCount())).sum();
        offPriceAmount = cart.stream().mapToLong(m -> (long) (m.getOffPrice() * m.getInCartCount())).sum();
        primitiveAmount = cart.stream().mapToLong(CartItemResponse::getTotalAmount).sum() + offPriceAmount;

        finalAmount = primitiveAmount - offPriceAmount;




//        AddressEntity address = request.getAddressId() == 0 ? null :
//                addressRepository.findFirstByIdAndDeletedIsFalse(request.getAddressId());

//        if(address != null){
//            if( address.getAccount().getId() != customerId ){
//                throw new ApiActionSecurityException();
//            }else{
//                deliveryAmount = calculateDeliveryAmount(address) / 10;
//            }
//        }
//        if (request.getAddressId() == 0){
//            deliveryAmount = 1000;
//        }

        availableInBalance  = getCustomerBalance(customerId)/ 10;;

        OffCodeEntity offCode = StringUtil.isNullOrEmpty(request.getOffCode()) ? null :
                offCodeRepository.findFirstByCodeAndDeletedIsFalse(request.getOffCode());

        if(!StringUtil.isNullOrEmpty(request.getOffCode()) && offCode == null){
            offCodeMessage = "کد تخفیف وارد شده صحیح نمی‌باشد.";
        }
        if(!StringUtil.isNullOrEmpty(request.getOffCode()) && offCode != null){
            if(!offCode.isForAll() && offCode.getCustomer() != null && offCode.getCustomer().getId() != customerId){
                offCodeMessage = "این تخفیف برای شما قابل استفاده نیست.";
            }else if(offCode.getExpireDate() != null && offCode.getExpireDate().getTime() < new Date().getTime() ){
                offCodeMessage = "مهلت استفاده از این تخفیف به پایان رسیده.";
            }else if(offCode.getStartDate() != null && offCode.getStartDate().getTime() > new Date().getTime() ){
                offCodeMessage = "تخفیف هنوز فعال نشده ;)";
            }else if(finalAmount < offCode.getMinAmount() ) {
                offCodeMessage = "مبلغ سفارش از حداقل مبلغ مجاز برای اعمال تخفیف کمتر است.";
            }
            else {

                List<Long> usages = jdbcTemplate.query(
                        "SELECT count(*) " +
                                "FROM bill_bill_status_last bbl " +
                                "INNER JOIN bill bl ON bl.id = bbl.bill_id " +
                                "WHERE bbl.bill_status_id not in (:sid) " +
                                "AND bl.customer_id = :cid " +
                                "AND bl.off_code_id = :fid ",
                        // TODO ASK Hapi
                        new MapSqlParameterSource("sid", Arrays.asList(BillStatusOption.WAIT_FOR_PAY.getId(), BillStatusOption.CANCELED.getId() ))
                        .addValue("cid", customerId)
                        .addValue("fid", offCode.getId()),
                        (res, rowId) -> res.getLong(1)
                );
                long usedCount = ArraysUtil.isNullOrEmpty(usages) ? 0 : usages.get(0);
                if(usedCount >= offCode.getUsageCount()){
                    offCodeMessage = "شما از این کد تخفیف " + usedCount + " مرتبه استفاده کرده اید.";
                }else{
                    offCodeMessage = "تبریک! " + offCode.getDescription();
                    offCodeMessageState = true;
                    offCodeAmount = (long) (finalAmount * offCode.getPercent() / 100.0);
                    if(offCodeAmount > offCode.getMaxAmount()){
                        offCodeAmount = (long) offCode.getMaxAmount();
                    }
                }

            }
        }

        long finalWithoutTax = primitiveAmount + deliveryAmount - offPriceAmount - offCodeAmount;

//        taxAmount = (long) (finalWithoutTax * 0.09);
        taxAmount = 0;

        finalAmount = primitiveAmount + deliveryAmount - offPriceAmount - offCodeAmount + taxAmount;


        FactorPaymentResponse response =
                new FactorPaymentResponse(
                        count  ,
                        primitiveAmount,
                        offPriceAmount,
                        offCodeAmount,
                        deliveryAmount,
                        taxAmount,
                        finalAmount,
                        availableInBalance,
                        offCodeMessage,
                        offCodeMessageState
                );

        return new ApiResponseBody<>().ok(response);

    }



    @ApiAction( value = "app.customer.factor.new" )
    @Transactional(rollbackFor = { Exception.class})
    public ApiResponseBody factorNew (FactorRequest request, int customerId ) throws ApiActionException, BehtaShopException {

        CustomerEntity customer =
                getCustomerById(customerId);

        if(request.getSelectedDate() == null){
            throw new ApiActionWrongDataException("لطفا تاریخ تحویل را مشخص کنید.");
        }

        CandidateTimeEntity time = request.getTimeId() == 0 ? null :
                candidateTimeRepository.findFirstById(request.getTimeId());

        if (time == null) {
            throw new ApiActionWrongDataException("لطفا زمان تحویل را مشخص کنید.");
        }

        if(request.getSelectedDate() == null){
            throw new ApiActionWrongDataException("لطفا تاریخ تحویل را مشخص کنید.");
        }



        AddressEntity address = request.getAddressId() == 0 ? null :
                addressRepository.findFirstByIdAndDeletedIsFalse(request.getAddressId());

        if (address == null) {
            throw new ApiActionWrongDataException("آدرس خود را مشخص کنید.");
        }
        if(address.getAccount().getId() != customerId){
            throw new ApiActionSecurityException();
        }
        OffCodeEntity offCode = StringUtil.isNullOrEmpty(request.getOffCode()) ? null :
                offCodeRepository.findFirstByCodeAndDeletedIsFalse(request.getOffCode());

        if(!StringUtil.isNullOrEmpty(request.getOffCode()) && offCode == null){
            throw new ApiActionWrongDataException("تخفیف وارد شده صحیح نمی‌باشد.");
        }

        if(offCode != null){
            if(!offCode.isForAll() && offCode.getCustomer() != null){
                throw new ApiActionWrongDataException("تخفیف وارد برای شما قابل استفاده نمی‌باشد.");
            }

            if (offCode.getExpireDate() != null && offCode.getExpireDate().getTime() < new Date().getTime()){
                throw new ApiActionWrongDataException("فرصت استفاده از تخفیف به پایان رسیده است.");
            }

            if (offCode.getStartDate() != null && offCode.getStartDate().getTime() > new Date().getTime()){
                throw new ApiActionWrongDataException("مهلت استفاده از تخفیف آغاز نشده است.");
            }

        }

        ApiResponseBody<List<CartItemResponse>> listResponse =
                cartList(customerId);

        List<CartItemResponse> cart = listResponse.getData();

        if(ArraysUtil.isNullOrEmpty(cart)){
            throw new ApiActionWrongDataException("لطفا سبد خرید خود را پر کنید.");
        }

        long prepareHour =
                cart.stream().mapToLong(ProductResponse::getPrepareHour).max().getAsLong();

        List<Long> productProviderIds =
                cart.stream().map(
                        m-> m.getId())
                        .collect(Collectors.toList());

        List<ProductProviderEntity> productProvideList =
                productProviderRepository.findAllByIdInAndDeletedIsFalse(productProviderIds);
        if (ArraysUtil.isNullOrEmpty(productProvideList) || productProvideList.size() != cart.size()){
            throw new ApiActionWrongDataException("حطا! محصول یافت نشد.");
        }

        Map<Long, ProductProviderEntity> ppMap =
                productProvideList.stream().collect(
                        Collectors.toMap(
                                BaseEntity::getId,
                                v -> v
                        )
                );
        for ( CartItemResponse item : cart ) {
            checkProductCount(ppMap.get(item.getId()), (long) item.getInCartCount());
        }

        LocalDateTime minPrepareHour = LocalDateTime.now().plusHours(prepareHour);

        LocalDateTime selectedHour = LocalDateTime.of(
                request.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                time.getTime()
        );

//        if(!selectedHour.isAfter(minPrepareHour)){
//            throw new ApiActionWrongDataException("حداقل زمان تحویل این سفارش در تاریخ " +
//                    PersianDateUtil.getPersianDate(request.getSelectedDate().getTime()) + " " +
//                    "ساعت " + minPrepareHour.getHour() + " می باشد."
//            );
//        }



        long primitiveAmount = 0;
        long offPriceAmount = 0;
        long deliveryAmount = 0;
        long offCodeAmount = 0;
        long finalAmount = 0;
        long taxAmount = 0;

        offPriceAmount = cart.stream().mapToLong(f -> (long) (f.getOffPrice() * f.getInCartCount())).sum();
        primitiveAmount = cart.stream().mapToLong(f -> (long) (f.getPrimitiveAmount() * f.getInCartCount())).sum();
        finalAmount = primitiveAmount - offPriceAmount;


//        if (finalAmount < 50000){
//            throw new ApiActionWrongDataException("حداقل مبلغ سفارش ازین تأمین کننده 50,000 تومان می باشد");
//
//        }
        if(offCode != null) {
            offCodeAmount = (long) (finalAmount * offCode.getPercent() / 100.0);
            if (offCodeAmount > offCode.getMaxAmount()) {
                offCodeAmount = (long) offCode.getMaxAmount();
            }
        }

        deliveryAmount = 0;
//        if (request.getAddressId() == 0){
//            deliveryAmount = 1000;
//        }

//        long finalWithoutTax = primitiveAmount + deliveryAmount - offPriceAmount - offCodeAmount;

//        taxAmount = (long) (finalWithoutTax * 0.09);
        taxAmount = 0;

        finalAmount = primitiveAmount + deliveryAmount - offPriceAmount - offCodeAmount + taxAmount;


        BillEntity billEntity =
                new BillEntity(
                        customer,
                        primitiveAmount,
                        offPriceAmount,
                        offCodeAmount,
                        deliveryAmount,
                        taxAmount,
                        finalAmount,
                        address,
                        offCode,
                        request.getSelectedDate(),
                        time,
                        PaymentMethodOption.getById(request.getPaymentMethod())
                );
        billEntity.setDescription(request.getUserDescription());
        billRepository.save(billEntity);

        List<CustomerOrderEntity> customerOrder =
                customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse(customerId);

        if(ArraysUtil.isNullOrEmpty(customerOrder)){
            throw new ApiActionWrongDataException("خطا در محاسبه فاکتور");
        }

        List<Long> ppIds =
                cart.stream().map(ProductResponse::getId).collect(Collectors.toList());

        Map<Long, PriceRestResponse> priceMap = jdbcTemplate.query(
                "SELECT ppl.product_provider_id pid, ppl.id prc, ppl.primitive_amount pri, ppl.off_price dis " +
                        "FROM product_price_last ppl " +
                        "WHERE ppl.product_provider_id IN (:ids) ",
                new MapSqlParameterSource("ids", ppIds),
                (res, rowId) ->
                      new PriceRestResponse(
                                res.getLong("pid"),
                                res.getLong("prc"),
                                res.getLong("pri"),
                                res.getLong("dis"),
                              0
                        )
        ).stream().collect(Collectors.toMap(PriceRestResponse::getPpId, v -> v));

        Map<Long, Long> cartTotal = cart.stream().collect(Collectors.toMap(
                ProductResponse::getId,
                CartItemResponse::getTotalAmount
        ));



        // save be Toman
        List<BillProductProviderEntity> billOrder = customerOrder.stream().map(
                m -> new BillProductProviderEntity(
                        billEntity,
                        m.getProductProvider(),
                        m.getProductProvider() == null ? 0 : priceMap.get(m.getProductProvider().getId()).getPriceId(),
                        cartTotal.get(m.getProductProvider().getId()) ,
                        m.getOrderCount(),
                        m.getProductProvider() == null ? 0 : priceMap.get(m.getProductProvider().getId()).getPrimitiveAmount() / 10  ,
                        m.getProductProvider() == null ? 0 : priceMap.get(m.getProductProvider().getId()).getDiscountAmount() / 10
                )
        ).collect(Collectors.toList());

        billProductProviderRepository.saveAll(billOrder);


        BillBillStatusEntity billBillStatus =
                new BillBillStatusEntity(
                        billEntity,
                        new BillStatusEntity(){{setId(BillStatusOption.WAIT_FOR_PAY.getId());}},
                        customer
                );
        billBillStatusRepository.save(billBillStatus);


        long shouldPayWithBank = 0;
        String link = null;

        long availableInBalance = getCustomerBalance(customerId) / 10;

        System.out.println("\n availableInBalance :" + availableInBalance + " FactorAmount : " + billEntity.getPayableAmount());

        if ( availableInBalance < billEntity.getPayableAmount() && request.getPaymentMethod() == PaymentMethodOption.INTERNETI.getId()){
            shouldPayWithBank = billEntity.getPayableAmount() - availableInBalance;
            shouldPayWithBank *= 10;
            if(shouldPayWithBank < 1000){
                shouldPayWithBank = 1000;
            }
            try {
                String bankToken = paymentComponent.createLinkForFactor(
                        billEntity,
                        shouldPayWithBank
                );

                link = BankValuesConfiguration.SAMAN_PAY_URL + bankToken;

                System.out.println("\n\n\n" + "bankToken " + bankToken +" bankLink " + link  +"\n\n\n");

            } catch (BehtaShopException e) {
                e.printStackTrace();
                throw new ApiActionWrongDataException("خطا در عملیات خرید، لطفا بعدا تلاش‌ کنید.");

            }
        }else{

            BillBillStatusEntity mBillBillStatus =
                    new BillBillStatusEntity(
                            billEntity,
                            new BillStatusEntity(){{setId(BillStatusOption.PAYED.getId());}},
                            customer
                    );
            billBillStatusRepository.save(mBillBillStatus);

            if (request.getPaymentMethod() == PaymentMethodOption.INTERNETI.getId()) {
                BalanceEntity newBalance =
                        new BalanceEntity(
                                null,
                                availableInBalance * 10 - billEntity.getPayableAmount() * 10,
                                customer
                        );

                balanceRepository.save(newBalance);
                BalanceHistoryEntity history =
                        new BalanceHistoryEntity(
                                customer,
                                - billEntity.getPayableAmount() * 10,
                                0,
                                billEntity.getId(),
                                "-",
                                " پرداخت اعتبار برای سفارش " + billEntity.getId()
                        );


                balanceHistoryRepository.save(history);
            }

            cartDeleteAll(customerId);

            paymentComponent.sendSms(billEntity);

            List<ProductProviderEntity> productProviders = new ArrayList<>();

            for ( CartItemResponse item : cart ) {
                productProviders.add(reduceProductCount(ppMap.get(item.getId()), (long) item.getInCartCount()));
            }
            productProviderRepository.saveAll(productProviders);

            link = "https://shop.behrouz.com/pay/success/" + 0 + '/' + billEntity.getId();
        }

        return new ApiResponseBody <>().ok(
                new PaymentAmount(
                        shouldPayWithBank,
                        link,
                        billEntity.getId()
                )
        );
    }


    public ProductProviderEntity reduceProductCount(ProductProviderEntity productProvider, long inCart) throws ApiActionWrongDataException{
        long count = productProvider.getProductCount() - inCart;
        count = count < 0 ? 0 : count;
        productProvider.setProductCount(count);
        return productProvider;
    }


    @ApiAction( value = "web.cart.add.all")
    public ApiResponseBody cartAddAll ( List<CartAddProductRequest> request, int customerId ) throws ApiActionException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.readValue(mapper.writeValueAsString(request) , TypeFactory.defaultInstance().constructCollectionLikeType(List.class, CartAddProductRequest.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.forEach( e -> {
            try {
                cartAdd( e, customerId );
            } catch ( ApiActionException e1 ) {
//                e1.printStackTrace();
            }
        } );


        return new ApiResponseBody().ok();
    }


    public String createLink(BillEntity bill, long amount) throws BehtaShopException {
        BankTransactionEntity transaction = melliBankComponent.createTransaction(
                bill.getCustomer().getId(),
                bill.getCustomer().getMobile(),
                bill.getId(),
                amount
        );
        return transaction.getUserPayUrl();
    }
    public BillEntity successPayment(BillEntity bill ){
        return new BillEntity();
    }
    public BillEntity errorPayment(BillEntity bill ){
        // TODO Hapi error Payment
        return new BillEntity();
    }

    private long calculateDeliveryAmount(AddressEntity address) {
        assert address != null;

        if(address.getLocation() == null){
            return 0;
        }


        CityOption city = CityLocationUtils.city(address.getLocation().getCoordinate().y, address.getLocation().getCoordinate().x);
//
//        switch (city){
//            case Karaj:
//                return 250000;
//            case Tehran:
//                return 250000;
//            case Mashhad:
//                return 100000;
//        }

        return 0;
    }



}
