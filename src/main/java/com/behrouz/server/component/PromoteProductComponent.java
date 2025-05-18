package com.behrouz.server.component;

import com.behrouz.server.api.provider.request.PromoteSaveRequest;
import com.behrouz.server.api.provider.response.PromoteDigestResponse;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.PromoteEntity;
import com.behrouz.server.model.product.PromoteProductEntity;
import com.behrouz.server.repository.*;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.product.*;
import com.behrouz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project server
 * 24 September 2018 15:42
 **/
@Component
public class PromoteProductComponent {


    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private PromoteRepository promoteRepository;

    @Autowired
    private PromoteProductRepository promoteProductRepository;


    public DataTableResponse<PromoteDigestResponse> getList(int page, int limit, String search, long providerId) throws BehtaShopException {

        PageRequest pageRequest = PageRequest.of(page, limit);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageOffset", pageRequest.getOffset());
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("proId", providerId);
        params.put("searchStr", "%" + search + "%");
        String queryPart = "FROM promote  p " +
                "WHERE p.deleted = FALSE " +
                (StringUtil.isNullOrEmpty(search) ? "" :
                        "AND ( p.name LIKE :searchStr " +
                                "OR p.name LIKE :searchStr) ");

        String query =
                "SELECT " +
                        "p.id id, " +
                        "p.name nm, " +
                        "p.start_date sdt, " +
                        "p.end_date edt " +
                        queryPart +
                        "ORDER BY p.name " +
                        "LIMIT :pageLimit OFFSET :pageOffset";


        List<PromoteDigestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->

                        new PromoteDigestResponse(
                                res.getLong("id"),
                                res.getString("nm"),
                                res.getObject("sdt") == null ? 0: res.getTimestamp("sdt").getTime(),
                                res.getObject("edt") == null ? 0: res.getTimestamp("edt").getTime()
                        )
        );
        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";
        List<Long> totals =
                jdbcTemplate.query(
                        queryCount,
                        params,
                        (res, rowId) -> res.getLong(1)
                );

        long total = ArraysUtil.isNullOrEmpty(totals) ? 0L : totals.get(0);


        DataTableResponse<PromoteDigestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);


        return dateTable;
    }

    public PromoteDigestResponse getPromoteDetail(long promoteId) throws BehtaShopException {

        PromoteEntity promote =
                promoteRepository.findFirstByIdAndDeletedIsFalse(promoteId);
        if (promote == null) {
            throw new BehtaShopException(" رویداد پیدا نشد.");
        }

        List<IdName> products =
                promoteProductRepository.findAllByPromote_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse(promoteId)
                        .stream().distinct().map(m ->
                        new IdName(
                                m.getProductProvider().getId(),
                                m.getProductProvider().getProduct().getName() + "( " + m.getProductProvider().getProvider().getName() +  " )"
                         )
                ).collect(Collectors.toList());
        return new PromoteDigestResponse(promote, products);
    }

    public IdName savePromoteDetail(PromoteSaveRequest request) throws BehtaShopException {

        PromoteEntity promote = request.getId() == 0 ? null :
                promoteRepository.findFirstByIdAndDeletedIsFalse(request.getId());
        if (promote == null && request.getId() != 0) {
            throw new BehtaShopException(" رویداد پیدا نشد.");
        }
        if (request.getId() == 0) {
            promote = new PromoteEntity();
        }

        promote.setName(request.getName());
        promote.setStartDate(LocalDateTime.ofInstant(request.getStartDate().toInstant(), ZoneId.systemDefault()));
        promote.setEndDate(LocalDateTime.ofInstant(request.getEndDate().toInstant(), ZoneId.systemDefault()));
        promoteRepository.save(promote);


        List<PromoteProductEntity> oldProPro = request.getId() == 0 ? null :
                promoteProductRepository.findAllByPromote_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse(request.getId())
                        .stream().peek(p -> p.setDeleted(true)).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(oldProPro)) {
            promoteProductRepository.saveAll(oldProPro);
        }
        PromoteEntity finalPromote = promote;
        List<PromoteProductEntity> proPro = request.getProductProviderIds().stream().distinct().map(m -> {
            if (m != null) {
                ProductProviderEntity productProvider = productProviderRepository.findFirstByIdAndDeletedIsFalse(m);
                return new PromoteProductEntity(finalPromote, productProvider);
            }
            return null;
        }).collect(Collectors.toList());
        proPro = proPro.stream().filter(Objects::nonNull).collect(Collectors.toList());
        promoteProductRepository.saveAll(proPro);
        return new IdName(1,"موفقیت آمیز");
    }

    public void deletePromoteProduct(IdRequest request) throws BehtaShopException {

        PromoteEntity promt =
                promoteRepository.findFirstByIdAndDeletedIsFalse(request.getId());
        if (promt == null) {
            throw new BehtaShopException(" رویداد پیدا نشد.");
        }
        promt.setDeleted(true);
        promoteRepository.save(promt);


        List<PromoteProductEntity> oldProPro =
                promoteProductRepository.findAllByPromote_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse(promt.getId())
                        .stream().peek( p -> p.setDeleted(true)).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(oldProPro)) {
            promoteProductRepository.saveAll(oldProPro);
        }

    }
}
