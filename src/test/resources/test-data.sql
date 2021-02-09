delete from rsrt_dict_localities;
delete from rsrt_dict_countries;
delete from rsrt_section_images;
delete from rsrt_sections;
delete from rsrt_ad_images;
delete from rsrt_ads;
delete from rsrt_users;

/*password = 1q2w3e4r*/
insert into rsrt_users (id_, username_, password_)
values (1, '87013904442', '5d300725295093548317105ce15fe1a1b4385a5368f4d107925566b9003e08bbce22151567e9bc4a');

insert into rsrt_dict_countries (id_, name_ru_, name_en_) values (1, 'Казахстан', 'Kazakhstan');
insert into rsrt_dict_countries (id_, name_ru_, name_en_) values (2, 'Кыргызстан', 'Kyrgyzstan');

insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_) values (1, 'Зеренда', 'Зеренда', 1);
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_) values (2, 'Бурабай', 'Бурабай', 1);
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_) values (3, 'Иссык-Куль', 'Иссык-Куль', 2);
insert into rsrt_dict_localities (id_, name_ru_, name_en_, country_id_) values (4, 'Бишкек', 'Бишкек', 2);
