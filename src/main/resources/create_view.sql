CREATE OR REPLACE VIEW product_price_last as
  SELECT pp.* FROM product_provider_price pp
    INNER JOIN (
                 SELECT max(pp2.id) id
                 FROM product_provider_price pp2
                 GROUP BY pp2.product_provider_id
               ) ppg ON ppg.id = pp.id;




CREATE OR REPLACE VIEW balance_last as
  SELECT pp.* FROM balance pp
    INNER JOIN (
                 SELECT max(pp2.id) id
                 FROM balance pp2
                 GROUP BY pp2.account_id
               ) ppg ON ppg.id = pp.id;






CREATE OR REPLACE  VIEW bill_bill_status_last as
SELECT bb.* FROM bill_bill_status bb
                     INNER JOIN (
    SELECT max(bb2.id) id
    FROM bill_bill_status  bb2
    GROUP BY bb2.bill_id
) bbg ON bbg.id = bb.id;



