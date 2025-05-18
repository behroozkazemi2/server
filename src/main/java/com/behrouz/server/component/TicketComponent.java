package com.behrouz.server.component;


import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.ticket.TicketEntity;
import com.behrouz.server.model.ticket.TicketImportanceEntity;
import com.behrouz.server.model.ticket.TicketMessageEntity;
import com.behrouz.server.model.ticket.project.ProjectEntity;
import com.behrouz.server.repository.AccountRepository;
import com.behrouz.server.repository.Document.DocumentRepository;
import com.behrouz.server.repository.ticket.*;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.SaveTicketMessageRestRequest;
import com.behrouz.server.rest.request.SaveTicketRestRequest;
import com.behrouz.server.rest.request.TicketListRestRequest;
import com.behrouz.server.rest.response.TicketDetailRestResponse;
import com.behrouz.server.rest.response.TicketMessageRestResponse;
import com.behrouz.server.utils.NiazPardazandehSendSmsUtil;
import com.behrouz.server.utils.PersianNumberText;
import com.behrouz.server.utils.StringsUtil;
import com.behrouz.server.utils.date.PersianDateUtil;
import com.behrouz.server.model.ticket.*;
import com.behrouz.server.repository.ticket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TicketComponent {


    @Autowired
    private DocumentComponent documentComponent;


    @Autowired
    private DocumentRepository documentRepository;


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMessageRepository ticketMessageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TicketMessageDocumentRepository ticketMessageDocumentRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private TicketImportanceRepository importanceRepository;

    @Autowired
    private ExcelGeneratorComponent excelGeneratorComponent;


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private NiazPardazandehSendSmsUtil niazPardazandehSendSmsUtil;

    @Transactional(rollbackOn = {Exception.class, BehtaShopException.class})
    public String saveOrEditTicket(SaveTicketRestRequest rest, AccountEntity user) throws BehtaShopException {

        if (StringsUtil.isNullOrEmpty(rest.getSubject())) {
            throw new BehtaShopException(" لطفا موضوع را وارد کنید ");
        }

        if (user == null) {
            throw new BehtaShopException("خطا در ذخیره کاربر یافت نشد");
        }


        TicketImportanceEntity importance =
                importanceRepository.findFirstByIdAndDeletedIsFalse(rest.getImportance());

        if (importance == null) {
            throw new BehtaShopException("خطا در ذخیره سازی، اهمیت یافت نشد");
        }

        ProjectEntity project =
                projectRepository.findFirstByIdAndDeletedIsFalse(rest.getProject());

        if (project == null) {
            throw new BehtaShopException("خطا در ذخیره سازی، واحد مربوطه یافت نشد");
        }

        TicketEntity ticket = new TicketEntity(rest.getSubject(), user, importance);
        ticket.setProject(project);
        ticketRepository.save(ticket);

        TicketMessageEntity message = new TicketMessageEntity(rest.getText(), ticket, user);
        ticketMessageRepository.save(message);

//        List<DocumentEntity> docs = documentRepository.findAllByIdInAndDeletedIsFalse(rest.getDocuments());
//
//
//        List<TicketMessageDocumentEntity> messageDoc = docs.stream()
//                .map(m -> new TicketMessageDocumentEntity(
//                        message,
//                        m
//                )).collect(Collectors.toList());
//
//        ticketMessageDocumentRepository.saveAll(messageDoc);

        niazPardazandehSendSmsUtil.sendNewTicketSms(user.getMobile(), ticket.getTrackingCode());


        return ticket.getTrackingCode();
    }


    @Transactional(rollbackOn = {Exception.class, BehtaShopException.class})
    public void addNewTicketMessage(SaveTicketMessageRestRequest restRequest, AccountEntity user) throws BehtaShopException {

        if (StringsUtil.isNullOrEmpty(restRequest.getText())) {
            throw new BehtaShopException("لطفا متن را وارد کنید");
        }

        TicketEntity ticket =
                ticketRepository.findFirstByIdAndDeletedIsFalse(restRequest.getTicketId());

        if (ticket == null) {
            throw new BehtaShopException("خطا تیکت یافت نشد");
        }
        if (
                ticket.getAccount().getId() != user.getId() &&
                        !(user instanceof ProviderEntity) &&
                        !(user instanceof OperatorEntity)
        ) throw new BehtaShopException("خطا تیکت یافت نشد");

        if (user instanceof CustomerEntity) {
            ticket.setRead(true);
        } else {
            ticket.setRead(false);
        }

        TicketMessageEntity newMsg = new TicketMessageEntity(restRequest.getText(), ticket, user);
        ticketMessageRepository.save(newMsg);

//        if (!ArraysUtil.isNullOrEmpty(restRequest.getDocument())) {
//            List<DocumentEntity> docs = documentRepository.findAllByIdInAndDeletedIsFalse(restRequest.getDocument());
//
//
//            List<TicketMessageDocumentEntity> messageDoc = docs.stream()
//                    .map(m -> new TicketMessageDocumentEntity(
//                            newMsg,
//                            m
//                    )).collect(Collectors.toList());
//
//            ticketMessageDocumentRepository.saveAll(messageDoc);
//
//        }

        if (!(user instanceof CustomerEntity)) {
            ticket.setLastMessageDeveloper(true);
            niazPardazandehSendSmsUtil.sendNewTicketReplySms(ticket.getAccount().getMobile(), ticket.getTrackingCode());
        } else {
            ticket.setLastMessageDeveloper(false);
        }

        ticketRepository.save(ticket);
    }

    public TicketDetailRestResponse getTicketDetail(String  trackingCode) {
        TicketEntity ticket = ticketRepository.findFirstByTrackingCodeAndDeletedIsFalse(trackingCode);
        TicketDetailRestResponse response = new TicketDetailRestResponse(ticket);
        return response;
    }

    public List<TicketMessageRestResponse> getTicketMessages(long ticketId, boolean lastMassage, AccountEntity account) {

        Map<String, Object> params = new HashMap<>();
        params.put("ticketId", ticketId);
        params.put("accountId", account.getId());


        String query = "" +
                "SELECT " +
                "       tm.id          id,   " +
                "       tm.text        txt,  " +
                "       tm.insert_date insertDate,  " +
                "       concat(a.first_name, ' ' ,a.last_name )  accountName, " +
                "       a.dtype  opty  " +
                "FROM ticket_message tm  " +
                "         INNER JOIN ticket tk ON tm.ticket_id = tk.id AND tk.deleted = FALSE " +
                "         INNER JOIN project p ON p.id = tk.project_id AND p.deleted = false  " +
                "         INNER JOIN account a ON a.id = tm.account_id AND a.deleted = False  " +
                (lastMassage ? " INNER JOIN last_ticket_message ltms ON ltms.ticket_message_id = tm.id  " : "") +
                "WHERE tm.deleted = False AND tm.ticket_id = :ticketId " +
                (account instanceof OperatorEntity || account instanceof ProviderEntity? " ": "AND tk.account_id = :accountId ") +
                "GROUP BY tm.id, tm.text, tm.insert_date, a.dtype, concat(a.first_name, ' ', a.last_name)   " +
                "ORDER BY tm.insert_date ASC ";

        return jdbcTemplate.query(
                query,
                params,
                (res, ids) -> {
//                    List<TicketMessageDocumentEntity> ticketMsgDoc = ticketMessageDocumentRepository.findAllByTicketMessage_IdAndDeletedIsFalse(res.getLong("id"));
//
//                    List<DocumentRestResponse> docs = new ArrayList<>();
//                    if (!ArraysUtil.isNullOrEmpty(ticketMsgDoc)) {
//                        docs = ticketMsgDoc
//                                .stream()
//                                .map(m -> (new DocumentRestResponse(m.getDocument()))).collect(Collectors.toList());
//                    }
//                    ;

                    return new TicketMessageRestResponse(
                            Objects.equals(res.getString("opty"), "OperatorEntity") || Objects.equals(res.getString("opty"), "providerEntity"),
                            Objects.equals(res.getString("opty"), "OperatorEntity") || Objects.equals(res.getString("opty"), "providerEntity") ? "واحد پشتیبانی" : res.getString("accountName") ,
                            PersianNumberText.toPersianNumber(PersianDateUtil.getPersianDateAndHour(res.getTimestamp("insertDate").getTime())),
                            res.getString("txt"),
                            null,
                            Objects.equals(res.getString("opty"), "OperatorEntity") || Objects.equals(res.getString("opty"), "providerEntity") ? "واحد پشتیبانی" : res.getString("accountName")
                    );
                }

        );
    }

    public DataTableResponse<TicketDetailRestResponse> getTicketList(
            TicketListRestRequest search,
            long accountId
    ) {
     return getTicketList(
             search.getPage()   ,
             search.getPerpage(),
             search.getProject(),
             search.getTicketImportance(),
             search.getSearch(),
             search.getSort(),
             search.getField(),
             search.getResponseType(),
             search.getLastMsgDate(),
             search.getLastMsgDate(),
             search.getLastMsgToDate(),
             accountId,
             true
     );
    }

    public DataTableResponse<TicketDetailRestResponse> getTicketList(
            int page,
            int perpage,
            long project,
            long ticketImportance,
            String search,
            String sort,
            String field,
            long responseType,
            long closed,
            long lastMsgDate,
            long lastMsgToDate,
            long accountId,
            boolean admin
    ) {


        String orderStr = " tk.closed, im.id, ltm.insert_date desc, tk.insert_date desc ";

//                "importance".equals(field) ? "im.id" :
//                        "project".equals(field) ? "p.id " :
//                                "date".equals(field) ? "ltm.insert_date " :
//                                     "submitDate".equals(field) ? "tk.insert_date " :
//                                        "response".equals(field) ? "tk.read " :
//                                              "closed".equals(field) ? "tk.closed " : "ltm.insert_date ";


//        String step = "desc".equals(sort) ? "desc" : "asc";
//        if (StringsUtil.isNullOrEmpty(sort))
//            step = "desc";


        PageRequest pageRequest = PageRequest.of(page - 1, perpage);


        Map<String, Object> params = new HashMap<>();
        // TODO ticket
//        params.put("accountID", SessionHolder.getUserId());
        params.put("accountID", accountId);
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("pageOffset", pageRequest.getOffset());
        params.put("project", project);
        params.put("ticketImportance", ticketImportance);
//        params.put("submitDate", new Date(submitDate));
//        params.put("submitToDate", new Date(submitToDate));
        params.put("lastMsgDate", new Date(lastMsgDate));
        params.put("lastMsgToDate", new Date(lastMsgToDate));
        params.put("search", "%" + search + "%");


        String queryPart = "" +
                "FROM ticket tk " +
                "         INNER JOIN project p " +
                "                    on tk.project_id = p.id AND p.deleted = FALSE " +
                (project != 0 ? " AND p.id = :project  " : " ") +
                (accountId != 0 && !admin ? " AND tk.account_id =:accountID  " : " ") +
                (!StringsUtil.isNullOrEmpty(search) ? " AND ( tk.subject LIKE LOWER(:search) OR tk.tracking_code LIKE LOWER(:search)) " : " ") +
                (ticketImportance != 0 ? " AND tk.ticket_importance_id =:ticketImportance " : " ") +
                (responseType == 0 ? " " : (responseType == 2 ? "AND tk.read = true " : " AND tk.read = FALSE  ")) +
                (closed == 0 ? " " : (closed == 1 ? "AND tk.closed = FALSE " : " AND tk.closed = TRUE  ")) +
//                (submitDate != 0 ? " AND DATE(tk.insert_date) => DATE(:submitDate) " : " ") +
//                (submitToDate != 0 ? " AND DATE(tk.insert_date) <= DATE(:submitToDate) " : " ") +
                "         INNER JOIN ticket_importance im on tk.ticket_importance_id = im.id And im.deleted = FALSE " +
                "         INNER JOIN last_ticket_message ltm ON ltm.ticket_id =   tk.id " +
                "         INNER JOIN ticket_message tkm ON ltm.ticket_message_id =   tkm.id " +
                (lastMsgDate != 0 ? " AND ( DATE(ltm.insert_date) >= DATE(:lastMsgDate) OR DATE(tk.insert_date) >= DATE(:lastMsgDate) ) " : " ") +
                (lastMsgToDate != 0 ? " AND ( DATE(ltm.insert_date) <= DATE(:lastMsgToDate) OR DATE(tk.insert_date) <= DATE(:lastMsgToDate) ) " : " ") +
                "WHERE tk.deleted = FALSE " +
                "GROUP BY ltm.account_id, tk.id, tk.subject, p.name, p.id, tk.insert_date, tk.closed, tk.tracking_code, im.id, im.name, tk.read, ltm.insert_date " +
                "";


        String query =
                "SELECT tk.id            ticketID, " +
                        "       tk.subject       subject, " +
                        "       p.name           project, " +
                        "       p.id             projectId, " +
                        "       tk.insert_date   submitDate, " +
                        "       tk.last_message_developer   isLastMessageFromDeveloper, " + //TODO change this to AccountTypeEntity
                        "       tk.closed        closed, " +
                        "       tk.tracking_code trackingCode, " +
                        "       im.id            importance, " +
                        "       im.name          importanceName, " +
                        "       tk.read          ticketRead , " +
                        "       ltm.account_id  lastMessageaccount_id,  " +
                        "       ltm.insert_date  lastMessageDate  " +
                        queryPart + " " +
                        " " +
                        "ORDER BY " + orderStr + "  " +
//                        step + " " +
                        "LIMIT :pageLimit OFFSET :pageOffset";

        String queryCount =
                "SELECT Count(*) FROM (  SELECT 1 " + queryPart + " ) mmm ";


        List<Long> resultCount = jdbcTemplate.query(
                queryCount,
                params,
                (res, rowId) -> res.getLong(1)

        );


        long total = resultCount.isEmpty() ? 0L : resultCount.get(0);

        List<TicketDetailRestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, row) -> {

                    //TODO change this to AccountTypeEntity
                    // TODO ticket
                    boolean read = res.getLong("lastMessageaccount_id") == accountId;
//                            (SessionHolder.isDeveloper() && res.getBoolean("isLastMessageFromDeveloper")) ||
//                                    (!SessionHolder.isDeveloper() && !res.getBoolean("isLastMessageFromDeveloper"));

//                    System.out.println(  SessionHolder.getAccountUserName() + " SessionHolder.isDeveloper() " + SessionHolder.isDeveloper() +  " res.getBoolean(\"isLastMessageFromDeveloper\") " + res.getBoolean("isLastMessageFromDeveloper") + " read " + read );

                    return new TicketDetailRestResponse(
                            res.getString("trackingCode"),
                            res.getLong("ticketID"),
                            res.getString("project"),
                            res.getString("subject"),
                            PersianNumberText.toPersianNumber(PersianDateUtil.getPersianDateAndHour(res.getTimestamp("submitDate").getTime())),
                            res.getBoolean("closed"),
                            new IdName(res.getLong("importance"), res.getString("importanceName")),
                            read,
                            PersianNumberText.toPersianNumber(PersianDateUtil.getPersianDateAndHour(res.getTimestamp("lastMessageDate").getTime()))

                    );
                });
//        System.out.println("----------");
        result.stream().distinct().collect(Collectors.toList());
        return new DataTableResponse<>(result, total);
    }

    public void closeTicket(long ticketId, AccountEntity account) throws BehtaShopException {
        TicketEntity ticket =
                ticketRepository.findFirstByIdAndDeletedIsFalse(ticketId);
        if (ticket == null) {
            throw new BehtaShopException("تیکت یافت نشد");
        }

        if (
                account.getId() != ticket.getAccount().getId() &&
                        !(account instanceof OperatorEntity) &&
                        !(account instanceof ProviderEntity)
        ) throw new BehtaShopException("تیکت یافت نشد");

        ticket.setClosed(true);
        ticketRepository.save(ticket);
    }

    public List<IdName> getTicketImportanceList(String search) {

        return importanceRepository.findAllByNameLikeAndDeletedIsFalse( "%" + search + "%").stream().map(m -> new IdName(m.getId(), m.getName())).collect(Collectors.toList());

    }

    public byte[] createTicketListExcel(int project, long ticketImportance, String search, long responseType, long closed, long lastMsgDate, long lastMsgToDate) {


        Map<String, Object> params = new HashMap<>();
        // TODO ticket
        params.put("accountID", 1);
//        params.put("accountID", SessionHolder.getUserId());
        params.put("project", project);
        params.put("ticketImportance", ticketImportance);
        params.put("lastMsgDate", new Date(lastMsgDate));
        params.put("lastMsgToDate", new Date(lastMsgToDate));
        params.put("search", "%" + search + "%");


        String queryPart = "" +
                "FROM ticket tk " +
                "         INNER JOIN project p " +
                "                    on tk.project_id = p.id AND p.deleted = FALSE " +
                (project != 0 ? " AND p.id =:project  " : " ") +
                (!StringsUtil.isNullOrEmpty(search) ? " AND ( tk.subject LIKE LOWER(:search) OR tk.tracking_code LIKE LOWER(:search)) " : " ") +
                (ticketImportance != 0 ? " AND tk.ticket_importance_id =:ticketImportance " : " ") +
                "INNER JOIN account_project ap ON ap.project_id = p.id AND ap.account_id =:accountID AND ap.deleted = FALSE " +
                (responseType == 0 ? " " : (responseType == 2 ? "AND tk.read = true " : " AND tk.read = FALSE  ")) +
                (closed == 0 ? " " : (closed == 1 ? "AND tk.closed = FALSE " : " AND tk.closed = TRUE  ")) +
//                (submitDate != 0 ? " AND DATE(tk.insert_date) => DATE(:submitDate) " : " ") +
//                (submitToDate != 0 ? " AND DATE(tk.insert_date) <= DATE(:submitToDate) " : " ") +
                "         INNER JOIN ticket_importance im on tk.ticket_importance_id = im.id And im.deleted = FALSE " +
                "         INNER JOIN last_ticket_message ltm ON ltm.ticket_id =   tk.id " +
                "         INNER JOIN ticket_message tkm ON ltm.ticket_message_id =   tkm.id " +
                (lastMsgDate != 0 ? " AND ( DATE(ltm.insert_date) >= DATE(:lastMsgDate) OR DATE(tk.insert_date) >= DATE(:lastMsgDate) ) " : " ") +
                (lastMsgToDate != 0 ? " AND ( DATE(ltm.insert_date) <= DATE(:lastMsgToDate) OR DATE(tk.insert_date) <= DATE(:lastMsgToDate) ) " : " ") +
                "WHERE tk.deleted = FALSE " +
                "GROUP BY tk.id, tk.subject, p.name, p.id, tk.insert_date, tk.closed, tk.tracking_code, im.id, im.name, tk.read, ltm.insert_date " +
                "";


        String query =
                "SELECT tk.id            ticketID, " +
                        "       tk.subject       subject, " +
                        "       p.name           project, " +
                        "       p.id             projectId, " +
                        "       tk.insert_date   submitDate, " +
                        "       tk.last_message_developer   isLastMessageFromDeveloper, " +
                        "       tk.closed        closed, " +
                        "       tk.tracking_code trackingCode, " +
                        "       im.id            importance, " +
                        "       im.name          importanceName, " +
                        "       tk.read          ticketRead , " +
                        "       ltm.insert_date  lastMessageDate  " +
                        queryPart + " " +
                        " ";

        List<TicketDetailRestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, row) -> {

                    //TODO change this to AccountTypeEntity
                    // TODO ticket
                    boolean read = false;
//                            (SessionHolder.isDeveloper() && res.getBoolean("isLastMessageFromDeveloper")) ||
//                                    (!SessionHolder.isDeveloper() && !res.getBoolean("isLastMessageFromDeveloper"));

//                    System.out.println(  SessionHolder.getAccountUserName() + " SessionHolder.isDeveloper() " + SessionHolder.isDeveloper() +  " res.getBoolean(\"isLastMessageFromDeveloper\") " + res.getBoolean("isLastMessageFromDeveloper") + " read " + read );

                    return new TicketDetailRestResponse(
                            res.getString("trackingCode"),
                            res.getLong("ticketID"),
                            res.getString("project"),
                            res.getString("subject"),
                            PersianNumberText.toPersianNumber(PersianDateUtil.getPersianDateAndHour(res.getTimestamp("submitDate").getTime())),
                            res.getBoolean("closed"),
                            new IdName(res.getLong("importance"), res.getString("importanceName")),
                            read,
                            PersianNumberText.toPersianNumber(PersianDateUtil.getPersianDateAndHour(res.getTimestamp("lastMessageDate").getTime()))

                    );
                });

        result.stream().distinct().collect(Collectors.toList());

        return excelGeneratorComponent.createTicketExcel(result);
    }

}