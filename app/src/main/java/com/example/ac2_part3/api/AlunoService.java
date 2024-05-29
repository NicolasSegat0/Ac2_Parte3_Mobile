package com.example.ac2_part3.api;

import com.example.ac2_part3.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlunoService {
    @GET("aluno")
    Call<List<Aluno>> getAluno();

    @POST("aluno")
    Call<Aluno> postAluno(@Body Aluno aluno);

}
