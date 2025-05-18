
package com.behrouz.server.component;

import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.api.provider.request.IdActiveRequest;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.model.global.RegionEntity;
import com.behrouz.server.repository.global.RegionRepository;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.ProviderRequest;
import com.behrouz.server.rest.response.digestList.ProviderListDigestResponse;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.api.provider.response.geo.ProviderPolygonResponse;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.CategoryRepository;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project Koala Server
 * 09 September 2018 16:00
 **/
@Component
public class ProviderComponent {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ProviderRepository providerRepository;

//
//    @Autowired
//    private AddressRepository addressRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageComponent imageComponent;

    @Autowired
    private RegionRepository regionRepository;


    public DataTableResponse<ProviderListDigestResponse> getList(int page, int limit, String search) {

        PageRequest pageRequest = PageRequest.of(page, limit);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageOffset", pageRequest.getOffset());
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("searchStr", "%" + search + "%");

        String queryPart = "FROM account ac " +
                "WHERE ac.dtype = 'ProviderEntity' " +
                "AND ac.deleted = FALSE " +
                (StringUtil.isNullOrEmpty(search) ? "" : "AND ac.name LIKE :searchStr ");


        String query =
                "SELECT " +
                        "ac.id id, " +
                        "ac.name pname, " +
                        "ac.address adr, " +
                        "ac.phone phone, " +
                        "ac.image_id image, " +
                        "ac.active active, " +
                        "ac.insert_date date, " +
                        "ac.min_day minDay, " +
                        "ac.rate rate, " +
                        "ac.telegram_id tlg, " +
                        "ac.instagram_id ins, " +
                        "ac.region_id rgId, " +
                        "ac.short_description shortDesc, " +
                        "ac.full_description fullDesc " +
                        queryPart +
                        "ORDER BY ac.name " +
                        "LIMIT :pageLimit OFFSET :pageOffset";


        List<ProviderListDigestResponse> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) -> new ProviderListDigestResponse(
                                res.getLong("id"),
                                res.getString("pname"),
                                res.getString("adr"),
                                res.getString("phone"),
                                res.getLong("image"),
                                res.getBoolean("active"),
                                res.getTimestamp("date"),
                                res.getLong("minDay"),
                                res.getDouble("rate"),
                                res.getString("shortDesc"),
                                res.getString("fullDesc"),
                                res.getString("ins"),
                                res.getString("tlg"),
                                res.getLong("rgId"),
                                new ArrayList<>()


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


        //providerCategory
        List<Long> providerIds =
                result.stream().map(ProviderListDigestResponse::getId).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(providerIds)) {
//            result = fillCategoryIdsProvider(result, providerIds);

        }


        DataTableResponse<ProviderListDigestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);


        return dateTable;
    }


    @Transactional(rollbackFor = {BehtaShopException.class, Exception.class})
    public ProviderListDigestResponse add(ProviderRequest request) throws BehtaShopException {

        checkProviderAdd(request);

        ProviderEntity provider = request.getId() == 0 ? null :
                providerRepository.findFirstById(request.getId());

        if (request.getId() != 0 && provider == null) {
            throw new BehtaShopException("تامین کننده مورد نظر پیدا نشد.");
        }

        if (provider == null) {
            provider = new ProviderEntity();
        }


        ImageEntity logoImage = request.getLogoId() == 0 ? null :
                imageRepository.findFirstById(request.getLogoId());

        if (logoImage == null && provider.getLogo() == null) {
            throw new BehtaShopException("عکس تامین کننده مشخص نشده.");
        }


        RegionEntity region =
                saveProviderPolygon(request.getLocation());

        provider.update(request, region, logoImage);
        providerRepository.save(provider);

        ProviderPolygonResponse item = new ProviderPolygonResponse(provider.getId(), request.getLocation());
        saveProviderPolygon(item);

        return new ProviderListDigestResponse(provider, new ArrayList<>());
    }


    public ProviderListDigestResponse detail(IdName request) throws BehtaShopException {


        ProviderEntity provider = request.getId() == 0 ? null :
                providerRepository.findFirstById(request.getId());

        if (provider == null) {
            throw new BehtaShopException("تامین کننده مورد نظر پیدا نشد.");
        }

        return new ProviderListDigestResponse(provider, getPolygon(provider.getId()).getPoints());
    }


    private void checkProviderAdd(ProviderRequest request) throws BehtaShopException {

        if (request == null) {
            throw new BehtaShopException("درخواست ارسالی دچار ایراد می‌باشد.");
        }

        if (StringUtil.isNullOrEmpty(request.getName())) {
            throw new BehtaShopException("نام تامین کننده را وارد کنید.");
        }

        if (StringUtil.isNullOrEmpty(request.getAddress())) {
            throw new BehtaShopException("آددرس را وارد کنید.");
        }

        if (StringUtil.isNullOrEmpty(request.getPhone())) {
            throw new BehtaShopException("تلفن تماس را وارد کنید.");
        }

        if (StringUtil.isNullOrEmpty(request.getTelegramId())) {
            throw new BehtaShopException("ایدی تلگرام مدیر را وارد کنید.");
        }

        if (StringUtil.isNullOrEmpty(request.getInstagramId())) {
            throw new BehtaShopException("پیج اینستاگرام تامین کنننده وارد کنید.");
        }

        if (StringUtil.isNullOrEmpty(request.getShortDescription())) {
            throw new BehtaShopException("توضیحات کوتاه مربوط تامین کننده را وارد کنید.");
        }

        if (StringUtil.isNullOrEmpty(request.getFullDescription())) {
            throw new BehtaShopException("توضیحات تکمیلی در مورد تامین کننده را وارد کنید.");
        }


        if (request.getLogoId() == 0) {
            throw new BehtaShopException("عکس تامین کننده را ارسال کنید.");
        }

        if (ArraysUtil.isNullOrEmpty(request.getLocation())) {
            throw new BehtaShopException("مناطق تحت پوشش تامین کننده معتبر نمی‌باشد.");
        }


    }


    public IdRequest activeDeactivated(IdActiveRequest request) throws BehtaShopException {

        ProviderEntity provider = providerRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (provider == null) {
            throw new BehtaShopException("تامین کننده مورد نظر یافت نشد!");
        }

        provider.setActive(request.isActive());

        provider.setUpdateDate(new Date());

        providerRepository.save(provider);

        return new IdRequest(provider.getId());
    }

    public IdRequest delete(IdName providerId) throws BehtaShopException {

        ProviderEntity provider =
                providerRepository.findFirstByIdAndDeletedIsFalseAndBannedIsFalse(providerId.getId());

        if (provider == null) {
            throw new BehtaShopException("تامین کننده مورد نظر یافت نشد!");
        }

        provider.setDeleted(true);

        provider.setActive(false);

        provider.setUpdateDate(new Date());

        providerRepository.save(provider);


        return new IdRequest(provider.getId());
    }


    public List<IdName> getAll() {
        return jdbcTemplate.query(
                "SELECT ac.id id , ac.name nm FROM account ac WHERE ac.dtype='ProviderEntity' AND ac.deleted = FALSE ORDER BY ac.name ",
                (res, rowId) -> new IdName(res.getLong("id"), res.getString("nm"))
        );
    }

    public List<IdName> category() throws BehtaShopException {
        return jdbcTemplate.query(
                "SELECT c.id id, c.name nm " +
                        "FROM  category c " +
                        "WHERE c.deleted = FALSE " +
                        "GROUP BY c.id, c.name",
                (res, rowId) -> new IdName(res.getLong("id"), res.getString("nm"))
        );
    }


    public ProviderPolygonResponse getPolygon(long provider) {
        List<String> result = jdbcTemplate.query(
                "SELECT " +
                        "ST_AsText(rg.location) atext " +
                        "FROM account prv " +
                        "INNER JOIN regional rg ON rg.id = prv.region_id " +
                        "WHERE prv.deleted = FALSE " +
                        "AND prv.id = :pid ",
                new MapSqlParameterSource("pid", provider),
                (res, rowId) -> res.getString("atext")
        );

        List<LatLngData> latLngData = new ArrayList<>();
        if (!ArraysUtil.isNullOrEmpty(result) && result.get(0) != null) {
            String[] points = result.get(0)
                    .replaceAll("POINT", "")
                    .replaceAll("POLYGON", "")
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .split(",");

            latLngData =
                    Stream.of(points).map(m -> {
                        String[] latLng = m.split(" ");
                        assert latLng.length == 2;
                        return new LatLngData(
                                Double.parseDouble(latLng[0]),
                                Double.parseDouble(latLng[1])
                        );
                    }).collect(Collectors.toList());

        }

        return new ProviderPolygonResponse(
                provider,
                latLngData
        );
    }

    public RegionEntity saveProviderPolygon(ProviderPolygonResponse request) throws BehtaShopException {
        if (request == null) {
            throw new BehtaShopException("محدوده خدمت دهی الزامی می‌باشد.");
        }
        return saveProviderPolygon(request.getPoints());
    }

    public RegionEntity saveProviderPolygon(List<LatLngData> request) throws BehtaShopException {


        if (ArraysUtil.isNullOrEmpty(request)) {
            throw new BehtaShopException("محدوده خدمت دهی الزامی می‌باشد.");
        }

        if (request.size() < 4) {
            throw new BehtaShopException("محدوده خدمت دهی حداقل به شکل مثلث می‌باشد.");
        }

        if (!request.get(0).equals(request.get(request.size() - 1))) {
            throw new BehtaShopException("محدوده خدمت دهی می بایست از یک شکل بسته تشکیل شده باشد(نقطه ابتدایی و انتهایی یکسان).");
        }


        RegionEntity regionEntity =
                new RegionEntity();
        regionRepository.save(regionEntity);


        String pointsStr =
                request.stream().map(m -> m.getLat() + " " + m.getLng()).collect(Collectors.joining(","));

        String query = "UPDATE regional SET location = ST_MakePolygon(" +
                "    ST_GeomFromText(" +
                "        'LINESTRING( " + pointsStr + ")'" +
                "    )" +
                ") WHERE id = :rid";
        jdbcTemplate.update(
                query,
                new MapSqlParameterSource("rid", regionEntity.getId())
        );

        return regionEntity;

    }


    public List<ProviderEntity> getAllProviderInRegional(LatLngData latLng) {

        String pointStr = latLng.getLat() + " " + latLng.getLng();

        String query = "SELECT " +
                " " +
                "pr.id " +
                "FROM account pr " +
                "INNER JOIN regional rg ON rg.id = pr.region_id " +
                "WHERE pr.deleted = FALSE " +
                "AND pr.dtype = 'ProviderEntity' " +
                "AND ST_INTERSECTS(" +
                "      rg.location," +
                "      st_geomfromtext('Point(" + pointStr + ")')" +
                "  )";
        List<Long> ids = jdbcTemplate.query(
                query,
                new MapSqlParameterSource("mLat", latLng.getLat()).addValue("mLng", latLng.getLng()),
                (res, row) -> res.getLong(1)
        );

        List<ProviderEntity> providers = new ArrayList<>();

        if (!ArraysUtil.isNullOrEmpty(ids)) {
            providers = providerRepository.findAllByIdInAndDeletedIsFalse(ids);
        }

        return ArraysUtil.isNullOrEmpty(providers) ? new ArrayList<>() : providers;
    }


}