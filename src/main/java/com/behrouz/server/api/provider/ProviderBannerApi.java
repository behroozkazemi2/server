package com.behrouz.server.api.provider;


import com.behrouz.server.api.provider.request.BannerRestRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.component.ImageComponent;
import com.behrouz.server.model.global.BannerEntity;
import com.behrouz.server.model.global.BannerTypeEntity;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.repository.BannerRepository;
import com.behrouz.server.repository.BannerTypeRepository;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.utils.ArraysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProviderBannerApi extends ProviderBaseApi {


    @Autowired
    private ImageComponent imageComponent;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private BannerRepository bannerRepository;


    @Autowired
    private BannerTypeRepository bannerTypeRepository;




    @ApiAction("app.banner.list")
    public ApiResponseBody<List<IdName>> bannerList( int userId) {
        List<IdName> oldBanner =
                bannerTypeRepository.findAllByDeletedIsFalse()
                        .stream().map( m -> new IdName(m.getId(), m.getName())).collect(Collectors.toList());

        return new ApiResponseBody<>().ok(oldBanner);

    }


    @ApiAction("app.banner.detail")
    public ApiResponseBody<BannerRestRequest> bannerDetail(IdRequest idRequest, int userId) {
        List<IdName> oldBanner =
                bannerRepository.findAllByBannerType_IdAndDeletedIsFalseOrderById(idRequest.getId())
                        .stream().map( m -> new IdName(m.getImage().getId(), m.getLink())).collect(Collectors.toList());

        return new ApiResponseBody<>().ok(new BannerRestRequest(oldBanner));

    }

    @ApiAction(value = "customer.banner.detail", tokenRequired = false)
    public ApiResponseBody<List<BannerRestRequest>> webBannerDetail(int userId) {

        Map<Long, List<BannerEntity>> bannerMap =
                bannerRepository.findAllByDeletedIsFalse()
                .stream().collect(Collectors.groupingBy(g -> g.getBannerType().getId()));


        List<BannerRestRequest> imgType= new ArrayList<>();
        for (Map.Entry<Long, List<BannerEntity>> banner : bannerMap.entrySet()  ) {
            imgType.add( new BannerRestRequest(
                    banner.getValue().stream().map( m -> new IdName(m.getImage().getId(), m.getLink())).collect(Collectors.toList()),
                    banner.getKey()
            ));
        }

        return new ApiResponseBody<>().ok(imgType);

    }

    @ApiAction("app.banner.add")
    public ApiResponseBody<IdName> addBanner(@ApiActionParam(nullable = false) BannerRestRequest imageRequest, int userId) {

        List<BannerEntity> oldBanner =
                bannerRepository.findAllByBannerType_IdAndDeletedIsFalseOrderById(imageRequest.getType()).stream().peek( p -> p.setDeleted(true)).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(oldBanner)){
            bannerRepository.saveAll(oldBanner);
        }

        BannerTypeEntity type =
                bannerTypeRepository.findFirstByIdAndDeletedIsFalse(imageRequest.getType());

        List<BannerEntity> bannerImgs =
                imageRequest.getImages().stream().map(m -> {
                    ImageEntity img = imageRepository.findFirstByIdAndDeletedIsFalse(m.getId());
                    return new BannerEntity(
                            img,
                            type,
                            m.getName()
                    );


                }).collect(Collectors.toList());

        bannerRepository.saveAll(bannerImgs);

        return new ApiResponseBody<>().ok(new IdName(0));

    }

}
