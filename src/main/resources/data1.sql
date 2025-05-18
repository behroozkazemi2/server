create extension postgis;
INSERT INTO public.category (id, name, parent_id) VALUES (1, 'رمضان', NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (2, 'شیرینی', 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (3, 'شکلات', 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (4, 'پاستیل', 1 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (5, 'کیک', 1 ) ON CONFLICT (id) DO NOTHING;

INSERT INTO public.category (id, name, parent_id) VALUES (6, 'گل', NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (7, 'رز', 6 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (8, 'بنقشه', 6 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (9, 'لاله', 6 ) ON CONFLICT (id) DO NOTHING;

INSERT INTO public.category (id, name, parent_id) VALUES (10, 'بستنی', NULL ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (11, 'شکلاتی', 10 ) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.category (id, name, parent_id) VALUES (12, 'سنتی', 10 ) ON CONFLICT (id) DO NOTHING;

INSERT INTO public.unit (id, name, dividable, ratio, tolerance) VALUES (1, 'کیلوگرم', TRUE, 1, 0.1) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.unit (id, name, dividable, ratio, tolerance) VALUES (2, 'گرم', FALSE , 50, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.unit (id, name, dividable, ratio, tolerance) VALUES (3, 'گرم', FALSE , 100, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO public.unit (id, name, dividable, ratio, tolerance) VALUES (4, 'تعداد ', FALSE , 1, 0) ON CONFLICT (id) DO NOTHING;

-- INSERT INTO public.product_tag (id, name) VALUES (1) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.product_tag (id, name) VALUES (2, 'خامه ای') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.product_tag (id, name) VALUES (3, 'گرون') ON CONFLICT (id) DO NOTHING;

INSERT INTO public.order_status (id, name) VALUES (1, 'اضافه') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.order_status (id, name) VALUES (2, 'حذف') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.order_status (id, name) VALUES (3, 'پرداخت شده') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.order_status (id, name) VALUES (4, 'افضایش تعداد') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.order_status (id, name) VALUES (5, 'کاهش تعداد') ON CONFLICT (id) DO NOTHING;

-- INSERT INTO public.bill_status (id, name) VALUES (1, 'ثبت شد') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.bill_status (id, name) VALUES (2, 'درحال آماده سازی') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.bill_status (id, name) VALUES (3, 'ارسال شد') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.bill_status (id, name) VALUES (4, 'تحویل داده شد') ON CONFLICT (id) DO NOTHING;

INSERT INTO public.bill_status (id, name) VALUES (1, 'در انتظار پرداخت') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.bill_status (id, name) VALUES (2, 'پرداخت شد') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.bill_status (id, name) VALUES (3, 'در حال ارسال') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.bill_status (id, name) VALUES (4, 'تحویل داده شد') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.bill_status (id, name) VALUES (5, 'لغو شد') ON CONFLICT (id) DO NOTHING;
update public.bill_status set name = 'در انتظار پرداخت'where  id = 1;
update public.bill_status set name = 'پرداخت شد'where  id = 2;
update public.bill_status set name = 'در حال ارسال'where  id = 3;
update public.bill_status set name = 'تحویل داده شد'where  id = 4;
update public.bill_status set name = 'لغو شد'where  id = 5;


-- INSERT INTO public.bill_charge_status (id, name) VALUES (1, 'لینک دانلود تولید شد') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.bill_charge_status (id, name) VALUES (2, 'خطا در Request') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.bill_charge_status (id, name) VALUES (3, 'با موفقیت انجام شد') ON CONFLICT (id) DO NOTHING;

INSERT INTO public.comment_status (id, name) VALUES (1, 'ثبت شده') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.comment_status (id, name) VALUES (2, 'تایید شده') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.comment_status (id, name) VALUES (3, 'خذف شده') ON CONFLICT (id) DO NOTHING;

-- INSERT INTO public.call_support_status (id, name) VALUES (1, 'جدید') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.call_support_status (id, name) VALUES (2, 'پاسخ داده شده') ON CONFLICT (id) DO NOTHING;

-- INSERT INTO public.gender (id, name) VALUES (1, 'مرد') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.gender (id, name) VALUES (2, 'زن') ON CONFLICT (id) DO NOTHING;

-- INSERT INTO public.special_product_provider_status(id, name) VALUES (1, 'در حال بررسی') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.special_product_provider_status(id, name) VALUES (2, 'قیمت گذاری شد') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.special_product_provider_status(id, name) VALUES (3, 'به سبد خرید اضافه‌شد') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.special_product_provider_status(id, name) VALUES (4, 'حذف شد') ON CONFLICT (id) DO NOTHING;


-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (1,true,false,'مشهد',null) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (2,true,false,'تهران',null) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (5,false,false,'شمال',2) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (6,false,false,'غرب',2) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (7,false,false,'جنوب',2) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (8,false,false,'شرق',2) ON CONFLICT (id) DO NOTHING;
-- INSERT INTO public.regional (id, city, deleted, name, regional_parent_id) VALUES (10,true,false,'کرج',null) ON CONFLICT (id) DO NOTHING;



INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '9-10',  '09:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '10-11', '10:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '11-12', '11:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '12-13', '12:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '13-14', '13:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '14-15', '14:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '15-16', '15:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '16-17', '16:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '17-18', '17:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '18-19', '18:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '19-20', '19:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '20-21', '20:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '21-22', '21:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '22-23', '23:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '23-00', '00:00:00');
INSERT INTO candidate_time( deleted, insert_date, name, time) VALUES (FALSE, now(), '00-01', '01:00:00');


-- INSERT INTO public.account(dtype ,name, phone, short_description, full_description, rate)
-- VALUES ('ProviderEntity', 'تامین کننده ۱', '39256190', 'توضیح کوتاه', 'توضیح کامل' , 5);
-- INSERT INTO public.account(dtype, id ,name, phone, short_description, full_description, rate)
-- VALUES ('ProviderEntity', 'تامین کننده 2', '31234567', 'توضیح کوتاه', 'توضیح کامل' , 3);
-- INSERT INTO public.account(dtype, id ,name, phone, short_description, full_description, rate)
-- VALUES ('ProviderEntity', 'تامین کننده 3', '36041245', 'توضیح کوتاه', 'توضیح کامل' , 4);
-- INSERT INTO public.account(dtype, id ,name, phone, short_description, full_description, rate)
-- VALUES ('ProviderEntity', 'تامین کننده 4', '34125907', 'توضیح کوتاه', 'توضیح کامل' , 4);
-- INSERT INTO public.account(dtype, id ,name, phone, short_description, full_description, rate)
-- VALUES ('ProviderEntity', 'تامین کننده 5', '34110297', 'توضیح کوتاه', 'توضیح کامل' , 1);
--
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی خامه ای', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی گردویی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی کشمشی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی زبان', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی ناپلئونی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی نخودچی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی پسته ای', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی باقلوا', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی نارگیلی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی قرابیه', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی قطاب یزدی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی کنجدی', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('شیرینی اریس', 2);
-- INSERT INTO public.product(name, category_id) VALUES ('کاکائویی', 3);
-- INSERT INTO public.product(name, category_id) VALUES ('شکلات تلخ', 3);
-- INSERT INTO public.product(name, category_id) VALUES ('شکلات سفید', 3);
-- INSERT INTO public.product(name, category_id) VALUES ('شکلات شیری', 3);


UPDATE product_provider SET rate = 5 where deleted = FALSE;



INSERT INTO off_code ( deleted, insert_date, code, description, expire_date, for_all, max_amount, min_amount, percent, start_date, usage_count, updater_id, customer_id, product_provider_id, provider_id)
VALUES (FALSE, now(), 'test_12345', 'code_for_test', '2024-12-31 00:09:17.143000', TRUE, 10000, 1000, 20, now(), 4, null, '5' , null, null);







-----------------------------------------

INSERT INTO public.banner_type(name) VALUES ('اسلایدر بالا‌ صفحه اصلی');
INSERT INTO public.banner_type(name) VALUES ('عکس‌های کنار اسلایدر بالا‌ صفحه اصلی');
INSERT INTO public.banner_type(name) VALUES ('عکس‌های  پایین صفحه اصلی');


UPDATE banner SET type_id = 1 WHERE deleted = false
SELECT setval('category_id_seq', (SELECT MAX(id) FROM public.category));

