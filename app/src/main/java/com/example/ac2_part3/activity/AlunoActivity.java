package com.example.ac2_part3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ac2_part3.R;
import com.example.ac2_part3.api.AlunoService;
import com.example.ac2_part3.api.ApiClient;
import com.example.ac2_part3.model.Aluno;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoActivity extends AppCompatActivity {

    private EditText raEditText, nomeEditText, cepEditText, logradouroEditText,
            complementoEditText, bairroEditText, cidadeEditText, ufEditText;
    private Button cadastrarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        raEditText = findViewById(R.id.ra_edit_text);
        nomeEditText = findViewById(R.id.nome_edit_text);
        cepEditText = findViewById(R.id.cep_edit_text);
        logradouroEditText = findViewById(R.id.logradouro_edit_text);
        complementoEditText = findViewById(R.id.complemento_edit_text);
        bairroEditText = findViewById(R.id.bairro_edit_text);
        cidadeEditText = findViewById(R.id.cidade_edit_text);
        ufEditText = findViewById(R.id.uf_edit_text);
        cadastrarButton = findViewById(R.id.cadastrar_button);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAluno();
            }
        });

        cepEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cep = cepEditText.getText().toString().trim();
                    if (!cep.isEmpty()) {
                        obterDadosEndereco(cep);
                    }
                }
            }
        });
    }

    private void cadastrarAluno() {
        int ra = Integer.parseInt(raEditText.getText().toString());
        String nome = nomeEditText.getText().toString();
        String cep = cepEditText.getText().toString();
        String logradouro = logradouroEditText.getText().toString();
        String complemento = complementoEditText.getText().toString();
        String bairro = bairroEditText.getText().toString();
        String cidade = cidadeEditText.getText().toString();
        String uf = ufEditText.getText().toString();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        AlunoService alunoService = ApiClient.getAlunoService();
        Call<Aluno> call = alunoService.postAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AlunoActivity.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(AlunoActivity.this, "Erro ao cadastrar aluno: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AlunoActivity.this, "Erro ao cadastrar aluno", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(AlunoActivity.this, "Falha na comunicação com o servidor: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void obterDadosEndereco(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        new Thread(() -> {
            try {
                java.net.URL viacepUrl = new java.net.URL(url);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) viacepUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();

                java.io.InputStream inputStream = connection.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                org.json.JSONObject jsonObject = new org.json.JSONObject(response.toString());
                String logradouro = jsonObject.getString("logradouro");
                String complemento = jsonObject.optString("complemento", "");
                String bairro = jsonObject.getString("bairro");
                String cidade = jsonObject.getString("localidade");
                String uf = jsonObject.getString("uf");

                runOnUiThread(() -> {
                    logradouroEditText.setText(logradouro);
                    complementoEditText.setText(complemento);
                    bairroEditText.setText(bairro);
                    cidadeEditText.setText(cidade);
                    ufEditText.setText(uf);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void limparCampos() {
        raEditText.setText("");
        nomeEditText.setText("");
        cepEditText.setText("");
        logradouroEditText.setText("");
        complementoEditText.setText("");
        bairroEditText.setText("");
        cidadeEditText.setText("");
        ufEditText.setText("");
    }
}
