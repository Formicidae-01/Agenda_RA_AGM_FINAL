package com.example.agenda.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {

    //Classe de personagens

    //Dados do personagem, nome altura, nascimento, telefone, endereço, cep, rg e gênero
    private String nome;
    private String altura;
    private String nascimento;
    private String telefone;
    private String endereco;
    private String cep;
    private String rg;
    //Gêneros 0 = Masculino, 1 = Feminino, 2 = Indefinido
    private int genero;
    //Índice do personagem
    int id = 0;

    //Método construtor do personagem
    public Personagem(String nome, String altura, String nascimento, String telefone, String endereco, String cep, String rg)
    {
        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cep = cep;
        this.rg = rg;
    }

    //Método construtor vazio do personagem, gerará um personagem vazio
    public Personagem()
    {}

    //Encapsulamento do nome, obtendo e definindo o nome do personagem
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //Encapsulamento da altura do personagem
    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    //Encapsulamento da data de nascimento do personagem
    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    //Encapsulamento do telefone
    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    //Encapsulamento do endereço
    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    //Encapsulamento do CEP
    public String getCep() { return cep; }

    public void setCep(String cep) { this.cep = cep; }

    //Encapsulamento do RG
    public String getRg() { return rg; }

    public void setRg(String rg) { this.rg = rg; }

    //Encapsulamento do Gênero
    public int getGenero() {return genero;}

    public void setGenero(int genero) {this.genero = genero;}

    //Encapsulamento do índice do personagem
    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {return id;}

    //Cria um override para o método ToString, que retorna somente o nome do personagem
    @NonNull
    @Override
    public String toString()
    {
        return nome;
    }

    //Método que  verifica se o índice do personagem é válido, retorna um valor booleano
    public boolean IdValido()
    { return id > 0;}

}
