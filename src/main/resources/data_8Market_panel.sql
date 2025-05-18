INSERT INTO public.account (dtype, provider_id, full_description, short_description,first_name , last_name, phone, banned , image_id, username, accounting_number, active, address, avatar_id, birth_date  ,man, mobile)
VALUES ('OperatorEntity',null , '', '' , 'behrouz', 'kazemi' ,'09153223693', FALSE, NULL, 'test', '14523', TRUE, 'mashhad',NULL, NULL, TRUE, '939123' ) ON CONFLICT (id) DO NOTHING;
INSERT into public.password (id, deleted, hash_password, operator_id)
VALUES (1,false,'$2a$12$CbWY0I3ciPprHwMgbKN7geSO36.FFBNhtOaOCVJKgREx0sh.j3IR.' ,4) -- pass = 1qaz!QAZ