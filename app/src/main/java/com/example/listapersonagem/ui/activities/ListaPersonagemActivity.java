package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagens";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    /*Criação do override para a lista de Personagem */
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        //Setando o Titulo do app
        setTitle(TITULO_APPBAR);
        configuraFabNovoPersonagem();
        configuraLista();
    }

    private void configuraFabNovoPersonagem() {
        //pegando o botão
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormulario();
            }
        });
    }

    private void abreFormulario() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    //proteção aos dados, impede deles serem apagados ao dar back na pagina
    @Override
    protected  void onResume(){
        super.onResume();
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Aparece o botão de Remover
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Remover");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // remove o personagem selecionado
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
        adapter.remove(personagemEscolhido);
        return super.onContextItemSelected(item);
    }

    private void configuraLista() {
        /*Vai Adaptar a Lista de Personagem */
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        //set de personagens da nossa lista
        listaDePersonagens(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //seleção de personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                //entrando no formulario
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    private void abreFormularioEditar(Personagem personagemEscolhido) {
        //Coleta a informação para a abrir a pagina do personagem escolhido para editar
        Intent vaiParaoFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaoFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaoFormulario);
    }

    private void listaDePersonagens(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}