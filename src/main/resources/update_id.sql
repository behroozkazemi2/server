SELECT setval('bank_transaction_id_seq', 150);

DROP VIEW last_ticket_message CASCADE;
CREATE OR REPLACE VIEW last_ticket_message AS
    (
        SELECT tk.id ticket_id,
               t.account_id,
               tm.ticket_message_id ticket_message_id,
               t.insert_date insert_date
        FROM ticket tk
                 INNER JOIN (
            SELECT
                   MAX(tkms.id)   ticket_message_id,
                   tkms.ticket_id ticket_id
            FROM ticket_message tkms
            WHERE tkms.deleted = FALSE
            GROUP BY tkms.ticket_id
                     ) tm ON tm.ticket_id = tk.id AND tk.deleted = FALSE
        INNER JOIN ticket_message t on tm.ticket_message_id = t.id
        WHERE tk.deleted = FALSE
    );


INSERT INTO project(id,name) values (1,'بهتا‌تهویه');
INSERT INTO ticket_importance(id,deleted,insert_date,name) values (1,false,now(),'خیلی فوری');
INSERT INTO ticket_importance(id,deleted,insert_date,name) values (2,false,now(),'فوری');
INSERT INTO ticket_importance(id,deleted,insert_date,name) values (3,false,now(),'عادی');
INSERT INTO ticket_importance(id,deleted,insert_date,name) values (4,false,now(),'پایین');



INSERT INTO information_category(id,name, show_order) values (1, 'مشخصات عمومی', 1);
INSERT INTO information_category(id,name, show_order) values (2, 'مشخصات تخصصی', 2);