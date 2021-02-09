drop table if exists rsrt_images;
drop table if exists rsrt_price_tags;
drop table if exists rsrt_ads;
drop table if exists rsrt_password_reset_tokens;
drop table if exists rsrt_users;
drop table if exists rsrt_dict_localities;
drop table if exists rsrt_dict_countries;

drop sequence if exists hibernate_sequence;
drop sequence if exists IMAGES_SEQUENCE_ID;
drop sequence if exists AD_SECTION_SEQUENCE_ID;

CREATE SEQUENCE hibernate_sequence START 100;

create sequence if not exists IMAGES_SEQUENCE_ID start 100;
create sequence if not exists AD_SECTION_SEQUENCE_ID start 100;


create table if not exists rsrt_users
(
    id_       bigint primary key,
    username_ varchar(50)  not null,
    password_ varchar(500) not null
);

create table if not exists rsrt_dict_countries (
                                                   id_ bigint primary key,
                                                   name_ru_ varchar(50) not null,
                                                   name_en_ varchar(50) not null
);

create table if not exists rsrt_dict_localities (
                                                    id_ bigint primary key,
                                                    name_ru_ varchar(50) not null,
                                                    name_en_ varchar(50) not null,
                                                    image_file_name_with_ext_ varchar(50) not null,
                                                    country_id_ bigint references rsrt_dict_countries ON DELETE CASCADE not null
);

create table if not exists rsrt_ads (
  id_ bigint primary key,
  owner_id_ bigint references rsrt_users,
  header_ varchar(50),
  created_at_ timestamp with time zone not null,
  edited_at_ timestamp with time zone not null,
  description_ varchar(1000),
  short_description_ varchar(500),
  housing_type_ varchar(20) not null,
  summer_houses_count_ integer,
  views_num_ integer,
  sauna_ boolean,
  bathhouse_ boolean,
  restaurant_ boolean,
  swimming_pool_ boolean,
  billiards_ boolean,
  latitude_ decimal,
  longitude_ decimal,
  status_ varchar(5) not null,
  locality_id_ bigint references rsrt_dict_localities,
  street_ varchar(50),
  house_number_ varchar(50),
  summer_kitchen_ boolean,
  cook_ boolean,
  alcove_ boolean,
  territory_area_ bigint,
  tapchan_ boolean,
  bicycles_ boolean,
  quad_bikes_ boolean,
  playground_ boolean,

  weekday_price_per_day_ bigint ,
  holiday_price_per_day_ bigint ,
  is_price_per_person_ boolean not null,
  room_num_ integer,
  floor_ integer,
  floor_num_ integer,
  sleep_num_ integer,
  area_ integer,
  hot_water_ boolean,
  cold_water_ boolean,
  tv_ boolean,
  fridge_ boolean,
  stove_ boolean,
  washer_ boolean,
  microwave_ boolean,
  wifi_ boolean,
  conditioner_ boolean,
  brazier_ boolean,
  kazan_ boolean,

  CHECK (views_num_ > 0),
  CHECK (room_num_ > 0),
  CHECK (floor_ > 0),
  CHECK (sleep_num_ > 0),
  CHECK (area_ > 0),
  CHECK (weekday_price_per_day_ > 0),
  CHECK (holiday_price_per_day_ > 0)
);

create table if not exists rsrt_images (
  id_ bigint primary key default nextval('IMAGES_SEQUENCE_ID'),
  ad_id_ bigint references rsrt_ads ON DELETE CASCADE,
  file_name_with_ext_ varchar(50) not null,
  mime_type_ varchar(50) not null,
  order_number_ integer,

  CHECK (order_number_ > 0),
  UNIQUE (ad_id_, order_number_)
);

create table if not exists rsrt_price_tags (
  id_ bigint primary key,
  ad_id_ bigint references rsrt_ads ON DELETE CASCADE,
  price_ integer not null,
  start_date_ date not null,
  end_date_ date not null,

  CHECK (price_ > 0)
);


create table if not exists rsrt_password_reset_tokens (
  id_ bigint primary key,
  token_ varchar(50) not null,
  is_used_ boolean not null,
  expiry_time_ timestamp without time zone not null,
  user_id_ bigint references rsrt_users ON DELETE CASCADE
);