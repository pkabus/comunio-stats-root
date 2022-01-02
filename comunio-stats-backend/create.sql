create sequence hibernate_sequence start with 1 increment by 1
create table club_entity (id bigint not null, name varchar(255), primary key (id)) engine=InnoDB
create table player_entity (id bigint not null, link varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table player_snapshot_entity (id bigint not null, created date, market_value bigint, points_during_current_season integer, position varchar(255), club_id bigint, player_id bigint, primary key (id)) engine=InnoDB
alter table player_snapshot_entity add constraint FKjwvl2dbny8uj7og9hsh6eusht foreign key (club_id) references club_entity (id)
alter table player_snapshot_entity add constraint FKiji0a10j60onbjegofgs29e1h foreign key (player_id) references player_entity (id) on delete cascade
