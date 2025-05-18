package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.api.customer.request.AddressRequestResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.ProviderComponent;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.repository.AddressRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdLong;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 13 December 2018
 **/
public class AddressApi extends BaseApi {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProviderComponent providerComponent;


    @ApiAction(value = "app.customer.address.add")
    public ApiResponseBody<IdRequest> add(AddressRequestResponse request, long customerId) throws ApiActionException {


        if (request == null || StringUtil.isNullOrEmpty(request.getProvince())) {
            throw new ApiActionWrongDataException(
                    "لطفا استان را وارد کنید."
            );
        }

        if (request == null || StringUtil.isNullOrEmpty(request.getPostalCode())) {
            throw new ApiActionWrongDataException(
                    "لطفا کد پستی را وارد کنید."
            );
        }
        if ( StringUtil.isNullOrEmpty(request.getCity())) {
            throw new ApiActionWrongDataException(
                    "لطفا شهر را وارد کنید."
            );
        }

        if ( StringUtil.isNullOrEmpty(request.getAddress())) {
            throw new ApiActionWrongDataException(
                    "لطفا آدرس خود را وارد کنید."
            );
        }
        if (StringUtil.isNullOrEmpty(request.getTitle())) {
            throw new ApiActionWrongDataException(
                    "لطفا عنوان آدرس خود را وارد کنید."
            );
        }

        AddressEntity sameTitle =
                addressRepository.findFirstByAccount_IdAndTitleAndDeletedIsFalse(customerId, request.getTitle());

        if (sameTitle != null && sameTitle.getId() != request.getId()) {
            throw new ApiActionWrongDataException(
                    "شما قبلا عنوان آدرسی با این نام انتخاب کرده اید."
            );
        }

        CustomerEntity customer = getCustomerById(customerId);

        // Edit
        if (request.getId() != 0) {
            deleteAddress(request, customer);
        }

        // New
        AddressEntity newAddress = new AddressEntity(customer, request);

        addressRepository.save(newAddress);


        selectAddress(customerId, newAddress.getId());


        return new ApiResponseBody().ok(new IdRequest(newAddress.getId()));
    }


    @ApiAction(value = "app.customer.address.delete")
    public ApiResponseBody delete(IdRequest addressId, long customerId) throws ApiActionException {

        CustomerEntity customer = getCustomerById(customerId);

        AddressEntity address = addressRepository.findFirstById(addressId.getId());


        deleteAddress(customerId, address);


        return new ApiResponseBody().ok();
    }


    @ApiAction(value = "app.customer.address.list")
    public ApiResponseBody<List<AddressRequestResponse>> list(long customerId) throws ApiActionException {

        CustomerEntity customer = getCustomerById(customerId);
        List<AddressEntity> addresses =
                addressRepository.findAllByAccount_IdAndDeletedIsFalse(customerId);


        return new ApiResponseBody<>().ok(
                addresses
                        .stream()
                        .map(AddressRequestResponse::new)
                        .sorted(Comparator.comparingLong(e -> -e.getId()))
                        .collect(Collectors.toList())
        );
    }

    @ApiAction(value = "app.customer.address.detail")
    public ApiResponseBody<AddressRequestResponse> detail(IdRequest addressId, long customerId) throws ApiActionException {

        AddressEntity addresses =
                addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId, addressId.getId());


        return new ApiResponseBody<>().ok(
                new AddressRequestResponse(addresses)
        );
    }

    @ApiAction(value = "app.customer.selected.address.detail")
    public ApiResponseBody<AddressRequestResponse> selectedAddressDetail(IdRequest addressId, long customerId) throws ApiActionException {
        AddressEntity addresses = new AddressEntity();
        if (addressId.getId() != 0) {
            addresses =
                    addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId, addressId.getId());
            if (addresses == null) {
                addresses =
                        addressRepository.findFirstByAccount_IdAndDeletedIsFalse(customerId);
            }
        } else {
            addresses =
                    addressRepository.findFirstByAccount_IdAndDeletedIsFalse(customerId);
        }

        return new ApiResponseBody<>().ok(
                new AddressRequestResponse(addresses != null && addresses.getId() != 0 ? addresses : new AddressEntity())
        );
    }


    @ApiAction(value = "app.customer.address.select")
    public ApiResponseBody select(Long addressId, long customerId) throws ApiActionException {

        CustomerEntity customer = getCustomerById(customerId);

        AddressEntity address =
                addressRepository.findFirstById(addressId);

        if (address == null) {
            throw new ApiActionNotFoundException("آدرس مورد نظر یافت نشد. لطفا با پشتیبانی تماس بگیرید.");
        }


        selectAddress(customerId, addressId);


        return new ApiResponseBody<>().ok();
    }


    @ApiAction(value = "app.customer.check.changed.address_area_is_same")
    public ApiResponseBody<Boolean> checkIsInSameArea(IdLong addressId, long customerId) throws ApiActionException {
        //TODO Check New And Old Area By ProviderId

        AddressEntity currentAddress =
                addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId, addressId.getId());
        LatLngData currentLatLng = currentAddress == null ? new LatLngData() : new LatLngData(currentAddress);
        List<Long> currentArea = providerComponent.getAllProviderInRegional(currentLatLng)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());

        AddressEntity newAddressSelected =
                addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId, addressId.getValue());

        LatLngData newAddressSelectedLatLng = newAddressSelected == null ? new LatLngData() : new LatLngData(newAddressSelected);
        List<Long> newAddressSelectedArea = providerComponent.getAllProviderInRegional(newAddressSelectedLatLng)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());


        return new ApiResponseBody<>().ok(( ArraysUtil.isNullOrEmpty(currentArea) ? 0 : currentArea.get(0)) == (ArraysUtil.isNullOrEmpty(newAddressSelectedArea) ? 0 : newAddressSelectedArea.get(0)));
    }


    private void selectAddress(long customerId, Long addressId) {

        List<AddressEntity> ownAddresses =
                addressRepository.findAllByAccount_IdAndDeletedIsFalse(customerId);


        ownAddresses.forEach(e -> {

            if (e.getId() == addressId) {

                e.setAddressOrder(1);

            } else {

                e.setAddressOrder(0);

            }

        });

        addressRepository.saveAll(ownAddresses);

    }

    private void deleteAddress(long customerId, AddressEntity address) throws ApiActionWrongDataException {

        if (address == null || address.getAccount().getId() != customerId) {
            throw new ApiActionWrongDataException("اطلاعات وارد شده صحیح نمیباشد لطفا با پشتیبانی تماس بگیرید.");
        }


        address.setTitle(address.getTitle() + "_" + new Date().toString() );
        address.setDeleted(true);

        addressRepository.save(address);

    }

    private void deleteAddress(AddressRequestResponse request, final CustomerEntity customer) throws ApiActionNotFoundException, ApiActionWrongDataException {

        AddressEntity address = addressRepository.findFirstById(request.getId());

        if (address == null || StringUtil.isNullOrEmpty(request.getAddress())) {
            throw new ApiActionNotFoundException("آدرس مورد نظر یافت نشد. لطفا با پشتیبانی تماس بگیرید.");
        }


        deleteAddress(customer.getId(), address);
    }


}
