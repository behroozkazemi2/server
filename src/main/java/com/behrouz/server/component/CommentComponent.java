package com.behrouz.server.component;

import com.behrouz.server.api.customer.request.CommentListRequest;
import com.behrouz.server.api.customer.request.CommentRequest;
import com.behrouz.server.api.customer.response.CommentResponse;
import com.behrouz.server.api.customer.response.CommentResponseList;
import com.behrouz.server.api.provider.request.CommentStatusRequest;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.model.CommentEntity;
import com.behrouz.server.model.CommentStatusEntity;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.modelOption.CommentStatusOption;
import com.behrouz.server.repository.CommentRepository;
import com.behrouz.server.repository.CommentStatusRepository;
import com.behrouz.server.repository.ProductProviderRepository;
import com.behrouz.server.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 15 September 2020
 **/
@Component
public class CommentComponent {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentStatusRepository commentStatusRepository;


    public void addComment(CommentRequest request, CustomerEntity customer) throws BehtaShopException {  // add comment by customer for product Or provider

        CommentStatusEntity commentStatusEntity =
                commentStatusRepository.findFirstById(CommentStatusOption.CREATED.getId());

        if(commentStatusEntity == null){
            throw new BehtaShopException("خطا در ثبت نظر!!!");
        }

        if (customer == null && request.isLogedIn())
            throw new BehtaShopException("خطا در ثبت نظر!!!");


        if( (StringUtil.isNullOrEmpty(request.getCommenter())  || StringUtil.isNullOrEmpty(request.getEmail()) ) && !request.isLogedIn()){
            throw new BehtaShopException("خطا در ثبت نظر، اطلاعات را به درستی وارد نمایید.!!!");
        }

        CommentEntity commentEntity =
                new CommentEntity(customer,commentStatusEntity, request);

        //comment add for product
        if(request.getProductId() != 0){
            ProductProviderEntity product =
                    productProviderRepository.findFirstByIdAndDeletedIsFalse(request.getProductId());

            if (product == null)
                throw new BehtaShopException("خطا در ثبت نظر!!!");
            commentEntity.setProductProvider(product);
        }

        //comment add for provider
//        if(request.getProviderId() != 0){
//            ProviderEntity provider = providerRepository.findFirstByIdAndDeletedIsFalse(request.getProviderId());
//            commentEntity.setProvider(provider);
//        }

        commentRepository.save(commentEntity);
    }

    public List<CommentResponse> getCommentListForWeb(CommentListRequest request){

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageOffset" , pageRequest.getOffset());
        params.put("pageLimit" , pageRequest.getPageSize());
        params.put("providerId" , request.getProviderId());
        params.put("productId" , request.getProductId());

        String queryPart = " from comment c " +
                (request.getProductId() == 0 ? " " : "left join product_provider pp on c.product_provider_id = pp.id ") +
                (request.getProviderId() == 0 ? " " : "left join account p on c.provider_id = p.id AND p.dtype = 'ProviderEntity' ") +
                "left join account cu on c.customer_id = cu.id " +
                " where c.deleted = FALSE " +
                " and c.status_id = 2 " +
                (request.getProviderId() == 0 ? " " : " and p.id = :providerId ") +
                (request.getProductId() == 0 ? " " : " and pp.id = :productId ") +
                "group by (c.id,c.edited_text,c.rate,c.insert_date,cu.first_name,cu.last_name, c.customer_id , commenter, email) " ;


        String query =
                "SELECT " +
                        "c.id id, " +
                        "c.edited_text txt, " +
                        "c.rate rate, " +
                        "c.insert_date date, " +
                        "c.customer_id, " +
                        "c.email, " +
                        "c.commenter, " +
                        "cu.first_name fName, " +
                        "cu.last_name lName " +
                        " " +
                        queryPart +
                        " ORDER BY c.insert_date " +
                        " LIMIT :pageLimit OFFSET :pageOffset";

        List<CommentResponse> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) -> new CommentResponse(
                                res.getObject("customer_id") != null,
                                res.getLong("id"),
                                res.getObject("customer_id") == null ? res.getString("commenter"): res.getString("fName") + " " + res.getString("lName"),
                                res.getString("txt"),
                                res.getFloat("rate"),
                                res.getTimestamp("date")
                        )
                );

        return result;

    }


    public ListResponse<CommentResponseList> getCommentListForPanel(CommentListRequest request){

        PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getSize());

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageOffset" , pageRequest.getOffset());
        params.put("pageLimit" , pageRequest.getPageSize());
        params.put("providerId" , request.getProviderId());
        params.put("productId" , request.getProductId());
        params.put("status" , request.getStatus());

        String queryPart = " from comment c " +
                "inner join product_provider pp on c.product_provider_id = pp.id AND pp.deleted = false " +
                "inner join product p on pp.product_id = p.id AND p.deleted = false " +
                (request.getProductId() == 0 ? " " : " and p.id = :productId  ") +
                "left join account cu on c.customer_id = cu.id " +
                " where c.deleted = FALSE " +
                (request.getStatus() == 0 ? " " : " and c.status_id = :status ") +
                (request.getProductId() == 0 ? " " : " and pp.id = :productId ") +
                "group by c.id,c.text,c.rate,c.insert_date,cu.first_name,cu.last_name, commenter, email, status_id, p.name, p.id " ;


        String query =
                "SELECT " +
                        "c.id id, " +
                        "p.id p_id, " +
                        "p.name p_name, " +
                        "c.status_id, " +
                        "c.edited_text, " +
                        "c.text txt, " +
                        "c.rate rate, " +
                        "c.insert_date date, " +
                        "c.customer_id, " +
                        "c.email, " +
                        "c.commenter, " +
                        "cu.first_name fName, " +
                        "cu.last_name lName " +
                        " " +
                        queryPart +
                        " ORDER BY c.insert_date desc " +
                        " LIMIT :pageLimit OFFSET :pageOffset";

        List<CommentResponseList> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) -> new CommentResponseList(
                                res.getInt("id"),
                                res.getLong("status_id"),
                                res.getObject("customer_id") == null ? res.getString("commenter"): res.getString("fName") + " " + res.getString("lName"),
                                res.getString("p_name"),
                                res.getLong("p_id"),
                                res.getFloat("rate"),
                                res.getDate("date"),
                                res.getObject("customer_id") != null,
                                res.getString("edited_text"),
                                res.getString("email")
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

        return new ListResponse<>(result, total);

    }

    public void changeCommentStatus(CommentStatusRequest request, AccountEntity customer) throws BehtaShopException {

        CommentEntity comment =
                commentRepository.findFirstByIdAndDeletedIsFalse(request.getCommentId());

        if (comment == null)
            throw new BehtaShopException("کامنت یافت نشد");


        CommentStatusEntity commentStatusEntity =
                commentStatusRepository.findFirstById(request.getStatusId());

        if(commentStatusEntity == null){
            throw new BehtaShopException("خطا در ثبت نظر!!!");
        }

        comment.setStatus(commentStatusEntity);
//        comment.setEditedText(request.getEditedText());
        comment.setUpdater(customer);
        comment.setChangeStatusDate(
                new Date()
        );

        commentRepository.save(comment);
    }
}