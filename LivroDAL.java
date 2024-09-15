
package com.mycompany.desafio;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author SONY VAIO
 */
public class LivroDAL {
    
    public static Firestore db;
    
    public static void conectarFirebase(){
        if (FirebaseApp.getApps().isEmpty()) {
        try{
        FileInputStream sa = new FileInputStream("desafiojavabeans.json");
        
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(sa))
            .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
        System.out.println("CONECTADO");
        }
        catch (IOException e)
        {
            Erro.setErro(e.getMessage());
        }
        } else {
            db = FirestoreClient.getFirestore();
            System.out.println("Firebase já estava inicializado.");
        }
    }
    
    public static void inserirLivro(Livro livro){
        
        Erro.setErro(false);
        try {
            CollectionReference livros = db.collection("TabLivros");

            Map<String, Object> livroData = new HashMap<>();
            livroData.put("titulo", livro.getTitulo());
            livroData.put("autor", livro.getAutor());
            livroData.put("editora", livro.getEditora());
            livroData.put("ano", livro.getAnoEdicao());
            livroData.put("localizacao", livro.getLocalizacao());

            ApiFuture<DocumentReference> future = livros.add(livroData);
            DocumentReference result = future.get();

            System.out.println("Livro inserido com sucesso " + result.getId());
        } catch (InterruptedException | ExecutionException e) {
            Erro.setErro(e.getMessage());
        }
    } 
    
    public static Livro consultarLivro(String titulo) {
        Erro.setErro(false);
        Livro livro = null;
        try {
            CollectionReference livros = db.collection("TabLivros");

            ApiFuture<QuerySnapshot> future = livros.whereEqualTo("titulo", titulo).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            if (!documents.isEmpty()) {
                DocumentSnapshot document = documents.get(0);
                livro = new Livro();
                livro.setTitulo(document.getString("titulo"));
                livro.setAutor(document.getString("autor"));
                livro.setEditora(document.getString("editora"));
                livro.setAnoEdicao(document.getString("ano"));
                livro.setLocalizacao(document.getString("localizacao"));
            } else {
                System.out.println("Nenhum livro encontrado com o título: " + titulo);
            }
        } catch (InterruptedException | ExecutionException e) {
            Erro.setErro(e.getMessage());
        }
        return livro;
        }
    
    public static void excluirLivro(String titulo) {
        Erro.setErro(false);
        try {
            CollectionReference livros = db.collection("TabLivros");

            ApiFuture<QuerySnapshot> future = livros.whereEqualTo("titulo", titulo).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            if (!documents.isEmpty()) {
                for (QueryDocumentSnapshot document : documents) {
                    DocumentReference docRef = document.getReference();
                    ApiFuture<WriteResult> deleteFuture = docRef.delete();
                    deleteFuture.get(); 
                }
                System.out.println("Livro excluído com sucesso.");
            } else {
                System.out.println("Nenhum livro encontrado com o título: " + titulo);
            }
        } catch (InterruptedException | ExecutionException e) {
            Erro.setErro(e.getMessage());
            System.err.println("Erro ao excluir o livro: " + e.getMessage());
        }
    }
    
    public static void alterarLivro(String tituloAntigo, Livro livroNovo) {
        Erro.setErro(false);
        try {
            CollectionReference livros = db.collection("TabLivros");

            ApiFuture<QuerySnapshot> future = livros.whereEqualTo("titulo", tituloAntigo).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            if (!documents.isEmpty()) {
                for (QueryDocumentSnapshot document : documents) {
                    DocumentReference docRef = document.getReference();
                    ApiFuture<WriteResult> updateFuture = docRef.update(
                        "titulo", livroNovo.getTitulo(),
                        "autor", livroNovo.getAutor(),
                        "editora", livroNovo.getEditora(),
                        "ano", livroNovo.getAnoEdicao(),
                        "localizacao", livroNovo.getLocalizacao()
                    );
                    updateFuture.get();
                }
                System.out.println("Livro(s) atualizado(s) com sucesso.");
            } else {
                System.out.println("Nenhum livro encontrado com o título: " + tituloAntigo);
            }
        } catch (InterruptedException | ExecutionException e) {
            Erro.setErro(e.getMessage());
            System.err.println("Erro ao atualizar o livro: " + e.getMessage());
        }
    }
}
   
