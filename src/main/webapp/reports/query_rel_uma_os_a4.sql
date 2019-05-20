SELECT os.cod_os,
	os.data_solicitacao,
	os.data_previsao,
	os.valor_total,
	item.quantidade,
	item.atividadeitem,
	item.valor_parcial,
	item.data_entrega,
	item.quantidade_horas,
	produto_os.descricao,
	produto_os.valorporhora,
	usuario.nome,
	os.atividade,
	os.data_solicitacao
FROM item
	INNER JOIN os ON 
	 item.os_cod = os.cod_os 
	INNER JOIN usuario ON 
	 os.usuario_cod = usuario.cod_usuario 
	INNER JOIN produto_os ON 
	 item.produto_cod = produto_os.cod_produto 
WHERE 
	 os.cod_os = $P{cod_os}