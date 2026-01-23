insert into orgao (id, nome, codigo)
values
    (1, 'Orgao A', 'ORG-A'),
    (2, 'Orgao B', 'ORG-B');

insert into unidade (id, orgao_id, nome, codigo)
values
    (1, 1, 'Unidade 1', 'UNI-1'),
    (2, 1, 'Unidade 2', 'UNI-2'),
    (3, 2, 'Unidade 3', 'UNI-3');

insert into permissao (id, code, descricao)
values
    (1, 'ADMIN', 'Admin access'),
    (2, 'MENU.DASH.A', 'Dashboard A'),
    (3, 'MENU.DASH.B', 'Dashboard B');

insert into grupo (id, nome, descricao)
values
    (1, 'FINANCEIRO', 'Grupo Financeiro'),
    (2, 'REGIONAL', 'Grupo Regional');

insert into grupo_permissao (grupo_id, permissao_id)
values
    (1, 2),
    (2, 3);

insert into usuario (id, username, password_hash, orgao_id, unidade_id, cidade_id, ativo, created_at)
values
    (1, 'admin', '$2a$10$GVlO4wp2.Ov4PJP0z4XvHe9znhQICwbBO/L207Sn.Fc/.jHImDCEC', 1, 1, 100, true, now()),
    (2, 'userA', '$2a$10$GVlO4wp2.Ov4PJP0z4XvHe9znhQICwbBO/L207Sn.Fc/.jHImDCEC', 1, 2, 200, true, now()),
    (3, 'userB', '$2a$10$GVlO4wp2.Ov4PJP0z4XvHe9znhQICwbBO/L207Sn.Fc/.jHImDCEC', 1, 1, 300, true, now());

insert into usuario_grupo (usuario_id, grupo_id)
values
    (2, 1),
    (3, 1),
    (3, 2);

insert into usuario_permissao (usuario_id, permissao_id)
values
    (1, 1),
    (1, 2),
    (1, 3);

insert into powerbi_report (id, nome, workspace_id, report_id, dataset_id, ativo)
values
    (1, 'Painel A', 'workspace-A', 'report-A', 'dataset-A', true),
    (2, 'Painel B', 'workspace-B', 'report-B', 'dataset-B', true);

insert into menu_item (id, parent_id, label, icon, route, ordem, ativo, resource_type, resource_id, permissao_id)
values
    (1, null, 'Dashboards', 'dashboard', null, 1, true, 'PAGE', null, null),
    (2, 1, 'Painel A', 'chart', '/dash/a', 1, true, 'POWERBI_REPORT', 1, 2),
    (3, 1, 'Painel B', 'chart', '/dash/b', 2, true, 'POWERBI_REPORT', 2, 3);

insert into report_dimension (id, report_id, dimension_key, dimension_label, table_name, column_name, value_type, active)
values
    (1, 1, 'orgao_id', 'Orgao', 'DADOS_ADIANTAMENTO', 'IDORGAO', 'INT', true),
    (2, 1, 'unidade_id', 'Unidade', 'DADOS_ADIANTAMENTO', 'IDUNIDADE', 'INT', true),
    (3, 1, 'tipo_despesa_id', 'Tipo Despesa', 'DADOS_ADIANTAMENTO', 'TIPO_DESPESA_ID', 'STRING', true),
    (4, 2, 'cidade_id', 'Cidade', 'DADOS_CIDADE', 'CIDADE_ID', 'INT', true),
    (5, 2, 'tipo_despesa_id', 'Tipo Despesa', 'DADOS_CIDADE', 'TIPO_DESPESA_ID', 'STRING', true);

insert into report_access_policy (id, report_id, subject_type, subject_id, effect, priority, active)
values
    (1, 1, 'GROUP', 1, 'ALLOW_LIST', 10, true),
    (2, 1, 'USER', 3, 'ALLOW_LIST', 20, true),
    (3, 2, 'GROUP', 2, 'ALLOW_LIST', 10, true);

insert into report_access_policy_rule (id, policy_id, dimension_key, operator, values_mode, user_attribute, active)
values
    (1, 1, 'orgao_id', 'IN', 'FROM_USER_ATTRIBUTE', 'orgao_id', true),
    (2, 1, 'unidade_id', 'IN', 'STATIC', null, true),
    (3, 1, 'tipo_despesa_id', 'IN', 'STATIC', null, true),
    (4, 2, 'orgao_id', 'IN', 'FROM_USER_ATTRIBUTE', 'orgao_id', true),
    (5, 2, 'unidade_id', 'IN', 'FROM_USER_ATTRIBUTE', 'unidade_id', true),
    (6, 2, 'tipo_despesa_id', 'IN', 'STATIC', null, true),
    (7, 3, 'cidade_id', 'IN', 'FROM_USER_ATTRIBUTE', 'cidade_id', true),
    (8, 3, 'tipo_despesa_id', 'IN', 'STATIC', null, true);

insert into report_access_policy_rule_value (rule_id, rule_value)
values
    (2, '*'),
    (3, '*'),
    (6, 'X'),
    (8, '*');
