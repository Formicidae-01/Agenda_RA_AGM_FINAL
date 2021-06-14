package com.example.agenda.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.dao.PersonagemDAO;
import com.example.agenda.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaPersonagemActivity extends AppCompatActivity {

    //Cria um objeto que receberá os personagens salvos
    final PersonagemDAO dao = new PersonagemDAO();
    //Objeto que manipula uma lista/array de objetos
    ArrayAdapter<Personagem> adapter;

    //Método que ocorre no início desse script
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Executa método que iniciará a tela escolhida
        super.onCreate(savedInstanceState);
        //Reproduz na tela os elementos contidos no xml da tela principal
        setContentView(R.layout.activity_main);
        //Altera o título do aplicativo na tela
        setTitle(ConstantActivities.tituloTelaLista);

        //Método que administra o botão que levará o usuário à tela de criação
        BotaoNovoPeronagem();
        //Método que configurará a lista presente na tela
        ConfiguraLista();
    }

    private void BotaoNovoPeronagem() {
        //Atribui um objeto de botão de acordo com o botão presente no layout
        FloatingActionButton botaoAdicionar = findViewById(R.id.fab_add);
        //Método que irá alterar o que acontecerá ao clicar no botão
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            //Define que ao clicar no botão, abrirá a tela de formulário
            public void onClick(View v)
            {
                AbreFormulario();
            }
        });
    }

    //Método que abrirá o formulário
    void AbreFormulario()
    {
        //Inicia o script da tela de formulário
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }


    //Método que ocorre quando o usuário retornar a tela
    @Override
    protected void onResume() {
        super.onResume();
        //Atualiza o adaptador de lista
        AtualizaAdapter();
    }

    //Método que atualizará o adaptador de lista
    private void AtualizaAdapter() {
        //Limpa a lista do adaptador e logo depois a preenche com os dados salvos em persistência
        adapter.clear();
        adapter.addAll(dao.Todos());
    }

    //Método que abrirá uma opção ao clicar e segurar em um item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Adiciona uma opção na tela, que servirá para poder remover um item da lista
        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu,menu);
    }

    //Método que ocorre ao pressionar a opção criada no método anterior
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        //Obtem o índice do item selecionado
        int itemId = item.getItemId();
        //Abre uma tela de confirmação de remoção
        MenuDeRemocao(item, itemId);
        return super.onContextItemSelected(item);
    }

    private void MenuDeRemocao(@NonNull MenuItem item, int itemId) {
        //Executa método se o índice do objeto do menu for encontrado
        if(itemId == R.id.activity_lista_personagem_menu_remover)
        {
            //Cria um painel que irá perguntar ao usuário se ele quer remover o personagem
            //Adiciona nesse painel duas opções, uma positiva e outra negativa (sim ou não)
            new AlertDialog.Builder(this).setTitle("Remover Personagem")
                    //Definindo a mensagem que aparecerá no topo do painel
                    .setMessage("Tem certeza que deseja remover o item?")
                    //Definindo o a mensagem no botão positivo e o que acontecerá ao clicá-lo
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        //Executa método ao clicar no botão positivo
                        public void onClick(DialogInterface dialog, int which) {
                            //Obtém informações do item escolhido para remoção
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            //Define uma variável de personagem de acordo com o item relativo ao que foi obtido na linha anterior
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            //Remove o personagem escolhido
                            Remover(personagemEscolhido);
                        }
                    })
                    //Define o texto da opção negativa, nada ocorre ao pressioná-la (somente fecha a janela)
                    .setNegativeButton("Não", null).show();
        }
    }

    //Método que irá configurar o ListView presente na tela
    private void ConfiguraLista() {
        //Atribui um list view de acordo com o list view presente no layout
        ListView listaDePersonagens = findViewById(R.id.lista);
        //Preenche o listview com a lista de personagens salvos
        ListaPersonagens(listaDePersonagens);
        //Configura um item presente na lista ao selecioná-lo
        ConfiguraItemAoClicar(listaDePersonagens);
        //Torna possível clicar em um item e apresentar uma opção de remoção
        registerForContextMenu(listaDePersonagens);
    }

    //Método que configura um item ao pressioná-lo
    private void ConfiguraItemAoClicar(ListView listaDePersonagens) {
        //Definirá o que acontecerá ao clicar no item
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Método que habilitará a edição de um item
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {

                //Obtém o item no adaptador referente a posição do item escolhido
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                //Define que esse item será o item editado no formulário
                EditarFormulario(personagemEscolhido);
                //Remove o item atual da lista para manter o espaço ao item editado
                Remover(personagemEscolhido);
            }
        });
    }

    //Método que permitirá a edição no formulário
    void EditarFormulario(Personagem personagemEscolhido)
    {
        //Cria um transporte para o item selecionário
        Intent paraFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        //Atribui o item selecionado ao transporte
        paraFormulario.putExtra(ConstantActivities.chave_personagem, personagemEscolhido);
        //Inicia a tela de formulário
        startActivity(paraFormulario);
    }


    //Método que criará o adaptador de lista conforme o list view da tela
    private void ListaPersonagens(ListView listaDePersonagens)
    {
        //Define a variável adapter conforme os itens na lista presente da tela
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //Define o adaptador da lista dessa tela conforme o adaptador criado anteriormente
        listaDePersonagens.setAdapter(adapter);
    }

    //Método que irá remover um item tanto do adaptador quanto da persistência
    void Remover(Personagem personagem)
    {
        //Remove item do adaptador
        adapter.remove(personagem);
        //Remove item da persistência
        dao.Remover(personagem);
    }
}
