package com.behrouz.server.retrofit;

import com.behrouz.server.rest.request.bank.SamanReverseTransactionRestRequest;
import com.behrouz.server.rest.request.bank.SamanTokenRestRequest;
import com.behrouz.server.rest.request.bank.SamanVerifyTransactionRestRequest;
import com.behrouz.server.rest.response.bank.SamanReverseTransactionRestResponse;
import com.behrouz.server.rest.response.bank.SamanTokenRestResponse;
import com.behrouz.server.rest.response.bank.SamanVerifyTransactionRestResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface SamanBankService {
    @HTTP(method = "POST", path = "/onlinepg/onlinepg", hasBody = true)
    public Call<SamanTokenRestResponse> getBankToken(
            @Body SamanTokenRestRequest request
    );

    @HTTP(method = "POST", path = "/verifyTxnRandomSessionkey/ipg/VerifyTransaction", hasBody = true)
    public Call<SamanVerifyTransactionRestResponse> verifyTransaction(
            @Body SamanVerifyTransactionRestRequest request
    );

    @HTTP(method = "POST", path = "/verifyTxnRandomSessionkey/ipg/ReverseTransaction", hasBody = true)
    public Call<SamanReverseTransactionRestResponse> reverseTransaction(
            @Body SamanReverseTransactionRestRequest request
    );

}
