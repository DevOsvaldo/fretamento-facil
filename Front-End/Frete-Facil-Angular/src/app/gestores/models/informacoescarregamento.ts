export interface InformacoesCarregamento {
  informacoes: Informacao[];
}

export interface Informacao {
  carga: Carga;
  condutor: Condutor;
}

export interface Carga {
  id: number;
  nomeCliente: string;
  enderecoCliente: string;
  pesoCarga: number;
  valorFrete: number;
  tipoVeiculo: string;
  situacaoCarga: string;
  condutorId: number;
}

export interface Condutor {
  id: number;
  nome: string;
  cpf: string;
  endereco: string;
  cep: string;
  tipo_Veiculo: string;
  capacidadeVeiculo: number;
  deleted: boolean;
  situacaoCondutor: string;
}
