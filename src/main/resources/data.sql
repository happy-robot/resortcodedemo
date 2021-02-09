delete from rsrt_dict_localities;
delete from rsrt_dict_countries;
delete from rsrt_images;
delete from rsrt_ads;
delete from rsrt_users;

/*password = 1q2w3e4r*/
insert into rsrt_users (id_, username_, password_)
values (1, '87013904442', '5d300725295093548317105ce15fe1a1b4385a5368f4d107925566b9003e08bbce22151567e9bc4a');

insert into rsrt_dict_countries (id_, name_ru_, name_en_) values (1, 'Казахстан', 'Kazakhstan');
insert into rsrt_dict_countries (id_, name_ru_, name_en_) values (2, 'Кыргызстан', 'Kyrgyzstan');

insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (1, 'Зеренда', 'Zerenda', 1, 'zerenda.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (2, 'Бурабай', 'Burabay', 1, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (3, 'Алаколь', 'Alakol', 1, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (4, 'Балхаш', 'Balkhash', 1, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (5, 'Баянаул', 'Bayanaul', 1, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (6, 'Капчагай', 'Kapchagay', 1, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (7, 'Каркаралинск', 'Karkaralinsk', 1, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (8, 'Иссык-Куль', 'Issyk-Kul', 2, 'burabay.jpg');
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_, image_file_name_with_ext_) values (9, 'Каракол', 'Karakol', 2, 'burabay.jpg');

insert into rsrt_ads (id_, owner_id_, header_, description_, short_description_, created_at_, edited_at_, housing_type_, views_num_,
sauna_, bathhouse_, restaurant_, swimming_pool_, billiards_, latitude_, longitude_, status_, locality_id_, street_, house_number_,

is_price_per_person_, room_num_, floor_, floor_num_,
sleep_num_, area_, hot_water_, cold_water_, tv_, fridge_, stove_, washer_, microwave_, wifi_, conditioner_,
brazier_

)
values (1, 1, 'header1',
'осень! зима! весна! 5000 тысяч тенге в сутки!Предлагаю Вам снять в аренду трёхкомнатную квартиру посуточно в центре Борового. ' ||
 'Чистая и уютная квартира, оснащена всем необходимым и полностью меблирована, только после ремонта! Так же есть и летние комнаты, ' ||
  'недорого, в районе Чебачьего озера, на 5 человек. Рядом лес, озеро в 5 минутах ходьбы! На территории имеется бесплатная автостоянка, ' ||
   'кухня для собственного приготовления и место для палаток! Это идеальное место для спокойного отдыха! В лесу много ягод и грибов! ' ||
    'Сдам на зимний период. неугловая, комнаты изолированы, тихий двор, чистая, уютная, холодильник, кабельное ТВ, по часам, телевизор, вся бытовая техника',
'Рядом лес, озеро в 5 минутах ходьбы! На территории имеется бесплатная автостоянка, кухня для собственного приготовления и ' ||
 'место для палаток! Это идеальное место для спокойного отдыха! В лесу много ягод и грибов! Сдам на зимний период.',
'2004-10-19 10:23:54+02', '2004-10-19 10:23:54+02', 'FLT', 1000, true, true, true, true, true, 53.093154, 70.303322, 'P',
1, 'Менжинского', '92',

false, 3, 2, 5, 4, 33, true, true, true, true, true, true, true, true, true, true
);


insert into rsrt_images (id_, ad_id_, file_name_with_ext_, mime_type_, order_number_)
values (1, 1, '0d39bbe2-6326-4a1f-959b-0ba78238b6e2.jpg', 'image/jpeg', 1);
insert into rsrt_images (id_, ad_id_, file_name_with_ext_, mime_type_, order_number_)
values (2, 1, '45131a9e-d449-4dcf-928b-5074cc35134e.jpg', 'image/jpeg', 2);
insert into rsrt_images (id_, ad_id_, file_name_with_ext_, mime_type_, order_number_)
values (3, 1, '030e43ec-2cbc-48a5-9416-b7b3b9a2a752.jpg', 'image/jpeg', 3);
