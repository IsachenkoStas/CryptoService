create table crypto_users
(
    id        bigserial
        primary key,
    login     varchar(20) not null,
    password  varchar     not null,
    user_role varchar default 'USER':: character varying not null
);

create table wallet
(
    id             bigserial
        primary key,
    wallet_address varchar(10) not null,
    usd_balance    float       not null,
    usdt_balance   float       not null,
    eth            float       not null,
    btc            float       not null,
    avax           float       not null,
    matic          float       not null,
    sol            float       not null,
    arb            float       not null,
    op             float       not null,
    bnb            float       not null,
    created        timestamp,
    crypto_user_id integer     not null
        constraint wallet_crypto_users_id_fk
            references crypto_users
            on update cascade on delete cascade
);

create table deposits
(
    id          bigserial
        primary key,
    usdt_amount float   not null,
    eth_amount  float   not null,
    wallet_id   integer not null
        constraint deposits_wallet_id_fk
            references wallet
            on update cascade on delete cascade,
    created     timestamp
);

create table transactions
(
    id        bigserial
        primary key,
    amount    float   not null,
    created   timestamp,
    wallet_id integer not null
        constraint transactions_wallet_id_fk
            references wallet
            on update cascade on delete cascade
);

create table crypto_rates
(
    id              bigserial
        primary key,
    base_currency   varchar,
    target_currency varchar,
    rate            float
);