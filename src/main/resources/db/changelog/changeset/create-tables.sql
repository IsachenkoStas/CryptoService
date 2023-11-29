create table crypto_users
(
    id        bigint      not null AUTO_INCREMENT primary key,
    login     varchar(20) not null,
    password  varchar     not null,
    user_role varchar default 'USER':: character varying not null
);

create table accounts
(
    id                          bigint         not null AUTO_INCREMENT primary key,
    user_id                     bigint         not null,
    balance                     decimal(15, 2) not null,
    currency_code               varchar(4)     not null,
    account_type                varchar(10)    not null,
    interest_rate               decimal(5, 4) default null,
    last_interest_application   timestamp null,
    interest_compounding_period varchar(10) null,
    created                     timestamp     default CURRENT_TIMESTAMP,
    foreign key (user_id) references crypto_users(id)
);

create table transactions
(
    id               bigint         not null AUTO_INCREMENT primary key,
    amount           decimal(15, 2) not null,
    created          timestamp default CURRENT_TIMESTAMP,
    account_id       bigint         not null,
    transaction_type varchar(20)    not null,
    foreign key (account_id) references accounts(id)
);

create table crypto_rates
(
    id              bigint  not null AUTO_INCREMENT primary key,
    base_currency   varchar not null,
    target_currency varchar not null,
    rate            decimal not null,
    last_updated    timestamp default CURRENT_TIMESTAMP
);