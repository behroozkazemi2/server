package com.behrouz.server.api.universal;


import com.behrouz.server.api.provider.ProviderBaseApi;
import com.behrouz.server.api.provider.response.RegionResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.IdNameType;
import com.behrouz.server.rest.request.IdNameTypeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniversalConstantApi extends ProviderBaseApi {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @ApiAction("app.universal.constant.category")
    public ApiResponseBody<List<IdName>> category(){

        String query =
                "SELECT cg.id cid, cg.name cname FROM category cg WHERE cg.deleted = FALSE AND cg.parent_id IS NULL ORDER BY cg.name ";

        List<IdName> result = jdbcTemplate.query(
                query,
                (res, rowId) -> new IdName(
                        res.getInt("cid"),
                        res.getString("cname")
                )
        );

        return new ApiResponseBody<>().ok(result);

    }


    @ApiAction(value = "app.universal.constant.region", tokenRequired = false)
    public ApiResponseBody<List<RegionResponse>> region(){

//        String query =
//                "select " +
//                        "rg.name pnm, " +
//                        "rga.region_id id, " +
//                        "rga.region_name nm " +
//                        "from provider_region_all rga " +
//                        "LEFT JOIN regional rg ON rg.id = rga.region_parent " +
//                        "group by rg.name ,rga.region_id ,rga.region_name " +
//                        "ORDER BY pid";
//
//        List<RegionResponse> result = jdbcTemplate.query(
//                query,
//                (res, rowId) -> new RegionResponse(
//                        res.getInt("pid"),
//                        res.getString("pnm"),
//                        res.getInt("id") == 0 ?
//                                new ArrayList<>() :
//                                Collections.singletonList(
//                                        new IdName(
//                                                res.getInt("id"),
//                                                res.getString("nm")
//                                        )
//                                )
//                )
//        );

//        List<RegionResponse> parent =
//                result.stream().filter(g -> g.getId() == 0).collect(Collectors.toList());
//
//        parent = parent.stream().peek( p -> {
//            IdName par = p.getRegions().get(0);
//            List<IdName> sub = result.stream().filter(f -> f.getId() == par.getId()).map(m -> m.getRegions().get(0)).collect(Collectors.toList());
//            p.setId(par.getId());
//            p.setName(par.getName());
//            p.setRegions(sub);
//        }).sorted(Comparator.comparingInt(IdName::getId)).collect(Collectors.toList());
//
//
//        return new ApiResponseBody<>().ok(parent);
return null;
    }


    @ApiAction(value = "app.universal.constant.gCategory", tokenRequired = false)
    public ApiResponseBody<HashMap<Integer, List<IdName>>> gCategory(){


        String query =
                "SELECT " +
                "ctg.id cid, " +
                "ctg.name cnm, " +
                "rg.region_id rid " +
                "FROM provider_region_all rg " +
                "INNER JOIN provider_category pc ON pc.provider_id = rg.provider_id AND pc.deleted = FALSE " +
                "INNER JOIN category ctg ON ctg.id = pc.category_id AND ctg.deleted  = FALSE " +
                "INNER JOIN account prv ON prv.id = pc.provider_id AND prv.deleted = FALSE " +
                "GROUP BY ctg.id, ctg.name,rg.region_id ";

        List<IdNameType> result = jdbcTemplate.query(
                query,
                (res, rowId) -> new IdNameType(
                        res.getInt("cid"),
                        res.getString("cnm"),
                        res.getInt("rid")
                )
        );

        Map<Long, List<IdName>> mResult = result.stream().collect(Collectors.groupingBy(IdNameType::getType)).entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue().stream().map(e -> new IdName(e.getId(), e.getName())).collect(Collectors.toList())
                )
        );

        return new ApiResponseBody<>().ok(mResult);

    }


    @ApiAction(value = "app.universal.constant.gProvider", tokenRequired = false)
    public ApiResponseBody<HashMap<Integer, HashMap<Integer,  List<IdName>>>> gProvider(){

        String query =
                "SELECT " +
                        "prv.id pid, " +
                        "prv.name pnm, " +
                        "rg2.id rid, " +
                        "ctg.id cid " +
                        "FROM category ctg " +
                        "INNER JOIN provider_category pc ON pc.category_id = ctg.id AND pc.deleted = FALSE AND ctg.deleted = FALSE " +
                        "INNER JOIN account prv ON prv.id = pc.provider_id AND prv.deleted = FALSE " +
                        "INNER JOIN provider_region pr ON pr.provider_id = pc.provider_id AND pr.deleted = FALSE " +
                        "INNER JOIN regional rg ON (rg.id = pr.region_id OR rg.regional_parent_id = pr.region_id ) AND rg.deleted = FALSE " +
                        "INNER JOIN regional rg2 ON rg2.id = rg.id OR rg2.id = rg.regional_parent_id " +
                        "GROUP BY prv.id ,prv.name, pr.region_id, rg2.id, ctg.id " +
                        "ORDER BY prv.id";

        List<IdNameTypeValue> result = jdbcTemplate.query(
                query,
                (res, rowId) -> new IdNameTypeValue(
                        res.getInt("pid"),
                        res.getString("pnm"),
                        res.getInt("rid"),
                        res.getInt("cid")
                )
        );

        Map<Long, Map<Long, List<IdNameTypeValue>>> mResult = result.stream()
                .collect(
                        Collectors.groupingBy(
                                IdNameTypeValue::getType,
                                Collectors.groupingBy(IdNameTypeValue::getValue)
                        ));

        HashMap<Long, HashMap<Long, List<IdName>>> res = new HashMap<>();

        mResult.forEach( (k,v) -> {
            HashMap<Long, List<IdName>> cur = new HashMap<>();
            v.forEach((k2,v2) -> {
                List<IdName> vv = v2.stream().map(e -> new IdName(e.getId(), e.getName())).collect(Collectors.toList());
                cur.put(k2, vv);
            });
            res.put(k, cur);
        });
        return new ApiResponseBody<>().ok(res);

    }






}
