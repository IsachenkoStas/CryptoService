create unique index accounts_currency_code_account_type_index
    on accounts (user_id, currency_code, account_type);

create unique index transaction_fees_currency_index
    on transaction_fees (currency);