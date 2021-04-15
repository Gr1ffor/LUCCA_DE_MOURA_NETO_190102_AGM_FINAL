package com.example.listapersonagem.dao;

import com.example.listapersonagem.model.Personagem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {

    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;

    public void salvo(Personagem personagemSalvo) {
        //adiciona um id para cada personagem salvo
        personagemSalvo.setId(contadorDeId);
        //adiciona o personagem
        personagens.add(personagemSalvo);
        //adiciona mais 1 id
        atualizaId();
    }

    private void atualizaId() {
        contadorDeId++;
    }

    public void editar(Personagem personagem){
        //pega as info do id selecionado
        Personagem personagemEscolhido = buscaPersonagemId(personagem);
        if(personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    private Personagem buscaPersonagemId(Personagem personagem) {
        //passar pela listagem
        for (Personagem p: personagens){
            if(p.getId() == personagem.getId()){
                //armazena informação
                return p;
            }
        }
        return null;
    }

    public List<Personagem> todos(){

        return  new ArrayList<>(personagens);

    }

}
