create unique index accounts_currency_code_account_type_index
    on accounts (user_id, currency_code, account_type);