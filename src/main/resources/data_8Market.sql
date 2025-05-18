
-- /////////////////////////////////////////////////    CATEGORY    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.category (id, name, parent_id,image_id) VALUES (1, 'سوپر‌‌مارکت', NULL, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (2, 'لبنیات', 1, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (3, 'تنقلات', 1, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (4, 'بهداشی', 1, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (5, 'شیر', 2, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (6, 'پنیر', 2, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (7, 'کره', 2, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (8, 'بسکوییت', 3, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (9, 'کیک', 3, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (10, 'پاستیل', 3, NULL ) ON CONFLICT (id) DO NOTHING;





-- /////////////////////////////////////////////////   BRAND   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.brand (id, name) VALUES (1, 'کاله' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand (id, name) VALUES (2, 'سلامت' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand (id, name) VALUES (3, 'شیبا' ) ON CONFLICT (id) DO NOTHING;




-- /////////////////////////////////////////////////    BRAND_CATEGORY    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (1, 1, 2 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (2, 1, 5 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (3, 1, 6 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (4, 1, 7 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (5, 1, 7 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (6, 2, 8 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (7, 2, 9 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (12, 2, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (8, 3,  9) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (9, 3, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (10, 3, 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (11, 3, 3) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////  TAG   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.tag (id, name ) VALUES (1, 'خامه ای') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.tag (id, name ) VALUES (2, 'ساده') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.tag (id, name ) VALUES (3, 'شکلاتی') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.tag (id, name ) VALUES (4, 'خرسی') ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////  UNIT   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.unit (id, name, dividable, ratio, tolerance ) VALUES (1, 'گرم', false, 250, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.unit (id, name, dividable, ratio, tolerance ) VALUES (2, 'عدد' , TRUE, 0, 0) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////      PRODUCT       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product (id, name, brand_id, category_id, unit_id, unit_step, short_description, full_description ) VALUES (1, 'پنیر کاله' , 1, 2, 2, 1,'یک پنیر خوشمزه','یک پنیر خوشمزه طولانی' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product (id, name, brand_id, category_id, unit_id, unit_step, short_description, full_description ) VALUES (2, 'کیک سلامت' , 2, 9, 2, 1,'یک کیک خوشمزه','یک کیک خوشمزه طولانی' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product (id, name, brand_id, category_id, unit_id, unit_step, short_description, full_description ) VALUES (3, 'پاستیل شیبا' , 3, 10, 2, 1,'یک پاستیل خوشمزه','یک پاستیل خوشمزه طولانی' ) ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      REGIONAL       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.regional (id, name, description) VALUES (1,'منطقه یک',  'ندارد' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.regional (id, name, description) VALUES (2,'منطقه 2',  'darad' ) ON CONFLICT (id) DO NOTHING;

-- /////////////////////////////////////////////////      PROVIDER       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

-- // TODO ASK provider_id
INSERT INTO public.account (id, name, provider_id, dtype, full_description, short_description,first_name , last_name, phone, banned , image_id, username, accounting_number, active, address, avatar_id, birth_date  ,man, mobile) VALUES (1,'منطقه یک' ,1, 1, '' , '', 'منطقه' , 'یک ' ,'09029090', FALSE, NULL, 'hapi', '123', TRUE, 'mashhad',NULL, NULL, TRUE, '939123' ) ON CONFLICT (id) DO NOTHING;

INSERT INTO public.provider_region (id, region_id, provider_id) VALUES (1, 1,  1 ) ON CONFLICT (id) DO NOTHING;

-- /////////////////////////////////////////////////      ACCOUNT       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\




-- /////////////////////////////////////////////////      PRODUCT_PROVIDER       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product_provider (id, product_id, provider_id, rate, product_provider_order, product_provider_existence, min_allow, max_allow,prepare_hour  ) VALUES (1, 1, 1, 4, 2, TRUE, 1, 5, 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_provider (id, product_id, provider_id, rate, product_provider_order, product_provider_existence, min_allow, max_allow,prepare_hour  ) VALUES (2, 2, 1, 5, 1, TRUE, 1, 10, 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_provider (id, product_id, provider_id, rate, product_provider_order, product_provider_existence, min_allow, max_allow,prepare_hour  ) VALUES (3, 3, 1, 2, 1, FALSE, 1, 10, 1 ) ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      PRODUCT_PROVIDER_PRICE       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

-- /////////////////////////////////////////////////    CATEGORY    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.category (id, name, parent_id,image_id) VALUES (1, 'سوپر‌‌مارکت', NULL, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (2, 'لبنیات', 1, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (3, 'تنقلات', 1, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (4, 'بهداشی', 1, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (5, 'شیر', 2, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (6, 'پنیر', 2, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (7, 'کره', 2, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (8, 'بسکوییت', 3, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (9, 'کیک', 3, NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id,image_id) VALUES (10, 'پاستیل', 3, NULL ) ON CONFLICT (id) DO NOTHING;





-- /////////////////////////////////////////////////   BRAND   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.brand (id, name) VALUES (1, 'کاله' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand (id, name) VALUES (2, 'سلامت' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand (id, name) VALUES (3, 'شیبا' ) ON CONFLICT (id) DO NOTHING;




-- /////////////////////////////////////////////////    BRAND_CATEGORY    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (1, 1, 2 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (2, 1, 5 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (3, 1, 6 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (4, 1, 7 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (5, 1, 7 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (6, 2, 8 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (7, 2, 9 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (12, 2, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (8, 3,  9) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (9, 3, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (10, 3, 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.brand_category (id, brand_id, category_id ) VALUES (11, 3, 3) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////  TAG   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.tag (id, name ) VALUES (1, 'خامه ای') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.tag (id, name ) VALUES (2, 'ساده') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.tag (id, name ) VALUES (3, 'شکلاتی') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.tag (id, name ) VALUES (4, 'خرسی') ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////  UNIT   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.unit (id, name, dividable, ratio, tolerance ) VALUES (1, 'گرم', false, 250, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.unit (id, name, dividable, ratio, tolerance ) VALUES (2, 'عدد' , TRUE, 0, 0) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////      PRODUCT       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product (id, name, brand_id, category_id, unit_id, unit_step, short_description, full_description ) VALUES (1, 'پنیر کاله' , 1, 2, 2, 1,'یک پنیر خوشمزه','یک پنیر خوشمزه طولانی' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product (id, name, brand_id, category_id, unit_id, unit_step, short_description, full_description ) VALUES (2, 'کیک سلامت' , 2, 9, 2, 1,'یک کیک خوشمزه','یک کیک خوشمزه طولانی' ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product (id, name, brand_id, category_id, unit_id, unit_step, short_description, full_description ) VALUES (3, 'پاستیل شیبا' , 3, 10, 2, 1,'یک پاستیل خوشمزه','یک پاستیل خوشمزه طولانی' ) ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      REGIONAL       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.regional (id, name, description) VALUES (1,'منطقه یک',  'ندارد' ) ON CONFLICT (id) DO NOTHING;

-- /////////////////////////////////////////////////      PROVIDER       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.provider_region (id, region_id, provider_id) VALUES (1, 1,  1 ) ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      ACCOUNT       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.account (id, name, provider_id, dtype, full_description, short_description,first_name , last_name, phone, banned , image_id, username, accounting_number, active, address, avatar_id, birth_date  ,man, mobile) VALUES (1,'منطقه یک' ,1, 1, '' , '', 'منطقه' , 'یک ' ,'09029090', FALSE, NULL, 'hapi', '123', TRUE, 'mashhad',NULL, NULL, TRUE, '939123' ) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////      PRODUCT_PROVIDER       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product_provider (id, product_id, provider_id, rate, product_provider_order, product_provider_existence, min_allow, max_allow,prepare_hour  ) VALUES (1, 1, 1, 4, 2, TRUE, 1, 5, 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_provider (id, product_id, provider_id, rate, product_provider_order, product_provider_existence, min_allow, max_allow,prepare_hour  ) VALUES (2, 2, 1, 5, 1, TRUE, 1, 10, 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_provider (id, product_id, provider_id, rate, product_provider_order, product_provider_existence, min_allow, max_allow,prepare_hour  ) VALUES (3, 3, 1, 2, 1, FALSE, 1, 10, 1 ) ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      PRODUCT_PROVIDER_PRICE       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


INSERT INTO public.product_provider_price (id, product_provider_id, primitive_amount, off_price, off_percent, final_amount) VALUES (1, 1, 17000, 1700, 10, 15300  ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_provider_price (id, product_provider_id, primitive_amount, off_price, off_percent, final_amount) VALUES (2, 2, 10000, 1000, 10, 90000  ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_provider_price (id, product_provider_id, primitive_amount, off_price, off_percent, final_amount) VALUES (3, 3, 5000, 1234, 1234, 1234  ) ON CONFLICT (id) DO NOTHING;

-- /////////////////////////////////////////////////      PRODUCT_TAG       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (1, 1, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (2, 1, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (3, 2, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (4, 2, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (5, 3, 2) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////      PROMOTE       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.promote (id,name) VALUES (1, 'فروش ویژه') ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      PROMOTE_PROVIDER_PRODUCT      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product_promote (id,product_provider_id,promote_id , order_show ) VALUES (1, 1, 1 , 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_promote (id,product_provider_id,promote_id , order_show ) VALUES (2, 2, 1 , 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_promote (id,product_provider_id,promote_id , order_show ) VALUES (3, 3, 1 , 3) ON CONFLICT (id) DO NOTHING;

-- /////////////////////////////////////////////////      PRODUCT_TAG       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (1, 1, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (2, 1, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (3, 2, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (4, 2, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_tag (id,product_id, tag_id) VALUES (5, 3, 2) ON CONFLICT (id) DO NOTHING;



-- /////////////////////////////////////////////////      PROMOTE       \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.promote (id,name) VALUES (1, 'فروش ویژه') ON CONFLICT (id) DO NOTHING;


-- /////////////////////////////////////////////////      PROMOTE_PROVIDER_PRODUCT      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.product_promote (id,product_provider_id,promote_id , order_show ) VALUES (1, 1, 1 , 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_promote (id,product_provider_id,promote_id , order_show ) VALUES (2, 2, 1 , 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.product_promote (id,product_provider_id,promote_id , order_show ) VALUES (3, 3, 1 , 3) ON CONFLICT (id) DO NOTHING;




-- /////////////////////////////////////////////////////////          BILL STATUS           \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

INSERT INTO public.bill_status(id, name) VALUES (1, 'تعریف شده') ON CONFLICT (Id) DO NOTHING;
INSERT INTO public.bill_status(id, name) VALUES (2, 'در انتظار پرداخت') ON CONFLICT (Id) DO NOTHING;
INSERT INTO public.bill_status(id, name) VALUES (3, 'پرداخت شده') ON CONFLICT (Id) DO NOTHING;
INSERT INTO public.bill_status(id, name) VALUES (4, 'در حال ارسال') ON CONFLICT (Id) DO NOTHING;
INSERT INTO public.bill_status(id, name) VALUES (5, 'تحویل داده شد') ON CONFLICT (Id) DO NOTHING;
INSERT INTO public.bill_status(id, name) VALUES (6, 'لغو شده') ON CONFLICT (Id) DO NOTHING;