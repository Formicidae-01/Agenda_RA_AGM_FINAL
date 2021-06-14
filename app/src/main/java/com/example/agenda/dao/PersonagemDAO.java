package com.example.agenda.dao;

import com.example.agenda.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {

    //Esse script salvará os itens criados numa lista persistente

    //Lista onde os itens serão armazenados
    final static List<Personagem> personagens = new ArrayList<>();
    //Índice contador, aumenta sempre que um objeto novo é salvo, serve para atribuir o índice aos itens criados
    static int contadorId = 1;

    //Método que salva na lista de personagens um novo item criado
    public void Salvar(Personagem personagem)
    {
        //Define o índice do item criado como o índice contador
        personagem.setId(contadorId);
        //Adiciona o item criado à lista
        personagens.add(personagem);
        //Altera o valor do índice contador, aumentando-o
        contadorId++;
    }

    //Método que edita um item existente na lista
    public void Editar(Personagem personagem)
    {
        //Obtém na lista o personagem que corresponde ao parâmetro desse método, faz a busca através do índice
        Personagem personagemEscolhido = BuscaID(personagem);

        //Executa função caso o personagem tenha sido encontrado na lista
        if (personagem != null)
        {
            //Obtém a posição do personagem atual na lista
            int posicaoPersonagem = personagens.indexOf(personagemEscolhido);
            //Coloca o personagem do parâmetro na lista, atualizando o objeto presente nela
            personagens.set(posicaoPersonagem, personagem);
        }
    }

    //Método que encontra o índice do personagem
    private Personagem BuscaID(Personagem personagem)
    {
        //Verifica todos os personagens na lista
        for(Personagem p : personagens)
        {
            //Executa função se o índice certo for encontrado
            if (p.getId() == personagem.getId())
            {
                //Retorna o personagem desejado
                return p;
            }
        }
        //Retorna valor nulo caso o índice não seja encontrado
        return null;
    }

    //Método que remove um personagem da lista
    public void Remover(Personagem personagem)
    {
        //Identifica na lista o personagem a ser removido
        Personagem personagemDevolvido = BuscaID(personagem);
        //Executa função se o personagem for encontrado na linha anterior
        if (personagemDevolvido != null)
        {
            //Remove o personagem da lista
            personagens.remove(personagem);
        }
    }

    //Método que retorna toda a lista de objetos
    public List<Personagem> Todos()
    {
        //Retorna a lista com os objetos criados
        return new ArrayList<>(personagens);
    }

}
