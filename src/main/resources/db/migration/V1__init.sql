create table orgao (
    id bigserial primary key,
    nome varchar(255) not null,
    codigo varchar(100) not null
);

create table unidade (
    id bigserial primary key,
    orgao_id bigint not null references orgao(id),
    nome varchar(255) not null,
    codigo varchar(100) not null
);

create table usuario (
    id bigserial primary key,
    username varchar(150) not null unique,
    password_hash varchar(255) not null,
    orgao_id bigint not null references orgao(id),
    unidade_id bigint references unidade(id),
    cidade_id bigint,
    ativo boolean not null,
    created_at timestamp not null
);

create table grupo (
    id bigserial primary key,
    nome varchar(150) not null unique,
    descricao varchar(255)
);

create table usuario_grupo (
    usuario_id bigint not null references usuario(id),
    grupo_id bigint not null references grupo(id),
    primary key (usuario_id, grupo_id)
);

create table permissao (
    id bigserial primary key,
    code varchar(150) not null unique,
    descricao varchar(255)
);

create table grupo_permissao (
    grupo_id bigint not null references grupo(id),
    permissao_id bigint not null references permissao(id),
    primary key (grupo_id, permissao_id)
);

create table usuario_permissao (
    usuario_id bigint not null references usuario(id),
    permissao_id bigint not null references permissao(id),
    primary key (usuario_id, permissao_id)
);

create table powerbi_report (
    id bigserial primary key,
    nome varchar(255) not null,
    workspace_id varchar(150) not null,
    report_id varchar(150) not null,
    dataset_id varchar(150) not null,
    ativo boolean not null
);

create table menu_item (
    id bigserial primary key,
    parent_id bigint references menu_item(id),
    label varchar(255) not null,
    icon varchar(150),
    route varchar(255),
    ordem int not null,
    ativo boolean not null,
    resource_type varchar(30) not null,
    resource_id bigint,
    permissao_id bigint references permissao(id)
);

create table report_dimension (
    id bigserial primary key,
    report_id bigint not null references powerbi_report(id),
    dimension_key varchar(150) not null,
    dimension_label varchar(255) not null,
    value_type varchar(20) not null,
    active boolean not null,
    unique (report_id, dimension_key)
);

create table report_access_policy (
    id bigserial primary key,
    report_id bigint not null references powerbi_report(id),
    subject_type varchar(20) not null,
    subject_id bigint not null,
    effect varchar(30) not null,
    priority int not null default 0,
    active boolean not null
);

create index idx_policy_subject on report_access_policy (report_id, subject_type, subject_id, active);

create table report_access_policy_rule (
    id bigserial primary key,
    policy_id bigint not null references report_access_policy(id),
    dimension_key varchar(150) not null,
    operator varchar(10) not null,
    values_mode varchar(30) not null,
    user_attribute varchar(150),
    active boolean not null,
    unique (policy_id, dimension_key)
);

create table report_access_policy_rule_value (
    rule_id bigint not null references report_access_policy_rule(id),
    rule_value varchar(255) not null,
    primary key (rule_id, rule_value)
);

create table security_scope (
    id bigserial primary key,
    principal varchar(150) not null,
    report_key varchar(150) not null,
    dimension_key varchar(150) not null,
    dimension_value varchar(255) not null,
    active boolean not null default true,
    updated_at timestamp not null
);

create index idx_security_scope_principal_report on security_scope (principal, report_key);
create index idx_security_scope_dimension on security_scope (principal, report_key, dimension_key);
create unique index ux_security_scope on security_scope (principal, report_key, dimension_key, dimension_value);
