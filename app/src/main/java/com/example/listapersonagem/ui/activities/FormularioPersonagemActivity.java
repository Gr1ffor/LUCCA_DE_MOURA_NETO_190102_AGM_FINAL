package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagens";
    public static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Formulário de Personagens";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        configuraBotaoSalvar();
        carregaPersonagem();
    }

    private void configuraBotaoSalvar() {
        /* criou uma variavel e vinculou o botão do formulario*/
        Button botaoEnviar = findViewById(R.id.button_enviar);
        /*se clicar o sistema ouve*/
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarFormulario();
            }
        });
    }

    private void finalizarFormulario() {
        preencherPersonagem();
        if(personagem.IdValido()){
            //edita as info
            dao.editar(personagem);
            //finaliza
            finish();
        } else {
            //salva o personagem no banco
            dao.salvo(personagem);
        }
        //finaliza
        finish();
    }

    private void carregaPersonagem() {
        //pega da listagem e joga para mim
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            //Altera o Titulo do Cabeçario do app
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else{
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }




    private void inicializacaoCampos() {
        //Identificação dos campos
        campoNome = findViewById(R.id.editTextTextPersonName);
        campoNascimento = findViewById(R.id.editTextDataDeNascimento);
        campoAltura = findViewById(R.id.editTextAltura);
    }

    private void preencherPersonagem(){
        //Busca as informações de nome, nascimento e altura, assim os convertendo em string
        String nome = campoNome.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();

        //set e editar o personagem
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
    }

}