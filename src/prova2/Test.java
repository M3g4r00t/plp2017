/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova2;

import java.util.ArrayList;
import java.util.List;
 
class Coordenada {

    int x, y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Arquivo {

    private String nome, localizacao;
    private int tamanho;
    private List<Anotacao> anotacoes;

    public Arquivo(String nome, String localizacao, int tamanho) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        anotacoes = new ArrayList<>();
    }

    class Anotacao {

        String titulo, data;
        Arquivo arquivo;

        private Anotacao(Arquivo arquivo, String titulo, String data) {
            this.arquivo = arquivo;
            this.titulo = titulo;
            this.data = data;
        }
    }

    class AnotacaoVisual extends Anotacao {

        List<Coordenada> coordenadas;

        private AnotacaoVisual(Arquivo arquivo, String titulo, String data, List<Coordenada> coordenadas) {
            super(arquivo, titulo, data);
            this.coordenadas = coordenadas;
        }
    }

    class AnotacaoTextual extends Anotacao {

        String texto;

        private AnotacaoTextual(Arquivo arquivo, String titulo, String data, String texto) {
            super(arquivo, titulo, data);
            this.texto = texto;
        }
    }

    public void crearAnotacaoVisual(String titulo, String data, List<Coordenada> coordenadas) {
        Anotacao a = new AnotacaoVisual(this, titulo, data, coordenadas);
        this.getAnotacoes().add(a);
    }

    public void crearAnotacaoTextual(String titulo, String data, String texto) {
        Anotacao a = new AnotacaoTextual(this, titulo, data, texto);
        this.getAnotacoes().add(a);
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the localizacao
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * @param localizacao the localizacao to set
     */
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * @return the anotacoes
     */
    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }
}

public class Test {

    public static void main(String[] args) {
        Arquivo a = new Arquivo("titulo", "data", 100);
        a.crearAnotacaoTextual("titulo1", "data1", "texto1");
        a.crearAnotacaoTextual("titulo2", "data2", "texto2");
        List<Coordenada> coordenadas = new ArrayList<>();
        coordenadas.add(new Coordenada(4, 4));
        coordenadas.add(new Coordenada(4, 10));
        a.crearAnotacaoVisual("titulo3", "data3", coordenadas);
        System.out.println(a.getAnotacoes());
        Arquivo.Anotacao x;
    }
}
