package com.example.agenda.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.dao.PersonagemDAO;
import com.example.agenda.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.github.rtoshiro.util.format.text.SimpleMaskTextWatcher;

public class FormularioPersonagemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    //Criando componentes de texto a serem editados (correspondem a nome, altura e data de nascimento)
    EditText campoNome;
    EditText campoAltura;
    EditText campoNascimento;
    EditText campoTelefone;
    EditText campoEndereco;
    EditText campoCep;
    EditText campoRg;
    //Criando componente spinner para a escolha de gênero
    Spinner generoSpinner;

    //Cria um objeto da classe "DAO", que faz a persistência dos dados
    final PersonagemDAO dao = new PersonagemDAO();
    //Cria um objeto da classe personagem, que contém as informações definidas nos textos das linhas anteriores.
    Personagem personagem;

    //Adaptador para as opções de gênero
    ArrayAdapter<CharSequence> genderAdapter;


    //Método que cria pequeno botão no topo da tela ao entrar na tela de formulário
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Obtém a barra superior do menu e gera um ícone nela
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        //Retornando o item criado
        return super.onCreateOptionsMenu(menu);
    }

    //Executa função ao pressionar o botão gerado no método anterior
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //Obtém o índice do item pressionado
        int itemId = item.getItemId();
        //Executa método caso o índice obtido seja correspondente ao objeto de salvamento
        if (itemId == R.id.activity_menu_formulario_personagem_menu_salvar);
        {
            //Finaliza o formulário, salvando o objeto criado e retornando à tela anterior
            FinalizarFormulario();
        }
        //Retornando se o botão foi pressionado
        return super.onOptionsItemSelected(item);
    }

    @Override
    //Método executado ao início do script
    protected void onCreate(Bundle savedInstanceState) {
        //Executa método que iniciará a tela escolhida
        super.onCreate(savedInstanceState);
        //Ativando o .xml da tela de formulário, que carregará os itens na tela
        setContentView(R.layout.activity_formulario_personagem);

        //Método que administra os textos editáveis, preenchendo-os
        IniciaCampos();
        //Executa método que verificará o botão de salvamento(atualmente ocultado), verificando se foi pressionado e o que fazer após isso
        AdministraBotao();
        //Obtém os dados dos itens salvos, para verificar se o item atual já existe
        ObtemDados();
    }

    private void ObtemDados() {
        //Obtém intent onde os dados dos itens estarão salvos
        Intent dados = getIntent();
        //Executa função caso o item selecionado já existe nos dados guardados
        if (dados.hasExtra(ConstantActivities.chave_personagem))
        {
            //Define o título para a tela de edição
            setTitle(ConstantActivities.tituloEdicao);
            //Define os atributos do personagem atual como os dados de um objeto salvo
            personagem = (Personagem) dados.getSerializableExtra(ConstantActivities.chave_personagem);
            //Preenche os campos com as informações obtidas acima
            PreencheCampos();
        }
        //Executa função caso o objeto atual não exista
        else
        {
            //Define título para tela de criação de formulário
            setTitle(ConstantActivities.tituloTelaFormulario);
            //Define o personagem atual como um novo personagem
            personagem = new Personagem();
        }
    }

    //Método que preenche os campos de texto como as variáveis de um objeto atual
    private void PreencheCampos() {
        //Preenche o texto "nome" com a variável nome do personagem atual
        campoNome.setText(personagem.getNome());
        //Preenche o texto "altura" com a variável altura do personagem atual
        campoAltura.setText(personagem.getAltura());
        //Preenche o texto "nascimento" com a data de nascimento do personagem atual
        campoNascimento.setText(personagem.getNascimento());
        //Preenche o texto "telefone" com o telefone do personagem atual
        campoTelefone.setText(personagem.getTelefone());
        //Preenche o texto "endereço" com o endereço do personagem atual
        campoEndereco.setText(personagem.getEndereco());
        //Preenche o texto "CEP" com o CEP do personagem atual
        campoCep.setText(personagem.getCep());
        //Preenche o texto "RG" com o RG do personagem atual
        campoRg.setText(personagem.getRg());
        //Seleciona o spinner de gênero
        generoSpinner.setSelection(personagem.getGenero());
    }

    //Método que administra o botão de salvamento(atualmente oculto)
    private void AdministraBotao() {
        //Criando uma variável "Button" e a atribui com o botão existente no layout da tela de formulário
        Button botaoSalvar = findViewById(R.id.button_Salvar);
        //Método que vai definir quem receberá o clique do botão
        botaoSalvar.setOnClickListener(new View.OnClickListener()
        {
            //Define o que acontecerá quando o botão for pressionado
            @Override
            public void onClick(View view)
            {
                //Finaliza o formulário, salvando o que foi criado
                FinalizarFormulario();
            }
        }
        );
    }

    //Método que iniciará os componentes presentes na tela, atribuindo-os
    private void IniciaCampos() {
        //Atribui os "EditText" presentes na tela através dos índices presentes nos objetos
        campoNome = findViewById(R.id.editText_Nome);
        campoAltura = findViewById(R.id.editText_Altura);
        campoNascimento = findViewById(R.id.editText_DataNascimento);
        campoTelefone = findViewById(R.id.editText_Telefone);
        campoEndereco = findViewById(R.id.editText_Endereco);
        campoCep = findViewById(R.id.editText_CEP);
        campoRg = findViewById(R.id.editText_RG);
        //Obtendo o spinner do gênero
        generoSpinner = findViewById(R.id.dropDown_genero);
        FormataTexto();
        //Método que inicializa o spinner de gênero
        InicializaGenero();
    }

    private void FormataTexto() {
        //Cria uma formatação para o texto de altura, mantendo o texto num formato específico
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        //Cria uma segunda formatação com base na variável criada na linha anterior e o campo de texto "altura"
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        //Adiciona a formatação ao campo de texto
        campoAltura.addTextChangedListener(mtwAltura);

        //Cria uma formatação para o texto de data de nascimento
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        //Cria uma segunda formatação com base na variável da linha anterior
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento,smfNascimento);
        //Adiciona a formatação ao campo de texto
        campoNascimento.addTextChangedListener(mtwNascimento);

        //Cria uma formatação para o texto de telefone e a aplica para o campo de telefone
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoTelefone, smfTelefone);
        campoTelefone.addTextChangedListener(mtwTelefone);

        //Cria uma formatação para o texto de CEP e a aplica para o campo de CEP
        SimpleMaskFormatter sfmCep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoCep, sfmCep);
        campoCep.addTextChangedListener(mtwCep);

        //Cria uma formatação para o texto de RG e a aplica para o campo de RG
        SimpleMaskFormatter smfRg = new SimpleMaskFormatter("NN.NNN.NNN-N");
        MaskTextWatcher mtwRg = new MaskTextWatcher(campoRg, smfRg);
        campoRg.addTextChangedListener(mtwRg);
    }

    void InicializaGenero()
    {
        //Iniciando o campo de gênero
        //Atribuindo a lista de gêneros
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_dropdown_item);
        //Definindo o layout para a lista de gêneros
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Definindo a lista contida no spinner como a gerada na linha anterior
        generoSpinner.setAdapter(genderAdapter);
        //Definindo quais itens aparecerão ao clicar no spinner
        generoSpinner.setOnItemSelectedListener(this);
    }

    //Método que preencherá as variáveis personagem atual com base no que será digitado nos campos de texto
    void PreencherPersonagem()
    {
        //Obtém o texto do campo de texto "nome"
        String nome = campoNome.getText().toString();
        //Obtém o texto do campo de texto "altura"
        String altura = campoAltura.getText().toString();
        //Obtém o texto do campo de texto "nascimento"
        String nascimento = campoNascimento.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String cep = campoCep.getText().toString();
        String rg = campoRg.getText().toString();
        int genero = generoSpinner.getSelectedItemPosition();

        //Adicionando os textos obtidos nas linhas anteriores ao personagem atual
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setCep(cep);
        personagem.setRg(rg);
        personagem.setGenero(genero);
    }


    //Método que finalizará o preenchimento do formulário
    void FinalizarFormulario()
    {
        //Preenche o personagem com os dados escritos nos campos de texto
        PreencherPersonagem();
        //Executa função caso o índice de personagem já exista
        if (personagem.IdValido())
        {
            //Método que altera um item existente na persistência
            dao.Editar(personagem);
            //Finaliza a edição do formulário
            finish();
        }

        //Executa método caso o objeto ainda não exista na persistência
        else {
            //Salva o objeto gerado na persistência
            dao.Salvar(personagem);
        }
        //Finaliza a edição do formulário
        finish();
    }

    //Métodos que administram oque ocorrerá ao utilizar o spinner de gênero (Se forem apagados, ocorrem erros)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}