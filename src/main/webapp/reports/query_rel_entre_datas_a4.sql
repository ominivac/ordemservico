select 
			cod_os, atividade, data_solicitacao, data_entrega,  quantidade_horas , valor_parcial, valor_total,
		    sum(case  when cod_produto = '2' then valor_parcial end) as v_captacao,
		    sum(case when cod_produto = '2' then quantidade_horas end) as t_horas_captacao,
		    sum(case  when cod_produto = '3' then valor_parcial end) as v_edicao,
		    sum(case when cod_produto = '3' then quantidade_horas end) as t_horas_edicao,
		    sum(case when cod_produto = '4' then valor_parcial end) as v_trans_grav,
		    sum(case when cod_produto = '4' then quantidade_horas end) as t_horas_trans_grav,
	        sum(case when cod_produto = '5' then valor_parcial end) as v_conf_tric,
	        sum(case when cod_produto = '5' then quantidade_horas end) as t_horas_conf_tric,
		    sum(case when cod_produto = '6' then valor_parcial end) as v_comp ,
		    sum(case when cod_produto = '6' then quantidade_horas end) as t_comp
			from os
			inner join
			(select 
						os_cod AS orderid,
						 COUNT (*) AS quantidadeItem,
						 SUM (quantidade_horas) AS total_horas
						FROM item
								group by 
								 os_cod ) newtable

			on  os.cod_os = newtable.orderid 
			INNER JOIN "public".item ON "public".item.os_cod = "public".os.cod_os
			INNER JOIN produto_os ON item.produto_cod = produto_os.cod_produto
			GROUP BY cod_os, orderid, quantidadeitem, total_horas, cod_item, quantidade_horas,valor_parcial, cod_produto
			ORDER BY orderid