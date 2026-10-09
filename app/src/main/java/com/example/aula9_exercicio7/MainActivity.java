package com.example.aula9_exercicio7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText nomeUsuario;
    private EditText distanciaPercorrida;
    private Button registrar;
    private Button limpar;
    private TextView resultado;
    private String nome;
    private float distanciaTotal;
    private SharedPreferences preferencias; //declarando um objeto para fazer persistência de dados em memória
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        nomeUsuario = findViewById(R.id.editTextNome);
        distanciaPercorrida = findViewById(R.id.editTextDistancia);
        registrar = findViewById(R.id.buttonRegistrar);
        limpar = findViewById(R.id.buttonLimpar);
        resultado = findViewById(R.id.textViewResultado);

        preferencias = getSharedPreferences("corridas.txt", MODE_PRIVATE); //ALOCANDO ESPAÇO DE MEMÓRIA PRIVADO
        nome = preferencias.getString("chaveNome", ""); //criando uma chave
        distanciaTotal = preferencias.getFloat("chaveDistancia", 0); //criando outra chave


        nomeUsuario.setText(nome);
        if(distanciaTotal > 0){
            resultado.setText("Você já percorreu " + distanciaTotal + " KMs");
        }else{
            resultado.setText("Nenhuma corrida registrada!");
        }


        registrar.setOnClickListener(new View.OnClickListener() {//exemplo com interface funcional e classe anônima
            @Override
            public void onClick(View view) {
                registrarCorrida();
            }
        });

        limpar.setOnClickListener(view -> {limparDados();}); //exemplo com lambda
    }

    private void limparDados() {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear(); //limpa todas as chaves
        //editor.remove("chaveCorrida");//limpa só uma chave
        editor.apply();
        nomeUsuario.setText("");
        resultado.setText("Nenhuma corrida registrada!");
    }

    private void registrarCorrida() {
        if(nomeUsuario.getText().toString().isBlank() || distanciaPercorrida.getText().toString().isBlank()){
            Toast.makeText(this, "Preencha nome e distância!", Toast.LENGTH_LONG).show();
            return;
        }
        nome = nomeUsuario.getText().toString();
        float distanciaParcial = Float.parseFloat(distanciaPercorrida.getText().toString());
        distanciaTotal += distanciaParcial;
        resultado.setText("Parabéns " + nome + ", \nvocê percorreu um total de " + distanciaTotal + " KMs");
        distanciaPercorrida.setText("");//limpando o EditText de distância

        SharedPreferences.Editor editor = preferencias.edit(); //criando um editor pras minhas preferências
        editor.putString("chaveNome", nome); //alterando o valor do nome do usuário pela sua chave
        editor.putFloat("chaveDistancia", distanciaTotal); //alterando o valor da distância total pela sua chave
        editor.apply(); //submetendo as alterações

    }
}