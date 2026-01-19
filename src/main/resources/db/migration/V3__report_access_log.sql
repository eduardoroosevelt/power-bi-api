create table report_access_log (
    id bigserial primary key,
    report_id bigint not null references powerbi_report(id),
    usuario_id bigint not null references usuario(id),
    username varchar(150) not null,
    ip_address varchar(100) not null,
    duration_seconds int not null,
    accessed_at timestamp not null
);

create index idx_report_access_log_report on report_access_log (report_id);
create index idx_report_access_log_user on report_access_log (usuario_id);
