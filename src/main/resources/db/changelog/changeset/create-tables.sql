create table crypto_users
(
    id        bigint      not null AUTO_INCREMENT primary key,
    login     varchar(20) not null,
    password  varchar     not null,
    user_role varchar default 'USER':: character varying not null
);

create table accounts
(
    id                          bigint
        primary key,
    user_id                     bigint         not null
        constraint accounts_crypto_users_id_fk
            references accounts,
    balance                     decimal(15, 2) not null,
    currency_code               varchar(4)     not null,
    account_type                varchar(10)    not null,
    interest_rate               decimal(5, 4) default null,
    last_interest_application   timestamp null,
    interest_compounding_period varchar(10) null,
    created                     timestamp     default CURRENT_TIMESTAMP
);

create table transactions
(
    id               bigint
        primary key,
    amount           decimal(15, 2) not null,
    created          timestamp,
    account_id       bigint         not null
        constraint transactions_accounts_id_fk
            references accounts,
    transaction_type varchar(20)    not null
);

create table crypto_rates
(
    id              bigint
        primary key,
    base_currency   varchar,
    target_currency varchar,
    rate            decimal,
    last_updated    timestamp
);