package com.behrouz.server.api;

import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.AccountRepository;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.repository.OrderStatusRepository;
import com.behrouz.server.values.DebugValues;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.api
 * project name:  ximaServer
 * 07 July 2018
 **/

public class BaseApi {

    protected Logger logger = Logger.getLogger( BaseApi.class.getSimpleName() );


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private AccountRepository accountRepository;


    protected void loggerWarn(String str , Object... obj){

        if ( DebugValues.DEBUG_MODE ) {

            logger.warning( String.format( str, obj ) );

        }

    }

    protected void loggerInfo(String str , Object... obj){

        if ( DebugValues.DEBUG_MODE ) {

            logger.info( String.format( str, obj ) );

        }

    }

    protected void loggerFiner(String str , Object... obj){

        if ( DebugValues.DEBUG_MODE ) {

            logger.finer( String.format( str, obj ) );

        }

    }



    protected CustomerEntity getCustomerById(long customerId ) throws ApiActionException {

        CustomerEntity customer = customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse( customerId );

        if ( customer == null ) {
            throw new ApiActionNotFoundException( "کاربر مورد نظر یافت نشد!" );
        }

        return customer;

    }
    protected AccountEntity getAccountById(long accountId ) throws ApiActionException {

        AccountEntity customer =
                accountRepository.findFirstByIdAndDeletedIsFalse(accountId);

        if ( customer == null ) {
            throw new ApiActionNotFoundException( "کاربر مورد نظر یافت نشد!" );
        }

        return customer;

    }

    protected long getCustomerBalance (long customerId ) throws ApiActionException {

        BalanceEntity balance =
                balanceRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc( customerId );

//        if ( balance == null ) {
//            throw new ApiActionNotFoundException( "حساب مالی کاربر مورد نظر یافت نشد. لطفا با پشتیبانی تماس بگیرید." );
//        }

        return balance != null ? balance.getAmount() : 0 ;

    }



//    protected void saveToCustomerOrderHistory(CustomerOrderEntity customerOrder, long statusId ) {
//
//        CustomerOrderHistoryEntity customerOrderHistory = new CustomerOrderHistoryEntity(
//                customerOrder,
//                orderStatusRepository.findFirstById( statusId )
//        );
//
//
//        customerOrderHistoryRepository.save( customerOrderHistory );
//
//    }

//    protected void saveToBalanceHistory ( BalanceEntity newBalance ) {
//        BalanceHistoryEntity balanceHistory = new BalanceHistoryEntity( newBalance );
//        balanceHistoryRepository.save( balanceHistory );
//    }
//
//    protected void saveToBalanceHistory ( long customerId, float amount, String description ) {
//        BalanceHistoryEntity balanceHistory =
//                new BalanceHistoryEntity(
//                        new AccountEntity(){{setId(customerId);}},
//                        amount,
//                        description,null
//                );
//        balanceHistoryRepository.save( balanceHistory );
//    }

}