create table organizations
(
    id                   bigint unsigned auto_increment primary key,
    name                 varchar(255)            not null comment '企業名',
    representative_name  varchar(255)            not null comment '代表者名',
    phone_number         varchar(20)             not null comment '電話番号',
    postal_code          char(7)                 not null comment '郵便番号',
    address              varchar(255)            not null comment '住所',
    created_at           timestamp               not null default current_timestamp,
    updated_at           timestamp               not null default current_timestamp on update current_timestamp
) comment '企業';


create table users
(
    id              bigint unsigned auto_increment primary key,
    organization_id bigint unsigned comment '企業 ID',
    name            varchar(255) not null comment '氏名',
    email           varchar(255) not null comment 'メールアドレス',
    password        varchar(255) not null comment 'パスワード',
    salt            varchar(255) not null comment 'ソルト',
    verify_token    varchar(255) not null unique key comment '認証用のトークン',
    verified        bool         not null default false comment '認証済みフラグ。true の場合は認証済み。',
    created_at      timestamp    not null default current_timestamp,
    updated_at      timestamp    not null default current_timestamp on update current_timestamp
) comment 'ユーザー';


create table payments
(
    id                       bigint unsigned auto_increment primary key,
    user_id                  bigint unsigned not null,
    amount                   int             not null,
    fee                      int             not null,
    tax_rate                 int             not null,
    billing_amount           int             not null,
    transfer_date            date            not null,
    uploaded_date            date            not null,
    status                   int             not null,
    created_at               timestamp       not null default current_timestamp,
    updated_at               timestamp       not null default current_timestamp on update current_timestamp
);

create table payment_bank_accounts
(
    id             bigint unsigned auto_increment primary key,
    payment_id     bigint unsigned not null unique key,
    bank_code      varchar(4)      not null,
    bank_name      varchar(64)     not null,
    branch_code    varchar(3)      not null,
    branch_name    varchar(64)     not null,
    account_type   int             not null,
    account_number varchar(8)      not null,
    account_holder varchar(128)    not null,
    created_at     timestamp       not null default current_timestamp,
    updated_at     timestamp       not null default current_timestamp on update current_timestamp
);

create table banks
(
    id         bigint unsigned auto_increment primary key,
    bank_code  varchar(4)  not null unique key,
    bank_name  varchar(64) not null,
    created_at timestamp   not null default current_timestamp,
    updated_at timestamp   not null default current_timestamp on update current_timestamp
);

create table bank_branches
(
    id          bigint unsigned auto_increment primary key,
    bank_code   varchar(4)  not null,
    branch_code varchar(3)  not null,
    branch_name varchar(64) not null,
    created_at  timestamp   not null default current_timestamp,
    updated_at  timestamp   not null default current_timestamp on update current_timestamp,
    unique key uk_bank_code_branch_code (bank_code, branch_code)
);
