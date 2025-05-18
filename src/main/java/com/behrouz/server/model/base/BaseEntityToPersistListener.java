package com.behrouz.server.model.base;

import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.security.model.SessionHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class BaseEntityToPersistListener{

   @PrePersist
   @PreUpdate
   public void methodExecuteBeforeSave(final BaseEntity reference) {
//      Make any change to the entity such as calculation before the save process
       if(SessionHolder.isLogin()){
           long accountId = SessionHolder.getOperatorId();
           if(accountId != 0) {
               reference.setUpdater(new AccountEntity() {{
                   setId((int) accountId);
               }});
           }
       }else{
           Optional<HttpServletRequest> request = getCurrentHttpRequest();
           if(request.isPresent()){
               Object apiAccountId = request.get().getAttribute("api-account-id");
               long accountId = apiAccountId instanceof Long ? (long) apiAccountId : 0L;
               if(accountId != 0) {
                   reference.setUpdater(new AccountEntity() {{
                       setId((int) accountId);
                   }});
               }
           }
       }


    }

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }

}