package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagens";
    public static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Formulário de Personagens";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoTelefone;
    private EditText campoCep;
    private EditText campoRg;
    private EditText campoGenero;
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //passa as info
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //ao selecionar finaliza o formulario
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_personagem_menu_salvar) {
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

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
        if (personagem.IdValido()) {
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
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    // preenche os campos da agenda
    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        campoTelefone.setText(personagem.getTelefone());
        campoCep.setText(personagem.getCep());
        campoRg.setText(personagem.getRg());
        campoGenero.setText(personagem.getGenero());
    }


    private void inicializacaoCampos() {
        //Identificação dos campos
        campoNome = findViewById(R.id.editTextNome);
        campoNascimento = findViewById(R.id.editTextDataDeNascimento);
        campoAltura = findViewById(R.id.editTextAltura);
        campoTelefone = findViewById(R.id.editTextTelefone);
        campoCep = findViewById(R.id.editTextTextCep);
        campoRg = findViewById(R.id.editTextTextRg);
        campoGenero = findViewById(R.id.editTextTextGenero);
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter(("N, NN"));
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        //coloca uma mascara para ajeitar os numeros de acordo com uma data de nascimento
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter(("NN/NN/NNNN"));
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);

        //coloca uma mascara para ajeitar os numeros de acordo com um telefone
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter(("(NN)NNNNN-NNNN"));
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoTelefone, smfTelefone);
        campoTelefone.addTextChangedListener(mtwTelefone);

        //coloca uma mascara para ajeitar os numeros de acordo com um Cep
        SimpleMaskFormatter smfCep = new SimpleMaskFormatter(("NNNNN-NNN"));
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoCep, smfCep);
        campoCep.addTextChangedListener(mtwCep);

        //coloca uma mascara para ajeitar os numeros de acordo com um Rg
        SimpleMaskFormatter smfRg = new SimpleMaskFormatter(("NN.NNN.NNN-N"));
        MaskTextWatcher mtwRg = new MaskTextWatcher(campoRg, smfRg);
        campoRg.addTextChangedListener(mtwRg);

    }

    private void preencherPersonagem() {
        //Busca as informações de nome, nascimento e altura, assim os convertendo em string
        String nome = campoNome.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String cep = campoCep.getText().toString();
        String rg = campoRg.getText().toString();
        String genero = campoGenero.getText().toString();

        //set e editar o personagem
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setCep(cep);
        personagem.setRg(rg);
        personagem.setGenero(genero);
    }

}